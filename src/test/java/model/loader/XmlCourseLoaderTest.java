package model.loader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.course.Course;
import model.course.Lesson;
import model.course.LessonGroup;
import model.course.StuffMember;

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
		
		assert "014536".equals(coursesMap.get("014536").getId());
		assert "טורי חתולים ופוררריה".equals(coursesMap.get("014536").getName());
		
		assert "678910".equals(coursesMap.get("678910").getId());
		assert "'אלרגיות למתקדמים ת".equals(coursesMap.get("678910").getName());
		assert "2016-12-11T13:00".equals((coursesMap.get("678910").getaTerm() + ""));
		
		assert "234107".equals(coursesMap.get("234107").getId());
		assert "אנליזה נמרית".equals(coursesMap.get("234107").getName());
		/*
		for (StuffMember i : coursesMap.get("678910").getStuff()) {
			System.out.println(i.getFirstName());
			System.out.println(i.getLastName());
			System.out.println(i.getTitle());
		}
		
		for (LessonGroup i : coursesMap.get("014536").getLecturesLG()) {
			System.out.println(i.getGroupNum());
			System.out.println("**********");
			for (Lesson j : i.getLessons()) {
				System.out.println(j.getDay());
				System.out.println(j.getPlace());
				System.out.println((j.getType() + ""));
				System.out.println((j.getRepresenter().getFirstName()));
				System.out.println((j.getRepresenter().getLastName()));
			}
			System.out.println("**********");
		}
		
		for (LessonGroup i : coursesMap.get("234107").getTutorialsLG()) {
			System.out.println(i.getGroupNum());
			System.out.println("**********");
			for (Lesson j : i.getLessons()) {
				System.out.println(j.getDay());
				System.out.println(j.getPlace());
				System.out.println((j.getType() + ""));
				System.out.println((j.getRepresenter().getFirstName()));
				System.out.println((j.getRepresenter().getLastName()));
			}
			System.out.println("**********");
		}*/
		
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
