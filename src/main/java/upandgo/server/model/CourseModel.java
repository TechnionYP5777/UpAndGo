package upandgo.server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import java.util.TreeMap;

import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.CoursesEntity;
import upandgo.server.model.loader.ScheduleEntity;
import upandgo.shared.entities.Faculty;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.Semester;
import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;
import upandgo.shared.utils.FuzzySearch;

/**
 * 
 * @author Nikita Dizhur
 * @since 30-11-16
 * 
 * Class that is in charge of loading courses and choosing the ones that we want to schedule.
 * 
 */
public class CourseModel { // implements Model {

	protected Semester semester;
	protected TreeMap<String, Course> coursesById;
	protected TreeMap<String, Course> coursesByName;
	protected CourseLoader loader;
	protected List<Faculty> facultyList;

	public CourseModel(final CourseLoader loader, Semester semester) {
		this.loader = loader;
		this.semester = semester;
		coursesById = loader.loadAllCoursesById();
		coursesByName = loader.loadAllCoursesByName();
		facultyList = loader.loadFaculties();
	}

	public void addCourse(final String name) {
		if (name == null)
			throw new NullPointerException();
		final List<String> prevCourseList = getCoursesNames();
		coursesById.put(name, loader.loadCourse(name));

		final List<String> curCourseList = new ArrayList<>(prevCourseList);
		curCourseList.remove(name);
	}

	public List<String> getCoursesNames() {
		return new ArrayList<>(coursesById.keySet());
	}

	public Course getCourseByName(final String name) {
		if (name == null)
			throw new NullPointerException();
		return coursesByName.get(name);
	}

	public Course getCourseById(final String id) {
		if (id == null)
			throw new NullPointerException();
		return coursesById.get(id);
	}
	
	public CourseId getCourseId(String id) {
		Course course = coursesById.get(id);
		return new CourseId(course.getId(),course.getName(),course.getaTerm(),course.getbTerm());
	}

	public void saveChosenCourse(final String courseID) {
		CoursesEntity coursesEntity = loader.loadChosenCourses();
		if (coursesEntity == null){
			return;
		}
		coursesEntity.addCourse(semester.getId(), courseID);
		loader.saveChosenCourses(coursesEntity);
	}
	
	public void removeChosenCourse(final String courseID) {
		CoursesEntity coursesEntity = loader.loadChosenCourses();
		if (coursesEntity == null){
			return;
		}
		coursesEntity.removeCourse(semester.getId(), courseID);
		loader.saveChosenCourses(coursesEntity);
	}
	
	public void removeAllChosenCourse() {
		CoursesEntity coursesEntity = loader.loadChosenCourses();
		if (coursesEntity == null){
			return;
		}
		coursesEntity.removeAllCourses(semester.getId());
		loader.saveChosenCourses(coursesEntity);
	}
	
	public List<String> loadChosenCourses() {
		CoursesEntity coursesEntity = loader.loadChosenCourses();
		if (coursesEntity == null){
			return new ArrayList<>();
		}
		return coursesEntity.getCourses(semester.getId());
	}
	
	public List<CourseId> loadAllCourses(){
		List<CourseId> res = new ArrayList<>();
		for(Map.Entry<String, Course> entry : coursesById.entrySet()){
			Course c = entry.getValue();
			res.add(new CourseId(c.getId(), c.getName(),c.getaTerm(), c.getbTerm()));
		}
		return res;
		
	}
	
	private List<CourseId> getNotSelectedCoursesByFaculty(final String faculty){
		List<CourseId> res = new ArrayList<>();
		List<String> chosenCourses = loadChosenCourses();
		for(Map.Entry<String, Course> entry : coursesById.entrySet()){
			Course c = entry.getValue();
			if((faculty.isEmpty() && !chosenCourses.contains(c.getId()) )|| (c.getFaculty().equals(faculty)) && !chosenCourses.contains(c.getId()))
				res.add(new CourseId(c.getId(), c.getName(),
						c.getaTerm(), c.getbTerm()));
			
		}
		return res;
		
	}

	/*
	 * load needed courses from DB if empty, except those that are already chosen.
	 * the returned course are sorted by their fuzzy search score
	 */
	public List<CourseId> loadQueryByFaculty(final String query, final String faculty) {
		if(query.isEmpty())
			return getNotSelectedCoursesByFaculty(faculty);
		
		List<CourseId> relevantCourses = new ArrayList<>();
		relevantCourses.add(new CourseId());
		for(CourseId c : getNotSelectedCoursesByFaculty(faculty)){
			if(FuzzySearch.similarity(query, c.getTitle()) > 50)
				relevantCourses.add(c);
		}
		
		Collections.sort(relevantCourses, new Comparator<CourseId>() {
			@Override
			public int compare(CourseId o1, CourseId o2) {
				return FuzzySearch.similarity(query, o2.getTitle()) - FuzzySearch.similarity(query, o1.getTitle());
			}
			
		});

		return relevantCourses;
	}


	/*
	 * load faculty names
	 */
	public List<String> loadFacultyNames() {
		final List<String> faculties = new ArrayList<>();
		for (Faculty faculty : facultyList){
			if (!faculties.contains(faculty.getName()))
				faculties.add(faculty.getName());
		}
		return faculties;
	}


	public void loadGilaionFrom(@SuppressWarnings("unused") final String path) {
		// TODO: implement it
	}

	public void loadCatalogFrom(@SuppressWarnings("unused") final String path) {
		// TODO: implement it
	}
	
	public void saveChosenLessonGroups(final List<LessonGroup> lessonGroups) {
		ScheduleEntity scheduleEntity =  loader.loadChosenLessonGroups();
		scheduleEntity.removeAllLessons(semester.getId());
		
		for (LessonGroup lg : lessonGroups){
			scheduleEntity.addLesson(semester.getId(), lg.getCourseID(), lg.getGroupNum());
		}
		
		loader.saveChosenLessonGroups(scheduleEntity);
	}
	
	public List<LessonGroup> loadChosenLessonGroups() {
		ScheduleEntity scheduleEntity =  loader.loadChosenLessonGroups();
		List<LessonGroup> lgList = new ArrayList<>();
		if (scheduleEntity == null){
			return lgList;
		}
		
		for (ScheduleEntity.Lesson lesson : scheduleEntity.getLessons(semester.getId())){
			Course course = coursesById.get(lesson.getCourseId());
			if (course == null)
				continue;
			LessonGroup lessonGroup = course.getLessonGroup(lesson.getGroupNum());
			if (lessonGroup == null)
				continue;
			lgList.add(lessonGroup);
		}
		
		return lgList;
	}

	
}
