package model.course;



import java.time.DayOfWeek;
import java.util.Objects;

import logic.Event;
import model.constraint.TimeConstraint;
import model.course.StuffMember;
import model.loader.CourseLoader;
import model.loader.XmlCourseLoader;

public class Lesson implements Event {
	public enum Type {LECTURE, TUTORIAL, LABORATORY, PROJECT, SPORT}
	
	protected final StuffMember representer;
	protected final WeekTime startTime;
	protected final WeekTime endTime;
	protected final String place;
	protected final Type type;
	protected final int group;
	//protected int day;	
	protected final String course;
	protected final String courseName;
	
	public Lesson(StuffMember repr, WeekTime theStartTime, WeekTime endTime, String place1, Type t, int g, String c, String n) {
		if((theStartTime==null) || (place1==null))
			throw new NullPointerException();
		
		this.representer = repr;
		this.startTime = theStartTime;
		this.endTime = endTime;
		this.place = place1;
		this.type = t;
		this.group = g;
		//this.day = (theStartTime.getDay().getValue()) % 7 + 1 ;
		this.course = c;
		this.courseName = n;
	}
	
	public String getCourse() {
		return this.course;
	}
	
	public String getCourseName() {
		return this.courseName;
	}
	
	public int getGroup() {
		return this.group;
	}

	public StuffMember getRepresenter() {
		return this.representer;
	}

	public WeekTime getStartTime() {
		return this.startTime;
	}
	
	public String getRoomNumber() {
		if (!this.place.isEmpty()) {
			String[] spilt = this.place.split(" ");
			if (spilt.length > 1)
				return spilt[spilt.length - 1];
		}
		return "";
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
	
	// return value between 0 to 6.
	public int getDay(){
		return startTime.getDay() == DayOfWeek.SUNDAY ? 0 : startTime.getDay().getValue();
	}
	
	@Override
	public boolean equals(Object l){
		if (l == null) return false;
	    if (l == this) return true;
	    if (!(l instanceof Lesson))return false;
	    Lesson lesson = (Lesson)l;
	    return (((this.place).equals(lesson.getPlace())) && ((this.type) == lesson.getType())
	    		&& (((this.group) == lesson.getGroup()))
	    		&&  ((this.course).equals(lesson.getCourse())) &&
	    		((this.startTime).equals(lesson.getStartTime())) &&
	    				((this.endTime).equals(lesson.getEndTime())));
	}
	
	
	public boolean IsClashWith(Lesson ¢){
		return (startTime.compareTo(¢.getStartTime()) >= 0) && startTime.compareTo(¢.getEndTime()) < 0
				|| (endTime.compareTo(¢.getStartTime()) > 0) && endTime.compareTo(¢.getEndTime()) <= 0;

		/*
		return (startTime.compareTo(¢.getStartTime()) != 0 && startTime.compareTo(¢.getStartTime()) <= 0
				|| startTime.compareTo(¢.getEndTime()) >= 0)
				&& (endTime.compareTo(¢.getStartTime()) <= 0 || endTime.compareTo(¢.getEndTime()) >= 0)
				&& endTime.compareTo(¢.getEndTime()) != 0;*/
	}
	
	public boolean IsClashWith(TimeConstraint ¢){
		return (startTime.compareTo(¢.getStartTime()) >= 0) && startTime.compareTo(¢.getEndTime()) < 0
				|| (endTime.compareTo(¢.getStartTime()) > 0) && endTime.compareTo(¢.getEndTime()) <= 0;

		/*return (startTime.compareTo(¢.getStartTime()) != 0 && startTime.compareTo(¢.getStartTime()) <= 0
				|| startTime.compareTo(¢.getEndTime()) >= 0)
				&& (endTime.compareTo(¢.getStartTime()) <= 0 || endTime.compareTo(¢.getEndTime()) >= 0)
				&& endTime.compareTo(¢.getEndTime()) != 0;*/
	}
	
	@Override
	public String toString(){
		return "representer: " + representer + " start time: " + startTime + " end time: " + endTime;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(place, type, course, startTime, endTime);
	}
}
