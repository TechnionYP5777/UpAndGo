package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class SemesterTest {

	@Test
	@SuppressWarnings("static-method")
	public void testa() {
		assertEquals(Semester.SPRING16, Semester.fromId("201602"));
		assertEquals(Semester.SUMMER16, Semester.fromId("201603"));
		assertNull(Semester.fromId("201604"));
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testb() {
		assertEquals("אביב 2016/17 תשע\"ז", Semester.fromId("201602").getName());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testc() {
		assertEquals("201602", Semester.fromId("201602").getId());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testd() {
		assertEquals(Semester.WINTER17.getStartDate(), "22/10/2017");
		assertEquals(Semester.WINTER17.getEndDate(), "25/01/2018");
	}

}
