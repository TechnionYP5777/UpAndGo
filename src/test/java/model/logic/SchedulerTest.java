package model.logic;
/**
 * @author kobybs
 * @since 2-1-17
 */

import static org.junit.Assert.assertNull;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import logic.Scheduler;
import model.constraint.TimeConstraint;
import model.course.Course;
import model.course.WeekTime;
import model.loader.CourseLoader;
import model.loader.XmlCourseLoader;
import model.schedule.Schedule;
import model.schedule.Timetable;

@SuppressWarnings("static-method")
public class SchedulerTest {

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

		final Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		System.out.println(s);

		assert s.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 00)));
		assert s.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(13, 00)));
		assert s.getLessonGroups().get(2).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(11, 00)));
		assert s.getLessonGroups().get(3).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.WEDNESDAY, LocalTime.of(14, 00)));

	}

	@Test
	public void test_a2() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		// List<Timetable> tl = Scheduler.getTimetablesList(courses);
		// System.out.println(tl.size());
		final Timetable t = Scheduler.getTimetablesList(courses).get(0);

		assert t.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 00)));
		assert t.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(13, 00)));
		assert t.getLessonGroups().get(2).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(11, 00)));
		assert t.getLessonGroups().get(3).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.WEDNESDAY, LocalTime.of(14, 00)));

	}

	@Test
	public void test_b() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest2.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		final Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		System.out.println(s);

		assert s.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 00)));
		assert s.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(12, 00)));

	}

	@Test
	public void test_b2() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest2.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		final Timetable t = Scheduler.getTimetablesList(courses).get(0);

		assert t.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 00)));
		assert t.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(12, 00)));

	}

	@Test
	public void test_c() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest3.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		final Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		System.out.println(s);

		assert s.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(14, 00)));
		assert s.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(12, 00)));

	}

	@Test
	public void test_c2() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest3.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		final Timetable t = Scheduler.getTimetablesList(courses).get(0);

		assert t.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(14, 00)));
		assert t.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(12, 00)));

	}

	@Test
	public void test_d() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest4.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		final Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		System.out.println(s);

		assertNull(s);
	}

	@Test
	public void test_d2() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest4.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		assert Scheduler.getTimetablesList(courses).isEmpty();

	}

	@Test
	public void test_e() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest7.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		final Schedule s = Scheduler.schedule(courses, new ArrayList<TimeConstraint>());
		System.out.println(s);

		assert s.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 00)));
		assert s.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.WEDNESDAY, LocalTime.of(13, 00)));
		assert s.getLessonGroups().get(2).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(11, 00)));
		assert s.getLessonGroups().get(3).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(14, 00)));

		// assertNull(s);
	}

	@Test
	public void test_e2() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest7.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		final Timetable t = Scheduler.getTimetablesList(courses).get(0);

		assert t.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 00)));
		assert t.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.WEDNESDAY, LocalTime.of(13, 00)));
		assert t.getLessonGroups().get(2).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(11, 00)));
		assert t.getLessonGroups().get(3).getLessons().get(0).getStartTime()
				.equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(14, 00)));

		// assertNull(s);
	}

	@Test
	public void test_scheduleTimetable1() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		System.out.println(Scheduler.getTimetablesList(courses));
	}

}
