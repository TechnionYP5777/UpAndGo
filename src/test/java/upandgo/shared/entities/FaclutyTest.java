package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class FaclutyTest {

	@Test
	public void test() {
		Faculty faculty = new Faculty("23", "מדעי המחשב");
		assertEquals("23", faculty.getId());
		assertEquals("מדעי המחשב", faculty.getName());
	}
	
	@Test
	public void setTest() {
		Faculty faculty = new Faculty("23", "מדעי המחשב");
		faculty.setId("99");
		faculty.setName("בלה בלה");
		assertEquals("99", faculty.getId());
		assertEquals("בלה בלה", faculty.getName());
	}
	@Test
	public void equalsTest() {
		Faculty facultya = new Faculty("23", "מדעי המחשב");
		Faculty facultyb = new Faculty("14", "מתמטיקה");
		Faculty facultyc = new Faculty("23", "מדעי המחשב");
		
		assertFalse(facultya.equals(facultyb));
		assertTrue(facultya.equals(facultyc));
		assertTrue(facultya.equals(facultya));
	}

}
