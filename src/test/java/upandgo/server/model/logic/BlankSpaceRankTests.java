package upandgo.server.model.logic;
/**
 * @author kobybs
 * @since 2-1-17
 */

import java.time.LocalTime;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Test;


import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.Scheduler;
import upandgo.shared.model.scedule.Timetable;

@SuppressWarnings("static-method")
public class BlankSpaceRankTests {

	CourseLoader cr;

	@After
	public void after() {
		System.out.println("***");
	}

	/*
	 * @Test public void test_a() { cr = new
	 * XmlCourseLoader("resources/testXML/schedulerTest.XML");
	 * 
	 * List<Course> courses = new ArrayList<>(cr.loadAllCourses().values());
	 * System.out.println(courses);
	 * 
	 * 
	 * List<Timetable> tl = Scheduler.getTimetablesList(courses); for(Timetable
	 * xxx : tl) System.out.println("rank: " + xxx.getRankOfDaysoff());
	 * Iterator<Timetable> it = Scheduler.sortedBy(tl, false, false); while
	 * (it.hasNext()) { System.out.println(it.next().getRankOfDaysoff()); }
	 * 
	 * 
	 * //System.out.println(tl);
	 * 
	 * }
	 * 
	 */

	@Test
	@SuppressWarnings("deprecation")
	public void test_a() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest9.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		for (final Iterator<Timetable> it = Scheduler.sortedBy(Scheduler.getTimetablesList(courses, null), true, false); it
				.hasNext();) {
			final Timetable currentTable = it.next();
			System.out.println("\ndays of rank: " + currentTable.getRankOfDaysoff());
			System.out.println("blank space rank: " + currentTable.getRankOfBlankSpace());
			System.out.println("time start of 10:00 rank: " + currentTable.getRankOfStartTime(LocalTime.of(10, 00)));
			System.out.println("time table: " + currentTable);
		}

	}

	@Test
	public void test_b() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest8.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		final List<Timetable> tablesList = Scheduler.getTimetablesList(courses, null);
		Iterator<Timetable> it = Scheduler.sortedBy(tablesList, true, false, LocalTime.of(10, 00), null);
		Timetable currentTable = it.next();
		assertTrue(currentTable.getRankOfDaysoff() == 4);
		assert currentTable.getRankOfBlankSpace() == 1.75;
		assert currentTable.getRankOfStartTime(LocalTime.of(10, 00)) == 0.5;

		currentTable = it.next();
		assert currentTable.getRankOfDaysoff() == 3;
		assert currentTable.getRankOfBlankSpace() == 2.0;
		assert currentTable.getRankOfStartTime(LocalTime.of(10, 00)) == 1;

		it = Scheduler.sortedBy(tablesList, false, true, LocalTime.of(10, 00), null);

		currentTable = it.next();
		assert currentTable.getRankOfDaysoff() == 3;
		assert currentTable.getRankOfBlankSpace() == 2.0;
		assert currentTable.getRankOfStartTime(LocalTime.of(10, 00)) == 1;

		currentTable = it.next();
		assert currentTable.getRankOfDaysoff() == 4;
		assert currentTable.getRankOfBlankSpace() == 1.75;
		assert currentTable.getRankOfStartTime(LocalTime.of(10, 00)) == 0.5;

		it = Scheduler.sortedBy(tablesList, false, true, LocalTime.of(10, 00), null);

		currentTable = it.next();
		assert currentTable.getRankOfDaysoff() == 3;
		assert currentTable.getRankOfBlankSpace() == 2.0;
		assert currentTable.getRankOfStartTime(LocalTime.of(10, 00)) == 1;

		currentTable = it.next();
		assert currentTable.getRankOfDaysoff() == 4;
		assert currentTable.getRankOfBlankSpace() == 1.75;
		assert currentTable.getRankOfStartTime(LocalTime.of(10, 00)) == 0.5;

		/*
		 * it = Scheduler.sortedBy(tablesList, false, true); assert
		 * it.next().getRankOfBlankSpace() == 2.0; assert
		 * it.next().getRankOfBlankSpace() == 1.75;
		 */

	}

}
