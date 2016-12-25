package model.course;


import logic.Event;
import model.course.StuffMember;

public class Lesson implements Event {
	public enum Type {LECTURE, TUTORIAL, LABORATORY, PROJECT}
	
	protected final StuffMember representer;
	protected final WeekTime startTime;
	protected final WeekTime endTime;
	protected final int duration;
	protected final String place;
	protected final Type type;
	protected final int group;
	protected int day;	
	protected final Course course;
	
	public Lesson(StuffMember repr, WeekTime theStartTime, WeekTime endTime, int dur, String place1, Type t, int g, Course c) {
		if((repr==null) || (theStartTime==null) || (place1==null))
			throw new NullPointerException();
		
		this.representer = repr;
		this.startTime = theStartTime;
		this.endTime = endTime;
		this.duration = dur;
		this.place = place1;
		this.type = t;
		this.group = g;
		this.course = c;
	}
	
	public Course getCourse() {
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

	public int getDuration() {
		return this.duration;
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
