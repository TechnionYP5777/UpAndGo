package model.constraint;

import logic.Event;
import model.course.Course;

public abstract class Constraint implements Event {
	public abstract boolean canMeetConstraint(Course c);
}