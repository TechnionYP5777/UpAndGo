package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class MonthTest {

	@Test
	public void test() {
		Month[] month = new Month[] {Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE,
				Month.JULY, Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER};
		for (int i = 0; i < month.length; i++) {
			assertEquals(i+1, Month.toInt(month[i]));
			if (i < 9) {
				assertEquals("0"+(i+1), Month.toString(month[i]));
			} else {
				assertEquals(""+(i+1), Month.toString(month[i]));
			}
		}
		
		
	}

}
