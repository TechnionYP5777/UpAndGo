package controller;

import model.Model;
import model.course.Course;
import view.View;

/**
 * It's the main class that speaks with View-part of the program.
 * It interacts with UI and updates Models.
 * 
 * The GUI parts which do not update when the model changes - are the responsibility of the controller.
 * When you try to modify a field in the view, the controller needs to pick up the editing event,
 * process it, and send it to the model; the model will then update the view if/when the value actually changes.
 * In many frameworks, the controller appears as a collection of methods and listeners built into both
 * the data model and the view.
 * */
public class Controller {
	Model model;
	View view;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	public void updateView() {
		// TODO: implement
		// 
	}
	
	@SuppressWarnings("static-method")
	public Course getCourse(@SuppressWarnings("unused") String name) {
		// TODO: implement
		return null;
	}
	
	public void pickCourse(@SuppressWarnings("unused") String name) {
		// TODO: implement
	}
	
	public void dropCourse(@SuppressWarnings("unused") String name) {
		// TODO: implement
	}
	
}
