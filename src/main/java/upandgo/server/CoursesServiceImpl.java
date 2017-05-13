package upandgo.server;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import upandgo.client.CoursesService;
import upandgo.server.model.CourseModel;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.constraint.TimeConstraint;
import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;
import upandgo.shared.model.scedule.Schedule;

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

	private String REP_XML_PATH = "../../resources/testXML/test.XML";
	
	private final CourseModel model;
	
	public CoursesServiceImpl() {
		System.out.println("!!!!!!!!!!!!!!!!!!"+REP_XML_PATH);
		
		XmlCourseLoader loader = new XmlCourseLoader(REP_XML_PATH);
		model = new CourseModel(loader);
	}
	
	public CoursesServiceImpl(String path) {
		System.out.println("!!!!!!!!!!!!!!!!!!"+REP_XML_PATH);
		REP_XML_PATH = path;
		XmlCourseLoader loader = new XmlCourseLoader(REP_XML_PATH);
		model = new CourseModel(loader);
	}
	
	@Override
	public List<CourseId> getSelectedCourses() {
		return model.loadChosenCourses();
	}

	@Override
	public List<CourseId> getNotSelectedCourses(String query, String faculty) {
		Log.warn("!!!!!!!!!!!!!!!!!!"+REP_XML_PATH);
		return model.loadQueryByFaculty(query, faculty);
	}

	@Override
	public List<String> getFaculties() {
		return model.loadFacultyNames();
	}

	@Override
	public Course getCourseDetails(CourseId id) {
		return model.getCourseById(id.number);
	}

	@Override
	public void selectCourse(CourseId id) {
		model.pickCourse(id.number);
	}

	@Override
	public void unselectCourse(CourseId id) {
		model.dropCourse(id.number);
	}

	@Override
	public List<Course> getCoursesByCourseID(List<CourseId> selectedCourses) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
