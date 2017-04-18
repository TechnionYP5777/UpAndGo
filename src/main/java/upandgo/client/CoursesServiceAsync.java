package upandgo.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-04-16
 * 
 * Remote Procedure Call Service for retrieving information about courses in DB and selecting needed courses.
 * 
 */

public interface CoursesServiceAsync {
	public void getSelectedCourses(AsyncCallback<ArrayList<CourseId>> callback);

	public void getNotSelectedCourses(String faculty, AsyncCallback<ArrayList<CourseId>> callback);

	public void getCourseDetails(AsyncCallback<Course> callback);

	public void selectCourse(CourseId id, AsyncCallback<Void> callback);

	public void unselectCourse(CourseId id, AsyncCallback<Void> callback);
}
