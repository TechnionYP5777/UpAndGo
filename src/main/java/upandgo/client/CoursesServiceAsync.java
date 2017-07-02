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
	public void getSelectedCourses(Semester s, AsyncCallback<ArrayList<CourseId>> is);

	public void getNotSelectedCourses(Semester s, String query, String faculty, AsyncCallback<ArrayList<CourseId>> is);
	
	public void getAllCourses(Semester s, AsyncCallback<ArrayList<CourseId>> is);
	
	public void getFaculties(Semester s, AsyncCallback<ArrayList<String>> ss);

	public void getCourseDetails(Semester s, CourseId i,AsyncCallback<Course> c);

	public void selectCourse(Semester s, CourseId i, AsyncCallback<Void> v);

	public void unselectCourse(Semester s, CourseId i, AsyncCallback<Void> v);
	
	public void unselectAllCourses(Semester s, AsyncCallback<Void> v);
	
	public void saveSchedule(Semester s, List<LessonGroup> sched, AsyncCallback<Void> v);

	public void loadSchedule(Semester s, AsyncCallback<List<LessonGroup>> gs);
	
	public void getSomeString(AsyncCallback<String> s);
	
	public void getSelectedCoursesString(Semester s, AsyncCallback<String> callback);
			
	public void exportSchedule(List<LessonGroup> sched, Map<String, Color> colorMap, AsyncCallback<Void> v);
}
