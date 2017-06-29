package upandgo.server.model.loader;

import static org.junit.Assert.*;

import org.junit.Test;

public class ScheduleEntityTest {

	@Test
	public void test_a() {
		ScheduleEntity sched = new ScheduleEntity();
		assertEquals("", sched.id);
		assertEquals(0, sched.lessons.size());
		ScheduleEntity.Lesson lesson = new ScheduleEntity.Lesson();
		assertEquals(-1, lesson.getGroupNum());
		assertEquals("", lesson.getCourseId());
		assertEquals(960, lesson.hashCode());
		
		ScheduleEntity.Lesson lesson2 = new ScheduleEntity.Lesson("999999", 10);
		ScheduleEntity.Lesson lesson4 = new ScheduleEntity.Lesson("888888", 10);
		ScheduleEntity.Lesson lesson5 = new ScheduleEntity.Lesson("888888", 5);
		ScheduleEntity.Lesson lesson3 = new ScheduleEntity.Lesson(null, 10);
		assert lesson2.equals(lesson2);
		assert !lesson2.equals(null);
		assert !lesson2.equals(sched);
		assert !lesson3.equals(lesson2);
		assert !lesson2.equals(lesson4);
		assert !lesson4.equals(lesson5);
	}
	
	@Test
	public void test_b() {
		ScheduleEntity sched = new ScheduleEntity("999999");
		assertEquals("999999", sched.id);
		sched.setId("888888");
		assertEquals("888888", sched.id);
		sched.addLesson("201602", "999999", 10);
		sched.addLesson("201602", "888888", 5);
		assertEquals(2, sched.getLessons("201602").size());
		assertEquals(0, sched.getLessons("201603").size());
		sched.removeAllLessons("201602");
		assertEquals(0, sched.getLessons("201602").size());
		
		
	}
}
