package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

import upandgo.shared.entities.Lesson.Type;
import upandgo.shared.entities.constraint.TimeConstraint;

public class LessonTest {

	@Test
	@SuppressWarnings("static-method")
	public void EmptyConstructorTest() {
		Lesson lesson = new Lesson();
		assertNull(lesson.getRepresenter());
		assertNull(lesson.getStartTime());
		assertNull(lesson.getEndTime());
		assertNull(lesson.getPlace());
		assertNull(lesson.getCourseId());
		assertNull(lesson.getCourseName());
		assertEquals(0, lesson.getGroup());
	}
	

	@Test
	@SuppressWarnings("static-method")
	public void TypeTest() {
		assertNull(Lesson.Type.fromString(null));
		assertNull(Lesson.Type.fromString("בלה בלה"));
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void copyConstructorTest() {
		Lesson lesson = new Lesson(new StuffMember("אולמן", "טאוב"), new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")), "טאוב 5", Type.LECTURE, 10, "999999", "אנליזה");
		Lesson copy = new Lesson(lesson);
		assertEquals(new StuffMember("אולמן", "טאוב"), copy.getRepresenter());
		assertEquals(new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")), copy.getStartTime());
		assertEquals(new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")), copy.getEndTime());
		assertEquals("טאוב 5", copy.getPlace());
		assertEquals("999999", copy.getCourseId());
		assertEquals("אנליזה", copy.getCourseName());
		assertEquals(10, copy.getGroup());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void getRoomNumberTest() {
		assertEquals("5",
				new Lesson(new StuffMember("אולמן", "טאוב"), new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")),
						new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")), "טאוב 5", Type.LECTURE, 10, "999999",
						"אנליזה").getRoomNumber());
		assertEquals("",
				(new Lesson(new StuffMember("אולמן", "טאוב"), new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")),
						new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")), "", Type.LECTURE, 10, "999999", "אנליזה"))
								.getRoomNumber());
	}

	@Test
	@SuppressWarnings("static-method")
	public void equalsTest() {
		Lesson lesson = new Lesson(new StuffMember("אולמן", "טאוב"), new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")), "טאוב 5", Type.LECTURE, 10, "999999", "אנליזה");
		Lesson copy = new Lesson(lesson);
		Lesson lesson2 = new Lesson(new StuffMember("פישבך", "מאייר"),
				new WeekTime(Day.MONDAY, LocalTime.parse("11:30")), new WeekTime(Day.MONDAY, LocalTime.parse("13:30")),
				"אולמן 11", Type.TUTORIAL, 111, "999989", "דמה");
		assert !lesson.equals(null);
		assert lesson.equals(lesson);
		assert !lesson.equals(new StuffMember("אולמן", "טאוב"));
		assert lesson.equals(copy);
		assert !lesson.equals(lesson2);
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void IsClashWithTest() {
		Lesson lesson = new Lesson(new StuffMember("אולמן", "טאוב"), new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")), "טאוב 5", Type.LECTURE, 10, "999999", "אנליזה");
		TimeConstraint cons = new TimeConstraint(new WeekTime(Day.SUNDAY, LocalTime.parse("15:30")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("17:30")));
		TimeConstraint cons2 = new TimeConstraint(new WeekTime(Day.SUNDAY, LocalTime.parse("08:30")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("17:30")));
		TimeConstraint cons3 = new TimeConstraint(new WeekTime(Day.SUNDAY, LocalTime.parse("08:30")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("11:30")));
		TimeConstraint cons4 = new TimeConstraint(new WeekTime(Day.SUNDAY, LocalTime.parse("11:30")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("17:30")));
		TimeConstraint cons5 = new TimeConstraint(new WeekTime(Day.SUNDAY, LocalTime.parse("08:30")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")));
		assert !lesson.IsClashWith(cons);
		assert lesson.IsClashWith(cons2);
		assert lesson.IsClashWith(cons3);
		assert lesson.IsClashWith(cons4);
		assert !lesson.IsClashWith(cons5);
	}
	
	
}
