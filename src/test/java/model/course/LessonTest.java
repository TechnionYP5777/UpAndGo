package model.course;
/**
 * @author kobybs
 * @since 2-1-17
 */


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.loader.CourseLoader;
import model.loader.XmlCourseLoader;

public class LessonTest {

CourseLoader cr;
	
	
	
	@Test
	public void test_a() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest5.XML");
		
		List<Course> courses = new ArrayList<>(cr.loadAllCourses().values());

		assert !courses.get(0).getLectures().get(0).getLessons().get(0)
				.IsClashWith(courses.get(0).getTutorials().get(0).getLessons().get(0));
		//assertNull(s);
	}
	
	@Test
	public void test_b() {
		cr = new XmlCourseLoader("resources/testXML/schedulerTest6.XML");
		
		List<Course> courses = new ArrayList<>(cr.loadAllCourses().values());

		assert courses.get(0).getLectures().get(0).getLessons().get(0)
				.IsClashWith(courses.get(0).getTutorials().get(0).getLessons().get(0));
		//assertNull(s);
	}

}
