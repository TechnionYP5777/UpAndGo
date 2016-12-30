package model.course;


import java.time.DayOfWeek;

import logic.Event;
import model.course.StuffMember;

public class Lesson implements Event {
	public enum Type {LECTURE, TUTORIAL, LABORATORY, PROJECT}
	
	protected final StuffMember representer;
	protected final WeekTime startTime;
	protected final WeekTime endTime;
	protected final String place;
	protected final Type type;
	protected final int group;
	protected int day;	
	protected final String course;
	
	public Lesson(StuffMember repr, WeekTime theStartTime, WeekTime endTime, String place1, Type t, int g, String c) {
		if((repr==null) || (theStartTime==null) || (place1==null))
			throw new NullPointerException();
		
		this.representer = repr;
		this.startTime = theStartTime;
		this.endTime = endTime;
		this.place = place1;
		this.type = t;
		this.group = g;
		this.day = (theStartTime.getDay().getValue()) % 7 + 1 ;
		this.course = c;
	}
	
	public String getCourse() {
		return this.course;
	}

	public StuffMember getRepresenter() {
		return this.representer;
	}

	public WeekTime getStartTime() {
		return this.startTime;
	}
	
	public WeekTime getEndTime() {
		return this.endTime;
	}

	public String getPlace() {
		return this.place;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public int getDay(){
		return this.day;
	}
	
	
}
