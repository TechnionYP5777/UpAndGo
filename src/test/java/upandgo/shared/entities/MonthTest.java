package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class MonthTest {

	@Test
	@SuppressWarnings("static-method")
	public void test() {
		Month[] month = new Month[] { Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE,
				Month.JULY, Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER };
		for (int i = 0; i < month.length; ++i) {
			assertEquals(i + 1, Month.toInt(month[i]));
			assertEquals((i < 9 ? "0" : "") + (i + 1), Month.toString(month[i]));
		}
	}

}
