package upandgo.server.model.logic;
/**
 * @author kobybs
 * @since 2-1-17
 */

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import upandgo.server.logic.Scheduler;
import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.server.model.schedule.Schedule;
import upandgo.server.model.schedule.Timetable;
import upandgo.shared.entities.constraint.TimeConstraint;
import upandgo.shared.entities.course.Course;

@SuppressWarnings("deprecation")
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
