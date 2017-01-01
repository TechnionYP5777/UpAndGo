package model.course;

import java.time.DayOfWeek;
/**
 * @author kobybs
 * @since 25-12-16
 */
import java.time.LocalTime;

public class WeekTime {
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
	
	@Override
	public boolean equals(Object d){
		return d != null && (d == this || d instanceof WeekTime && (this.day.equals(((WeekTime) d).getDay()))
				&& ((this.time).equals(((WeekTime) d).getTime())));
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
	public String toString(){
		return day + " " + time;
	}
}
