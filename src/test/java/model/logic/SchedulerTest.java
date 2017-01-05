package model.logic;
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

public class SchedulerTest {

CourseLoader cr;
	
	
	@Test
	public void test_a() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest.XML");
		
		List<Course> courses = new ArrayList<>(cr.loadAllCourses().values());
		System.out.println(courses);
		
		Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		System.out.println(s);
		
		assert s.getLessonGroups().get(0).getLessons().get(0).getStartTime().equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 00)));
		assert s.getLessonGroups().get(1).getLessons().get(0).getStartTime().equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(13, 00)));
		assert s.getLessonGroups().get(2).getLessons().get(0).getStartTime().equals(new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(11, 00)));
		assert s.getLessonGroups().get(3).getLessons().get(0).getStartTime().equals(new WeekTime(DayOfWeek.WEDNESDAY, LocalTime.of(14, 00)));
		
	}
	
	@Test
	public void test_b() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest2.XML");
		
		List<Course> courses = new ArrayList<>(cr.loadAllCourses().values());
		System.out.println(courses);
		
		Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		System.out.println(s);
		
		assert s.getLessonGroups().get(0).getLessons().get(0).getStartTime().equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 00)));
		assert s.getLessonGroups().get(1).getLessons().get(0).getStartTime().equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(12, 00)));
		
	}
	
	@Test
	public void test_c() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest3.XML");
		
		List<Course> courses = new ArrayList<>(cr.loadAllCourses().values());
		System.out.println(courses);
		
		Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		System.out.println(s);
		
		assert s.getLessonGroups().get(0).getLessons().get(0).getStartTime().equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(14, 00)));
		assert s.getLessonGroups().get(1).getLessons().get(0).getStartTime().equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(12, 00)));
		
	}
	
	@Test
	public void test_d() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest4.XML");
		
		List<Course> courses = new ArrayList<>(cr.loadAllCourses().values());
		System.out.println(courses);
		
		Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		System.out.println(s);
		
		assertNull(s);
	}

}
