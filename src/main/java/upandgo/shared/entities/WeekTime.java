package upandgo.shared.entities;

/**
 * @author kobybs
 * @since 25-12-16
 */
import java.util.Objects;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author kobybs
 * @since 25-12-16
 * 
 * Class that represents moment of time on specific day of the week.
 * 
 */

public class WeekTime implements IsSerializable {
		
	private Day day;
	private LocalTime time; 

	public WeekTime() {
		this.day = null;
		this.time = null;
	}
	
	
	public WeekTime(final Day day, final LocalTime localTime) {
		this.day = day;
		this.time = localTime;
	}

	public Day getDay() {
		return day;
	}

	public LocalTime getTime() {
		return time;
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @return the amount of minutes between b to a (like a-b)
	 */
	public static int difference(final WeekTime a, final WeekTime b) {
		return a.getTime().getMinute() + 60 * (a.getTime().getHour() - b.getTime().getHour()) - b.getTime().getMinute();
	}
	
	public static int difference(final LocalTime t, LocalTime b){
		return t.getMinute() + 60 * (t.getHour() - b.getHour()) - b.getMinute();
	}
	

	public static int compareTo(final WeekTime a, final WeekTime b) {
		return a.getDay().compareTo(b.getDay()) != 0 ? a.getDay().compareTo(b.getDay())
				: a.getTime().compareTo(b.getTime());
	}
	
	public WeekTime addDuration(final LocalTime duration){
		int minutes = duration.getMinute() + this.time.getMinute(), hours = duration.getHour() + this.time.getHour();
		if (minutes < 60)
			return new WeekTime(this.day, LocalTime.of(hours, minutes));
		++hours;
		minutes -= 60;
		return new WeekTime(this.day, LocalTime.of(hours, minutes));
	}

	public int compareTo(final WeekTime b) {
		return getDay().compareTo(b.getDay()) != 0 ? getDay().compareTo(b.getDay()) : getTime().compareTo(b.getTime());
	}

	@Override
	public boolean equals(final Object d) {
		return d instanceof WeekTime && compareTo((WeekTime) d) == 0;
	}
	
	public String toHebrewString(){
		switch (day){
			case FRIDAY:
				return "שישי " + time;
			case MONDAY:
				return "שני " + time;
			case SUNDAY:
				return "ראשון " + time;
			case THURSDAY:
				return "חמישי " + time;
			case TUESDAY:
				return "שלישי " + time;
			case WEDNESDAY:
				return "רביעי " + time;
			default:
				return "שבת " + time;
		}
	}

	@Override
	public String toString() {
		return day + " " + time;
	}

	@Override
	public int hashCode() {
		return Objects.hash(day);
	}
}
