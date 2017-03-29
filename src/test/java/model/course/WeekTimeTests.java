package model.course;
/**
 * @author kobybs
 * @since 25-12-16
 */

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.Test;

@SuppressWarnings("static-method")
public class WeekTimeTests {

	@Test
	public void test_a() {
		assertEquals(new WeekTime(Day.SUNDAY, LocalTime.of(10, 30))
				.compareTo(new WeekTime(Day.SUNDAY, LocalTime.of(10, 30))), 0);
	}

	@Test
	public void test_b() {
		assert(new WeekTime(Day.SUNDAY, LocalTime.of(10, 30))
				.compareTo(new WeekTime(Day.SUNDAY, LocalTime.of(10, 40))) < 0);
	}

	@Test
	public void test_c() {
		assert(new WeekTime(Day.SUNDAY, LocalTime.of(10, 30))
				.compareTo(new WeekTime(Day.SUNDAY, LocalTime.of(10, 20))) > 0);
	}

	@Test
	public void test_d() {
		assert(new WeekTime(Day.MONDAY, LocalTime.of(10, 30))
				.compareTo(new WeekTime(Day.SUNDAY, LocalTime.of(10, 30))) > 0);
	}

	@Test
	public void test_e() {
		assert(new WeekTime(Day.SUNDAY, LocalTime.of(10, 30))
				.compareTo(new WeekTime(Day.MONDAY, LocalTime.of(10, 30))) < 0);
	}

	@Test
	public void test_f() {
		assert(new WeekTime(Day.SATURDAY, LocalTime.of(10, 30))
				.compareTo(new WeekTime(Day.SUNDAY, LocalTime.of(10, 30))) > 0);
	}

}
