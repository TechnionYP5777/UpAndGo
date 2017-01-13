package controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import model.TimeTableModel;
import model.course.Course;
import property.CourseProperty;
import property.TimeTableProperty;
import view.ITimeTableView;

public class TimeTableController implements Controller, PropertyChangeListener{

	protected TimeTableModel model;
	protected ITimeTableView view;
	protected CourseListController clCtrl;
	
	public TimeTableController(TimeTableModel model, ITimeTableView view, CourseListController clCtrl) {
		this.model = model;
		this.view = view;
		
		this.model.addPropertyChangeListener(TimeTableProperty.SCHEDULE, this.view);
		
		this.view.addActionListener(this);
		
		this.clCtrl = clCtrl;
		this.clCtrl.registerListenerToProperty(this, CourseProperty.CHOSEN_LIST_DETAILS);
		
		init();
	}
	@Override
	public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		clCtrl.loadChosenCoursesDetails();
		
	}
	
	@Override
	public void registerListenerToProperty(PropertyChangeListener l, String p) {
		model.addPropertyChangeListener(p, l);
	}

	@Override
	public void unregisterListenerToProperty(PropertyChangeListener l, String p) {
		model.removePropertyChangeListener(p, l);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case CourseProperty.CHOSEN_LIST_DETAILS:
			model.setCourses((List<Course>) evt.getNewValue());
			break;
		default:
			break;
		}
		
	}

}
