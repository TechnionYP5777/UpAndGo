package upandgo.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import upandgo.shared.entities.constraint.TimeConstraint;
import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;
import upandgo.shared.model.scedule.Schedule;

/**
 * 
 * @author Nikita Dizhur
 * @since 05-05-17
 * 
 * Remote Procedure Call Service for retrieving information about courses in DB and selecting needed courses.
 * 
 */

@RemoteServiceRelativePath("coursesManipulations")
public interface CoursesService extends RemoteService {
	
	public List<CourseId> getSelectedCourses();

	public List<CourseId> getNotSelectedCourses(String query, String faculty);
	
	public List<String> getFaculties();

	public Course getCourseDetails(CourseId id);

	public void selectCourse(CourseId id);

	public void unselectCourse(CourseId id);
	
	public List<Course> getCoursesByCourseID(List<CourseId> selectedCourses);
}
