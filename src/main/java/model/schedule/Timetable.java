package model.schedule;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.course.Lesson;
import model.course.LessonGroup;
import model.course.WeekTime;

/**
 * /**
 * @author kobybs
 * @since 5-1-17
 */

public class Timetable {
	private final int DAYS_IN_WEEK = 7;
	private final double MAX_BLANKSPACE_RANK = 2;
	private final double BLANKSPACE_PENALTY_PER_HOUR = 0.25;
	private final double TIME_START_BONUS_PER_DAY = 0.5;
	private final double TIME_END_BONUS_PER_DAY = 0.5;
	private final double EACH_FREE_DAY_RANK = 1.5;
	
	private final List<LessonGroup> lessonGroups;
	
	private final double rankDaysoff;
	private final double rankBlankSpace;
	private double rankFreeSunday;
	private double rankFreeMonday;
	private double rankFreeTuesday;
	private double rankFreeWednesday;
	private double rankFreeThursday;
	
	public Timetable(final List<LessonGroup> lessons){
		lessonGroups = new ArrayList<>(lessons);
		rankDaysoff = rankDaysoff();
		rankBlankSpace = rankBlankSpace();
	}
	
	
	

	public List<LessonGroup> getLessonGroups(){
		return lessonGroups;
	}
	
	
	/**
	 * 
	 * @param startTime
	 * @return a rank built like that: each day which start after startTime get 0.5 points
	 */
	public double getRankOfStartTime(final LocalTime startTime) {
		if(startTime == null)
			return 0.0;
		double $ = 0.0;
		// create an histogram which holds a list of lessons times of each day
		final ArrayList< ArrayList<WeekTime> > histogram = new ArrayList<>();
		for(int ¢ = 0; ¢<DAYS_IN_WEEK; ++¢)
			histogram.add(new ArrayList<>());
		
		for(final LessonGroup lg : lessonGroups)
			for (final Lesson ¢ : lg.getLessons()) {
				histogram.get(¢.getDay()).add(¢.getStartTime());
				histogram.get(¢.getDay()).add(¢.getEndTime());
			}
		
		for(final ArrayList<WeekTime> daySchedule : histogram){
			// sort them such that the first start time of each day will be first in the list too
			Collections.sort(daySchedule, (t1, t2) -> t1.compareTo(t2));
			// add 0.5 points if daySchedule start time is greater than wanted start time
			if(!daySchedule.isEmpty() && daySchedule.get(0).getTime().compareTo(startTime) >= 0)
				$ += TIME_START_BONUS_PER_DAY;
		}
		
		return $;
	}
	
	
	/**
	 * 
	 * @param startTime
	 * @return a rank built like that: each day which start after startTime get 0.5 points
	 */
	public double getRankOfEndTime(final LocalTime endTime) {
		if(endTime == null)
			return 0.0;
		double $ = 0.0;
		// create an histogram which holds a list of lessons times of each day
		final ArrayList< ArrayList<WeekTime> > histogram = new ArrayList<>();
		for(int ¢ = 0; ¢<DAYS_IN_WEEK; ++¢)
			histogram.add(new ArrayList<>());
		
		for(final LessonGroup lg : lessonGroups)
			for (final Lesson ¢ : lg.getLessons()) {
				histogram.get(¢.getDay()).add(¢.getStartTime());
				histogram.get(¢.getDay()).add(¢.getEndTime());
			}
		
		for(final ArrayList<WeekTime> daySchedule : histogram){
			// sort them such that the first start time of each day will be first in the list too
			Collections.sort(daySchedule, (t1, t2) -> t1.compareTo(t2));
			// add 0.5 points if daySchedule start time is greater than wanted start time
			if(!daySchedule.isEmpty() && daySchedule.get(daySchedule.size()-1).getTime().compareTo(endTime) <= 0)
				$ += TIME_END_BONUS_PER_DAY;
		}
		
		return $;
	}

	/**
	 * max bonus is 2 points. each blank hours costs a penalty of 0.25 points
	 * @return
	 */
	private double rankBlankSpace() {
		
		final ArrayList< ArrayList<WeekTime> > histogram = new ArrayList<>();
		for(int ¢ = 0; ¢<DAYS_IN_WEEK; ++¢)
			histogram.add(new ArrayList<>());
		
		for(final LessonGroup lg : lessonGroups)
			for (final Lesson ¢ : lg.getLessons()) {
				histogram.get(¢.getDay()).add(¢.getStartTime());
				histogram.get(¢.getDay()).add(¢.getEndTime());
			}
		
		
		
		
		int blankMinutesSum = 0;
		for(final ArrayList<WeekTime> daySchedule : histogram){
			Collections.sort(daySchedule, (t1, t2) -> t1.compareTo(t2));
			blankMinutesSum += sumBlank(daySchedule);
		}
		
		final double penalty = 1. * BLANKSPACE_PENALTY_PER_HOUR * blankMinutesSum / 60;
		
		System.out.println("hist: " + histogram);
		System.out.println("sum: " + blankMinutesSum);
		System.out.println("penalty: " + penalty);
		//System.out.println("histover");
				//histogram[¢.getDay()] = 1;
		// don't give any value for free friday or saturday since it's usual case
		/*for(int ¢ = 0; ¢ < DAYS_IN_WEEK-2; ++¢)
			$ += 1 - histogram[¢];(*/
		//return $;
		return MAX_BLANKSPACE_RANK-penalty;
	}
	
	private int sumBlank(final ArrayList<WeekTime> daySchedule){
		int $ = 0;
		for(int ¢ = daySchedule.size()-2; ¢>0; ¢-=2)
			$ += WeekTime.difference(daySchedule.get(¢), daySchedule.get(¢ - 1));
		return $;
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
		final int[] histogram = new int[DAYS_IN_WEEK];

		for(final LessonGroup lg : lessonGroups)
			for (final Lesson ¢ : lg.getLessons())
				histogram[¢.getDay()] = 1;
		
		rankFreeThursday = rankFreeWednesday = rankFreeTuesday = rankFreeMonday = rankFreeSunday = 0;
		if(histogram[0] == 0)
			rankFreeSunday += EACH_FREE_DAY_RANK;
		if(histogram[1] == 0)
			rankFreeMonday += EACH_FREE_DAY_RANK;
		if(histogram[2] == 0)
			rankFreeTuesday += EACH_FREE_DAY_RANK;
		if(histogram[3] == 0)
			rankFreeWednesday += EACH_FREE_DAY_RANK;
		if(histogram[4] == 0)
			rankFreeThursday += EACH_FREE_DAY_RANK;
		
		// don't give any value for free friday or saturday since it's usual case
		for(int ¢ = 0; ¢ < DAYS_IN_WEEK-2; ++¢)
			$ += 1 - histogram[¢];
		return $;
	}
	
	public double getRankOfDaysoff(){
		return rankDaysoff;
	}
	
	public double getRankOfBlankSpace(){
		return rankBlankSpace;
	}
	
	
	public double getRankOfFreeSunday(){
		return rankFreeSunday;
	}
	public double getRankOfFreeMonday(){
		return rankFreeMonday;
	}
	public double getRankOfFreeTuesday(){
		return rankFreeTuesday;
	}
	public double getRankOfFreeWednesday(){
		return rankFreeWednesday;
	}
	public double getRankOfFreeThursday(){
		return rankFreeThursday;
	}
	
	@Override
	public String toString(){
		return lessonGroups + "\n";
	}
	
	
	
}
