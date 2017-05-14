package upandgo.server;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import upandgo.client.CoursesService;
import upandgo.server.model.CourseModel;
import upandgo.server.model.loader.XmlCourseLoader;
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

	private String REP_XML_PATH = "./../../../resources/testXML/test.XML";
	
	private final CourseModel model;
	
	public CoursesServiceImpl() {
		XmlCourseLoader loader = new XmlCourseLoader(REP_XML_PATH);
		model = new CourseModel(loader);
	}
	
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
		return (ArrayList<CourseId>) model.loadQueryByFaculty(query, faculty);
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
	public List<Course> getCoursesByCourseID(List<CourseId> selectedCourses) {
		// TODO Auto-generated method stub
		return null;
	}

	
<<<<<<< HEAD
	static public String someString = "empty";
	
	@Override
	public String getSomeString() {
		Log.warn("check-check");
		return "paparapa " + someString;
	}

=======
>>>>>>> branch 'master' of https://github.com/TechnionYP5777/UpAndGo.git
}
