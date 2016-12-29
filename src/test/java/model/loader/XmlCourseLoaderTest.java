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
		assert "123456".equals(coursesMap.get("123456").getId());
		assert "מבוא לחתולים".equals(coursesMap.get("123456").getName());
		assert "3.0".equals(String.valueOf(coursesMap.get("123456").getPoints()));
		assert "2016-06-29T09:00".equals((coursesMap.get("123456").getaTerm() + ""));
		assert "2016-09-12T09:00".equals((coursesMap.get("123456").getbTerm() + ""));
	}
	/*
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
*/

}
