package controller;

import logic.course.Course;
import model.Model;
import view.View;

/**
 * It's the main class that speaks with View-part of the program.
 * It interacts with UI and updates Models.
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
