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
	/**
	 * 
	 */
	private static final long serialVersionUID = -7431131009009123098L;
	
	private DayOfWeek day;
	private LocalTime time; // = LocalTime.of(22, 15);
	
	public WeekTime(DayOfWeek day, LocalTime time){
		this.day = day;
		this.time = time;
	}
	
	public DayOfWeek getDay(){
		return day;
	}
	public LocalTime getTime(){
		return time;
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return the amount of minutes between b to a (like a-b)
	 */
	public static int difference(WeekTime a, WeekTime b){
		return a.getTime().getMinute() + 60 * (a.getTime().getHour() - b.getTime().getHour())-b.getTime().getMinute();
	}
	
	public static int compareTo(WeekTime a, WeekTime b){
		return a.getDay().compareTo(b.getDay()) != 0 ? a.getDay().compareTo(b.getDay())
				: a.getTime().compareTo(b.getTime());
	}
	
	public int compareTo(WeekTime b){
		return this.getDay().compareTo(b.getDay()) != 0 ? this.getDay().compareTo(b.getDay())
				: this.getTime().compareTo(b.getTime());
	}
	
	@Override
	public boolean equals(Object d){
		return d instanceof WeekTime && compareTo((WeekTime) d) == 0;
		/*return d != null && (d == this || d instanceof WeekTime && (this.day.equals(((WeekTime) d).getDay()))
				&& ((this.time).equals(((WeekTime) d).getTime())));*/
	}
	
	@Override
	public String toString(){
		return day + " " + time;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(day, time);
	}
}
