package model.loader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.course.Course;

import model.course.StuffMember;

public class XmlCourseLoaderTest {
	
	CourseLoader cr;
	
	@Before
	public void initialize() {
		//cr = new XmlCourseLoader("REPFILE/REP.XML");
		cr = new XmlCourseLoader("resources/testXML/REP.XML");
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
		assert "אלרגיות למתקדמים ת'".equals(coursesMap.get("678910").getName());
		assert "2016-12-11T13:00".equals((coursesMap.get("678910").getaTerm() + ""));
		
		assert "234107".equals(coursesMap.get("234107").getId());
		assert "אנליזה נמרית".equals(coursesMap.get("234107").getName());
		
		assert ((coursesMap.get("123456").getStuff()).size() == 7);
		assert ((coursesMap.get("014536").getStuff()).size() == 5);
		assert ((coursesMap.get("678910").getStuff()).size() == 3);
		assert ((coursesMap.get("234107").getStuff()).size() == 6);
		
		assert ((coursesMap.get("123456").getStuff()).get(2)).equals((new StuffMember("תומס", "אומאלי", "דר")));
		assert ((coursesMap.get("014536").getStuff()).get(0)).equals((new StuffMember("מיאו", "דזה-דונג", "פרופ")));
		assert ((coursesMap.get("678910").getStuff()).get(2)).equals((new StuffMember("", "א.ב.יהושוע", "מר")));
		
		assert ((coursesMap.get("123456").getLecturesLG().size() == 2));
		for (int ¢ = 0; ¢ < (coursesMap.get("123456").getLecturesLG().size()); ++¢)
			assert ((coursesMap.get("123456").getLecturesLG().get(¢).getGroupNum() == ¢ + 1));
		
		for (int ¢ = 0; ¢ < (coursesMap.get("234107").getLecturesLG().size()); ++¢)
			assert ((coursesMap.get("234107").getLecturesLG().get(¢).getGroupNum() == ¢ + 1));
		
		assert ((coursesMap.get("014536").getTutorialsLG().size() == 2));
		assert ((coursesMap.get("014536").getTutorialsLG().get(0).getGroupNum() == 10));
		assert ((coursesMap.get("014536").getTutorialsLG().get(1).getGroupNum() == 12));
		
	}
	
	/*@Test
	public void testLesson() {
		TreeMap<String, Course> coursesMap = cr.loadAllCourses();
		System.out.println("lectures: " + coursesMap.get("123456").getLecturesLG() );
		//assert "123456".equals(coursesMap.get("123456").getId());
		
		assert ((coursesMap.get("123456").getLecturesLG().size() == 2));
	}*/
	
	@Test
	public void testSaveChosenCourseNames() {
		List<String> names = new LinkedList<>();
		names.add("מבוא לחתולים");
		names.add("טורי חתולים ופוררריה");
		names.add("'אלרגיות למתקדמים ת");
		cr.saveChosenCourseNames(names);
		assert (new File("data/ChosenCourses.xml")).exists();
	}
	
	@Test
	public void testLoadChosenCourseNames() {
		List<String> names = new LinkedList<>();
		names.add("מבוא לחתולים");
		names.add("טורי חתולים ופוררריה");
		names.add("'אלרגיות למתקדמים ת");
		cr.saveChosenCourseNames(names);
		assert (new File("data/ChosenCourses.xml")).exists();
		//cr.loadChosenCourseNames().forEach(name -> System.out.println(name));
		assert cr.loadChosenCourseNames().size() == 3;
	}
	
	@After
	@SuppressWarnings("static-method")
	public void deleteXml() {
		new File("data/ChosenCourses.xml").delete();
	}


}
