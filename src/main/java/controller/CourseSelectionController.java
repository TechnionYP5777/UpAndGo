package controller;

import java.awt.event.ActionEvent;
import java.util.Observable;

import model.CourseModel;
import view.View;

public class CourseSelectionController extends Controller {

	public CourseSelectionController(CourseModel model, View view) {
		super(model, view);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
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
}
