package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Model;
import model.course.Course;
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
	public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
		// TODO Auto-generated method stub
		// enforce logic of model
		// e.g.:
//		if(view.wasPicked)
//			model.pickCourse(view.getHighlightedCourseName());
//		else
//			model.dropCousre(view.getHighlightedCourseName());
		
	}
	
	@SuppressWarnings("static-method")
	public Optional<Course> getCourseByName(@SuppressWarnings("unused") String name) {
		// TODO: get course
		// e.g.:
		// model.getCoursesByName(name);
		return Optional.empty();
	}
	
	@SuppressWarnings("static-method")
	public List<String> getCoursesNames() {
		// TODO: get list
		// e.g.:
		// model.getCourses();
		return new ArrayList<>();
	}
	
}
