package model;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.course.Course;
import model.loader.CourseLoader;

/**
 * Interface for storing data inside the program.
 * The data typically should come from loader.
 * */
public abstract class Model {
	// TODO: implement
	// Maybe it will be good to use Observer pattern to notify views when the Model changes.
	// smth like:
	// addObservers(), notifyObservers()
	
	protected List<Course> courseList;
	protected List<ActionListener> listenersList;
	protected CourseLoader loader;
	
	public Model(CourseLoader loader){
		this.courseList = new ArrayList<>();
		this.listenersList = new ArrayList<>();
		this.loader = loader;
	}
	
	public void addListener(ActionListener ¢) {
		if (¢ == null)
			throw new NullPointerException();
		this.listenersList.add(¢);
	}
	
	public void pickCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		this.courseList.add(loader.getLoadedCourse(name));
	}
	
	public List<String> getCoursesNames() {
		List<String> $ = new ArrayList<>();
		for(Course it:courseList)
			$.add(it.getName());
		return $;
	}
	
	public void dropCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		for(Course it:courseList)
			if (it.getName().equals(name)) {
				this.courseList.remove(it);
				return;
			}
		return;
	}
	
	public Course getCourseByName(String name) {
		if (name == null)
			throw new NullPointerException();
		for(Course $:courseList)
			if ($.getName().equals(name))
				return $;
		return null;
	}
}
