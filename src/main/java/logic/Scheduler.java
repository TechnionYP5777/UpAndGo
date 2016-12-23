package logic;

import java.util.List;

import model.constraint.Constraint;
import model.course.Course;

/**
 * 
 * @author kobybs
 * @since 22-12-16
 */

public class Scheduler {
	/**
	 * gets a list of courses and a list of constraints and return a possible 
	 * schedule of them which doesnt break the constraints
	 */
	public static void Schedule(List<Course> lcourse, List<Constraint> lconstraint){
		return;
	}
	
	/**
	 * @param c
	 * @param lconstraint
	 * @return false if course c cannot meet the given list of constraints
	 */
	public static boolean canMeetConstraints(Course c, List<Constraint> lconstraint){
		for(Constraint ¢ : lconstraint)
			if(!¢.canMeetConstraint(c))
				return false;
		return true;
	}
	
	/**
	 * @param c
	 * @param lconstraint
	 * @return false if list of courses cannot meet the given list of constraints
	 * 			this is a shallow check one by one, and doesnt mean all courses can be placed together.
	 */
	public static boolean canMeetConstraints(List<Course> lcourse, List<Constraint> lconstraint){
		for(Course ¢ : lcourse)
			if (!canMeetConstraints(¢, lconstraint))
				return false;
		return true;
	}
}
