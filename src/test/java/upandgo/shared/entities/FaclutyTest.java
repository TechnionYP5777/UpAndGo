package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class FaclutyTest {

	@Test
	@SuppressWarnings("static-method")
	public void test() {
		Faculty faculty = new Faculty("23", "מדעי המחשב");
		assertEquals("23", faculty.getId());
		assertEquals("מדעי המחשב", faculty.getName());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void setTest() {
		Faculty faculty = new Faculty("23", "מדעי המחשב");
		faculty.setId("99");
		faculty.setName("בלה בלה");
		assertEquals("99", faculty.getId());
		assertEquals("בלה בלה", faculty.getName());
	}
	@Test
	@SuppressWarnings("static-method")
	public void equalsTest() {
		Faculty facultya = new Faculty("23", "מדעי המחשב");
		Faculty facultyb = new Faculty("14", "מתמטיקה");
		Faculty facultyc = new Faculty("23", "מדעי המחשב");
		assert !facultya.equals(new Exam("13:00 08 08 2017 4"));
		assert !facultya.equals(null);
		assert !facultya.equals(facultyb);
		assert facultya.equals(facultyc);
		assert facultya.equals(facultya);
	}

}
