package upandgo.server.model.loader;
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

public class XmlCourseLoaderTest2 {

	CourseLoader cr;


	@Test
	public void test_b() {
		cr = new XmlCourseLoader("schedulerTest6.XML");

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		for(Course c : courses){
			System.out.println("course: " + c);
		}
		/*
		System.out.println("first: " + courses.get(0).getLectures().get(0).getLessons().get(0));
		System.out.println("second: " + courses.get(0).getTutorials().get(0).getLessons().get(0));
		assert courses.get(0).getLectures().get(0).getLessons().get(0)
				.IsClashWith(courses.get(0).getTutorials().get(0).getLessons().get(0));
		*/
		// assertNull(s);
	}

}
