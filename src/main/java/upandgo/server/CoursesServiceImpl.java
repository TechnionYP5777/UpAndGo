package upandgo.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import upandgo.client.CoursesService;
import upandgo.server.model.CalendarModel;
import upandgo.server.model.CourseModel;
import upandgo.server.model.loader.CoursesEntity;
import upandgo.server.model.loader.ScheduleEntity;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.Semester;
import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;

/**
 * 
 * @author Nikita Dizhur
 * @since 05-05-17
 * 
 *        Remote Procedure Call Service server side implementation for
 *        retrieving information about courses in DB and selecting needed
 *        courses.
 * 
 */

public class CoursesServiceImpl extends RemoteServiceServlet implements CoursesService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1193922002939188572L;

	static {
		// register Objectify-classes
		ObjectifyService.register(ScheduleEntity.class);
		ObjectifyService.register(CoursesEntity.class);
	}
	
	private Semester defaultSemester = Semester.WINTER16;

	private Map<Semester,CourseModel> courseModels = new TreeMap<Semester,CourseModel>();
	private final CalendarModel calendarModel = new CalendarModel();

	public CoursesServiceImpl() {
		Log.warn("in course service constractor");
		initilalizeCourseModel(defaultSemester);
		// Log.info("entered c'tor of CourseServiceImple");
	}
	
	private void initilalizeCourseModel(Semester semester){
		XmlCourseLoader loader = new XmlCourseLoader(semester.getId()+".XML");
		CourseModel model = new CourseModel(loader,semester);
		courseModels.put(semester, model);
	}

	@Override
	public ArrayList<CourseId> getSelectedCourses(Semester semester) {
		if (!courseModels.containsKey(semester)){
			initilalizeCourseModel(semester);
		}
		List<String> selectedCourses = new ArrayList<>(courseModels.get(semester).loadChosenCourses());
		if (selectedCourses.isEmpty()){
			return new ArrayList<>();
		}
		ArrayList<CourseId> selectesCoursesIDs = new ArrayList<>();
		for (String courseId : selectedCourses){
			selectesCoursesIDs.add(courseModels.get(semester).getCourseId(courseId));
		}
		return selectesCoursesIDs;
	}
	
	@Override
	public ArrayList<CourseId> getAllCourses(Semester semester) {
		if (!courseModels.containsKey(semester)){
			initilalizeCourseModel(semester);
		}
		ArrayList<CourseId> res = (ArrayList<CourseId>) courseModels.get(semester).loadAllCourses();
		Log.warn("CourseServiceImple got: " + res.size() + " courses from loader");
		return res;
	}

	@Override
	public ArrayList<CourseId> getNotSelectedCourses(Semester semester, String query, String faculty) {
		if (!courseModels.containsKey(semester)){
			initilalizeCourseModel(semester);
		}
		ArrayList<CourseId> res = (ArrayList<CourseId>) courseModels.get(semester).loadQueryByFaculty(query, faculty);
/*		Log.warn("CourseServiceImple got: " + res.get(0).getTitle() + "*" + res.get(0).aTerm() + "*"
				+ res.get(0).bTerm());*/
		return res;
	}

	@Override
	public ArrayList<String> getFaculties(Semester semester) {
		if (!courseModels.containsKey(semester)){
			initilalizeCourseModel(semester);
		}
		return (ArrayList<String>) courseModels.get(semester).loadFacultyNames();
	}

	@Override
	public Course getCourseDetails(Semester semester, CourseId id) {
		if (!courseModels.containsKey(semester)){
			initilalizeCourseModel(semester);
		}
		return courseModels.get(semester).getCourseById(id.number());
	}

/*	@Override
	public List<LessonGroup> getCourseLectures(String id) {
		return model.getCourseLectures(id);
	}*/

	@Override
	public void selectCourse(Semester semester, CourseId id) {
		//Log.info("^&^&^&^&&^&&^&&^picked course: " + id.number());
		//someString = "here0";
		//model.pickCourse(id.number());
		if (!courseModels.containsKey(semester)){
			initilalizeCourseModel(semester);
		}
		courseModels.get(semester).saveChosenCourse(id.number());
/*		List<String> chosenCourses = new ArrayList<>(courseModels.get(semester).loadChosenCourses());
		chosenCourses.add(id.number());
		courseModels.get(semester).saveChosenCourses(chosenCourses);
*/		//model.saveChosenCourses(model.getChosenCourseNames());
	}

	@Override
	public void unselectCourse(Semester semester, CourseId id) {
		if (!courseModels.containsKey(semester)){
			initilalizeCourseModel(semester);
		}
		courseModels.get(semester).removeChosenCourse(id.number());

/*		List<String> chosenCourses = new ArrayList<>(courseModels.get(semester).loadChosenCourses());
		chosenCourses.remove(id.number());
		courseModels.get(semester).saveChosenCourses(chosenCourses);*/

/*		model.dropCourse(id.number());
		model.saveChosenCourses(model.getChosenCourseNames());*/
	}

/*	@Override
	public List<Course> getChosenCoursesList(Semester semester) {
		if (!courseModels.containsKey(semester)){
			initilalizeCourseModel(semester);
		}
		return courseModels.get(semester).getPickedCoursesList();
	}*/

	static public String someString = "empty";

	@Override
	public String getSomeString() {
		return courseModels.get(Semester.WINTER16).loadChosenCourses().toString();
	}
	
	@Override
	public String getSelectedCoursesString(Semester semester){
		return courseModels.get(semester).loadChosenCourses().toString();
	}


	@Override
	public void unselectAllCourses(Semester semester) {
		if (!courseModels.containsKey(semester)){
			initilalizeCourseModel(semester);
		}
		courseModels.get(semester).removeAllChosenCourse();

/*		CoursesEntity coursesEntity = courseModels.get(semester).loadChosenCourses();
		if (coursesEntity == null){
			return;
		}
		coursesEntity.removeAllCourses(semester.getId());
		courseModels.get(semester).saveChosenCourses(coursesEntity);
*/
		//courseModels.get(semester).saveChosenCourses(new ArrayList<String>());
		
	}
	
	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}
	
	public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
	
	@Override
	public void saveSchedule(List<LessonGroup> sched) {
		return; //TODO: implement
	}

	@Override
	public List<LessonGroup> loadSchedule() {
		return new ArrayList<>(); //TODO: implement
	}
	

	public void exportSchedule(List<LessonGroup> sched) throws IOException {
		try {
			someString += "\n111";
			calendarModel.createCalendar(sched);
			someString += "\n222";
		} catch (IOException e) {
			someString += "\n333\n";
			someString += e.getMessage();
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IOException(CalendarModel.newFlow().newAuthorizationUrl().setRedirectUri(CalendarModel.getRedirectUri(this.getThreadLocalRequest())).build());
		}
	}
	
}
