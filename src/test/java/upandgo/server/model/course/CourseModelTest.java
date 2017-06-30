package upandgo.server.model.course;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import upandgo.server.model.CourseModel;
import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.Semester;

public class CourseModelTest {
	
	CourseLoader cr;
	
	@Before
	public void initialize() {
		cr = new XmlCourseLoader("testd.XML", true);
	}

	@Test(expected = NullPointerException.class)
	public void test_a() {
		CourseModel model = new CourseModel(cr, Semester.fromId("201602"));
		model.addCourse(null);
	}
	
	@Test
	public void test_b() {
		CourseModel model = new CourseModel(cr, Semester.fromId("201602"));
		assertEquals(1251, model.getCoursesNames().size());
		model.addCourse("אנליזה");
		assertEquals(1252, model.getCoursesNames().size());	
	}
	
	@Test(expected = NullPointerException.class)
	public void test_c() {
		CourseModel model = new CourseModel(cr, Semester.fromId("201602"));
		model.getCourseByName(null);
	}
	
	@Test
	public void test_d() {
		CourseModel model = new CourseModel(cr, Semester.fromId("201602"));
		assertEquals("234107", model.getCourseByName("אנליזה נומרית 1").getId());
	}
	
	@Test(expected = NullPointerException.class)
	public void test_e() {
		CourseModel model = new CourseModel(cr, Semester.fromId("201602"));
		model.getCourseById(null);
	}
	
	@Test
	public void test_f() {
		CourseModel model = new CourseModel(cr, Semester.fromId("201602"));
		assertEquals("אנליזה נומרית 1", model.getCourseById("234107").getName());
	}
	
	@Test
	public void test_g() {
		CourseModel model = new CourseModel(cr, Semester.fromId("201602"));
		assertEquals("אנליזה נומרית 1", model.getCourseId("234107").name());
		assertEquals(1251, model.loadAllCourses().size());
		assertEquals(20, model.loadFacultyNames().size());
	}

}
