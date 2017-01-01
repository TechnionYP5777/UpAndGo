package model.course;



import logic.Event;
import model.constraint.TimeConstraint;
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
	
	public int getGroup() {
		return this.group;
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
	
	@Override
	public boolean equals(Object l){
		if (l == null) return false;
	    if (l == this) return true;
	    if (!(l instanceof Lesson))return false;
	    Lesson lesson = (Lesson)l;
	    return (((this.place).equals(lesson.getPlace())) && ((this.type) == lesson.getType())
	    		&& (((this.group) == lesson.getGroup())) && ((this.day) == lesson.getDay())
	    		&&  ((this.course).equals(lesson.getCourse())) && ((this.representer).equals(lesson.getRepresenter())) &&
	    		((this.startTime).equals(lesson.getStartTime())) &&
	    				((this.endTime).equals(lesson.getEndTime())));
	}
	public boolean IsClashWith(Lesson ¢){
		return (startTime.compareTo(¢.getStartTime()) != 0 && startTime.compareTo(¢.getStartTime()) <= 0
				|| startTime.compareTo(¢.getEndTime()) >= 0)
				&& (endTime.compareTo(¢.getStartTime()) <= 0 || endTime.compareTo(¢.getEndTime()) >= 0)
				&& endTime.compareTo(¢.getEndTime()) != 0;
	}
	
	public boolean IsClashWith(TimeConstraint ¢){
		return (startTime.compareTo(¢.getStartTime()) != 0 && startTime.compareTo(¢.getStartTime()) <= 0
				|| startTime.compareTo(¢.getEndTime()) >= 0)
				&& (endTime.compareTo(¢.getStartTime()) <= 0 || endTime.compareTo(¢.getEndTime()) >= 0)
				&& endTime.compareTo(¢.getEndTime()) != 0;
	}
	
	@Override
	public String toString(){
		String ret = "representer: " + representer + " start time: " + startTime + " end time: " + endTime; 
		return ret;
	}
}
