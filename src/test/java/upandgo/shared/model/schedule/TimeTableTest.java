package upandgo.shared.model.schedule;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.LocalTime;
import upandgo.shared.entities.constraint.TimeConstraint;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.Schedule;
import upandgo.shared.model.scedule.Scheduler;
import upandgo.shared.model.scedule.Timetable;

public class TimeTableTest {
	
	CourseLoader cr;
	
	@Test
	public void test_a() {
		Timetable t = new Timetable();
		
		assertEquals(t.getLessonGroups().size(), 0);
		assertNull(t.getColorMap());	
	}
	
	@Test
	public void test_b() {
		cr = new XmlCourseLoader("schedulerTest.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		final Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		final Timetable t = s.getTimetable();
		
		assertEquals("1.5", t.getRankOfStartTime(LocalTime.of(9, 30)) + "");
		assertEquals("1.0", t.getRankOfEndTime(LocalTime.of(15, 30)) + "");
		assertEquals("0.0", t.getRankOfFreeSunday() + "");
		assertEquals("1.5", t.getRankOfFreeMonday() + "");
		assertEquals("0.0", t.getRankOfFreeTuesday() + "");
		assertEquals("0.0", t.getRankOfFreeWednesday() + "");
		assertEquals("1.5", t.getRankOfFreeThursday() + "");
		assertEquals(405, t.toString().length());
		
	}

}
