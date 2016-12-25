package model.constraint;

import java.time.LocalDateTime;
import java.util.List;

import model.course.Course;
import model.course.Lesson;
import model.course.LessonGroup;

public class TimeConstraint extends Constraint {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	public TimeConstraint(LocalDateTime startTime, LocalDateTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	@Override
	public boolean canMeetConstraint(Course c) {
		//LocalDateTime courseStartTime = c.getLectures();
		for(LessonGroup lg : c.getLectures()){
			for(Lesson l : lg.getLessons()){
				//l.getStartTime() 
			}
		}
		return false;
	}

	@Override
	public List<LessonGroup> groupsMeetsConstraint(Course c) {
		// TODO Auto-generated method stub
		return null;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

}
