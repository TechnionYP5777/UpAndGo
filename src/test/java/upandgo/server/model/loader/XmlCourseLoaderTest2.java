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
		cr = new XmlCourseLoader("schedulerTest6.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		for(Course c : courses){
			System.out.println("course: " + c);
		}
	}

}
