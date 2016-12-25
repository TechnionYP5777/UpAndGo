package model.constraint;

import java.util.List;
 /**
  * @author kobybs
  * @since 25-12-16
  */

import logic.Event;
import model.course.Course;
import model.course.LessonGroup;

public abstract class Constraint implements Event {
	public abstract boolean canMeetConstraint(Course c);
	public abstract List<LessonGroup> groupsMeetsConstraint(Course c);
}