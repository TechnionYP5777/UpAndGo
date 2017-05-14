package upandgo.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;

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
	
	public ArrayList<CourseId> getSelectedCourses();

	public ArrayList<CourseId> getNotSelectedCourses(String query, String faculty);
	
	public ArrayList<String> getFaculties();

	public Course getCourseDetails(CourseId id);

	public void selectCourse(CourseId id);

	public void unselectCourse(CourseId id);
	
	public List<Course> getChosenCoursesList();
	
	public void saveSchedule (List<LessonGroup> sched);
	
	public String getSomeString();
}
