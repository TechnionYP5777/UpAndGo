package model.course;


import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.Test;

import model.course.Course.CourseBuilder;
import model.course.Lesson.Type;

@SuppressWarnings("static-method")
public class TimeConstraintTests {

	@Test
	public void test_a() {
		Course.CourseBuilder cb = new CourseBuilder();
		// Create some lesson:
		StuffMember sm = new StuffMember("koby", "bs");
		WeekTime startTime = new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 30));
		WeekTime endTime = new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 30));
		int dur = 2;
		String place1 = "Taub";
		Type t = Type.LECTURE;
		int group = 21;
		Lesson l = new Lesson(sm, startTime, endTime, dur, place1, t, group, null);
		Course c = cb.setId("1234").setName("first").addLesson(l).build();
		
		//TreeMap<String, Course> coursesMap = cr.loadAllCourses();
		//assert "648013".equals(coursesMap.get("648013").getId());
		//Course.CourseBuilder cb = new CourseBuilder();
		//cb.setId("1234").setName("first").addLesson(¢).build();
	}

}
