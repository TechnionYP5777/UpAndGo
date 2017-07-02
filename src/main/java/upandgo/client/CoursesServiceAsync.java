package upandgo.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.Semester;
import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;
import upandgo.shared.model.scedule.Color;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-04-16
 * 
 * Remote Procedure Call Service for retrieving information about courses in DB and selecting needed courses.
 * 
 */

public interface CoursesServiceAsync {
	public void getSelectedCourses(Semester semester, AsyncCallback<ArrayList<CourseId>> callback);

	public void getNotSelectedCourses(Semester semester, String query, String faculty, AsyncCallback<ArrayList<CourseId>> callback);
	
	public void getAllCourses(Semester semester, AsyncCallback<ArrayList<CourseId>> callback);
	
	public void getFaculties(Semester semester, AsyncCallback<ArrayList<String>> callback);

	public void getCourseDetails(Semester semester, CourseId id,AsyncCallback<Course> callback);

	//public void getCourseLectures(String id,AsyncCallback<List<LessonGroup>> callback);
	
	public void selectCourse(Semester semester, CourseId id, AsyncCallback<Void> callback);

	public void unselectCourse(Semester semester, CourseId id, AsyncCallback<Void> callback);
	
	public void unselectAllCourses(Semester semester, AsyncCallback<Void> callback);
	
	//public void getChosenCoursesList(Semester semester, AsyncCallback<List<Course>> callback);
	
	public void saveSchedule(Semester semester, List<LessonGroup> sched, AsyncCallback<Void> callback);

	public void loadSchedule(Semester semester, AsyncCallback<List<LessonGroup>> callback);
	
	public void getSomeString(AsyncCallback<String> callback);
	
	public void getSelectedCoursesString(Semester semester, AsyncCallback<String> callback);
			
	public void exportSchedule(List<LessonGroup> sched, Map<String, Color> colorMap, Semester semester, AsyncCallback<Void> callback);
}
