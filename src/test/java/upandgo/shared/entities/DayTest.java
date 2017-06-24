package upandgo.shared.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class DayTest {

	@Test
	public void test() {
		Day[] val = new Day[] {Day.SUNDAY, Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY,
				Day.THURSDAY, Day.FRIDAY, Day.SATURDAY};
		assertArrayEquals(val, Day.values());
		
	}

}
