package model.course;

import java.io.Serializable;
import java.time.DayOfWeek;
/**
 * @author kobybs
 * @since 25-12-16
 */
import java.time.LocalTime;
import java.util.Objects;

public class WeekTime implements Serializable {
	private static final long serialVersionUID = -7431131009009123098L;

	private final DayOfWeek day;
	private final LocalTime time; // = LocalTime.of(22, 15);

	public WeekTime(final DayOfWeek day, final LocalTime time) {
		this.day = day;
		this.time = time;
	}

	public DayOfWeek getDay() {
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
