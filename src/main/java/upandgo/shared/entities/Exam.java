package upandgo.shared.entities;

import java.util.Date;

public class Exam {
	
	private final LocalTime time;
	private final Month month;
	private final String dayOfMonth;
	private final String year;
	
	public Exam(LocalTime time, Month month, String dayOfMonth, String year) {
		this.time = time;
		this.dayOfMonth = dayOfMonth; 
		this.month = month;
		this.year = year;
	}
	
	
	public String getDay() {
		return dayOfMonth;
	}
	
	public LocalTime getTime() {
		return time;
	}
	
	public Month getMonth() {
		return month;
	}
	
	public String getYear() {
		return year;
	}
	
}
