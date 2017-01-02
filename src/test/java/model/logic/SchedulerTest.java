package model.logic;
/**
 * @author kobybs
 * @since 2-1-17
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import logic.Scheduler;
import model.constraint.TimeConstraint;
import model.course.Course;
import model.loader.CourseLoader;
import model.loader.XmlCourseLoader;
import model.schedule.Schedule;

public class SchedulerTest {

CourseLoader cr;
	
	@Before
	public void initialize() {
		cr = new XmlCourseLoader("resources/testXML/REP.XML");
	}
	
	@Test
	public void test() {
		List<Course> courses = new ArrayList<>(cr.loadAllCourses().values());
		System.out.println(courses);
		
		Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		System.out.println(s);
	}

}
