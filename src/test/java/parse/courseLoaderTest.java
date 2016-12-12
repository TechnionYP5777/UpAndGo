package parse;

import static org.junit.Assert.*;

import org.junit.Test;

import model.course.Course;
import model.loader.CourseLoader;

public class courseLoaderTest {

	@SuppressWarnings("static-method")
	@Test
	public void testloadCourse() {
		Course course = CourseLoader.loadCourse("אנליזה נומרית 1");
		assertEquals(course.getId(), 234107);
		assertEquals(course.getName(), "אנליזה נומרית 1");
		
		course = CourseLoader.loadCourse("מבוא לכימיה של הקרקע");
		assertEquals(course.getId(), 14956);
		
		course = CourseLoader.loadCourse("נומריזה אנליזית");
		assertNull(course);
		
		assertTrue(true);
	}

}
