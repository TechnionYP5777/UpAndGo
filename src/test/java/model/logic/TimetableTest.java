package model.logic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import logic.Scheduler;
import model.constraint.TimeConstraint;
import model.course.Course;
import model.loader.CourseLoader;
import model.loader.XmlCourseLoader;
import model.schedule.Schedule;
import model.schedule.Timetable;

public class TimetableTest {

	CourseLoader cr;

	@Test
	public void test_a() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		final Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		final Timetable t = s.getTimetable();

		System.out.println("rank: " + t.getRankOfDaysoff());
		assert t.getRankOfDaysoff() == 2;
		System.out.println(s);

	}

	@Test
	public void test_b() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest2.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		assert Scheduler.schedule(courses, new ArrayList<TimeConstraint>()).getTimetable().getRankOfDaysoff() == 4;
	}

}
