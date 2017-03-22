package model.loader;

import static org.junit.Assert.assertNull;

import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Faculty;
import model.course.Course;

public class XmlCourseLoaderTest {

	CourseLoader cr;

	@Before
	public void initialize() {
		cr = new XmlCourseLoader("resources/testXML/test.XML");
		// cr = new XmlCourseLoader("resources/testXML/REP.XML");
	}

	@Test
	public void testLoadAllCourses() {
		// Course course = CourseLoader.loadCourse("àðìéæä ðåîøéú 1");
		final TreeMap<String, Course> coursesMap = cr.loadAllCoursesById();

		assert "שיטות במיקרוסקופיה אופטית ביו-רפו".equals(coursesMap.get("338534").getName());
		assert "טי.אר.אקס-מעורב".equals(coursesMap.get("394800-13").getName());
		assert "פיסיקה 2ממ".equals(coursesMap.get("114075").getName());
		assert "מעבר חום".equals(coursesMap.get("034041").getName());
		assert "חשבון דיפרנציאלי ואינטגרלי 2מ'".equals(coursesMap.get("104022").getName());

		assert "205822".equals(coursesMap.get("205822").getId());
		assert "014411".equals(coursesMap.get("014411").getId());
		assert "315051".equals(coursesMap.get("315051").getId());
		assert "394902-37".equals(coursesMap.get("394902-37").getId());
		assert "234107".equals(coursesMap.get("234107").getId());

		assert "2.5".equals(String.valueOf(coursesMap.get("096808").getPoints()));
		assert "4.5".equals(String.valueOf(coursesMap.get("234123").getPoints()));
		assert "1.5".equals(String.valueOf(coursesMap.get("394902-11").getPoints()));
		assert "2.0".equals(String.valueOf(coursesMap.get("066246").getPoints()));
		assert "5.0".equals(String.valueOf(coursesMap.get("125802").getPoints()));

		assert "הנדסת מכונות".equals(String.valueOf(coursesMap.get("034044").getFaculty()));
		assert "מקצועות ספורט".equals(String.valueOf(coursesMap.get("394902-33").getFaculty()));
		assert "הנדסת חשמל".equals(String.valueOf(coursesMap.get("044202").getFaculty()));
		assert "רפואה".equals(String.valueOf(coursesMap.get("274349").getFaculty()));
		assert "הנדסת תעשיה וניהול".equals(String.valueOf(coursesMap.get("097120").getFaculty()));

		assert "2017-07-13T00:00".equals(coursesMap.get("134153").getaTerm() + "");
		assert "2017-07-18T00:00".equals(coursesMap.get("035022").getaTerm() + "");
		assertNull(coursesMap.get("205719").getaTerm());
		assert "2017-07-09T00:00".equals(coursesMap.get("014852").getaTerm() + "");
		assert "2017-07-12T00:00".equals(coursesMap.get("236635").getaTerm() + "");

		assert "2017-09-25T00:00".equals(coursesMap.get("044148").getbTerm() + "");
		assertNull(coursesMap.get("394820-12").getaTerm());
		assert "2017-09-25T00:00".equals(coursesMap.get("014003").getbTerm() + "");
		assert "2017-10-17T00:00".equals(coursesMap.get("095113").getbTerm() + "");
		assert "2017-10-03T00:00".equals(coursesMap.get("236353").getbTerm() + "");

		assert coursesMap.get("236350").getStuff().size() == 1;
		assert "ג.נקבלי".equals(coursesMap.get("236350").getStuff().get(0).getLastName());

		assert coursesMap.get("324975").getStuff().size() == 1;
		assert "א.פסקוביץ".equals(coursesMap.get("324975").getStuff().get(0).getLastName());

		assert coursesMap.get("044191").getStuff().isEmpty();

		assert coursesMap.get("034039").getStuff().size() == 2;

		assert "הצימוד החשמלי-מכני בשרירי הלב"
				.equals(coursesMap.get("338515").getLecturesLG().get(0).getLessons().get(0).getCourseName());
		/*
		 * for (LessonGroup lg : coursesMap.get("014146").getTutorialsLG()) {
		 * System.out.println(lg.getGroupNum()); for (Lesson l :
		 * lg.getLessons()) { System.out.println("type: " +
		 * l.getType().toString() + " day: " + l.getDay() + ", time: " +
		 * l.getStartTime().getTime().toString() + "-" +
		 * l.getEndTime().getTime().toString()); }
		 * System.out.println("*************"); }
		 */
		/*
		 * assert "123456".equals(coursesMap.get("123456").getId()); assert
		 * "מבוא לחתולים".equals(coursesMap.get("123456").getName()); assert
		 * "3.0".equals(String.valueOf(coursesMap.get("123456").getPoints()));
		 * 
		 * assert "2016-06-29T09:00".equals((coursesMap.get("123456").getaTerm()
		 * + "")); assert
		 * "2016-09-12T09:00".equals((coursesMap.get("123456").getbTerm() +
		 * ""));
		 * 
		 * assert "014536".equals(coursesMap.get("014536").getId()); assert
		 * "טורי חתולים ופוררריה".equals(coursesMap.get("014536").getName());
		 * 
		 * assert "678910".equals(coursesMap.get("678910").getId()); assert
		 * "אלרגיות למתקדמים ת'".equals(coursesMap.get("678910").getName());
		 * assert "2016-12-11T13:00".equals((coursesMap.get("678910").getaTerm()
		 * + ""));
		 * 
		 * assert "234107".equals(coursesMap.get("234107").getId()); assert
		 * "אנליזה נמרית".equals(coursesMap.get("234107").getName());
		 * 
		 * assert ((coursesMap.get("123456").getStuff()).size() == 7); assert
		 * ((coursesMap.get("014536").getStuff()).size() == 5); assert
		 * ((coursesMap.get("678910").getStuff()).size() == 3); assert
		 * ((coursesMap.get("234107").getStuff()).size() == 6);
		 * 
		 * assert
		 * "הנדסה אזרחית וסביבתית".equals((coursesMap.get("123456").getFaculty()
		 * ));
		 * 
		 * assert ((coursesMap.get("014005").getStuff().size()) == 0); assert
		 * ((coursesMap.get("014005").getLecturesLG().size()) == 0); assert
		 * ((coursesMap.get("014005").getTutorialsLG().size()) == 6);
		 * 
		 * for (int ¢ = 0; ¢ <
		 * (coursesMap.get("014005").getTutorialsLG().size()); ++¢) assert
		 * ((coursesMap.get("014005").getTutorialsLG().get(¢).getGroupNum() == ¢
		 * + 11));
		 * 
		 * assert
		 * (coursesMap.get("014005").getTutorialsLG().get(3).getGroupNum() ==
		 * 14);
		 * 
		 * System.out.println(coursesMap.get("014005").getTutorialsLG().get(4).
		 * getLessons().get(0).getStartTime());
		 * System.out.println(coursesMap.get("014005").getTutorialsLG().get(4).
		 * getLessons().get(0).getEndTime()); assert
		 * (coursesMap.get("014005").getTutorialsLG().get(3).getLessons().get(0)
		 * .getDay() == 0);
		 * 
		 * assert ((coursesMap.get("123456").getStuff()).get(2)).equals((new
		 * StuffMember("תומס", "אומאלי", "דר"))); assert
		 * ((coursesMap.get("014536").getStuff()).get(0)).equals((new
		 * StuffMember("מיאו", "דזה-דונג", "פרופ"))); assert
		 * ((coursesMap.get("678910").getStuff()).get(2)).equals((new
		 * StuffMember("", "א.ב.יהושוע", "מר")));
		 * 
		 * assert ((coursesMap.get("123456").getLecturesLG().size() == 2)); for
		 * (int ¢ = 0; ¢ < (coursesMap.get("123456").getLecturesLG().size());
		 * ++¢) assert
		 * ((coursesMap.get("123456").getLecturesLG().get(¢).getGroupNum() == ¢
		 * + 1));
		 * 
		 * for (int ¢ = 0; ¢ <
		 * (coursesMap.get("234107").getLecturesLG().size()); ++¢) assert
		 * ((coursesMap.get("234107").getLecturesLG().get(¢).getGroupNum() == ¢
		 * + 1));
		 * 
		 * assert ((coursesMap.get("014536").getTutorialsLG().size() == 2));
		 * assert
		 * ((coursesMap.get("014536").getTutorialsLG().get(0).getGroupNum() ==
		 * 10)); assert
		 * ((coursesMap.get("014536").getTutorialsLG().get(1).getGroupNum() ==
		 * 12));
		 * 
		 * System.out.println(coursesMap.get("394820-11").getTutorialsLG().get(0
		 * ).getLessons().get(0).getEndTime().toString());
		 */

	}

	@Test
	public void testLoadedLessons() {
		/*
		 * TreeMap<String, Course> coursesMap = cr.loadAllCourses();
		 * System.out.println("lectures: " +
		 * coursesMap.get("123456").getLecturesLG() ); System.out.println(
		 * coursesMap.get("123456").getLecturesLG().get(0).getLessons().get(0).
		 * getRepresenter().getLastName() );
		 * 
		 * assert "טולוז".equals(
		 * coursesMap.get("123456").getLecturesLG().get(0).getLessons().get(0).
		 * getRepresenter().getLastName());
		 * 
		 * assert
		 * coursesMap.get("123456").getLecturesLG().get(0).getLessons().get(0).
		 * getStartTime().equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(12,
		 * 30))); assert
		 * coursesMap.get("123456").getLecturesLG().get(0).getLessons().get(0).
		 * getEndTime().equals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(14,
		 * 30)));
		 * 
		 * assert
		 * coursesMap.get("123456").getLecturesLG().get(0).getLessons().get(1).
		 * getStartTime().equals(new WeekTime(DayOfWeek.MONDAY, LocalTime.of(12,
		 * 30))); assert
		 * coursesMap.get("123456").getLecturesLG().get(0).getLessons().get(1).
		 * getEndTime().equals(new WeekTime(DayOfWeek.MONDAY, LocalTime.of(14,
		 * 30)));
		 */
	}

	@Test
	public void testSaveChosenCourseNames() {
		/*
		 * List<String> names = new LinkedList<>(); names.add("מבוא לחתולים");
		 * names.add("טורי חתולים ופוררריה"); names.add("'אלרגיות למתקדמים ת");
		 * cr.saveChosenCourseNames(names); assert (new
		 * File("data/ChosenCourses.xml")).exists();
		 */
	}

	@Test
	public void testLoadChosenCourseNames() {
		/*
		 * List<String> names = new LinkedList<>(); names.add("מבוא לחתולים");
		 * names.add("טורי חתולים ופוררריה"); names.add("'אלרגיות למתקדמים ת");
		 * cr.saveChosenCourseNames(names); assert (new
		 * File("data/ChosenCourses.xml")).exists();
		 * //cr.loadChosenCourseNames().forEach(name ->
		 * System.out.println(name)); assert cr.loadChosenCourseNames().size()
		 * == 3;
		 */
	}

	@Test
	public void testLoadFaculties() {
		for (final Faculty ¢ : cr.loadFaculties())
			System.out.println(¢.getId() + "---" + ¢.getName());
	}

	@Test
	public void testloadChosenLessonGroups() {
		cr.loadChosenLessonGroups();
	}

	@After
	public void deleteXml() {
		// new File("data/ChosenCourses.xml").delete();
	}

}
