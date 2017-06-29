package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class SemesterTest {

	@Test
	public void testa() {
		assertEquals(Semester.SPRING16, Semester.fromId("201602"));
		assertEquals(Semester.SUMMER16, Semester.fromId("201603"));
		assertNull(Semester.fromId("201604"));
	}
	
	@Test
	public void testb() {
		assertEquals("אביב 2016/17 תשע\"ז", Semester.fromId("201602").getName());
	}
	
	@Test
	public void testc() {
		assertEquals("201602", Semester.fromId("201602").getId());
	}

}
