package model.course;


import org.junit.Test;

@SuppressWarnings("static-method")
public class DayTest {

	
	@Test
	public void test() {
		assert(Day.SUNDAY.ordinal() == 0);
		assert(Day.MONDAY.ordinal() == 1);
		assert(Day.TUESDAY.ordinal() == 2);
		assert(Day.WEDNESDAY.ordinal() == 3);
		assert(Day.THURSDAY.ordinal() == 4);
		assert(Day.FRIDAY.ordinal() == 5);
		assert(Day.SATURDAY.ordinal() == 6);
		assert(Day.SUNDAY.compareTo(Day.MONDAY) < 0);
		assert(Day.MONDAY.compareTo(Day.MONDAY) == 0);
		assert(Day.MONDAY.compareTo(Day.SUNDAY) > 0);
		
		assert(Day.TUESDAY.compareTo(Day.FRIDAY) < 0);
		assert(Day.FRIDAY.compareTo(Day.FRIDAY) == 0);
		assert(Day.FRIDAY.compareTo(Day.TUESDAY) > 0);
	}

}
