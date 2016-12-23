package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import model.course.Course;
import model.loader.CourseLoader;

/**
 * Interface for storing data inside the program.
 * The data typically should come from loader.
 * */
public abstract class Model extends Observable {
	// TODO: implement
	// Maybe it will be good to use Observer pattern to notify views when the Model changes.
	// smth like:
	// addObservers(), notifyObservers()
	
	protected TreeMap<String, Course> courseList;
	protected List<Course> pickedCourseList;
	protected List<Observer> listenersList;
	protected CourseLoader loader;
		
	public Model(CourseLoader loader){
		this.pickedCourseList = new ArrayList<>();
		this.listenersList = new ArrayList<>();
		this.loader = loader;
		this.courseList = loader.loadAllCourses();
	}
	
	@Override

	public synchronized void addObserver(Observer ¢) {
		if (¢ == null)
			throw new NullPointerException();
		this.listenersList.add(¢);
	}
	
	public void pickCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		Course pickedCourse = loader.loadCourse(name);
		if (!this.pickedCourseList.contains(pickedCourse))
			this.pickedCourseList.add(pickedCourse);
		setChanged();
		notifyObservers();
	}
	
	public void addCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		this.courseList.put(name, loader.loadCourse(name));
		setChanged();
		notifyObservers();
	}
	
	public List<String> getCoursesNames() {
		return new ArrayList<>(this.courseList.keySet());
	}
	
	public void dropCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		if (this.courseList.remove(name) == null)
			return;
		setChanged();
		notifyObservers();
	}
	
	public Course getCourseByName(String name) {
		if (name == null)
			throw new NullPointerException();
		return this.courseList.get(name);
	}
	
	public List<String> getChosenCourseNames() {
		return this.loader.loadChosenCourseNames();
	}
	
	public void choseCourses(List<String> names) {
		this.loader.saveChosenCourseNames(names);
	}
}
