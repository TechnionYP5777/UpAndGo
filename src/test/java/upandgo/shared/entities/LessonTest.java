package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class LessonTest {

	@Test
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

}
