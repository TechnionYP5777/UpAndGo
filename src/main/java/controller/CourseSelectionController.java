package controller;

import java.awt.event.ActionEvent;

import command.CourseCommand;
import model.CourseModel;
import view.CourseSelectionView;

public class CourseSelectionController implements Controller {

	protected CourseModel model;
	protected CourseSelectionView view;
	
	public CourseSelectionController(CourseModel model, CourseSelectionView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent ¢) {
		if (¢.getActionCommand().equals(CourseCommand.PICK))
			model.pickCourse(view.getLastPickedCourse());
		else if (¢.getActionCommand().equals(CourseCommand.DROP))
			model.dropCourse(view.getLastDropedCourse());
		else {
			if (!¢.getActionCommand().equals(CourseCommand.GET_QUERY))
				throw new IllegalArgumentException();
			model.getChosenCourseNames();
		}
	}
}
