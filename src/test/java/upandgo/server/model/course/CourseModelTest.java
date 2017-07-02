package upandgo.server.model.course;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import upandgo.server.model.CourseModel;
import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.CoursesEntity;
import upandgo.server.model.loader.ScheduleEntity;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.Semester;
import upandgo.shared.entities.course.CourseId;

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
	
	@Test
	@SuppressWarnings("static-method")
	public void test_h() {
		@SuppressWarnings("hiding")
		CourseLoader cr = Mockito.mock(CourseLoader.class);
		CoursesEntity courses = new CoursesEntity();
		Mockito.when(cr.loadChosenCourses()).thenReturn(courses);
		CourseModel model = new CourseModel(cr, Semester.fromId("201603"));
		model.saveChosenCourse("014003");
		Mockito.verify(cr).saveChosenCourses(courses);
		assertEquals("014003", courses.getCourses("201603").get(0));
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void test_h2() {
		@SuppressWarnings("hiding")
		CourseLoader cr = Mockito.mock(CourseLoader.class);
		CoursesEntity courses = new CoursesEntity();
		Mockito.when(cr.loadChosenCourses()).thenReturn(courses);
		CourseModel model = new CourseModel(cr, Semester.fromId("201603"));
		model.saveChosenCourse("014003");
		Mockito.verify(cr).saveChosenCourses(courses);
		assertEquals("014003", courses.getCourses("201603").get(0));
		model.removeChosenCourse("014003");
		verify(cr, times(2)).saveChosenCourses(courses);
		assertEquals(courses.getCourses("201603").size(), 0);
	}
	
	
	@Test
	@SuppressWarnings("static-method")
	public void test_h3() {
		@SuppressWarnings("hiding")
		CourseLoader cr = Mockito.mock(CourseLoader.class);
		CoursesEntity courses = new CoursesEntity();
		Mockito.when(cr.loadChosenCourses()).thenReturn(courses);
		CourseModel model = new CourseModel(cr, Semester.fromId("201603"));
		model.saveChosenCourse("014003");
		Mockito.verify(cr).saveChosenCourses(courses);
		assertEquals("014003", courses.getCourses("201603").get(0));
		model.removeAllChosenCourse();
		verify(cr, times(2)).saveChosenCourses(courses);
		assertEquals(courses.getCourses("201603").size(), 0);
	}
	

	

	@Test
	@SuppressWarnings("static-method")
	public void test_h5() {
		@SuppressWarnings("hiding")
		CourseLoader cr = new XmlCourseLoader("testd.XML", true);
		@SuppressWarnings("unused")
		CourseModel model2 = new CourseModel(cr, Semester.fromId("201602"));
		CourseLoader cr2 = Mockito.mock(CourseLoader.class);
		Mockito.when(cr2.loadAllCoursesById()).thenReturn(cr.loadAllCoursesById());
		Mockito.when(cr2.loadAllCoursesByName()).thenReturn(cr.loadAllCoursesByName());
		Mockito.when(cr2.loadFaculties()).thenReturn(cr.loadFaculties());
		CoursesEntity courses = new CoursesEntity();
		Mockito.when(cr2.loadChosenCourses()).thenReturn(courses);
		CourseModel model = new CourseModel(cr2, Semester.fromId("201603"));
		model.saveChosenCourse("014003");
		Mockito.verify(cr2).saveChosenCourses(courses);
		assertEquals("014003", courses.getCourses("201603").get(0));
		List<CourseId> resultList = model.loadQueryByFaculty("014003", "הנדסה אזרחית וסביבתית");
		List<String> resultIds = new ArrayList<>();
		for (CourseId c : resultList)
			resultIds.add(c.toString());
		assert !resultIds.contains("014003");
		resultList = model.loadQueryByFaculty("014005", "הנדסה אזרחית וסביבתית");
		resultIds = new ArrayList<>();
		for (CourseId c : resultList)
			resultIds.add(c.toString());
		assert resultIds.contains("014005");
	}
	
	
	@Test
	@SuppressWarnings("static-method")
	public void test_h6() {
		@SuppressWarnings("hiding")
		CourseLoader cr = Mockito.mock(CourseLoader.class);
		CoursesEntity courses = new CoursesEntity();
		Mockito.when(cr.loadChosenCourses()).thenReturn(courses);
		ScheduleEntity sched = new ScheduleEntity();
		Mockito.when(cr.loadChosenLessonGroups()).thenReturn(sched);
		CourseModel model = new CourseModel(cr, Semester.fromId("201603"));
		@SuppressWarnings("unused")
		List<LessonGroup> lessons = new ArrayList<LessonGroup>();
		model.saveChosenLessonGroups(lessons);
		Mockito.verify(cr).saveChosenLessonGroups(sched);
		model.loadChosenLessonGroups();
		Mockito.verify(cr, times(2)).loadChosenLessonGroups();
	}
	

}