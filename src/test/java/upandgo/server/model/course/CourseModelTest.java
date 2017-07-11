package upandgo.server.model.course;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import upandgo.server.model.CourseModel;
import upandgo.server.model.datastore.CoursesEntity;
import upandgo.server.model.datastore.Datastore;
import upandgo.server.model.datastore.GoogleDatastore;
import upandgo.server.model.datastore.ScheduleEntity;
import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.Semester;
import upandgo.shared.entities.course.CourseId;

public class CourseModelTest {
	
	CourseLoader cr;
	Datastore ds;
	
	@Before
	public void initialize() {
		cr = new XmlCourseLoader("testd.XML", true);
		ds = new GoogleDatastore();
	}

	@Test(expected = NullPointerException.class)
	public void test_a() {
		CourseModel model = new CourseModel(cr, Semester.fromId("201602"),ds);
		model.addCourse(null);
	}
	
	@Test
	public void test_b() {
		CourseModel model = new CourseModel(cr, Semester.fromId("201602"),ds);
		assertEquals(1251, model.getCoursesNames().size());
		model.addCourse("אנליזה");
		assertEquals(1252, model.getCoursesNames().size());	
	}
	
	@Test(expected = NullPointerException.class)
	public void test_c() {
		CourseModel model = new CourseModel(cr, Semester.fromId("201602"),ds);
		model.getCourseByName(null);
	}
	
	@Test
	public void test_d() {
		CourseModel model = new CourseModel(cr, Semester.fromId("201602"),ds);
		assertEquals("234107", model.getCourseByName("אנליזה נומרית 1").getId());
	}
	
	@Test(expected = NullPointerException.class)
	public void test_e() {
		CourseModel model = new CourseModel(cr, Semester.fromId("201602"),ds);
		model.getCourseById(null);
	}
	
	@Test
	public void test_f() {
		CourseModel model = new CourseModel(cr, Semester.fromId("201602"),ds);
		assertEquals("אנליזה נומרית 1", model.getCourseById("234107").getName());
	}
	
	@Test
	public void test_g() {
		CourseModel model = new CourseModel(cr, Semester.fromId("201602"),ds);
		assertEquals("אנליזה נומרית 1", model.getCourseId("234107").name());
		assertEquals(1251, model.loadAllCourses().size());
		assertEquals(20, model.loadFacultyNames().size());
	}
	
	@Test
	public void test_h() {
		Datastore ds = Mockito.mock(Datastore.class);
		CoursesEntity courses = new CoursesEntity();
		Mockito.when(ds.loadChosenCourses()).thenReturn(courses);
		CourseModel model = new CourseModel(cr, Semester.fromId("201603"),ds);
		model.saveChosenCourse("014003");
		///system.out.println(courses);
		Mockito.verify(ds).saveChosenCourses(courses);
		assertEquals("014003", courses.getCourses("201603").get(0)); 
		//assertEquals("אנליזה נומרית 1", model.getCourseById("234107").getName());
	}
	
	@Test
	public void test_h2() {
		Datastore ds = Mockito.mock(Datastore.class);
		CoursesEntity courses = new CoursesEntity();
		Mockito.when(ds.loadChosenCourses()).thenReturn(courses);
		CourseModel model = new CourseModel(cr, Semester.fromId("201603"),ds);
		model.saveChosenCourse("014003");
		///system.out.println(courses);
		Mockito.verify(ds).saveChosenCourses(courses);
		assertEquals("014003", courses.getCourses("201603").get(0)); 
		
		model.removeChosenCourse("014003");
		///system.out.println(courses);
		//Mockito.verify(cr).saveChosenCourses(courses);
		verify(ds, times(2)).saveChosenCourses(courses);
		/////system.out.println(courses);
		assertEquals(courses.getCourses("201603").size(), 0); 
		//assertEquals("אנליזה נומרית 1", model.getCourseById("234107").getName());
	}
	
	
	@Test
	public void test_h3() {
		Datastore ds = Mockito.mock(Datastore.class);
		CoursesEntity courses = new CoursesEntity();
		Mockito.when(ds.loadChosenCourses()).thenReturn(courses);
		CourseModel model = new CourseModel(cr, Semester.fromId("201603"),ds);
		model.saveChosenCourse("014003");
		///system.out.println(courses);
		Mockito.verify(ds).saveChosenCourses(courses);
		assertEquals("014003", courses.getCourses("201603").get(0)); 
		
		model.removeAllChosenCourse();
		///system.out.println(courses);
		//Mockito.verify(cr).saveChosenCourses(courses);
		verify(ds, times(2)).saveChosenCourses(courses);
		/////system.out.println(courses);
		assertEquals(courses.getCourses("201603").size(), 0); 
		//assertEquals("אנליזה נומרית 1", model.getCourseById("234107").getName());
	}
	

	

	@Test
	public void test_h5() {
		//CourseLoader cr = Mockito.mock(CourseLoader.class);
		CourseLoader cr = new XmlCourseLoader("testd.XML", true);
		CourseModel model2 = new CourseModel(cr, Semester.fromId("201602"),ds);
		///system.out.println(model2.loadFacultyNames());
		///system.out.println(model2.loadFacultyNames());
		
		//CourseModel model = new CourseModel(cr, Semester.fromId("201602"));
		//model.get
		
		CourseLoader cr2 = Mockito.mock(CourseLoader.class);
		Mockito.when(cr2.loadAllCoursesById()).thenReturn(cr.loadAllCoursesById());
		Mockito.when(cr2.loadAllCoursesByName()).thenReturn(cr.loadAllCoursesByName());
		Mockito.when(cr2.loadFaculties()).thenReturn(cr.loadFaculties());
		
		Datastore ds2 = Mockito.mock(Datastore.class);
		CoursesEntity courses = new CoursesEntity();
		Mockito.when(ds2.loadChosenCourses()).thenReturn(courses);
		CourseModel model = new CourseModel(cr2, Semester.fromId("201603"),ds2);
		model.saveChosenCourse("014003");
		///system.out.println(courses);
		Mockito.verify(ds2).saveChosenCourses(courses);
		assertEquals("014003", courses.getCourses("201603").get(0)); 
	
		
		List<CourseId> resultList = model.loadQueryByFaculty("014003", "הנדסה אזרחית וסביבתית");
		List<String> resultIds = new ArrayList<>();
		for(CourseId c : resultList)
			resultIds.add(c.toString());
		
		assertFalse(resultIds.contains("014003"));
		/////system.out.println("***result " + resultList);
		
		
		resultList = model.loadQueryByFaculty("014005", "הנדסה אזרחית וסביבתית");
		resultIds = new ArrayList<>();
		for(CourseId c : resultList)
			resultIds.add(c.toString());
		
		assertTrue(resultIds.contains("014005"));
		/////system.out.println("***result " + resultList);
		
/*
		verify(cr, times(2)).saveChosenCourses(courses);

		assertEquals(courses.getCourses("201603").size(), 0); */

	}
	
	
	@Test
	public void test_h6() {
		Datastore ds = Mockito.mock(Datastore.class);
		CoursesEntity courses = new CoursesEntity();
		Mockito.when(ds.loadChosenCourses()).thenReturn(courses);
		
		ScheduleEntity sched = new ScheduleEntity();
		Mockito.when(ds.loadChosenLessonGroups()).thenReturn(sched);
		
		
		CourseModel model = new CourseModel(cr, Semester.fromId("201603"),ds);
		
		List<LessonGroup> lessons = new ArrayList<LessonGroup>();
		model.saveChosenLessonGroups(lessons);

		Mockito.verify(ds).saveChosenLessonGroups(sched);
		
		model.loadChosenLessonGroups();

		Mockito.verify(ds, times(2)).loadChosenLessonGroups();

	}
	

}
