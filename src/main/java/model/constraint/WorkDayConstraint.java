package model.constraint;


// *** JUST A DEMO CLASS, PROBABLY WON'T BE USED IN OUR PROJECT.

import java.util.List;

/**
 * @author kobybs
 * @since 22-12-16
 */

import model.course.Course;
import model.course.Lesson;
import model.course.LessonGroup;

public class WorkDayConstraint extends Constraint {
	private int workDay;
	public WorkDayConstraint(int d) {
		workDay = d;
	}
	@Override
	public boolean canMeetConstraint(Course c) {
		// need to be changes after the implementaion of lessonGroup
		//for(Lesson ¢ : c.getLessons())
		//	if (¢.getDay() == workDay)
		//		return false;
		return true;
	}
	@SuppressWarnings("unused")
	@Override
	public List<LessonGroup> groupsMeetsConstraint(Course __) {
		// TODO Cannot be done until someone will finish the lessonGroup job
		return null;
	}
	
	

}
