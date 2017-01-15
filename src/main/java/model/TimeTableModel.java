package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;

import logic.Scheduler;
import model.course.Course;
import model.course.LessonGroup;
import model.schedule.Timetable;
import property.TimeTableProperty;

public class TimeTableModel implements Model {

	protected List<Course> courses = new ArrayList<>();

	protected boolean isDaysoffCount;
	protected boolean isBlankSpaceCount;
	protected LocalTime minStartTime;
	protected LocalTime maxEndTime;
	
	protected List<List<LessonGroup>> lessonGroupsList = new ArrayList<>();
	protected int sched_index;
	
	protected HashMultimap<String, PropertyChangeListener> listenersMap = HashMultimap.create();
	
	public TimeTableModel() {
		// nothing to do here
	}
	
	public void setCourses(List<Course> ¢) {
		courses = new ArrayList<>(¢);
	}
	
	public void setDaysoffFlag(boolean f) {
		isDaysoffCount = f;
	}
	
	public void setBlankSpaceFlag(boolean f) {
		isBlankSpaceCount = f;
	}
	
	public void setMinStartTime(LocalTime ¢) {
		minStartTime = ¢;
	}
	
	public void setMaxEndTime(LocalTime ¢) {
		maxEndTime = ¢;
	}
	
	public void loadSchedule() {
		List<Timetable> tables = Lists.newArrayList(Scheduler.sortedBy(
				Scheduler.getTimetablesList(courses), isDaysoffCount, isBlankSpaceCount, minStartTime, maxEndTime));
		lessonGroupsList.clear();
		sched_index = 0;
		tables.forEach((x) -> lessonGroupsList.add(x.getLessonGroups()));

		notifySchedListeners();
	}
	
	public void loadNextSchedule() {
		if (lessonGroupsList.size() >= sched_index - 1)
			return;
		++sched_index;
		notifySchedListeners();
	}

	public void loadPrevSchedule() {
		if (sched_index <= 0)
			return;
		--sched_index;
		notifySchedListeners();
		
	}
	
	private void notifySchedListeners() {
		this.listenersMap.get(TimeTableProperty.SCHEDULE).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, TimeTableProperty.SCHEDULE, null, lessonGroupsList.get(sched_index)))));
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
