package controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

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
		else if (¢.getActionCommand().equals(CourseCommand.SAVE_CHOSEN_COURSES))
			saveChosenCourses();
	}
	
	@Override
	public void init(){
		model.loadQuery("");
		model.getChosenCourseNames();
		model.loadFacultyNames();
		model.loadChosenCourses();
	}

	public void loadChosenCoursesDetails() {
		model.loadChosenCoursesDetails();
	}
	
	public void saveChosenCourses() {
		model.saveChosenCourses(model.getChosenCourseNames());
	}
	
	public void saveChosenLessonGroups() {
		model.saveChosenLessonGroups(model.getChosenLessonGroups());
	}
	
	public void loadGilaionFrom(String path) {
		model.loadGilaionFrom(path);
	}
	
	public void loadCatalogFrom(String path) {
		model.loadCatalogFrom(path);
	}
	
	@Override
	public void registerListenerToProperty(PropertyChangeListener l, String p) {
		model.addPropertyChangeListener(p, l);
	}

	@Override
	public void unregisterListenerToProperty(PropertyChangeListener l, String p) {
		model.removePropertyChangeListener(p, l);
	}

	public void saveCatalog() {
		// TODO Auto-generated method stub
		
	}

	public void saveGilaion() {
		// TODO Auto-generated method stub
		
	}
}
