package upandgo.shared.entities;

import java.util.Objects;

import com.google.gwt.user.client.rpc.IsSerializable;

import upandgo.shared.entities.constraint.TimeConstraint;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-12-16
 * 
 * Class that holds information about specific lesson.
 * 
 */
public class Lesson implements IsSerializable {
	
	public enum Type implements IsSerializable{
		LECTURE("הרצאה"), TUTORIAL("תרגול"), LABORATORY("מעבדה"), PROJECT("פרויקט"), SPORT("ספורט");
		
		private final String hebrewString;
		
		private Type(final String hebrewString){
			this.hebrewString = hebrewString;
		}
		
		public static Type fromString(String text){
			if (text != null)
				for (Type type : Type.values())
					if (type.hebrewString.equals(text))
						return type;
			return null;
		}
		
		@Override
		public String toString(){
			return hebrewString;
		}
	}

	protected StuffMember representer;
	protected WeekTime startTime;
	protected WeekTime endTime;
	protected String place;
	protected Type type;
	protected int group;
	protected String courseId;
	protected String courseName;

	public Lesson() {
		representer = null;
		startTime = null;
		endTime = null;
		place = null;
		group = 0;
		courseId = null;
		courseName = null;
	}
	
	
	public Lesson(final StuffMember repr, WeekTime theStartTime, WeekTime endTime, final String place1,
			final Type t, final int g, final String c, final String n) {
		representer = repr;
		startTime = theStartTime;
		this.endTime = endTime;
		place = place1;
		type = t;
		group = g;
		courseId = c;
		courseName = n;
	}
	
	public Lesson(Lesson other){
		representer = other.getRepresenter();
		startTime = other.getStartTime();
		endTime = other.getEndTime();
		place = other.getPlace();
		type = other.getType();
		group = other.getGroup();
		courseId = other.getCourseId();
		courseName = other.getCourseName();
	}

	public String getCourseId() {
		return courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public int getGroup() {
		return group;
	}

	public StuffMember getRepresenter() {
		return representer;
	}

	public WeekTime getStartTime() {
		return startTime;
	}

	public String getRoomNumber() {
		if (place.isEmpty())
			return "";
		final String[] $ = place.split(" ");
		return $.length <= 1 ? "" : $[$.length - 1];
	}

	public WeekTime getEndTime() {
		return endTime;
	}

	public String getPlace() {
		return place;
	}

	public Type getType() {
		return type;
	}

	// return value between 0 to 6.
	public int getDay() {
		return startTime.getDay().ordinal();
	}

	@Override
	public boolean equals(final Object l) {
		if (l == null)
			return false;
		if (l == this)
			return true;
		if (!(l instanceof Lesson))
			return false;
		final Lesson $ = (Lesson) l;
		return place.equals($.getPlace()) && type == $.getType() && group == $.getGroup()
				&& courseId.equals($.getCourseId()) && startTime.equals($.getStartTime())
				&& endTime.equals($.getEndTime());
	}

	public boolean IsClashWith(final Lesson xxx) {
		return startTime.compareTo(xxx.getStartTime()) >= 0 && startTime.compareTo(xxx.getEndTime()) < 0
				|| endTime.compareTo(xxx.getStartTime()) > 0 && endTime.compareTo(xxx.getEndTime()) <= 0
				|| startTime.compareTo(xxx.getStartTime()) < 0 && endTime.compareTo(xxx.getEndTime()) > 0;

	}

	public boolean IsClashWith(final TimeConstraint xxx) {
		return startTime.compareTo(xxx.getStartTime()) >= 0 && startTime.compareTo(xxx.getEndTime()) < 0
				|| endTime.compareTo(xxx.getStartTime()) > 0 && endTime.compareTo(xxx.getEndTime()) <= 0
				|| startTime.compareTo(xxx.getStartTime()) < 0 && endTime.compareTo(xxx.getEndTime()) > 0;


	}

	@Override
	public String toString() {
		return "courseID: " + courseId + "/" + group + " " + type + " start time: " + startTime + " end time: " + endTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(place, type, courseId, startTime, endTime);
	}
}
