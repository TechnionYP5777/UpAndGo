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
				|| endTime.compareTo(xxx.getStartTime()) > 0 && endTime.compareTo(xxx.getEndTime()) <= 0;
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
	
	// TO BE REMOVED ONCE ASSURED THERE IS NO NEED FOR THAT FUNCTIONALITY
	/*public boolean isCLashWIth(TimeConstraint newc) {
		// TODO Auto-generated method stub
		return false;
	}*/
	
	
	/* TO BE REMOVED ONCE MADE SOME PROGRESS WITH THE PROJECT THE ASsUREs
	 * THERE IS NOT NEED FOR THAT FUNCTIONALITY
	@Override
	public boolean canMeetConstraint(final Course c) {
		for (final LessonGroup xxx : c.getLectures())
			if (!clashWithLessonGroup(xxx))
				return true;
		return false;
	}

	@Override
	public List<LessonGroup> groupsMeetsConstraint(final Course c) {
		final List<LessonGroup> $ = new ArrayList<>();
		for (final LessonGroup xxx : c.getLectures())
			if (!clashWithLessonGroup(xxx))
				$.add(xxx);
		return $;
	}

	private boolean clashWithLessonGroup(final LessonGroup g) {
		for (final Lesson xxx : g.getLessons())
			if (clashWithLesson(xxx))
				return true;
		return false;
	}

	// DO NOT SPARTANIZE
	private boolean clashWithLesson(final Lesson xxx) {
		return startTime.compareTo(xxx.getStartTime()) >= 0 && startTime.compareTo(xxx.getEndTime()) < 0
				|| endTime.compareTo(xxx.getStartTime()) > 0 && endTime.compareTo(xxx.getEndTime()) <= 0;
	} */

	public WeekTime getStartTime() {
		return startTime;
	}

	public WeekTime getEndTime() {
		return endTime;
	}



	


	


	
}
