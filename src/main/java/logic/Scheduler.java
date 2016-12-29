package logic;

import java.util.ArrayList;
import java.util.List;

import model.constraint.Constraint;
import model.course.Course;
import model.course.LessonGroup;

/**
 * 
 * @author kobybs
 * @since 22-12-16
 */

@SuppressWarnings("boxing")
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
	
	private ArrayList< List<LessonGroup> > initMainArr(List<Course> lcourse){
		ArrayList< List<LessonGroup> > $ = new ArrayList<>();
		for(Course ¢ : lcourse){
			$.add(¢.getLectures());
			$.add(¢.getTutorials());
		}
		return $;
	}
	
	private ArrayList<Integer> initIndexes(int size){
		ArrayList<Integer> $ = new ArrayList<>();
		for(int ¢ = 0; ¢<size; ++¢)
			$.add(0);
		return $;
			
	}
	
	
	private ArrayList<Integer> initMax(ArrayList< List<LessonGroup> > lessonsGroupArray){
		ArrayList<Integer> $ = new ArrayList<>();
		for(List<LessonGroup> ¢ : lessonsGroupArray )
			$.add(¢.size()-1);
		return $;
			
	}
	
	
	
	public void expo(List<Course> lcourse){
		ArrayList< List<LessonGroup> > lessonsGroupArray = initMainArr(lcourse);
		
		ArrayList<Integer> indexes = initIndexes(lessonsGroupArray.size());
		ArrayList<Integer> max = initMax(lessonsGroupArray);
		
		for (int last = indexes.size() - 1, msb;;) {
			//List<LessonGroup> schedule = getScheduleByIndexes(indexes);
			//verifySchedule(schedule);
			System.out.println(indexes);
			indexes.set(last, indexes.get(last) + 1);
			if (indexes.get(last) > max.get(last)) {
				msb = last - 1;
				// find lowest index which is not yet maxed
				for (; msb >= 0; --msb)
					if (indexes.get(msb) < max.get(msb))
						break;
				// if every index is max than we made all combinations
				if (msb < 0)
					break;
				// increase msb and zero everything to its right
				indexes.set(msb, indexes.get(msb) + 1);
				for (int ¢ = msb + 1; ¢ <= last; ++¢)
					indexes.set(¢, 0);
			}
		}
		
	}
}
