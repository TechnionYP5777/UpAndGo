package upandgo.server.model.loader;

import static org.junit.Assert.*;

import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.Faculty;
import upandgo.shared.entities.course.Course;

public class XmlCourseLoaderTest {

	CourseLoader cr;

	@Before
	public void initialize() {
		cr = new XmlCourseLoader("testd.XML", true);
	}

	@Test
	public void test_a() {
		// Course course = CourseLoader.loadCourse("àðìéæä ðåîøéú 1");
		final TreeMap<String, Course> coursesMap = cr.loadAllCoursesById();
		assertNull(cr.loadAllCourseNames());
		assertEquals(1027, cr.loadAllCoursesByName().size());
		assert "אנליזה נומרית 1".equals(coursesMap.get("234107").getName());
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
		
		assert "13.07 יום ה'".equals(coursesMap.get("134153").getaTerm() + "");
		assert "18.07 יום ג'".equals(coursesMap.get("035022").getaTerm() + "");
		assertNull(coursesMap.get("205719").getaTerm());
				
		assert "09.07 יום א'".equals(coursesMap.get("014852").getaTerm() + "");
		assert "12.07 יום ד'".equals(coursesMap.get("236635").getaTerm() + "");
		
		assert "25.09 יום ב'".equals(coursesMap.get("044148").getbTerm() + "");
		assertNull(coursesMap.get("394820-12").getaTerm());
		
		assert "25.09 יום ב'".equals(coursesMap.get("014003").getbTerm() + "");
		assert "17.10 יום ג'".equals(coursesMap.get("095113").getbTerm() + "");
		assert "03.10 יום ג'".equals(coursesMap.get("236353").getbTerm() + "");

		assert coursesMap.get("236350").getStuff().size() == 1;
		assert "ג.נקבלי".equals(coursesMap.get("236350").getStuff().get(0).getLastName());

		assert coursesMap.get("324975").getStuff().size() == 1;
		assert "א.פסקוביץ".equals(coursesMap.get("324975").getStuff().get(0).getLastName());

		assert coursesMap.get("044191").getStuff().isEmpty();

		assert coursesMap.get("034039").getStuff().size() == 2;

		assert "הצימוד החשמלי-מכני בשרירי הלב"
				.equals(coursesMap.get("338515").getLecturesLG().get(0).getLessons().get(0).getCourseName());
	

	}
	

	@Test
	public void testLoadFaculties() {
		for (final Faculty xxx : cr.loadFaculties())
			System.out.println(xxx.getId() + "---" + xxx.getName());
	}


}
