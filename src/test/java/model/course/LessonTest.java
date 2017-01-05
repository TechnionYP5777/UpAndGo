package model.course;
/**
 * @author kobybs
 * @since 2-1-17
 */

import static org.junit.Assert.*;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import logic.Scheduler;
import model.constraint.TimeConstraint;
import model.course.Course;
import model.course.WeekTime;
import model.loader.CourseLoader;
import model.loader.XmlCourseLoader;
import model.schedule.Schedule;

public class LessonTest {

CourseLoader cr;
	
	
	
	@Test
	public void test_a() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest5.XML");
		
		List<Course> courses = new ArrayList<>(cr.loadAllCourses().values());

		assert !courses.get(0).getLectures().get(0).getLessons().get(0)
				.IsClashWith(courses.get(0).getTutorials().get(0).getLessons().get(0));
		//assertNull(s);
	}
	
	@Test
	public void test_b() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest6.XML");
		
		List<Course> courses = new ArrayList<>(cr.loadAllCourses().values());

		assert courses.get(0).getLectures().get(0).getLessons().get(0)
				.IsClashWith(courses.get(0).getTutorials().get(0).getLessons().get(0));
		//assertNull(s);
	}

}
