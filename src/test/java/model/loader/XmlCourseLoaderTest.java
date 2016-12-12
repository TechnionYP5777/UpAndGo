package model.loader;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.course.Course;

public class XmlCourseLoaderTest {
	
	CourseLoader cr;
	
	@Before
	public void initialize() {
		cr = new XmlCourseLoader("REPFILE\\REP.XML");
	}

	@Test
	public void testLoadAllCourses() {
		//Course course = CourseLoader.loadCourse("àðìéæä ðåîøéú 1");
		HashMap<String, Course> coursesMap = cr.loadAllCourses();
		assertTrue("648013".equals(coursesMap.get("648013").getId()));
		assertTrue("תופעות מעבר ננו מטריות".equals(coursesMap.get("648013").getName()));
	}
	
	@Test
	public void testSaveChosenCourseNames() {
		List<String> names = new LinkedList<>();
		names.add("מבוא לחתולים ביוטיוב");
		names.add("תכנות אנכי ומרוכז");
		names.add("תורת התורתיות");
		cr.saveChosenCourseNames(names);
		assertTrue((new File("data/ChosenCourses.xml")).exists());
	}

}
