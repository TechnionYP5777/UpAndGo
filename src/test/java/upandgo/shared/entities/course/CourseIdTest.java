package upandgo.shared.entities.course;

import static org.junit.Assert.*;

import org.junit.Test;

import upandgo.shared.entities.Exam;

public class CourseIdTest {

	@Test
	public void emptyConstructorTest() {
		CourseId course = new CourseId();
		assertEquals("", course.name());
		assertEquals("", course.number());
		assertNull(course.aTerm());
		assertNull(course.bTerm());
	}
	
	@Test
	public void equalsTest() {
		CourseId course = new CourseId("999999", "אנליזה", new Exam("13:00 08 08 2017 4"), new Exam("13:00 08 10 2017 4"));
		CourseId course2 = new CourseId("999899", "בלה", new Exam("13:00 10 07 2017 4"), new Exam("13:00 13 12 2017 4"));
		CourseId course3 = new CourseId(null, null, new Exam("13:00 08 08 2017 4"), new Exam("13:00 08 10 2017 4"));
		CourseId course4 = new CourseId(null, "בלה", new Exam("13:00 08 08 2017 4"), new Exam("13:00 08 10 2017 4"));
		
		
		assert !course.equals(null);
		assert course.equals(course);
		assert !course.equals(new Exam("13:00 08 08 2017 4"));
		assert !course3.equals(course2);
		assert !course2.equals(course3);
		assert !course2.equals(course4);
		assert !course4.equals(course2);
	}
	
	@Test
	public void Testc() {
		CourseId course = new CourseId("999999", "אנליזה", new Exam("13:00 08 08 2017 4"), new Exam("13:00 08 10 2017 4"));

		assertEquals("999999 - אנליזה", course.getTitle());
	}
	
	@Test
	public void compareTest() {
		CourseId course = new CourseId("999999", "אנליזה", new Exam("13:00 08 08 2017 4"), new Exam("13:00 08 10 2017 4"));
		CourseId course2 = new CourseId("999899", "בלה", new Exam("13:00 10 07 2017 4"), new Exam("13:00 13 12 2017 4"));
				
		assertEquals(1, course.compareTo(course2));
		assertEquals(-1, course2.compareTo(course));
		assertEquals(0, course.compareTo(course));
	}

}
