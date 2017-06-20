package upandgo.shared.entities;

import java.io.Serializable;
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
	private static final long serialVersionUID = 903076596050490635L;

	public enum Type {
		LECTURE, TUTORIAL, LABORATORY, PROJECT, SPORT
	}

	protected StuffMember representer;
	protected WeekTime startTime;
	protected WeekTime endTime;
	protected String place;
	protected Type type;
	protected int group;
	// protected int day;
	protected String courseId;
	protected String courseName;

	public Lesson() {
		representer = null;
		startTime = null;
		endTime = null;
		place = null;
		//type = t;
		group = 0;
		// this.day = (theStartTime.getDay().getValue()) % 7 + 1 ;
		courseId = null;
		courseName = null;
	}
	
	
	public Lesson(final StuffMember repr, WeekTime theStartTime, WeekTime endTime, final String place1,
			final Type t, final int g, final String c, final String n) {
//		if (theStartTime == null || place1 == null)
//			throw new NullPointerException();

		representer = repr;
		startTime = theStartTime;
		this.endTime = endTime;
		place = place1;
		type = t;
		group = g;
		// this.day = (theStartTime.getDay().getValue()) % 7 + 1 ;
		courseId = c;
		courseName = n;
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
		if (!place.isEmpty()) {
			final String[] $ = place.split(" ");
			if ($.length > 1)
				return $[$.length - 1];
		}
		return "";
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
		//return startTime.getDay() == DayOfWeek.SUNDAY ? 0 : startTime.getDay().getValue();
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
				|| endTime.compareTo(xxx.getStartTime()) > 0 && endTime.compareTo(xxx.getEndTime()) <= 0;

		// THOSE COMMENTS ARE HERE SINCE ITS ALMOST IMPOSSIBLE 
		// TO UNDERSTAND THE IDEA BEHIND THE SPARTANIZED VERSION OF IT
		/*
		 * return (startTime.compareTo(xxx.getStartTime()) != 0 &&
		 * startTime.compareTo(xxx.getStartTime()) <= 0 ||
		 * startTime.compareTo(xxx.getEndTime()) >= 0) &&
		 * (endTime.compareTo(xxx.getStartTime()) <= 0 ||
		 * endTime.compareTo(xxx.getEndTime()) >= 0) &&
		 * endTime.compareTo(xxx.getEndTime()) != 0;
		 */
	}

	public boolean IsClashWith(final TimeConstraint xxx) {
		return startTime.compareTo(xxx.getStartTime()) >= 0 && startTime.compareTo(xxx.getEndTime()) < 0
				|| endTime.compareTo(xxx.getStartTime()) > 0 && endTime.compareTo(xxx.getEndTime()) <= 0;

		/*
		 * return (startTime.compareTo(xxx.getStartTime()) != 0 &&
		 * startTime.compareTo(xxx.getStartTime()) <= 0 ||
		 * startTime.compareTo(xxx.getEndTime()) >= 0) &&
		 * (endTime.compareTo(xxx.getStartTime()) <= 0 ||
		 * endTime.compareTo(xxx.getEndTime()) >= 0) &&
		 * endTime.compareTo(xxx.getEndTime()) != 0;
		 */
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
