package logic.schedule;

import java.util.List;

import logic.course.Course;


public class Schedule {
	public Schedule(){
		//TODO: implement
	}
	
	public void addCourse(@SuppressWarnings("unused") Course __) {
		//TODO: implement
	}
	
	public void removeCourse(@SuppressWarnings("unused") Course __) {
		//TODO: implement
	}
	
	public void removeCourse(@SuppressWarnings("unused") String courseName) {
		//TODO: implement
	}
	
	public void addConstraint(@SuppressWarnings("unused") Constraint __) {
		//TODO: implement
	}
	
	public void removeConstraint(@SuppressWarnings("unused") Constraint __) {
		//TODO: implement
	}
	
	@SuppressWarnings("static-method")
	public List<Constraint> getConstraints() {
		//TODO: implement
		return null;
	}
	
	
	@SuppressWarnings("static-method")
	public List<Course> getCourses() {
		//TODO: implement
		return null;
	}
	
	@SuppressWarnings("static-method")
	public Timetable getTimetable() {
		//TODO: implement
		return null;
	}
	
	@SuppressWarnings("static-method")
	public boolean hasCourse(@SuppressWarnings("unused") String name) {
		//TODO: implement
		return true;
	}
	
	@SuppressWarnings("static-method")
	public boolean hasConstraint(@SuppressWarnings("unused") Constraint c) {
		//TODO: implement
		return true;
	}
}
