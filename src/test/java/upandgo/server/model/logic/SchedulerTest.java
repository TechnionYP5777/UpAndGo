package upandgo.server.model.logic;
/**
 * @author kobybs
 * @since 2-1-17
 */


import static org.junit.Assert.*;

import upandgo.shared.entities.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import upandgo.shared.model.scedule.Scheduler;
import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.Day;
import upandgo.shared.entities.WeekTime;
import upandgo.shared.entities.constraint.TimeConstraint;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.Schedule;
import upandgo.shared.model.scedule.Timetable;

@SuppressWarnings("deprecation")
public class SchedulerTest {

	CourseLoader cr;

	@After
	public void after() {
		///Do nothing
	}

	
	@Test
	public void test_a() {
		cr = new XmlCourseLoader("schedulerTest.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		final Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		
		assert s.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("10:00")));
		assert s.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("13:00")));
		assert s.getLessonGroups().get(2).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.TUESDAY, LocalTime.parse("11:00")));
		assert s.getLessonGroups().get(3).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.WEDNESDAY, LocalTime.parse("14:00")));

	}
	
	@Test
	public void test_a111() {
		cr = new XmlCourseLoader("schedulerTest.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		final Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		
		assert s.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("10:00")));
		assert s.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("13:00")));
		assert s.getLessonGroups().get(2).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.TUESDAY, LocalTime.parse("11:00")));
		assert s.getLessonGroups().get(3).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.WEDNESDAY, LocalTime.parse("14:00")));

	}

	@Test
	public void test_a2() {
		cr = new XmlCourseLoader("schedulerTest.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		final Timetable t = Scheduler.getTimetablesList(courses, null).get(0);

		assert t.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("10:00")));
		assert t.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("13:00")));
		assert t.getLessonGroups().get(2).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.TUESDAY, LocalTime.parse("11:00")));
		assert t.getLessonGroups().get(3).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.WEDNESDAY, LocalTime.parse("14:00")));

	}

	@Test
	public void test_b() {
		cr = new XmlCourseLoader("schedulerTest2.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		final Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		
		assert s.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("10:00")));
		assert s.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("12:00")));

	}

	@Test
	public void test_b2() {
		cr = new XmlCourseLoader("schedulerTest2.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		final Timetable t = Scheduler.getTimetablesList(courses, null).get(0);

		assert t.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("10:00")));
		assert t.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("12:00")));

	}

	@Test
	public void test_c() {
		cr = new XmlCourseLoader("schedulerTest3.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		final Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		
		assert s.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("14:00")));
		assert s.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("12:00")));

	}

	@Test
	public void test_c2() {
		cr = new XmlCourseLoader("schedulerTest3.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		final Timetable t = Scheduler.getTimetablesList(courses, null).get(0);

		assert t.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("14:00")));
		assert t.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("12:00")));

	}

	@Test
	public void test_d() {
		cr = new XmlCourseLoader("schedulerTest4.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		final Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		
		assertNull(s);
	}

	@Test
	public void test_d2() {
		cr = new XmlCourseLoader("schedulerTest4.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		assert Scheduler.getTimetablesList(courses, null).isEmpty();

	}

	@Test
	public void test_e() {
		cr = new XmlCourseLoader("schedulerTest7.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		final Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		
		assert s.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("10:00")));
		assert s.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.WEDNESDAY, LocalTime.parse("13:00")));
		assert s.getLessonGroups().get(2).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.TUESDAY, LocalTime.parse("11:00")));
		assert s.getLessonGroups().get(3).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("14:00")));

	}

	@Test
	public void test_e2() {
		cr = new XmlCourseLoader("schedulerTest7.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		final Timetable t = Scheduler.getTimetablesList(courses, null).get(0);

		assert t.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("10:00")));
		assert t.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.WEDNESDAY, LocalTime.parse("13:00")));
		assert t.getLessonGroups().get(2).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.TUESDAY, LocalTime.parse("11:00")));
		assert t.getLessonGroups().get(3).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("14:00")));

	}
	
	@Test
	public void test_constraints1() {
		cr = new XmlCourseLoader("schedulerTest7.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		final List<TimeConstraint> constraints = new ArrayList<>();
		constraints.add(new TimeConstraint(
				new WeekTime(Day.SUNDAY, LocalTime.parse("10:00")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("10:30"))));
		final Timetable t = Scheduler.getTimetablesList(courses, null).get(0);

		assert t.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("10:00")));
		assert t.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.WEDNESDAY, LocalTime.parse("13:00")));
		assert t.getLessonGroups().get(2).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.TUESDAY, LocalTime.parse("11:00")));
		assert t.getLessonGroups().get(3).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("14:00")));

		
	}
	
		
	@Test
	public void test_constraints3() {
		cr = new XmlCourseLoader("schedulerTest7.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		final List<TimeConstraint> constraints = new ArrayList<>();
		constraints.add(new TimeConstraint(
				new WeekTime(Day.SUNDAY, LocalTime.parse("09:00")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("10:00"))));
		final Timetable t = Scheduler.getTimetablesList(courses, constraints).get(0);

		assert t.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("10:00")));
		assert t.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.WEDNESDAY, LocalTime.parse("13:00")));
		assert t.getLessonGroups().get(2).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.TUESDAY, LocalTime.parse("11:00")));
		assert t.getLessonGroups().get(3).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("14:00")));

	}
		
	@Test
	public void test_constraints5() {
		cr = new XmlCourseLoader("schedulerTest7.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		
		final List<TimeConstraint> constraints = new ArrayList<>();
		constraints.add(new TimeConstraint(
				new WeekTime(Day.SUNDAY, LocalTime.parse("09:00")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("10:01"))));
		constraints.add(new TimeConstraint(
				new WeekTime(Day.SUNDAY, LocalTime.parse("10:00")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("10:05"))));
		
		assert Scheduler.getTimetablesList(courses, constraints).isEmpty();
		
	
	}
	

	@Test
	public void test_scheduleTimetable1() {
		cr = new XmlCourseLoader("schedulerTest.XML", true);

		@SuppressWarnings("unused")
		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());

	}

}
