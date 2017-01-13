package controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import command.CourseCommand;
import model.CourseModel;
import property.CourseProperty;
import view.CourseListView;

/*
 * Corresponds to the View that shows list of available courses, lets to pick/drop them and peep inside them.
 * 
 */
public class CourseListController implements Controller{

	protected CourseModel model;
	protected CourseListView view;
	
	public CourseListController(CourseModel model, CourseListView view) {
		this.model = model;
		this.view = view;
		
		this.model.addPropertyChangeListener(CourseProperty.COURSE_LIST, this.view);
		this.model.addPropertyChangeListener(CourseProperty.DETAILS, this.view);
		this.model.addPropertyChangeListener(CourseProperty.CHOSEN_LIST, this.view);
		this.model.addPropertyChangeListener(CourseProperty.FACULTY_LIST, this.view);

		this.view.addActionListener(this);
		
		init();
	}

	@Override
	public void actionPerformed(ActionEvent ¢) {
		if (¢.getActionCommand().equals(CourseCommand.DETAILS))
			model.exposeCourse(view.getHighlightedCourse());
		else if (¢.getActionCommand().equals(CourseCommand.GET_QUERY))
			if (view.getFaculty().equals(CourseListView.ALL_FACULTIES_STRING))
				model.loadQuery(view.getQuery());
			else
				model.loadQueryByFaculty(view.getQuery(), view.getFaculty());
		else if (¢.getActionCommand().equals(CourseCommand.PICK))
			model.pickCourse(view.getLastPickedCourse());
		else if (¢.getActionCommand().equals(CourseCommand.DROP))
			model.dropCourse(view.getLastDropedCourse());
		else if (¢.getActionCommand().equals(CourseCommand.GET_CHOSEN))
				model.getChosenCourseNames();
		else if (¢.getActionCommand().equals(CourseCommand.GET_FACULTIES))
			model.loadFacultyNames();
	}
	
	@Override
	public void init(){
		model.loadQuery("");
		model.getChosenCourseNames();
		model.loadFacultyNames();
	}

	void loadChosenCoursesDetails() {
		model.loadChosenCoursesDetails();
	}
	
	@Override
	public void registerListenerToProperty(PropertyChangeListener l, String p) {
		model.addPropertyChangeListener(p, l);
	}

	@Override
	public void unregisterListenerToProperty(PropertyChangeListener l, String p) {
		model.removePropertyChangeListener(p, l);
	}
}
