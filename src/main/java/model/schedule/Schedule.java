package model.schedule;

import java.util.ArrayList;
import java.util.List;

import model.constraint.TimeConstraint;
import model.course.LessonGroup;


public class Schedule {
	private List<LessonGroup> lessons;
	private List<TimeConstraint> constraints;
	
	public Schedule(){
		lessons = new ArrayList<>();
		constraints = new ArrayList<>();
	}
	public Schedule(List<LessonGroup> lessons,List<TimeConstraint> constraints){
		this.lessons = new ArrayList<>(lessons);
		this.constraints = new ArrayList<>(constraints);
	}
	
	/**
	 * 
	 * @param ¢
	 * @return true if lessonGroup can be added to lessond without causing a collision
	 */
	public boolean addLesson(LessonGroup ¢) {
		/*if(!lessons.contains(¢)) // add equals to lessonsgroup
			lessons.add(¢);*/
		if(lessons.isEmpty()){
			lessons.add(¢);
			return true;
		}
		for(LessonGroup l : lessons)
			if (l.isCLashWIth(¢))
				return false;
		lessons.add(¢);
		return true;
	}
	
	public void removeLesson(LessonGroup ¢) {
		lessons.remove(¢);
	}
	
	public void addConstraint(TimeConstraint ¢) {
		if(!constraints.contains(¢))
			constraints.add(¢);
	}
	
	public void removeConstraint(TimeConstraint ¢) {
		constraints.remove(¢);
	}
	
	
	public List<TimeConstraint> getConstraints() {
		return constraints;
	}
	
	
	public List<LessonGroup> getLessonGroups() {
		return lessons;
	}
	
	public Timetable getTimetable() {
		if (!isLegalSchedule())
			return null;
		Timetable $ = new Timetable();
		$.addLessons(lessons);
		return $;
			
	}
	
	public boolean hasLesson(LessonGroup ¢) {
		return lessons.contains(¢);
	}
	
	public boolean hasConstraint(TimeConstraint ¢) {
		return constraints.contains(¢);
	}
	
	public boolean isLegalSchedule(){
		for(int i=0; i < lessons.size(); ++i){
			for(int j=i+1; j < lessons.size(); ++j)
				if (lessons.get(i).isCLashWIth(lessons.get(j)))
					return false;
			for(TimeConstraint ¢ : constraints)
				if (lessons.get(i).isCLashWIth(¢))
					return false;
		}
		return true;
	}
	
	@Override
	public String toString(){
		return lessons + "";
	}
}
