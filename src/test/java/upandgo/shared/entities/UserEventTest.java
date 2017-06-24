package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

import upandgo.shared.entities.Lesson.Type;

public class UserEventTest {

	@Test
	public void test() {
		WeekTime time = new WeekTime(Day.SUNDAY, LocalTime.parse("10:30"));
		LocalTime duration = LocalTime.of(12, 30);
		
		UserEvent event = new UserEvent(time, "Lunch", duration);
		assertEquals(event.getWeekTime(), time);
		assertEquals(event.getDescription(), "Lunch");
		assertEquals(event.getDuration(), duration);
	}
	
	@Test
	public void setTest() {
		WeekTime time = new WeekTime(Day.SUNDAY, LocalTime.parse("10:30"));
		LocalTime duration = LocalTime.of(12, 30);
		
		UserEvent event = new UserEvent(time, "Lunch", duration);
		event.setDescription("dinner");
		event.setDuration(LocalTime.of(11, 30));
		event.setTime(new WeekTime(Day.SUNDAY, LocalTime.parse("9:30")));
		assertEquals(event.getWeekTime(), new WeekTime(Day.SUNDAY, LocalTime.parse("9:30")));
		assertEquals(event.getDescription(), "dinner");
		
	}
	
	@Test
	public void eventAsLessonTest() {
		WeekTime time = new WeekTime(Day.SUNDAY, LocalTime.parse("10:30"));
		LocalTime duration = LocalTime.of(2, 00);
		
		UserEvent event = new UserEvent(time, "Lunch", duration);
		Lesson lesson = event.getAsLesson();
		assertNull(lesson.getRepresenter());
		assertEquals(time, lesson.getStartTime());
		assertEquals(new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")), lesson.getEndTime());
		assertEquals("Lunch", lesson.getPlace());
		assertEquals(lesson.getType(), Type.LECTURE);
		assertEquals(999, lesson.getGroup());
		assertEquals("999999", lesson.getCourseId());
		assertEquals("user events", lesson.getCourseName());
		
	}

}
