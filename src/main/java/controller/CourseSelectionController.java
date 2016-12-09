package controller;

import java.awt.event.ActionEvent;

import model.Model;
import view.View;

public class CourseSelectionController extends Controller {

	public CourseSelectionController(Model model, View view) {
		super(model, view);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
		// TODO Auto-generated method stub
		// TODO:
		// model.saveSelection(view.getSelectedCourses());
	}
	
	public void loadSelection() {
		// TODO:
		// view.setSelectedCourses(model.loadSelection());
	}
}
