package upandgo.server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import com.google.common.collect.Collections2;		 
import com.google.common.collect.Lists;
import com.allen_sauer.gwt.log.client.Log;
import com.google.common.base.Predicate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.TreeMap;
import java.util.TreeSet;

import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.CoursesEntity;
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

	protected Semester semester = Semester.WINTER16;
	protected TreeMap<String, Course> coursesById;
	protected TreeMap<String, Course> coursesByName;
	//protected List<Course> pickedCourseList;
	protected CourseLoader loader;
	protected List<Faculty> facultyList;

	public CourseModel(final CourseLoader loader, Semester semester) {
		//pickedCourseList = new ArrayList<>();
		this.loader = loader;
		this.semester = semester;
		coursesById = loader.loadAllCoursesById();
		coursesByName = loader.loadAllCoursesByName();
		facultyList = loader.loadFaculties();
/*		for (final String id : this.loader.loadChosenCourseNames()){
			if(coursesById.get(id) != null)
				pickedCourseList.add(coursesById.get(id));
		}*/
	}

/*	public void pickCourse(final String id) {
		if (id == null)
			throw new NullPointerException();
		final Course pickedCourse = getCourseById(id);
		Log.info("current picked list: " + pickedCourseList);
		if (pickedCourseList.contains(pickedCourse)){
			Log.info("1/already have: " + id);
			Log.info("2/already have: " + pickedCourse);
			return;
		}

		// save picking in DB
		//final TreeSet<CourseId> pickedList = new TreeSet<>();
		pickedCourseList.add(pickedCourse);
		
//		Iterator<Course> it = pickedCourseList.iterator();
//		while(it.hasNext()) {
//			Course λ = it.next();
//			pickedList.add(new CourseId(λ.getId(), λ.getName(),
//					λ.getaTerm(), λ.getbTerm()));
//		}
		
	}*/

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

/*	public void dropCourse(final String id) {
		if (id == null)
			throw new NullPointerException();
		final Course droppedCourse = getCourseById(id);
		if (!pickedCourseList.contains(droppedCourse))
			return;

		// save picking in DB
		//final TreeSet<CourseId> pickedList = new TreeSet<>();
		pickedCourseList.remove(droppedCourse);
		
//		Iterator<Course> it = pickedCourseList.iterator();
//		while(it.hasNext()) {
//			Course λ = it.next();
//			pickedList.add(new CourseId(λ.getId(), λ.getName(),
//					λ.getaTerm(), λ.getbTerm()));
//		}
		
	}*/

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
	
/*	public List<LessonGroup> getCourseLectures(final String id) {
		if (id == null)
			throw new NullPointerException();
		return coursesById.get(id).getLectures();
	}*/

/*	public List<String> getChosenCourseNames() {
		final List<String> $ = new ArrayList<>();
		Iterator<Course> it = pickedCourseList.iterator();
		while(it.hasNext())
			$.add(it.next().getId());
		
		return $;
	}*/

/*	@Deprecated
	public List<CourseId> loadChosenCourses() {
		// save picking in DB
		final HashSet<CourseId> pickedList = new HashSet<>();
		Iterator<Course> it = pickedCourseList.iterator();
		while(it.hasNext()) {
			Course λ = it.next();
			pickedList.add(new CourseId(λ.getId(), λ.getName(),
					λ.getaTerm(), λ.getbTerm()));
		}
		
		return new ArrayList<>(pickedList);
	}*/
	
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
		//loader.saveChosenCourses(courseEntity);
	}
	
	public void removeChosenCourse(final String courseID) {
		CoursesEntity coursesEntity = loader.loadChosenCourses();
		if (coursesEntity == null){
			return;
		}
		coursesEntity.removeCourse(semester.getId(), courseID);
		loader.saveChosenCourses(coursesEntity);
		//loader.saveChosenCourses(courseEntity);
	}
	
	public void removeAllChosenCourse() {
		CoursesEntity coursesEntity = loader.loadChosenCourses();
		if (coursesEntity == null){
			return;
		}
		coursesEntity.removeAllCourses(semester.getId());
		loader.saveChosenCourses(coursesEntity);
		//loader.saveChosenCourses(courseEntity);
	}
	
	public List<String> loadChosenCourses() {
		CoursesEntity coursesEntity = loader.loadChosenCourses();
		if (coursesEntity == null){
			return new ArrayList<>();
		}
		return coursesEntity.getCourses(semester.getId());
		//return loader.loadChosenCourses();
	}
	
/*	public List<Course> getPickedCoursesList () {
		return pickedCourseList;
	}*/
	
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
		Iterator<Faculty> it = facultyList.iterator();
		while(it.hasNext()) {
			String facultyName = it.next().getName();
			if (!faculties.contains(facultyName))
				faculties.add(facultyName);
		}
		return faculties;
	}

/*	public List<Course> loadChosenCoursesDetails() {
		return pickedCourseList;
	}*/

	public void loadGilaionFrom(@SuppressWarnings("unused") final String path) {
		// TODO: implement it
	}

	public void loadCatalogFrom(@SuppressWarnings("unused") final String path) {
		// TODO: implement it
	}

/*	public void UnselectAllCourses() {
		pickedCourseList.clear();
		
	}*/
	
	public void saveChosenLessonGroups(final List<LessonGroup> xxx) {
		loader.saveChosenLessonGroups(xxx);
	}
	
	public List<LessonGroup> loadChosenLessonGroups() {
		return loader.loadChosenLessonGroups();
	}

/*	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}*/
	
	
}
