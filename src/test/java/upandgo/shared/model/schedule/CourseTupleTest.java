package upandgo.shared.model.schedule;

import static org.junit.Assert.*;

import org.junit.Test;

import upandgo.shared.model.scedule.CourseTuple;

public class CourseTupleTest {

	@Test
	public void test() {
		CourseTuple tup = new CourseTuple("999999", "אנלזה");
		
		assertEquals("999999", tup.getCourseId());
		assertEquals("אנלזה", tup.getCourseName());
	}

}
