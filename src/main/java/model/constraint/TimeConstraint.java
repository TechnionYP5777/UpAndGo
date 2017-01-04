package model.constraint;
/**
 * @author kobybs
 * @since 25-12-16
 */
import java.util.ArrayList;
import java.util.List;

import model.course.Course;
import model.course.Lesson;
import model.course.LessonGroup;
import model.course.WeekTime;

public class TimeConstraint extends Constraint {
	private WeekTime startTime;
	private WeekTime endTime;
	
	public TimeConstraint(WeekTime startTime, WeekTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	
	
	@Override
	public boolean canMeetConstraint(Course c) {
		for(LessonGroup ¢ : c.getLectures())
			if (!clashWithLessonGroup(¢))
				return true;
		return false;
	}

	@Override
	public List<LessonGroup> groupsMeetsConstraint(Course c) {
		List<LessonGroup> $ = new ArrayList<>();
		for(LessonGroup ¢ : c.getLectures())
			if (!clashWithLessonGroup(¢))
				$.add(¢);
		return $;
	}
	
	private boolean clashWithLessonGroup(LessonGroup g){
		for(Lesson ¢ : g.getLessons())
			if (clashWithLesson(¢))
				return true;
		return false;
	}
	
	// DO NOT SPARTANIZE
	private boolean clashWithLesson(Lesson ¢){
		return (startTime.compareTo(¢.getStartTime()) >= 0) && startTime.compareTo(¢.getEndTime()) < 0
				|| (endTime.compareTo(¢.getStartTime()) > 0) && endTime.compareTo(¢.getEndTime()) <= 0;
	}

	public WeekTime getStartTime() {
		return startTime;
	}

	public WeekTime getEndTime() {
		return endTime;
	}

}
