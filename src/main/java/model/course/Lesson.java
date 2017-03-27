package model.course;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Objects;

import logic.Event;
import model.constraint.TimeConstraint;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-12-16
 * 
 * Class that holds information about specific lesson.
 * 
 */
public class Lesson implements Event, Serializable {
	private static final long serialVersionUID = 903076596050490635L;

	public enum Type {
		LECTURE, TUTORIAL, LABORATORY, PROJECT, SPORT
	}

	protected final StuffMember representer;
	protected final WeekTime startTime;
	protected final WeekTime endTime;
	protected final String place;
	protected final Type type;
	protected final int group;
	// protected int day;
	protected final String courseId;
	protected final String courseName;

	public Lesson(final StuffMember repr, final WeekTime theStartTime, final WeekTime endTime, final String place1,
			final Type t, final int g, final String c, final String n) {
		if (theStartTime == null || place1 == null)
			throw new NullPointerException();

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

	public String getCourse() {
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
		return startTime.getDay() == DayOfWeek.SUNDAY ? 0 : startTime.getDay().getValue();
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
				&& courseId.equals($.getCourse()) && startTime.equals($.getStartTime())
				&& endTime.equals($.getEndTime());
	}

	public boolean IsClashWith(final Lesson ¢) {
		return startTime.compareTo(¢.getStartTime()) >= 0 && startTime.compareTo(¢.getEndTime()) < 0
				|| endTime.compareTo(¢.getStartTime()) > 0 && endTime.compareTo(¢.getEndTime()) <= 0;

		/*
		 * return (startTime.compareTo(¢.getStartTime()) != 0 &&
		 * startTime.compareTo(¢.getStartTime()) <= 0 ||
		 * startTime.compareTo(¢.getEndTime()) >= 0) &&
		 * (endTime.compareTo(¢.getStartTime()) <= 0 ||
		 * endTime.compareTo(¢.getEndTime()) >= 0) &&
		 * endTime.compareTo(¢.getEndTime()) != 0;
		 */
	}

	public boolean IsClashWith(final TimeConstraint ¢) {
		return startTime.compareTo(¢.getStartTime()) >= 0 && startTime.compareTo(¢.getEndTime()) < 0
				|| endTime.compareTo(¢.getStartTime()) > 0 && endTime.compareTo(¢.getEndTime()) <= 0;

		/*
		 * return (startTime.compareTo(¢.getStartTime()) != 0 &&
		 * startTime.compareTo(¢.getStartTime()) <= 0 ||
		 * startTime.compareTo(¢.getEndTime()) >= 0) &&
		 * (endTime.compareTo(¢.getStartTime()) <= 0 ||
		 * endTime.compareTo(¢.getEndTime()) >= 0) &&
		 * endTime.compareTo(¢.getEndTime()) != 0;
		 */
	}

	@Override
	public String toString() {
		return "representer: " + representer + " start time: " + startTime + " end time: " + endTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(place, type, courseId, startTime, endTime);
	}
}
