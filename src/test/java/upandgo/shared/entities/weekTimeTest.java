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

}
