package upandgo.server.model.logic;
/**
 * @author kobybs
 * @since 2-1-17
 */

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import upandgo.shared.model.scedule.Scheduler;
import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.constraint.TimeConstraint;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.Schedule;
import upandgo.shared.model.scedule.Timetable;

@SuppressWarnings("deprecation")
public class TimetableTest {

	CourseLoader cr;

	@Test
	public void test_a() {
		cr = new XmlCourseLoader("schedulerTest.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		final Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		final Timetable t = s.getTimetable();

		assert t.getRankOfDaysoff() == 2;
		
	}

	@Test
	public void test_b() {
		cr = new XmlCourseLoader("schedulerTest2.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
	
		assert Scheduler.schedule(courses, new ArrayList<TimeConstraint>()).getTimetable().getRankOfDaysoff() == 4;
	}

}
