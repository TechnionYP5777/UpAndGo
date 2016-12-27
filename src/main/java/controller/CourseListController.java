package controller;

import java.awt.event.ActionEvent;

import command.CourseCommand;
import model.CourseModel;
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
	}

//	@Override
//	public void update(@SuppressWarnings("unused") Observable __, @SuppressWarnings("unused") Object arg) {
//		// TODO
//		// enforce logic of model
//		// e.g.:
////		if(view.wasPicked())
////			model.pickCourse(view.getHighlightedCourseName());
////		else
////			model.dropCousre(view.getHighlightedCourseName());
//	}

	@Override
	public void actionPerformed(@SuppressWarnings("unused") ActionEvent ae) {
//		switch (ae.getActionCommand()) {
//		case CourseCommand.PICK.cmd():
//			
//			break;
//		case CourseCommand.DROP.cmd():
//			
//			break;
//		case CourseCommand.PICK.cmd():
//			
//			break;
//		}
		model.loadQuery(view.getQuery());
	}
	
}
