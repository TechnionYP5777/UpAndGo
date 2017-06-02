package upandgo.server.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import upandgo.shared.entities.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import java.util.function.Consumer;

import com.google.common.collect.HashMultimap;		 
import org.apache.commons.collections.map.HashedMap;
import com.google.common.collect.Lists;



import upandgo.server.model.loader.CourseLoader;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.Scheduler;
import upandgo.shared.model.scedule.Timetable;
import upandgo.shared.property.TimeTableProperty;

/**
 * 
 * @author Nikita Dizhur
 * @since 12-01-17
 * 
 * Class that is in charge of scheduling chosen courses under given constraints and ordering policy.
 * 
 */
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
	

	public TimeTableModel(final CourseLoader loader) {
		this.loader = loader;
	}

	public void setCourses(final List<Course> xxx) {
		courses = new ArrayList<>(xxx);
	}

	public void setDaysoffFlag(final boolean f) {
		isDaysoffCount = f;
	}

	public void setBlankSpaceFlag(final boolean f) {
		isBlankSpaceCount = f;
	}

	public void setMinStartTime(final LocalTime xxx) {
		minStartTime = xxx;
	}

	public void setMaxEndTime(final LocalTime xxx) {
		maxEndTime = xxx;
	}

	public void loadSchedule() {
		if (courses.isEmpty()) {
			notifySchedListenersNoCourses();

			return;
		}
		final List<Timetable> tables = Lists.newArrayList(Scheduler.sortedBy(Scheduler.getTimetablesList(courses, null),isDaysoffCount, isBlankSpaceCount, minStartTime, maxEndTime));
		if (tables.isEmpty())
			notifySchedListenersNoSched();
		else {
			lessonGroupsList.clear();
			sched_index = 0;
//			tables.forEach(new Consumer<Timetable>() {
//				@Override
//				public void accept(Timetable λ) {
//					lessonGroupsList.add(λ.getLessonGroups());
//				}
//			});
			notifySchedListeners();
		}
	}

	public void loadNextSchedule() {
		if (lessonGroupsList.size() <= sched_index + 1)
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
//		listenersMap.get(TimeTableProperty.SCHEDULE).forEach(new Consumer<PropertyChangeListener>() {
//			@Override
//			public void accept(PropertyChangeListener λ) {
//				λ.propertyChange(
//						new PropertyChangeEvent(TimeTableModel.this, TimeTableProperty.SCHEDULE, null, lessonGroupsList.get(sched_index)));
//			}
//		});
//		listenersMap.get(TimeTableProperty.SCHEDULE_INDEX).forEach(new Consumer<PropertyChangeListener>() {
//			@Override
//			public void accept(PropertyChangeListener λ) {
//				λ.propertyChange(new PropertyChangeEvent(TimeTableModel.this,
//						TimeTableProperty.SCHEDULE_INDEX, null, sched_index + 1 + "/" + lessonGroupsList.size()));
//			}
//		});
	}

	private void notifySchedListenersNoSched() {
//		listenersMap.get(TimeTableProperty.NO_SCHEDULE).forEach(
//				new Consumer<PropertyChangeListener>() {
//					@Override
//					public void accept(PropertyChangeListener λ) {
//						λ.propertyChange(new PropertyChangeEvent(TimeTableModel.this, TimeTableProperty.NO_SCHEDULE, null, null));
//					}
//				});
	}

	private void notifySchedListenersNoCourses() {
//		listenersMap.get(TimeTableProperty.NO_COURSES).forEach(
//				new Consumer<PropertyChangeListener>() {
//					@Override
//					public void accept(PropertyChangeListener λ) {
//						λ.propertyChange(new PropertyChangeEvent(TimeTableModel.this, TimeTableProperty.NO_COURSES, null, null));
//					}
//				});
	}

	public List<LessonGroup> getChosenLessonGroups() {
		return (List<LessonGroup>) (lessonGroupsList.size() <= sched_index + 1 ? new ArrayList<>()
				: lessonGroupsList.get(sched_index));
	}

	public void saveChosenLessonGroups(final List<LessonGroup> xxx) {
		loader.saveChosenLessonGroups(xxx);
	}

	public void loadChosenLessonGroups() {
		lessonGroupsList.add(loader.loadChosenLessonGroups());
		notifySchedListeners();
	}

	@Override
	public void addPropertyChangeListener(final String property, final PropertyChangeListener l) {
		if (property == null || l == null)
			throw new NullPointerException();
		listenersMap.put(property, l);

	}

	@Override
	public void removePropertyChangeListener(final String property, final PropertyChangeListener l) {
		if (property != null && l != null && listenersMap.containsKey(property))
			listenersMap.remove(property, l);

	}
}
