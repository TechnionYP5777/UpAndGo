package model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultimap;

import logic.Scheduler;
import model.course.Course;

public class TimeTableModel implements Model {

	List<Course> courses;

	boolean isDaysoffCount;
	boolean isBlankSpaceCount;
	
	protected HashMultimap<String, PropertyChangeListener> listenersMap;
	
	public TimeTableModel() {
		this.courses = new ArrayList<>();
		this.listenersMap = HashMultimap.create();
		this.isBlankSpaceCount = this.isDaysoffCount = false;
	}
	
	public void setCourses(List<Course> ¢) {
		courses = new ArrayList<>(¢);
	}
	
	public void loadSchedule() {
//		Scheduler.sortedBy(Scheduler.getTimetablesList(courses), isDaysoffCount, isBlankSpaceCount);
		// TODO implement it
	}
	
	public void loadNextSchedule() {
		// TODO implement it
	}

	public void loadPrevSchedule() {
		// TODO implement it
	}
	
	@Override
	public void addPropertyChangeListener(String property, PropertyChangeListener l) {
		if (property == null || l == null)
			throw new NullPointerException();
		this.listenersMap.put(property, l);

	}

	@Override
	public void removePropertyChangeListener(String property, PropertyChangeListener l) {
		if (property != null && l != null && this.listenersMap.containsKey(property))
			this.listenersMap.remove(property, l);

	}
}
