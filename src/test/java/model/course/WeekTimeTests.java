package model.course;
/**
 * @author kobybs
 * @since 25-12-16
 */

import static org.junit.Assert.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.Test;

@SuppressWarnings("static-method")
public class WeekTimeTests {

	@Test
	public void test_a() {
		assertEquals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 30))
				.compareTo(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 30))), 0);
	}

	public void test_b() {
		assertEquals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 30))
				.compareTo(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 40))), -1);
	}

	public void test_c() {
		assertEquals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 30))
				.compareTo(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 20))), 1);
	}

	public void test_d() {
		assertEquals(new WeekTime(DayOfWeek.MONDAY, LocalTime.of(10, 30))
				.compareTo(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 30))), 1);
	}

	public void test_e() {
		assertEquals(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 30))
				.compareTo(new WeekTime(DayOfWeek.MONDAY, LocalTime.of(10, 30))), -1);
	}

	public void test_f() {
		assertEquals(new WeekTime(DayOfWeek.SATURDAY, LocalTime.of(10, 30))
				.compareTo(new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 30))), 1);
	}

}
