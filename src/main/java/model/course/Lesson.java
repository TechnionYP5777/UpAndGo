package model.course;

import java.time.LocalDateTime;
import java.util.Optional;

import logic.Event;
import model.course.StuffMember;

public class Lesson implements Event {
	public enum Type {LECTURE, TUTORIAL, LABORATORY, PROJECT}
	
	protected final StuffMember representer;
	protected final LocalDateTime startTime;
	protected final int duration;
	protected final String place;
	protected final Type type;
	protected final int group;
	protected final Course course;
	
	public Lesson(StuffMember repr, LocalDateTime theStartTime, int dur, String place1, Type t, int g, Course c) {
		if((repr==null) || (theStartTime==null) || (place1==null))
			throw new NullPointerException();
		
		this.representer = repr;
		this.startTime = theStartTime;
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

	public LocalDateTime getStartTime() {
		return this.startTime;
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
	
	
}
