package upandgo.shared.entities.course;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import upandgo.shared.entities.Day;
import upandgo.shared.entities.Exam;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.LocalTime;
import upandgo.shared.entities.StuffMember;
import upandgo.shared.entities.WeekTime;
import upandgo.shared.entities.Lesson.Type;

public class courseTest {

	@Test
	public void testa() {
		Course course = new Course();
		
		assertEquals("", course.getName());
		assertEquals("", course.getId());
		assertEquals("", course.getFaculty());
		assertEquals("0.0", ""+course.getPoints());
		assertNull(course.getaTerm());
		assertNull(course.getbTerm());
		assertTrue(course.getStuff().isEmpty());
		assertNull(course.getLectures());
		assertNull(course.getTutorials());
		assertNull(course.getNotes());
	}
	
	@Test(expected = NullPointerException.class)
	public void testb() {
		StuffMember staff = new StuffMember("אולמן", "טאוב");
		List list  = new ArrayList<StuffMember>();
		list.add(staff);
		Course course = new Course(null, "999999", "חשמל", list, 4.5, new Exam("13:00 08 08 2017")
				,new Exam("13:00 08 10 2017"));
	}
	
	@Test(expected = NullPointerException.class)
	public void testc() {
		StuffMember staff = new StuffMember("אולמן", "טאוב");
		List list  = new ArrayList<StuffMember>();
		list.add(staff);
		Course course = new Course("אנליזה", "999999", null, list, 4.5, new Exam("13:00 08 08 2017")
				, new Exam("13:00 08 10 2017"));
	}
	
	@Test
	public void testd() {
		StuffMember staff = new StuffMember("אולמן", "טאוב");
		List list  = new ArrayList<StuffMember>();
		list.add(staff);
		Course course = new Course("אנליזה", "999999", "חשמל", list, 4.5, new Exam("13:00 08 08 2017")
				, new Exam("13:00 08 10 2017"));
		
		LessonGroup lg = new LessonGroup();
		lg.setGroupNum(10);
		lg.setConstrained(true);
		lg.addLesson(new Lesson(new StuffMember("אולמן", "טאוב"), new WeekTime(Day.SUNDAY, LocalTime.parse("10:30")),
				new WeekTime(Day.SUNDAY, LocalTime.parse("12:30")), "טאוב 5", Type.LECTURE, 10, "999999", "אנליזה"));
		
		course.addLecturesLessonGroup(lg);
		course.addTutorialLessonGroup(lg);
		course.addNote("קורס מעפן, לא להירשם");
		Course course2 = new Course(course);
		assertEquals("אנליזה", course2.getName());
		assertEquals("999999", course2.getId());
		assertEquals("חשמל", course2.getFaculty());
		assertEquals("4.5", ""+course2.getPoints());
		assertEquals(1, course.getLecturesLG().size());
		assertEquals(1, course.getTutorialsLG().size());
		assertEquals("קורס מעפן, לא להירשם", course.getNotes().get(0));
	}
	
	@Test
	public void teste() {
		StuffMember staff = new StuffMember("אולמן", "טאוב");
		List list  = new ArrayList<StuffMember>();
		list.add(staff);
		Course course = new Course("אנליזה", "999999", "חשמל", list, 4.5, new Exam("13:00 08 08 2017")
				, new Exam("13:00 08 10 2017"));
		Course course2 = new Course("אנליזה", "989999", "חשמל", list, 4.5, new Exam("13:00 08 08 2017")
				, new Exam("13:00 08 10 2017"));
		assertFalse(course.equals(null));
		assertTrue(course.equals(course));
		assertFalse(course.equals(new Exam("13:00 08 10 2017")));
		assertFalse(course.equals(course2));
	}
	
}
