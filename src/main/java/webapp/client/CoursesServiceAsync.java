package webapp.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import model.course.Course;
import model.course.CourseId;

public interface CoursesServiceAsync {
	public void getSelectedCourses(AsyncCallback<ArrayList<CourseId>> callback);

	public void getNotSelectedCourses(AsyncCallback<ArrayList<CourseId>> callback);

	public void getCourseDetails(AsyncCallback<Course> callback);

	public void selectCourse(String idOrName, AsyncCallback<Void> callback);

	public void unselectCourse(String idOrName, AsyncCallback<Void> callback);
}
