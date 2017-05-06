package upandgo.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import upandgo.client.CoursesService;
import upandgo.shared.entities.constraint.TimeConstraint;
import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;
import upandgo.shared.model.scedule.Schedule;

/**
 * 
 * @author Nikita Dizhur
 * @since 05-05-17
 * 
 * Remote Procedure Call Service server side implementation for retrieving information about courses in DB and selecting needed courses.
 * 
 */

public class CoursesServiceImpl extends RemoteServiceServlet implements CoursesService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1193922002939188572L;

	@Override
	public ArrayList<CourseId> getSelectedCourses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CourseId> getNotSelectedCourses(String query, String faculty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getFaculties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Course getCourseDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void selectCourse(CourseId id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unselectCourse(CourseId id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Schedule getSchedule(List<CourseId> selectedCourses, List<TimeConstraint> constraintsList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Schedule getNextSchedule(Schedule schedule) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Schedule getPreviousSchedule(Schedule schedule) {
		// TODO Auto-generated method stub
		return null;
	}

}
