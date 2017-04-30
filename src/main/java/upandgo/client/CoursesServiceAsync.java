package upandgo.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import upandgo.server.model.schedule.Schedule;
import upandgo.shared.entities.constraint.TimeConstraint;
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
	
	public void getFaculties(AsyncCallback<ArrayList<String>> callback);

	public void getCourseDetails(AsyncCallback<Course> callback);

	public void selectCourse(CourseId id, AsyncCallback<Void> callback);

	public void unselectCourse(CourseId id, AsyncCallback<Void> callback);
	
	public void getSchedule(List<CourseId> selectedCourses, List<TimeConstraint> constraintsList, AsyncCallback<Schedule> callback);

	public void getNextSchedule(Schedule schedule, AsyncCallback<Schedule> asyncCallback);

	public void getPreviousSchedule(Schedule schedule, AsyncCallback<Schedule> asyncCallback);

}
