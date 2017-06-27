package upandgo.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.Semester;
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
	
	public ArrayList<CourseId> getSelectedCourses(Semester semester);

	public ArrayList<CourseId> getNotSelectedCourses(Semester semester, String query, String faculty);
	
	public ArrayList<String> getFaculties(Semester semester);

	public Course getCourseDetails(Semester semester, CourseId id);
	
	//public List<LessonGroup> getCourseLectures(String id);

	public void selectCourse(Semester semester, CourseId id);

	public void unselectCourse(Semester semester, CourseId id);
	
	public void unselectAllCourses(Semester semester);
	
	//public List<Course> getChosenCoursesList(Semester semester);
	
	public void saveSchedule (List<LessonGroup> sched);
	
	public List<LessonGroup> loadSchedule();
	
	public String getSomeString();
	
	public void exportSchedule(List<LessonGroup> sched) throws IOException;

}
