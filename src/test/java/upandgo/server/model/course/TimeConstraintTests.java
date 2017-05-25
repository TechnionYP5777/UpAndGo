package upandgo.server.model.course;

import upandgo.shared.entities.LocalTime;

import org.junit.Test;

import upandgo.server.model.loader.CourseBuilder;
import upandgo.shared.entities.Day;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.StuffMember;
import upandgo.shared.entities.WeekTime;
import upandgo.shared.entities.Lesson.Type;
import upandgo.shared.entities.course.Course;

@SuppressWarnings("static-method")
public class TimeConstraintTests {

	@Test
	public void test_a() {
		@SuppressWarnings("unused")
		final Course c = new CourseBuilder().setId("1234").setName("first")
				.addLesson(
						new Lesson(new StuffMember("koby", "bs"), new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")),
								new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")), "Taub", Type.LECTURE, 21, "1234",
								"first"))
				.build();

		// TreeMap<String, Course> coursesMap = cr.loadAllCourses();
		// assert "648013".equals(coursesMap.get("648013").getId());
		// Course.CourseBuilder cb = new CourseBuilder();
		// cb.setId("1234").setName("first").addLesson(xxx).build();
	}

}
