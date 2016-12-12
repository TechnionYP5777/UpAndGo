package controller;

import java.util.Observable;

import model.Model;
import view.View;

/*
 * Corresponds to the View that shows list of available courses, lets to pick/drop them and peep inside them.
 * 
 */
public class CourseListController extends Controller{

	public CourseListController(Model model, View view) {
		super(model, view);
	}

	@Override
	public void update(@SuppressWarnings("unused") Observable __, @SuppressWarnings("unused") Object arg) {
		// TODO
		// enforce logic of model
		// e.g.:
//		if(view.wasPicked())
//			model.pickCourse(view.getHighlightedCourseName());
//		else
//			model.dropCousre(view.getHighlightedCourseName());
	}
	
}
