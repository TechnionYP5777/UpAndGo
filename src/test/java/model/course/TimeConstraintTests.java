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
		@SuppressWarnings("unused")
		final
		Course c = new CourseBuilder().setId("1234").setName("first")
				.addLesson(new Lesson(new StuffMember("koby", "bs"),
						new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 30)),
						new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 30)), "Taub", Type.LECTURE, 21, "1234", "first"))
				.build();
		
		//TreeMap<String, Course> coursesMap = cr.loadAllCourses();
		//assert "648013".equals(coursesMap.get("648013").getId());
		//Course.CourseBuilder cb = new CourseBuilder();
		//cb.setId("1234").setName("first").addLesson(¢).build();
	}

}
