package model.constraint;

/**
 * @author kobybs
 * @since 22-12-16
 */

import model.course.Course;
import model.course.Lesson;

public class WorkDayConstraint extends Constraint {
	private int workDay;
	public WorkDayConstraint(int d) {
		workDay = d;
	}
	@Override
	public boolean canMeetConstraint(Course c) {
		// need to be changes after the implementaion of lessonGroup
		for(Lesson ¢ : c.getLessons())
			if (¢.getDay() == workDay)
				return false;
		return true;
	}

}
