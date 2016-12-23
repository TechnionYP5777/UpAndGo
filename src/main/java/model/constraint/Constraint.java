package model.constraint;

import java.util.List;

import logic.Event;
import model.course.Course;
import model.course.LessonGroup;

public abstract class Constraint implements Event {
	public abstract boolean canMeetConstraint(Course c);
	public abstract List<LessonGroup> groupsMeetsConstraint(Course c);
}