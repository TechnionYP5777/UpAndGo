package model.logic;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import logic.Scheduler;
import model.constraint.TimeConstraint;
import model.course.Course;
import model.course.WeekTime;
import model.loader.CourseLoader;
import model.loader.XmlCourseLoader;
import model.schedule.Schedule;
import model.schedule.Timetable;

public class TimetableTest {
	
	CourseLoader cr;

	@Test
	public void test_a() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest.XML");
		
		List<Course> courses = new ArrayList<>(cr.loadAllCourses().values());
		System.out.println(courses);
		
		Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		Timetable t = s.getTimetable();
		
		System.out.println("rank: " + t.getRankOfDaysoff());
		assert (t.getRankOfDaysoff() == 2);
		System.out.println(s);
		
		
	}
	
	
	@Test
	public void test_b() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest2.XML");
		
		List<Course> courses = new ArrayList<>(cr.loadAllCourses().values());
		System.out.println(courses);
		
		assert (Scheduler.schedule(courses, new ArrayList<TimeConstraint>()).getTimetable().getRankOfDaysoff() == 4);
	}

}
