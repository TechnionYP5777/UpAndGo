package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

import upandgo.shared.entities.Lesson.Type;
import upandgo.shared.entities.constraint.TimeConstraint;

public class LessonGroupTest {

	@Test
	public void emptyConstructorTest() {
		LessonGroup lg = new LessonGroup();
		
		assertEquals(-1, lg.getGroupNum());
		assert !lg.isConstrained();
		assert lg.getLessons().isEmpty();
	}
	
	@Test
	public void copyConstructorTest() {
		LessonGroup lg = new LessonGroup();
		lg.setGroupNum(10);
		lg.setConstrained(true);
		lg.addLesson(new Lesson(new StuffMember("אולמן", "טאוב"), new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")), "טאוב 5", Type.LECTURE, 10, "999999", "אנליזה"));
		
		LessonGroup copy = new LessonGroup(lg);
		assertEquals(10, copy.getGroupNum());
		assert copy.isConstrained();
		assertEquals(1, copy.getLessons().size());

	}
	
	@Test
	public void getCourseIDTest() {
		LessonGroup lg = new LessonGroup();
		assertEquals("000000", lg.getCourseID());
		lg.setGroupNum(10);
		lg.setConstrained(true);
		lg.addLesson(new Lesson(new StuffMember("אולמן", "טאוב"), new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")), "טאוב 5", Type.LECTURE, 10, "999999", "אנליזה"));
		
		assertEquals("999999", lg.getCourseID());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addLessonTest() {
		(new LessonGroup()).addLesson(new Lesson(new StuffMember("אולמן", "טאוב"),
				new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")), new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")),
				"טאוב 5", Type.LECTURE, 10, "999999", "אנליזה"));
		
	}
	
	@Test
	public void isClashWithLessonGroupTest() {
		LessonGroup lg = new LessonGroup();
		lg.setGroupNum(10);
		lg.setConstrained(true);
		lg.addLesson(new Lesson(new StuffMember("אולמן", "טאוב"), new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")), "טאוב 5", Type.LECTURE, 10, "999999", "אנליזה"));
		
		assert lg.isClashWith(lg);
	}
	
	@Test
	public void isClashWithTimeConstraintTest() {
		LessonGroup lg = new LessonGroup();
		lg.setGroupNum(10);
		lg.setConstrained(true);
		lg.addLesson(new Lesson(new StuffMember("אולמן", "טאוב"), new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")), "טאוב 5", Type.LECTURE, 10, "999999", "אנליזה"));
		TimeConstraint cons2 = new TimeConstraint(new WeekTime(Day.SUNDAY, LocalTime.parse("08:30")), new WeekTime(Day.SUNDAY, LocalTime.parse("17:30")));
		TimeConstraint cons3 = new TimeConstraint(new WeekTime(Day.SUNDAY, LocalTime.parse("08:30")), new WeekTime(Day.SUNDAY, LocalTime.parse("09:30")));
		assert lg.isClashWith(cons2);
		assert !lg.isClashWith(cons3);
	}
	
	@Test
	public void equalsTest() {
		LessonGroup lg = new LessonGroup();
		lg.setGroupNum(10);
		lg.setConstrained(true);
		lg.addLesson(new Lesson(new StuffMember("אולמן", "טאוב"), new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")), "טאוב 5", Type.LECTURE, 10, "999999", "אנליזה"));
		LessonGroup copy = new LessonGroup(lg);
		assert !lg.equals(null);
		assert lg.equals(lg);
		assert !lg.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")));
		assert lg.equals(copy);
	}

}
