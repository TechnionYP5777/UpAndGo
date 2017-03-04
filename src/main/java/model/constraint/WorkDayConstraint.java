package model.constraint;

// *** JUST A DEMO CLASS, PROBABLY WON'T BE USED IN OUR PROJECT.

import java.util.List;

/**
 * @author kobybs
 * @since 22-12-16
 */

import model.course.Course;
import model.course.LessonGroup;

public class WorkDayConstraint extends Constraint {
	// private int workDay;
	public WorkDayConstraint(@SuppressWarnings("unused") final int d) {
		// workDay = d;
	}

	@Override
	public boolean canMeetConstraint(@SuppressWarnings("unused") final Course __) {
		// need to be changes after the implementaion of lessonGroup
		// for(Lesson ¢ : c.getLessons())
		// if (¢.getDay() == workDay)
		// return false;
		return true;
	}

	@Override
	@SuppressWarnings("unused")
	public List<LessonGroup> groupsMeetsConstraint(final Course __) {
		// TODO Cannot be done until someone will finish the lessonGroup job
		return null;
	}

}
