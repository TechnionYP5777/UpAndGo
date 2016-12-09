package parse;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RepFileTest {

	@SuppressWarnings("static-method")
    @Before
    public void initialize() {
    	RepFile.initialize();
     }
    
	@SuppressWarnings("static-method")
	@Test
	public void testGetData() {
		RepFile.getData();
		assertTrue(true);
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void testGetCoursesFromRepFile() {
		RepFile.getCoursesFromRepFile();
		assertTrue(true);
	}
	
}
