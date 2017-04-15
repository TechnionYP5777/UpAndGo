package upandgo.server.model.logic;
/**
 * @author kobybs
 * @since 2-1-17
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import upandgo.server.logic.Scheduler;
import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.server.model.schedule.Timetable;
import upandgo.shared.entities.course.Course;

@SuppressWarnings("static-method")
public class SchedulerIteratorTest {

	CourseLoader cr;

	@After
	public void after() {
		System.out.println("***");
	}

	@Test
	public void test_a() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		final List<Timetable> tl = Scheduler.getTimetablesList(courses, null);
		for (final Timetable ¢ : tl)
			System.out.println("rank: " + ¢.getRankOfDaysoff());
		for (@SuppressWarnings("deprecation")
		final Iterator<Timetable> ¢ = Scheduler.sortedBy(tl, false, false); ¢.hasNext();)
			System.out.println(¢.next().getRankOfDaysoff());

		// System.out.println(tl);
		/*
		 * Timetable t = Scheduler.getTimetablesList(courses).get(0);
		 * 
		 * assert
		 * t.getLessonGroups().get(0).getLessons().get(0).getStartTime().equals(
		 * new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 00))); assert
		 * t.getLessonGroups().get(1).getLessons().get(0).getStartTime().equals(
		 * new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(13, 00))); assert
		 * t.getLessonGroups().get(2).getLessons().get(0).getStartTime().equals(
		 * new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(11, 00))); assert
		 * t.getLessonGroups().get(3).getLessons().get(0).getStartTime().equals(
		 * new WeekTime(DayOfWeek.WEDNESDAY, LocalTime.of(14, 00)));
		 */
	}

	@Test
	@SuppressWarnings("deprecation")
	public void test_b() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest8.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		for (final Iterator<Timetable> it = Scheduler.sortedBy(Scheduler.getTimetablesList(courses, null), true, false); it
				.hasNext();) {
			final Timetable currentTable = it.next();
			System.out.println("days of rank: " + currentTable.getRankOfDaysoff());
			System.out.println("blank space rank: " + currentTable.getRankOfBlankSpace());
			System.out.println("time table: " + currentTable);
		}

	}

	@Test
	@SuppressWarnings("deprecation")
	public void test_c() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest8.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		final List<Timetable> tablesList = Scheduler.getTimetablesList(courses, null);
		Iterator<Timetable> it = Scheduler.sortedBy(tablesList, true, false);
		assert it.next().getRankOfDaysoff() == 4;
		assert it.next().getRankOfDaysoff() == 3;

		it = Scheduler.sortedBy(tablesList, false, true);
		assert it.next().getRankOfBlankSpace() == 2.0;
		assert it.next().getRankOfBlankSpace() == 1.75;

	}

}
