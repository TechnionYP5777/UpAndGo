package parse;

import org.junit.Test;

public class RepFileTest {

	@Test
	@SuppressWarnings("static-method")
	public void testGetData() {
		RepFile.downloadData();
		assert true;
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testGetCoursesFromRepFile() {
		RepFile.getCoursesFromRepFile();
		assert true;
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testGetCoursesInfoFromRepFile() {
		RepFile.getCoursesInfoFromRepFile();
		assert true;
	}
	
}
