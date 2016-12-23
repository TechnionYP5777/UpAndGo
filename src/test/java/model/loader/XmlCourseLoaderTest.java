package model.loader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.course.Course;

public class XmlCourseLoaderTest {
	
	CourseLoader cr;
	
	@Before
	public void initialize() {
		cr = new XmlCourseLoader();
	}

	@Test
	public void testLoadAllCourses() {
		//Course course = CourseLoader.loadCourse("àðìéæä ðåîøéú 1");
		TreeMap<String, Course> coursesMap = cr.loadAllCourses();
		assert "648013".equals(coursesMap.get("648013").getId());
		assert "תופעות מעבר ננו מטריות".equals(coursesMap.get("648013").getName());
	}
	
	@Test
	public void testSaveChosenCourseNames() {
		List<String> names = new LinkedList<>();
		names.add("מבוא לחתולים ביוטיוב");
		names.add("תכנות אנכי ומרוכז");
		names.add("תורת התורתיות");
		cr.saveChosenCourseNames(names);
		assert (new File("data/ChosenCourses.xml")).exists();
	}
	
	@Test
	public void testLoadChosenCourseNames() {
		List<String> names = new LinkedList<>();
		names.add("מבוא לחתולים ביוטיוב");
		names.add("תכנות אנכי ומרוכז");
		names.add("תורת התורתיות");
		cr.saveChosenCourseNames(names);
		assert (new File("data/ChosenCourses.xml")).exists();
		cr.loadChosenCourseNames().forEach(name -> System.out.println(name));
		assert cr.loadChosenCourseNames().size() == 3;
	}
	
	@After
	@SuppressWarnings("static-method")
	public void deleteXml() {
		new File("data/ChosenCourses.xml").delete();
	}


}
