package upandgo.shared.model.schedule;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.LocalTime;
import upandgo.shared.entities.constraint.TimeConstraint;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.Scheduler;
import upandgo.shared.model.scedule.Timetable;

public class SchedulerTest {
	
	CourseLoader cr;
	
	@Test
	@SuppressWarnings({ "deprecation", "boxing" })
	public void test() {
		cr = new XmlCourseLoader("schedulerTest.XML", true);
		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		List<Timetable> t = Scheduler.getTimetablesList(courses, null);
		assertEquals(4, t.size());
		assertEquals(2, Scheduler.getColorMap().size());
		assertEquals(1, Scheduler.getCollisionSolvers().size());
		assertEquals(2, Scheduler.mapLessonGroupsToColors(t.get(0).getLessonGroups()).size());
		List<Boolean> x = new ArrayList<>(Arrays.asList(true, true, true, true, true));
		assertEquals(4, Scheduler.ListSortedBy(t, true, true, LocalTime.of(9, 30), LocalTime.of(17, 30), x).size());
		assert Scheduler.sortedBy(t, true, true, LocalTime.of(9, 30), LocalTime.of(17, 30)).hasNext();
	}

}
