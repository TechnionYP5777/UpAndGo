package logic;

import java.util.ArrayList;

import java.util.List;

import model.constraint.Constraint;
import model.constraint.TimeConstraint;
import model.course.Course;
import model.course.LessonGroup;
import model.schedule.Schedule;

/**
 * 
 * @author kobybs
 * @since 22-12-16
 */

@SuppressWarnings("boxing")
public class Scheduler {
	/**
	 * gets a list of courses and a list of constraints and return a possible 
	 * schedule of them which doesn't break the constraints.
	 * it works for TimeConstraints for now.
	 */
	public static Schedule schedule(List<Course> lcourse, List<TimeConstraint> cs){
		ArrayList< List<LessonGroup> > lessonsGroupArray = initMainArr(lcourse);
		
		ArrayList<Integer> indexes = initIndexes(lessonsGroupArray.size());
		ArrayList<Integer> max = initMax(lessonsGroupArray);
		
		for (int last = indexes.size() - 1, msb;;) {
			System.out.println(indexes);
			List<LessonGroup> lessons = getScheduleByIndexes(lessonsGroupArray, indexes);
			Schedule $ = new Schedule();
			
			boolean b = false;
			for(int ¢ = 0; ¢ < lessons.size(); ++¢){
				b = $.addLesson( lessons.get(¢) );
				if(!b)
				{
					// if you can't add that lesson than all combination including him are not valid
					// therefore there is no use to check the rest of them - increase bit of found lesson
					indexes.set(¢, indexes.get(¢)+1);
					break;
				}
			}
			if(b){
				return $;
			}
			/*for(LessonGroup l : lessons){
				b = $.addLesson(l);
				if(!b) break;
			}
			if(b){
				return $;
			}*/
			
			/*Schedule $ = new Schedule(lessons,cs);
			if($.isLegalSchedule()){
				return $;
			}*/
			
			// increase last index by 1;
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
		return null;
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
	
	private static ArrayList< List<LessonGroup> > initMainArr(List<Course> lcourse){
		ArrayList< List<LessonGroup> > $ = new ArrayList<>();
		for(Course ¢ : lcourse){
			$.add(¢.getLectures());
			$.add(¢.getTutorials());
		}
		return $;
	}
	
	private static ArrayList<Integer> initIndexes(int size){
		ArrayList<Integer> $ = new ArrayList<>();
		for(int ¢ = 0; ¢<size; ++¢)
			$.add(0);
		return $;
			
	}
	
	
	private static ArrayList<Integer> initMax(ArrayList< List<LessonGroup> > lessonsGroupArray){
		ArrayList<Integer> $ = new ArrayList<>();
		for(List<LessonGroup> ¢ : lessonsGroupArray )
			$.add(¢.size()-1);
		return $;
			
	}
	
	private static List<LessonGroup> getScheduleByIndexes(ArrayList< List<LessonGroup> > lessonsGroupArray, ArrayList<Integer> indexes){
		List<LessonGroup> $ = new ArrayList<>();
		for(int ¢ = 0; ¢ < indexes.size(); ++¢)
			$.add(lessonsGroupArray.get(¢).get(indexes.get(¢)));
		
		return $;
		
	}
	
	
	

}
