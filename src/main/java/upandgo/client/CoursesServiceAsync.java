package upandgo.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import upandgo.shared.entities.LessonGroup;
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

	public void getCourseLectures(String id,AsyncCallback<List<LessonGroup>> callback);
	
	public void selectCourse(CourseId id, AsyncCallback<Void> callback);

	public void unselectCourse(CourseId id, AsyncCallback<Void> callback);
	
	public void unselectAllCourses(AsyncCallback<Void> callback);
	
	public void getChosenCoursesList(AsyncCallback<List<Course>> callback);
	
	public void saveSchedule(List<LessonGroup> sched, AsyncCallback<Void> callback);

	public void getSchedule(AsyncCallback<List<LessonGroup>> callback);
	
	public void getSomeString(AsyncCallback<String> callback);
	
	public void setSemester(String semesterId, AsyncCallback<Void> callback);
	
	public void exportSchedule(List<LessonGroup> sched, AsyncCallback<Void> callback);
}
