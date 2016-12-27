package controller;

import java.awt.event.ActionEvent;

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
	public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
		model.loadQuery(view.getQuery());
	}
	
}
