package upandgo.shared.entities;

import java.io.Serializable;
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
	
	private static final long serialVersionUID = -7431131009009123098L;

	private Day day;
	private LocalTime time; // = LocalTime.of(22, 15);

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
	
	public static int difference(final LocalTime a, LocalTime b){
		return a.getMinute() + 60 * (a.getHour() - b.getHour()) - b.getMinute();
	}
	

	public static int compareTo(final WeekTime a, final WeekTime b) {
		return a.getDay().compareTo(b.getDay()) != 0 ? a.getDay().compareTo(b.getDay())
				: a.getTime().compareTo(b.getTime());
	}

	public int compareTo(final WeekTime b) {
		return getDay().compareTo(b.getDay()) != 0 ? getDay().compareTo(b.getDay()) : getTime().compareTo(b.getTime());
	}

	@Override
	public boolean equals(final Object d) {
		return d instanceof WeekTime && compareTo((WeekTime) d) == 0;
		/*
		 * return d != null && (d == this || d instanceof WeekTime &&
		 * (this.day.equals(((WeekTime) d).getDay())) &&
		 * ((this.time).equals(((WeekTime) d).getTime())));
		 */
	}

	@Override
	public String toString() {
		return day + " " + time;
	}

	@Override
	public int hashCode() {
		return Objects.hash(day, time);
	}
}
