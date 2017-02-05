package controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import command.TimeTableCommand;
import model.TimeTableModel;
import model.course.Course;
import property.CourseProperty;
import property.TimeTableProperty;
import view.ITimeTableView;

public class TimeTableController implements Controller, PropertyChangeListener{

	protected TimeTableModel model;
	protected ITimeTableView view;
	protected CourseListController clCtrl;
	
	public TimeTableController(final TimeTableModel model, final ITimeTableView view, final CourseListController clCtrl) {
		this.model = model;
		this.view = view;
		
		this.model.addPropertyChangeListener(TimeTableProperty.SCHEDULE, this.view);
		this.model.addPropertyChangeListener(TimeTableProperty.SCHEDULE_INDEX, this.view);
		this.model.addPropertyChangeListener(TimeTableProperty.NO_SCHEDULE, this.view);
		this.model.addPropertyChangeListener(TimeTableProperty.NO_COURSES, this.view);
		
		this.view.addActionListener(this);
		
		this.clCtrl = clCtrl;
		this.clCtrl.registerListenerToProperty(this, CourseProperty.CHOSEN_LIST_DETAILS);
		
		init();
	}
	
	@Override
	public void actionPerformed(final ActionEvent ¢) {
		if (¢.getActionCommand().equals(TimeTableCommand.GET_NEXT_GENERATED_SCHED))
			model.loadNextSchedule();
		else if (¢.getActionCommand().equals(TimeTableCommand.GET_PREV_GENERATED_SCHED))
			model.loadPrevSchedule();
		else if (!¢.getActionCommand().equals(TimeTableCommand.RECALC_SCHED)) {
			if (¢.getActionCommand().equals(TimeTableCommand.SAVE_SCHED))
				saveChosenLessonGroups();
		} else {
			model.setDaysoffFlag(view.isDaysoffCount());
			model.setBlankSpaceFlag(view.isBlankSpaceCount());
			model.setMaxEndTime(view.getMaxEndTime());
			model.setMinStartTime(view.getMinStartTime());
			clCtrl.loadChosenCoursesDetails();
			model.loadSchedule();
		}
	}
	
	public void saveChosenLessonGroups() {
		model.saveChosenLessonGroups(model.getChosenLessonGroups());
	}

	@Override
	public void init() {
		model.loadChosenLessonGroups();
		
	}
	
	@Override
	public void registerListenerToProperty(final PropertyChangeListener l, final String p) {
		model.addPropertyChangeListener(p, l);
	}

	@Override
	public void unregisterListenerToProperty(final PropertyChangeListener l, final String p) {
		model.removePropertyChangeListener(p, l);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case CourseProperty.CHOSEN_LIST_DETAILS:
			model.setCourses((List<Course>) evt.getNewValue());
			break;
		default:
			break;
		}
		
	}

}
