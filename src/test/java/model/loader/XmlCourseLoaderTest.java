package model.loader;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.CourseModel;
import model.Faculty;
import model.course.Course;
import model.course.Lesson;
import model.course.LessonGroup;
import model.course.StuffMember;
import model.course.WeekTime;

public class XmlCourseLoaderTest {
	
	CourseLoader cr;
	
	@Before
	public void initialize() {
		cr = new XmlCourseLoader("REPFILE/test.XML");
		//cr = new XmlCourseLoader("resources/testXML/REP.XML");
	}

	@Test
	public void testLoadAllCourses() {
		//Course course = CourseLoader.loadCourse("àðìéæä ðåîøéú 1");
		TreeMap<String, Course> coursesMap = cr.loadAllCourses();
		/*
		for (LessonGroup lg : coursesMap.get("014146").getTutorialsLG()) {
		    System.out.println(lg.getGroupNum());
		    for (Lesson l : lg.getLessons()) {
		    	System.out.println("type: " + l.getType().toString() + " day: " + l.getDay() + ", time: " + l.getStartTime().getTime().toString() + "-" + l.getEndTime().getTime().toString());
		    }
		    System.out.println("*************");	
		}*/
		/*assert "123456".equals(coursesMap.get("123456").getId());
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
		
		assert "הנדסה אזרחית וסביבתית".equals((coursesMap.get("123456").getFaculty()));
		
		assert ((coursesMap.get("014005").getStuff().size()) == 0);
		assert ((coursesMap.get("014005").getLecturesLG().size()) == 0);
		assert ((coursesMap.get("014005").getTutorialsLG().size()) == 6);
		
		for (int ¢ = 0; ¢ < (coursesMap.get("014005").getTutorialsLG().size()); ++¢)
			assert ((coursesMap.get("014005").getTutorialsLG().get(¢).getGroupNum() == ¢ + 11));
		
		assert (coursesMap.get("014005").getTutorialsLG().get(3).getGroupNum() == 14);
		
		System.out.println(coursesMap.get("014005").getTutorialsLG().get(4).getLessons().get(0).getStartTime());
		System.out.println(coursesMap.get("014005").getTutorialsLG().get(4).getLessons().get(0).getEndTime());
		assert (coursesMap.get("014005").getTutorialsLG().get(3).getLessons().get(0).getDay() == 0);
		
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
		
		System.out.println(coursesMap.get("394820-11").getTutorialsLG().get(0).getLessons().get(0).getEndTime().toString());*/
		
	}
	
	@Test
	public void testLoadedLessons() {
		/*TreeMap<String, Course> coursesMap = cr.loadAllCourses();
		System.out.println("lectures: " + coursesMap.get("123456").getLecturesLG() );
		System.out.println( coursesMap.get("123456").getLecturesLG().get(0).getLessons().get(0).getRepresenter().getLastName() );
		
		assert "טולוז".equals(
				coursesMap.get("123456").getLecturesLG().get(0).getLessons().get(0).getRepresenter().getLastName());
		
		assert coursesMap.get("123456").getLecturesLG().get(0).getLessons().get(0).getStartTime().equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(12, 30)));
		assert coursesMap.get("123456").getLecturesLG().get(0).getLessons().get(0).getEndTime().equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(14, 30)));
		
		assert coursesMap.get("123456").getLecturesLG().get(0).getLessons().get(1).getStartTime().equals(new WeekTime(DayOfWeek.MONDAY, LocalTime.of(12, 30)));
		assert coursesMap.get("123456").getLecturesLG().get(0).getLessons().get(1).getEndTime().equals(new WeekTime(DayOfWeek.MONDAY, LocalTime.of(14, 30)));*/
	}
	
	@Test
	public void testSaveChosenCourseNames() {
		/*List<String> names = new LinkedList<>();
		names.add("מבוא לחתולים");
		names.add("טורי חתולים ופוררריה");
		names.add("'אלרגיות למתקדמים ת");
		cr.saveChosenCourseNames(names);
		assert (new File("data/ChosenCourses.xml")).exists();*/
	}
	
	@Test
	public void testLoadChosenCourseNames() {
		/*List<String> names = new LinkedList<>();
		names.add("מבוא לחתולים");
		names.add("טורי חתולים ופוררריה");
		names.add("'אלרגיות למתקדמים ת");
		cr.saveChosenCourseNames(names);
		assert (new File("data/ChosenCourses.xml")).exists();
		//cr.loadChosenCourseNames().forEach(name -> System.out.println(name));
		assert cr.loadChosenCourseNames().size() == 3;*/
	}
	
	@Test
	public void testLoadFaculties() {
		/*for (Faculty ¢ : cr.loadFaculties())
			System.out.println(¢.getId() + "---" + ¢.getName());*/
	}
	
	@After
	@SuppressWarnings("static-method")
	public void deleteXml() {
		new File("data/ChosenCourses.xml").delete();
	}

	

}
