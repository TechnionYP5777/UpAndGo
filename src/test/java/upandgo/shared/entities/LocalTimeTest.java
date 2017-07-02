package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class LocalTimeTest {

	@Test
	@SuppressWarnings("static-method")
	public void constructorTest() {
		LocalTime time = LocalTime.of(9, 30);
		LocalTime time2 = new LocalTime();
		assertEquals(9, time.getHour());
		assertEquals(30, time.getMinute());
		assertEquals(0, time2.getHour());
		assertEquals(0, time2.getMinute());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void compareTest() {
		LocalTime time1 = LocalTime.of(9, 30);
		LocalTime time2 = LocalTime.of(14, 30);
		LocalTime time3 = LocalTime.of(9, 12);
		assertEquals(0, time1.compareTo(time1));
		assert time3.compareTo(time1) < 0;
		assert time2.compareTo(time1) > 0;
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void toStringTest() {
		LocalTime time = LocalTime.of(9, 30);
		LocalTime time2 = LocalTime.of(14, 5);
		assertEquals("9:30", time.toString());
		assertEquals("14:05", time2.toString());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void ParseTest() {
		LocalTime time = LocalTime.parse("14:05");
		assertEquals(14, time.getHour());
		assertEquals(5, time.getMinute());
	}
	

}
