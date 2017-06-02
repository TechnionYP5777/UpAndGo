package upandgo.server;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import upandgo.client.CoursesService;
import upandgo.server.model.CourseModel;
import upandgo.server.model.TimeTableModel;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;

/**
 * 
 * @author Nikita Dizhur
 * @since 05-05-17
 * 
 * Remote Procedure Call Service server side implementation for retrieving information about courses in DB and selecting needed courses.
 * 
 */

public class CoursesServiceImpl extends RemoteServiceServlet implements CoursesService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1193922002939188572L;

	private String REP_XML_PATH = "test.XML";
	
	private final CourseModel model;
	private final TimeTableModel scheduleModel;
	
	public CoursesServiceImpl() {
		Log.warn("in course service constractor");
//		Log.info("entered c'tor of CourseServiceImple");
		XmlCourseLoader loader = new XmlCourseLoader(REP_XML_PATH);
		model = new CourseModel(loader);
		scheduleModel = new TimeTableModel(loader);
	}
	
	public CoursesServiceImpl(String path) {
		REP_XML_PATH = path;
		XmlCourseLoader loader = new XmlCourseLoader(REP_XML_PATH);
		model = new CourseModel(loader);
		scheduleModel = new TimeTableModel(loader);
	}
	
	@Override
	public ArrayList<CourseId> getSelectedCourses() {
		return (ArrayList<CourseId>) model.loadChosenCourses();
	}

	@Override
	public ArrayList<CourseId> getNotSelectedCourses(String query, String faculty) {
		ArrayList<CourseId> res = (ArrayList<CourseId>) model.loadQueryByFaculty(query, faculty);
		Log.warn("CourseServiceImple got: " + res.get(0).getTitle()+"*"+res.get(0).aTerm()+"*"+res.get(0).bTerm());
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
	public void selectCourse(CourseId id) {
		model.pickCourse(id.number());
	}

	@Override
	public void unselectCourse(CourseId id) {
		model.dropCourse(id.number());
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
	public void saveSchedule(@SuppressWarnings("unused") List<LessonGroup> sched) {
		scheduleModel.saveChosenLessonGroups(sched);
		
	}
}
