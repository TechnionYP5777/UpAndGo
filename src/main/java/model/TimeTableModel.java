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
import model.loader.CourseLoader;
import model.schedule.Timetable;
import property.TimeTableProperty;

public class TimeTableModel implements Model {

	protected List<Course> courses = new ArrayList<>();

	protected boolean isDaysoffCount;
	protected boolean isBlankSpaceCount;
	protected LocalTime minStartTime;
	protected LocalTime maxEndTime;
	protected CourseLoader loader;

	protected List<List<LessonGroup>> lessonGroupsList = new ArrayList<>();
	protected int sched_index;
	
	protected HashMultimap<String, PropertyChangeListener> listenersMap = HashMultimap.create();
	
	public TimeTableModel(CourseLoader loader) {
		this.loader = loader;
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
		if(courses.isEmpty())
			return;
		List<Timetable> tables = Lists.newArrayList(Scheduler.sortedBy(
				Scheduler.getTimetablesList(courses), isDaysoffCount, isBlankSpaceCount, minStartTime, maxEndTime));
		if (tables.isEmpty())
			notifySchedListenersNoSched();
		else {
			lessonGroupsList.clear();
			sched_index = 0;
			tables.forEach((x) -> lessonGroupsList.add(x.getLessonGroups()));
			notifySchedListeners();
		}
	}
	
	public void loadNextSchedule() {
		if (lessonGroupsList.size() - 1 <= sched_index)
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
		this.listenersMap.get(TimeTableProperty.SCHEDULE_INDEX).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, TimeTableProperty.SCHEDULE_INDEX, null,sched_index + 1 + "/" + lessonGroupsList.size()))));
	}
	private void notifySchedListenersNoSched() {
		this.listenersMap.get(TimeTableProperty.NO_SCHEDULE).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, TimeTableProperty.NO_SCHEDULE, null, null))));
	}
	
	
	public List<LessonGroup> getChosenLessonGroups() {
		return lessonGroupsList.get(sched_index);
	}
	
	public void saveChosenLessonGroups(List<LessonGroup> ¢) {
		this.loader.saveChosenLessonGroups(¢);
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
