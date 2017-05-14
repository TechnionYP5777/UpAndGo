package upandgo.client;

import java.util.ArrayList;
import java.util.List;

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

	public void getNotSelectedCourses(String query, String faculty, AsyncCallback<ArrayList<CourseId>> callback);
	
	public void getFaculties(AsyncCallback<ArrayList<String>> callback);

	public void getCourseDetails(CourseId id,AsyncCallback<Course> callback);

	public void selectCourse(CourseId id, AsyncCallback<Void> callback);

	public void unselectCourse(CourseId id, AsyncCallback<Void> callback);
	
	public void getCoursesByCourseID(List<CourseId> selectedCourses, AsyncCallback<List<Course>> callback);
	
	public void getSomeString(AsyncCallback<String> asyncCallback);
}
