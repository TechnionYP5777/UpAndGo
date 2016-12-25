package model.course;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class WeekTime2 {
	private DayOfWeek day;
	private LocalTime startHour; // = LocalTime.of(22, 15);
	private LocalTime endHour;
	
	public WeekTime2(DayOfWeek day, LocalTime startHour, LocalTime endHour){
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
	}
	
	/*public static WeekTime of(int hour,
            int minute){
				return new WeekTime(day, hour, minute);
		
	}
	
	public static WeekTime of(int hour){
				return new WeekTime(day, hour, 0);
		
	}*/
}
