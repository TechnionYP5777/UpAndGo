package model.loader;


import java.util.TreeMap;

import org.junit.Test;

import model.course.Course;

@Deprecated
@SuppressWarnings({"deprecation","static-method"})
public class KLoaderTest {

	
	@Test
	public void test() {
		//Course course = CourseLoader.loadCourse("àðìéæä ðåîøéú 1");
		CourseLoader cr = new KLoader("REPFILE\\REP.XML");
		TreeMap<String, Course> coursesMap = cr.loadAllCourses();
		assert("648013".equals(coursesMap.get("648013").getId()));
		assert("תופעות מעבר ננו מטריות".equals(coursesMap.get("648013").getName()));
		
		//fail("Not yet implemented");
	}

}