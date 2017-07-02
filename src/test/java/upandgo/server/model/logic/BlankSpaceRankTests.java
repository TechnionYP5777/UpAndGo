package upandgo.server.model.logic;
/**
 * @author kobybs
 * @since 2-1-17
 */

import upandgo.shared.entities.LocalTime;

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

	@Test
	@SuppressWarnings("deprecation")
	public void test_a() {
		cr = new XmlCourseLoader("schedulerTest9.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		for (final Iterator<Timetable> it = Scheduler.sortedBy(Scheduler.getTimetablesList(courses, null), true, false); it
				.hasNext();) {
			@SuppressWarnings("unused")
			final Timetable currentTable = it.next();
		}

	}

	@Test
	public void test_b() {
		cr = new XmlCourseLoader("schedulerTest8.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		final List<Timetable> tablesList = Scheduler.getTimetablesList(courses, null);
		Iterator<Timetable> it = Scheduler.sortedBy(tablesList, true, false, LocalTime.parse("10:00"), null);
		Timetable currentTable = it.next();
		assert currentTable.getRankOfDaysoff() == 4;
		assert currentTable.getRankOfBlankSpace() == 1.75;
		assert currentTable.getRankOfStartTime(LocalTime.parse("10:00")) == 0.5;

		currentTable = it.next();
		assert currentTable.getRankOfDaysoff() == 3;
		assert currentTable.getRankOfBlankSpace() == 2.0;
		assert currentTable.getRankOfStartTime(LocalTime.parse("10:00")) == 1;

		it = Scheduler.sortedBy(tablesList, false, true, LocalTime.parse("10:00"), null);

		currentTable = it.next();
		assert currentTable.getRankOfDaysoff() == 3;
		assert currentTable.getRankOfBlankSpace() == 2.0;
		assert currentTable.getRankOfStartTime(LocalTime.parse("10:00")) == 1;

		currentTable = it.next();
		assert currentTable.getRankOfDaysoff() == 4;
		assert currentTable.getRankOfBlankSpace() == 1.75;
		assert currentTable.getRankOfStartTime(LocalTime.parse("10:00")) == 0.5;

		it = Scheduler.sortedBy(tablesList, false, true, LocalTime.parse("10:00"), null);

		currentTable = it.next();
		assert currentTable.getRankOfDaysoff() == 3;
		assert currentTable.getRankOfBlankSpace() == 2.0;
		assert currentTable.getRankOfStartTime(LocalTime.parse("10:00")) == 1;

		currentTable = it.next();
		assert currentTable.getRankOfDaysoff() == 4;
		assert currentTable.getRankOfBlankSpace() == 1.75;
		assert currentTable.getRankOfStartTime(LocalTime.parse("10:00")) == 0.5;

	}

}
