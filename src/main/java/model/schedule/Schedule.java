package model.schedule;

import java.util.ArrayList;
import java.util.List;

import model.constraint.Constraint;
import model.constraint.TimeConstraint;
import model.course.LessonGroup;


public class Schedule {
	private List<LessonGroup> lessons;
	private List<TimeConstraint> constraints;
	public Schedule(){
		lessons = new ArrayList<>();
		constraints = new ArrayList<>();
	}
	
	public void addLesson(LessonGroup ¢) {
		if(!lessons.contains(¢)) // add equals to lessonsgroup
			lessons.add(¢);
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
	
	@SuppressWarnings("static-method")
	public List<Constraint> getConstraints() {
		//TODO: implement
		return null;
	}
	
	
	public List<LessonGroup> getLessons() {
		return lessons;
	}
	
	@SuppressWarnings("static-method")
	public Timetable getTimetable() {
		//TODO: implement
		return null;
	}
	
	public boolean hasLesson(LessonGroup ¢) {
		return lessons.contains(¢);
	}
	
	public boolean hasConstraint(TimeConstraint ¢) {
		return constraints.contains(¢);
	}
}
