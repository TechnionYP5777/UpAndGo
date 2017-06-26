package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("static-method")
public class weekTimeTest {

	@Test
	public void test_a() {
		assertEquals(new WeekTime(Day.SUNDAY, LocalTime.parse("10:30"))
				.compareTo(new WeekTime(Day.SUNDAY, LocalTime.parse("10:30"))), 0);
	}
	
	@Test
	public void test_a1() {
		WeekTime time = new WeekTime();
		assertNull(time.getDay());
		assertNull(time.getTime());
	}
	
	@Test
	public void test_b() {
		assert(new WeekTime(Day.SUNDAY, LocalTime.parse("10:30"))
				.compareTo(new WeekTime(Day.SUNDAY, LocalTime.parse("10:40"))) < 0);
	}

	@Test
	public void test_c() {
		assert(new WeekTime(Day.SUNDAY, LocalTime.parse("10:30"))
				.compareTo(new WeekTime(Day.SUNDAY, LocalTime.parse("10:20"))) > 0);
	}

	@Test
	public void test_d() {
		assert(new WeekTime(Day.MONDAY, LocalTime.parse("10:30"))
				.compareTo(new WeekTime(Day.SUNDAY, LocalTime.parse("10:30"))) > 0);
	}

	@Test
	public void test_e() {
		assert(new WeekTime(Day.SUNDAY, LocalTime.parse("10:30"))
				.compareTo(new WeekTime(Day.MONDAY, LocalTime.parse("10:30"))) < 0);
	}

	@Test
	public void test_f() {
		assert(new WeekTime(Day.SATURDAY, LocalTime.parse("10:30"))
				.compareTo(new WeekTime(Day.SUNDAY, LocalTime.parse("10:30"))) > 0);
	}
	
	@Test
	public void test_g() {
		assertEquals(60, WeekTime.difference(LocalTime.parse("11:30"), LocalTime.parse("10:30")));
	}
	
	@Test
	public void test_h() {
		WeekTime time1 = new WeekTime(Day.SUNDAY, LocalTime.parse("10:30"));
		WeekTime time2 = new WeekTime(Day.SUNDAY, LocalTime.parse("11:30"));
		WeekTime time3 = new WeekTime(Day.MONDAY, LocalTime.parse("11:30"));
		assertEquals(60, WeekTime.difference(time2, time1));
		assertEquals(60, WeekTime.difference(time3, time1));
	}
	

	@Test
	public void test_i() {
		WeekTime time1 = new WeekTime(Day.SUNDAY, LocalTime.parse("10:30"));
		LocalTime time2 = LocalTime.parse("11:30");
		LocalTime time3 = LocalTime.parse("22:00");
		assertEquals(time3.getHour(), time1.addDuration(time2).getTime().getHour());
		assertEquals(time3.getMinute(), time1.addDuration(time2).getTime().getMinute());
		
	}
	
	@Test
	public void test_j() {
		WeekTime time1 = new WeekTime(Day.SUNDAY, LocalTime.parse("10:30"));
		WeekTime time2 = new WeekTime(Day.MONDAY, LocalTime.parse("10:30"));
		WeekTime time3 = new WeekTime(Day.TUESDAY, LocalTime.parse("10:30"));
		WeekTime time4 = new WeekTime(Day.WEDNESDAY, LocalTime.parse("10:30"));
		WeekTime time5 = new WeekTime(Day.THURSDAY, LocalTime.parse("10:30"));
		WeekTime time6 = new WeekTime(Day.FRIDAY, LocalTime.parse("10:30"));
		WeekTime time7 = new WeekTime(Day.SATURDAY, LocalTime.parse("10:30"));
		assertEquals("ראשון 10:30", time1.toHebrewString());
		assertEquals("שני 10:30", time2.toHebrewString());
		assertEquals("שלישי 10:30", time3.toHebrewString());
		assertEquals("רביעי 10:30", time4.toHebrewString());
		assertEquals("חמישי 10:30", time5.toHebrewString());
		assertEquals("שישי 10:30", time6.toHebrewString());
		assertEquals("שבת 10:30", time7.toHebrewString());
		
		
		
	}
	
}
