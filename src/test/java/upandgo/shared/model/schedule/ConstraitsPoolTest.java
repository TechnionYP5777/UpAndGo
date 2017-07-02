package upandgo.shared.model.schedule;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import upandgo.shared.entities.Day;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.LocalTime;
import upandgo.shared.model.scedule.ConstraintsPool;
import upandgo.shared.model.scedule.ConstraintsPool.CourseConstraint;

public class ConstraitsPoolTest {

	@Test
	@SuppressWarnings({ "static-method", "unused" })
	public void test_a() {
		new ConstraintsPool().new CourseConstraint();
		CourseConstraint cons = new ConstraintsPool().new CourseConstraint(true, 10, false, 4),
				cons2 = new ConstraintsPool().new CourseConstraint(false, 10, true, 4),
				cons3 = new ConstraintsPool().new CourseConstraint(cons2);
		cons3.setSpecificLesson(Lesson.Type.LECTURE, true, 5);
		assert cons3.isSpecificLecture();
		assertEquals(5, cons3.getLectureLessonGroup());
		cons3.setSpecificLesson(Lesson.Type.TUTORIAL, false, 5);
		assert !cons3.isSpecificTutorial();
		assert cons.isSpecificLecture();
		assertEquals(10, cons.getLectureLessonGroup());
		assert !cons.isSpecificTutorial();
		assertEquals(4, cons.getTutorialLessonGroup());
		assertEquals("Specific Lecture Group: 10 ", cons.toString());
		assertEquals("Specific Tutorial Group: 4", cons2.toString());
	}
	
	@Test
	@SuppressWarnings({ "static-method", "boxing" })
	public void test_b() {
		ConstraintsPool pool = new ConstraintsPool();
		assert !pool.isDaysoffCount();
		assert !pool.isBlankSpaceCount();
		assertNull(pool.getMinStartTime());
		assertNull(pool.getMaxFinishTime());
		assertEquals(5, pool.getVectorDaysOff().size());
		assert pool.getCourseConstraints().isEmpty();
		ConstraintsPool pool2 = new ConstraintsPool(true, true, LocalTime.of(9, 30), LocalTime.of(10, 30));
		pool2.setCourseConstraint("999999", Lesson.Type.LECTURE, true, 10);
		pool2.setCourseConstraint("999999", true, 5, false, 5);
		pool2.setCourseConstraint("888888", true, 5, false, 5);
		assert pool2.isDaysoffCount();
		assert pool2.isBlankSpaceCount();
		ConstraintsPool pool3 = new ConstraintsPool(pool2);
		pool3.setDaysoffCount(false);
		pool3.setBlankSpaceCount(true);
		pool3.setMinStartTime(LocalTime.of(11, 30));
		pool3.setMaxFinishTime(LocalTime.of(13, 30));
		pool3.addVectorDaysOff(new ArrayList<>(Arrays.asList(true, true, true, true, true)));
		pool3.setDayOff(Day.SUNDAY, false);
	}
	
}
