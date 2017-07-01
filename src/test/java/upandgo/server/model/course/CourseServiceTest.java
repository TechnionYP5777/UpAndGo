package upandgo.server.model.course;

import static org.junit.Assert.*;

import org.junit.Test;

import upandgo.server.CoursesServiceImpl;
import upandgo.shared.entities.Exam;
import upandgo.shared.entities.Semester;
import upandgo.shared.entities.course.CourseId;

public class CourseServiceTest {

	@Test
	public void test() {
		CoursesServiceImpl course = new CoursesServiceImpl();
//		course.getCourseDetails(Semester.WINTER17, new CourseId("234107", "אנליזה נומרית 1", new Exam("13:00 08 08 2017 4"), new Exam("13:00 08 10 2017 4")));
		assertEquals("emptyWe have got our credentials!",course.getSomeString());
	}

}
