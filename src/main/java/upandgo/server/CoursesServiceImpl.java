package upandgo.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	private String REP_XML_PATH = "201602.XML";
	//private String REP_XML_PATH = "loadOf6CoursesTest.XML";

	private CourseModel model;
	private final CalendarModel calendarModel = new CalendarModel();

	public CoursesServiceImpl() {
		Log.warn("in course service constractor");
		// Log.info("entered c'tor of CourseServiceImple");

		XmlCourseLoader loader = new XmlCourseLoader(REP_XML_PATH);
		model = new CourseModel(loader);
	}

	// this one doesn't seem to be in use
	public CoursesServiceImpl(String path) {
		REP_XML_PATH = path;
		XmlCourseLoader loader = new XmlCourseLoader(REP_XML_PATH);
		model = new CourseModel(loader);
	}

	@Override
	public ArrayList<CourseId> getSelectedCourses() {
		return (ArrayList<CourseId>) model.loadChosenCourses();
	}

	@Override
	public ArrayList<CourseId> getNotSelectedCourses(String query, String faculty) {
		ArrayList<CourseId> res = (ArrayList<CourseId>) model.loadQueryByFaculty(query, faculty);
		Log.warn("CourseServiceImple got: " + res.get(0).getTitle() + "*" + res.get(0).aTerm() + "*"
				+ res.get(0).bTerm());
		return res;
	}

	@Override
	public ArrayList<String> getFaculties() {
		return (ArrayList<String>) model.loadFacultyNames();
	}

	@Override
	public Course getCourseDetails(CourseId id) {
		return model.getCourseById(id.number());
	}

	@Override
	public List<LessonGroup> getCourseLectures(String id) {
		return model.getCourseLectures(id);
	}

	@Override
	public void selectCourse(CourseId id) {
		Log.info("^&^&^&^&&^&&^&&^picked course: " + id.number());
		someString = "here0";
		model.pickCourse(id.number());
		model.saveChosenCourses(model.getChosenCourseNames());
	}

	@Override
	public void unselectCourse(CourseId id) {
		model.dropCourse(id.number());
		model.saveChosenCourses(model.getChosenCourseNames());
	}

	@Override
	public List<Course> getChosenCoursesList() {
		return model.getPickedCoursesList();
	}

	static public String someString = "empty";

	@Override
	public String getSomeString() {
		return someString;
	}

	@Override
	public void saveSchedule(List<LessonGroup> sched) {
		model.saveChosenLessonGroups(sched);

	}

	@Override
	public void unselectAllCourses() {
		model.UnselectAllCourses();
		model.saveChosenCourses(model.getChosenCourseNames());
		
	}
	
	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}
	
	public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

	@Override
	public List<LessonGroup> getSchedule() {
		return model.loadChosenLessonGroups();
	}
	
	@Override
	public void setSemester(String semesterId){
		XmlCourseLoader loader = new XmlCourseLoader(semesterId+".XML");
		model = new CourseModel(loader);
	};

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
