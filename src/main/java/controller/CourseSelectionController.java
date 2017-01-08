package controller;

import java.awt.event.ActionEvent;

import command.CourseCommand;
import model.CourseModel;
import property.CourseProperty;
import view.CourseSelectionView;

public class CourseSelectionController implements Controller {

	protected CourseModel model;
	protected CourseSelectionView view;
	
	public CourseSelectionController(CourseModel model, CourseSelectionView view) {
		this.model = model;
		this.view = view;
		
		this.model.addPropertyChangeListener(CourseProperty.CHOSEN_LIST, this.view);
		
		this.view.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ¢) {
		if (¢.getActionCommand().equals(CourseCommand.PICK))
			model.pickCourse(view.getLastPickedCourse());
		else if (¢.getActionCommand().equals(CourseCommand.DROP))
			model.dropCourse(view.getLastDropedCourse());
		else if (¢.getActionCommand().equals(CourseCommand.GET_QUERY))
				model.getChosenCourseNames();
	}

	@Override
	public void init() {
		model.getChosenCourseNames();
	}
}
