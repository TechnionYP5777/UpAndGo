package upandgo.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.Semester;
import upandgo.shared.entities.UserEvent;
import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;
import upandgo.shared.model.scedule.Color;

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
	
	public ArrayList<CourseId> getSelectedCourses(Semester s);

	public ArrayList<CourseId> getNotSelectedCourses(Semester s, String query, String faculty);
	
	public ArrayList<CourseId> getAllCourses(Semester s);
	
	public ArrayList<String> getFaculties(Semester s);

	public Course getCourseDetails(Semester s, CourseId i);
	
	public void selectCourse(Semester s, CourseId i);

	public void unselectCourse(Semester s, CourseId i);
	
	public void unselectAllCourses(Semester s);
	
	public void saveSchedule (Semester s, List<LessonGroup> sched);
	
	public List<LessonGroup> loadSchedule(Semester semester);
		
	public List<UserEvent> loadUserEvents(Semester s);
	
	public void saveUserEvents(Semester s, List<UserEvent> userEvents);

	public String getSomeString();
	
	public String getSelectedCoursesString(Semester s);
	
	public void exportSchedule(List<LessonGroup> sched, Map<String, Color> colorMap, Semester semester) throws IOException;

}
