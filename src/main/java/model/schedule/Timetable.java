package model.schedule;

import java.util.ArrayList;
import java.util.List;

import model.course.Lesson;
import model.course.LessonGroup;

/**
 * /**
 * @author kobybs
 * @since 5-1-17
 * notice that this file discarded the TimeTable previously written by danabra
 */

public class Timetable {
	private final int DAYS_IN_WEEK = 7;
	
	private int rankDaysoff;
	private int rankBlankSpace;
	private List<LessonGroup> lessonGroups;
	
	
	public Timetable(List<LessonGroup> lessons){
		this.lessonGroups = new ArrayList<>(lessons);
		rankDaysoff = rankDaysoff();
		rankBlankSpace = rankBlankSpace();
	}
	
	public List<LessonGroup> getLessonGroups(){
		return lessonGroups;
	}

	private int rankBlankSpace() {
		// TODO Auto-generated method stub
		return 0;
	}

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
	
	public int getRankOfBlankSpace(){
		return rankBlankSpace;
	}
	
	@Override
	public String toString(){
		return lessonGroups + "\n";
	}
	
}
