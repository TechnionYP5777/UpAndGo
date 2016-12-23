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
	//TODO: implement
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
}
