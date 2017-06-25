package upandgo.server.model.course;
/**
 * @author kobybs
 * @since 2-1-17
 */

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.course.Course;

public class LessonTest {

	CourseLoader cr;

	@Test
	public void test_a() {
		cr = new XmlCourseLoader("schedulerTest5.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());

		assert !courses.get(0).getLectures().get(0).getLessons().get(0)
				.IsClashWith(courses.get(0).getTutorials().get(0).getLessons().get(0));
		// assertNull(s);
	}

	@Test
	public void test_b() {
		cr = new XmlCourseLoader("schedulerTest6.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());

		System.out.println("first: " + courses.get(0).getLectures().get(0).getLessons().get(0));
		System.out.println("second: " + courses.get(0).getTutorials().get(0).getLessons().get(0));
		assert courses.get(0).getLectures().get(0).getLessons().get(0)
				.IsClashWith(courses.get(0).getTutorials().get(0).getLessons().get(0));
		// assertNull(s);
	}

}
