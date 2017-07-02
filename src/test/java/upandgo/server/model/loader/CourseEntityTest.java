package upandgo.server.model.loader;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import upandgo.shared.entities.course.Course;

public class CourseEntityTest {
	
	CourseLoader cr;
	
	@Test
	@SuppressWarnings("static-method")
	public void test_a() {
		CoursesEntity course = new CoursesEntity();
		assertEquals("", course.id);
		assertEquals(0, course.courses.size());
		CoursesEntity course2 = new CoursesEntity("999999");
		assertEquals("999999", course2.id);
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void test_b() {
		CoursesEntity course = new CoursesEntity();
		course.setId("888888");
		assertEquals("888888", course.id);
	}
	
	@Test
	public void test_c() {
		CoursesEntity course = new CoursesEntity();
		cr = new XmlCourseLoader("schedulerTest.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		for (Course i : courses) { 
			course.addCourse("201602", i.getId());
		}
		assertEquals(2, course.getCourses("201602").size());
		assertEquals(0, course.getCourses("201603").size());
		course.removeCourse("201602", "111111");
		assertEquals(1, course.getCourses("201602").size());
		course.removeAllCourses("201602");
		assertEquals(0, course.getCourses("201602").size());
	}
	

}
