package upandgo.shared.entities.constraint;

import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.WeekTime;

public class TimeConstraint implements Constraint<TimeConstraint> {
	private final WeekTime startTime;
	private final WeekTime endTime;

	public TimeConstraint(final WeekTime startTime, final WeekTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
		
	public boolean isClashWith(final Lesson xxx) {
		return startTime.compareTo(xxx.getStartTime()) >= 0 && startTime.compareTo(xxx.getEndTime()) < 0
				|| endTime.compareTo(xxx.getStartTime()) > 0 && endTime.compareTo(xxx.getEndTime()) <= 0
				|| startTime.compareTo(xxx.getStartTime()) < 0 && endTime.compareTo(xxx.getEndTime()) > 0;
	}
	
	@Override
	public boolean isClashWith(final LessonGroup g) {
		for (final Lesson xxx : g.getLessons())
			if (isClashWith(xxx))
				return true;
		return false;
	}

	@Override
	public boolean isClashWith(final TimeConstraint xxx) {
		return startTime.compareTo(xxx.getStartTime()) >= 0 && startTime.compareTo(xxx.getEndTime()) < 0
				|| endTime.compareTo(xxx.getStartTime()) > 0 && endTime.compareTo(xxx.getEndTime()) <= 0;

	}
	
	public WeekTime getStartTime() {
		return startTime;
	}

	public WeekTime getEndTime() {
		return endTime;
	}



	


	


	
}
