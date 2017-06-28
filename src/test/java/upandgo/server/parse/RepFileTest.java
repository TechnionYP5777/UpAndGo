package upandgo.server.parse;

import org.junit.Ignore;
import org.junit.Test;

import upandgo.server.parse.RepFile;


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
		RepFile.getCoursesNamesAndIds();
		assert true;
	}

	@Test
	@SuppressWarnings("static-method")
	public void testGetCoursesInfoFromRepFile() {
		RepFile.getCoursesInfoFromRepFile();
		assert true;
	}

}
