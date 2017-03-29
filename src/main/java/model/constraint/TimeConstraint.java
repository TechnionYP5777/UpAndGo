package model.constraint;

/**
 * @author kobybs
 * @since 25-12-16
 * 
 * Class that represents hours that aren't available for studying.
 */



import model.course.Lesson;
import model.course.LessonGroup;
import model.course.WeekTime;

public class TimeConstraint implements Constraint<TimeConstraint> {
	private final WeekTime startTime;
	private final WeekTime endTime;

	public TimeConstraint(final WeekTime startTime, final WeekTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	

	
	
	public boolean isClashWith(final Lesson ¢) {
		return startTime.compareTo(¢.getStartTime()) >= 0 && startTime.compareTo(¢.getEndTime()) < 0
				|| endTime.compareTo(¢.getStartTime()) > 0 && endTime.compareTo(¢.getEndTime()) <= 0;
	}
	
	@Override
	public boolean isClashWith(final LessonGroup g) {
		for (final Lesson ¢ : g.getLessons())
			if (isClashWith(¢))
				return true;
		return false;
	}

	@Override
	public boolean isClashWith(final TimeConstraint ¢) {
		return startTime.compareTo(¢.getStartTime()) >= 0 && startTime.compareTo(¢.getEndTime()) < 0
				|| endTime.compareTo(¢.getStartTime()) > 0 && endTime.compareTo(¢.getEndTime()) <= 0;

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
		for (final LessonGroup ¢ : c.getLectures())
			if (!clashWithLessonGroup(¢))
				return true;
		return false;
	}

	@Override
	public List<LessonGroup> groupsMeetsConstraint(final Course c) {
		final List<LessonGroup> $ = new ArrayList<>();
		for (final LessonGroup ¢ : c.getLectures())
			if (!clashWithLessonGroup(¢))
				$.add(¢);
		return $;
	}

	private boolean clashWithLessonGroup(final LessonGroup g) {
		for (final Lesson ¢ : g.getLessons())
			if (clashWithLesson(¢))
				return true;
		return false;
	}

	// DO NOT SPARTANIZE
	private boolean clashWithLesson(final Lesson ¢) {
		return startTime.compareTo(¢.getStartTime()) >= 0 && startTime.compareTo(¢.getEndTime()) < 0
				|| endTime.compareTo(¢.getStartTime()) > 0 && endTime.compareTo(¢.getEndTime()) <= 0;
	} */

	public WeekTime getStartTime() {
		return startTime;
	}

	public WeekTime getEndTime() {
		return endTime;
	}



	


	


	
}
