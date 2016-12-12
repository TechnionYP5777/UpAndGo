package model.loader;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import model.course.Course;

public class XmlCourseLoaderTest {

	@Test
	public void test() {
		//Course course = CourseLoader.loadCourse("àðìéæä ðåîøéú 1");
		CourseLoader cr = new XmlCourseLoader("REPFILE\\REP.XML");
		HashMap<String, Course> coursesMap = cr.loadAllCourses();
		assert(coursesMap.get("648013").getId().equals("648013"));
		assert(coursesMap.get("648013").getName().equals("תופעות מעבר ננו מטריות"));
		
		//fail("Not yet implemented");
	}

}
