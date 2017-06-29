package upandgo.server;

import java.io.IOException;
import java.util.ArrayList;
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

	
	private static final long serialVersionUID = 1193922002939188572L;

	static {
		// register Objectify-classes
		ObjectifyService.register(ScheduleEntity.class);
		ObjectifyService.register(CoursesEntity.class);
	}
	
	private Semester defaultSemester = Semester.WINTER17;

	private Map<Semester,CourseModel> courseModels = new TreeMap<Semester,CourseModel>();
	private final CalendarModel calendarModel = new CalendarModel();

	public CoursesServiceImpl() {
		Log.warn("in course service constractor");
		initilalizeCourseModel(defaultSemester);
	}
	
	private void initilalizeCourseModel(Semester s){
		courseModels.put(s, new CourseModel(new XmlCourseLoader(s.getId() + ".XML"), s));
	}

	@Override
	public ArrayList<CourseId> getSelectedCourses(Semester s) {
		if (!courseModels.containsKey(s))
			initilalizeCourseModel(s);
		List<String> selectedCourses = new ArrayList<>(courseModels.get(s).loadChosenCourses());
		if (selectedCourses.isEmpty())
			return new ArrayList<>();
		ArrayList<CourseId> selectesCoursesIDs = new ArrayList<>();
		for (String courseId : selectedCourses)
			selectesCoursesIDs.add(courseModels.get(s).getCourseId(courseId));
		return selectesCoursesIDs;
	}
	
	@Override
	public ArrayList<CourseId> getAllCourses(Semester s) {
		if (!courseModels.containsKey(s))
			initilalizeCourseModel(s);
		ArrayList<CourseId> res = (ArrayList<CourseId>) courseModels.get(s).loadAllCourses();
		Log.warn("CourseServiceImple got: " + res.size() + " courses from loader");
		return res;
	}

	@Override
	public ArrayList<CourseId> getNotSelectedCourses(Semester s, String query, String faculty) {
		if (!courseModels.containsKey(s))
			initilalizeCourseModel(s);
		return (ArrayList<CourseId>) courseModels.get(s).loadQueryByFaculty(query, faculty);
	}

	@Override
	public ArrayList<String> getFaculties(Semester s) {
		if (!courseModels.containsKey(s))
			initilalizeCourseModel(s);
		return (ArrayList<String>) courseModels.get(s).loadFacultyNames();
	}

	@Override
	public Course getCourseDetails(Semester s, CourseId i) {
		if (!courseModels.containsKey(s))
			initilalizeCourseModel(s);
		return courseModels.get(s).getCourseById(i.number());
	}

	@Override
	public void selectCourse(Semester s, CourseId i) {
		if (!courseModels.containsKey(s))
			initilalizeCourseModel(s);
		courseModels.get(s).saveChosenCourse(i.number());
	}

	@Override
	public void unselectCourse(Semester s, CourseId i) {
		if (!courseModels.containsKey(s))
			initilalizeCourseModel(s);
		courseModels.get(s).removeChosenCourse(i.number());
	}

	static public String someString = "empty";

	@Override
	public String getSomeString() {
		return " ";
	}
	
	@Override
	public String getSelectedCoursesString(Semester s){
		return courseModels.get(s).loadChosenCourses().toString();
	}


	@Override
	public void unselectAllCourses(Semester s) {
		if (!courseModels.containsKey(s))
			initilalizeCourseModel(s);
		courseModels.get(s).removeAllChosenCourse();
	}
	
	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}
	
	public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
	
	@Override
	public void saveSchedule(Semester s, List<LessonGroup> sched) {
		if (!courseModels.containsKey(s))
			initilalizeCourseModel(s);
		courseModels.get(s).saveChosenLessonGroups(sched);

	}

	@Override
	public List<LessonGroup> loadSchedule(Semester s) {
		if (!courseModels.containsKey(s))
			initilalizeCourseModel(s);
		return courseModels.get(s).loadChosenLessonGroups();
	}
	
	@Override
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
