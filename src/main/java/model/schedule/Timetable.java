package model.schedule;

import java.util.ArrayList;
import java.util.List;

import model.course.Lesson;
import model.course.LessonGroup;
import model.course.WeekTime;

/**
 * /**
 * @author kobybs
 * @since 5-1-17
 * notice that this file discarded the TimeTable previously written by danabra
 */

public class Timetable {
	private final int DAYS_IN_WEEK = 7;
	private final double MAX_BLANKSPACE_RANK = 2;
	private final double BLANKSPACE_PENALTY_PER_HOUR = 0.2;
	
	private int rankDaysoff;
	private double rankBlankSpace;
	private List<LessonGroup> lessonGroups;
	
	
	public Timetable(List<LessonGroup> lessons){
		this.lessonGroups = new ArrayList<>(lessons);
		rankDaysoff = rankDaysoff();
		rankBlankSpace = rankBlankSpace();
	}
	
	public List<LessonGroup> getLessonGroups(){
		return lessonGroups;
	}

	/**
	 * max bonus for 
	 * @return
	 */
	private double rankBlankSpace() {
		int $ = 0;
		//List<WeekTime>[] histogram = new List[DAYS_IN_WEEK];
		ArrayList< ArrayList<WeekTime> > histogram = new ArrayList<>();
		for(int i = 0; i<DAYS_IN_WEEK; i++)
			histogram.add(new ArrayList<>());
		//System.out.println("hist: " + histogram);
		for(LessonGroup lg : lessonGroups)
			for (Lesson ¢ : lg.getLessons()){
				histogram.get(¢.getDay()).add(¢.getStartTime());
				histogram.get(¢.getDay()).add(¢.getEndTime());
			}
		System.out.println("hist: " + histogram);
				//histogram[¢.getDay()] = 1;
		// don't give any value for free friday or saturday since it's usual case
		/*for(int ¢ = 0; ¢ < DAYS_IN_WEEK-2; ++¢)
			$ += 1 - histogram[¢];(*/
		//return $;
		return 0;
	}
	
	private void sumBlank(ArrayList<WeekTime> daySchedule){
		int numOfLessons = daySchedule.size()/2;
		/*for(int i = 0; i<numOfLessons-1; i++){
			(2*(i+1)+1)-(2*(i)+1)
		}*/
		for(int i = daySchedule.size()-2; i>0; i-=2){
			//System.out.println(daySchedule.get(i).getTime()-daySchedule.get(i-1).getTime());
		}
	}

	/**
	 * 
	 * @return a value between 0-5, 0 - no days off between sunday to thusday
	 * 5 - all days between sunday to thusday are free.
	 * friday and saturday doesn't count since they are usually free so we don't want to
	 * give bonus for that.
	 */
	private int rankDaysoff() {
		int $ = 0;
		int[] histogram = new int[DAYS_IN_WEEK];

		for(LessonGroup lg : lessonGroups)
			for (Lesson ¢ : lg.getLessons())
				histogram[¢.getDay()] = 1;
		// don't give any value for free friday or saturday since it's usual case
		for(int ¢ = 0; ¢ < DAYS_IN_WEEK-2; ++¢)
			$ += 1 - histogram[¢];
		return $;
	}
	
	public int getRankOfDaysoff(){
		
		return rankDaysoff;
	}
	
	public double getRankOfBlankSpace(){
		return rankBlankSpace;
	}
	
	@Override
	public String toString(){
		return lessonGroups + "\n";
	}
	
	
	
}
