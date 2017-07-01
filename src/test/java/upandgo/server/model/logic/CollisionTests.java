package upandgo.server.model.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import upandgo.server.model.loader.CourseLoader;
import upandgo.server.model.loader.XmlCourseLoader;
import upandgo.shared.entities.Day;
import upandgo.shared.entities.LocalTime;
import upandgo.shared.entities.WeekTime;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.Collision;
import upandgo.shared.model.scedule.Scheduler;
import upandgo.shared.model.scedule.Timetable;

public class CollisionTests {
	
	CourseLoader cr;

	@After
	public void after() {
		System.out.println("***");
	}
	
	@Test
	public void test1() {
		cr = new XmlCourseLoader("collisionTest1.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		final Timetable t = Scheduler.getTimetablesList(courses, null).get(0);

		assert t.getLessonGroups().get(0).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("10:00")));
		assert t.getLessonGroups().get(1).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("16:00")));
		assert Scheduler.getCollisionSolvers().size() == 2;
		//System.out.println(Scheduler.getCollisionSolvers());
		assert Scheduler.getCollisionSolvers().get(0).getCourseId().equals("111111");
		
		
		/*assert t.getLessonGroups().get(2).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.TUESDAY, LocalTime.parse("11:00")));
		assert t.getLessonGroups().get(3).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("14:00")));*/

		// assertNull(s);
	}
	
	
	@Test
	public void test2() {
		cr = new XmlCourseLoader("collisionTest2.XML", true);

		final List<Course> courses = new ArrayList<>(cr.loadAllCoursesById().values());
		System.out.println(courses);

		final List<Timetable> tlist = Scheduler.getTimetablesList(courses, null);
		assert (tlist.isEmpty());
		assert Scheduler.getCollisionSolvers().size() == 2;
		assert Scheduler.getCollisionSolvers().get(0).getCourseId().equals("111111");
		
		/*assert t.getLessonGroups().get(2).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.TUESDAY, LocalTime.parse("11:00")));
		assert t.getLessonGroups().get(3).getLessons().get(0).getStartTime()
				.equals(new WeekTime(Day.SUNDAY, LocalTime.parse("14:00")));*/

		
	}

}
