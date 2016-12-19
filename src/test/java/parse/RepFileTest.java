package parse;

import static org.junit.Assert.*;

import org.junit.Test;

public class RepFileTest {

	@SuppressWarnings("static-method")
	@Test
	public void testGetData() {
		RepFile.downloadData();
		assertTrue(true);
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void testGetCoursesFromRepFile() {
		RepFile.getCoursesFromRepFile();
		assertTrue(true);
	}
	
}
