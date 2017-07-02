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

import upandgo.shared.model.scedule.Scheduler;
import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.Timetable;

public class SchedulerIteratorTest {

	CourseLoader cr;

	@After
	public void after() {
//		Do nothing
	}

	@Test
	public void test_a() {
		cr = new XmlCourseLoader("schedulerTest.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		@SuppressWarnings("unused")
		final List<Timetable> tl = Scheduler.getTimetablesList(courses, null);
	}

	@Test
	@SuppressWarnings("deprecation")
	public void test_b() {
		cr = new XmlCourseLoader("schedulerTest8.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		for (final Iterator<Timetable> it = Scheduler.sortedBy(Scheduler.getTimetablesList(courses, null), true, false); it
				.hasNext();) {
			@SuppressWarnings("unused")
			final Timetable currentTable = it.next();
		}

	}

	@Test
	@SuppressWarnings("deprecation")
	public void test_c() {
		cr = new XmlCourseLoader("schedulerTest8.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());

		final List<Timetable> tablesList = Scheduler.getTimetablesList(courses, null);
		Iterator<Timetable> it = Scheduler.sortedBy(tablesList, true, false);
		assert it.next().getRankOfDaysoff() == 4;
		assert it.next().getRankOfDaysoff() == 3;

		it = Scheduler.sortedBy(tablesList, false, true);
		assert it.next().getRankOfBlankSpace() == 2.0;
		assert it.next().getRankOfBlankSpace() == 1.75;

	}

}
