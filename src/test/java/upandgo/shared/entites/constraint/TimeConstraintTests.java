package upandgo.shared.entites.constraint;

import upandgo.shared.entities.LocalTime;

import org.junit.Test;

import upandgo.server.model.loader.CourseBuilder;
import upandgo.shared.entities.Day;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.StuffMember;
import upandgo.shared.entities.WeekTime;
import upandgo.shared.entities.Lesson.Type;
import upandgo.shared.entities.constraint.TimeConstraint;
import upandgo.shared.entities.course.Course;


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
	}
	
	@Test
	public void test_b() {
		Lesson lesson = new Lesson(new StuffMember("אולמן", "טאוב"), new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")), new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")), 
				"טאוב 5", Type.LECTURE, 10, "999999", "אנליזה");
		TimeConstraint cons = new TimeConstraint(new WeekTime(Day.SUNDAY, LocalTime.parse("15:30")), new WeekTime(Day.SUNDAY, LocalTime.parse("17:30")));
		TimeConstraint cons2 = new TimeConstraint(new WeekTime(Day.SUNDAY, LocalTime.parse("08:30")), new WeekTime(Day.SUNDAY, LocalTime.parse("17:30")));
		TimeConstraint cons3 = new TimeConstraint(new WeekTime(Day.SUNDAY, LocalTime.parse("08:30")), new WeekTime(Day.SUNDAY, LocalTime.parse("11:30")));
		TimeConstraint cons4 = new TimeConstraint(new WeekTime(Day.SUNDAY, LocalTime.parse("11:30")), new WeekTime(Day.SUNDAY, LocalTime.parse("17:30")));
		TimeConstraint cons5 = new TimeConstraint(new WeekTime(Day.SUNDAY, LocalTime.parse("08:30")), new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")));
		assert !cons.isClashWith(lesson);
		assert cons2.isClashWith(lesson);
		assert cons3.isClashWith(lesson);
		assert cons4.isClashWith(lesson);
		assert !cons5.isClashWith(lesson);
	}
	
	@Test
	public void test_c() {
		LessonGroup lg = new LessonGroup();
		lg.setGroupNum(10);
		lg.setConstrained(true);
		lg.addLesson(new Lesson(new StuffMember("אולמן", "טאוב"), new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")), "טאוב 5", Type.LECTURE, 10, "999999", "אנליזה"));
		TimeConstraint cons2 = new TimeConstraint(new WeekTime(Day.SUNDAY, LocalTime.parse("08:30")), new WeekTime(Day.SUNDAY, LocalTime.parse("17:30")));
		TimeConstraint cons3 = new TimeConstraint(new WeekTime(Day.SUNDAY, LocalTime.parse("08:30")), new WeekTime(Day.SUNDAY, LocalTime.parse("09:30")));
		assert cons2.isClashWith(lg);
		assert !cons3.isClashWith(lg);
	}

}
