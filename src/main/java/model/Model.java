package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
	
	protected List<Course> courseList;
	protected List<Course> pickedCourseList;
	protected List<Observer> listenersList;
	protected CourseLoader loader;
	
	public Model(CourseLoader loader){
		this.courseList = new ArrayList<>();
		this.pickedCourseList = new ArrayList<>();
		this.listenersList = new ArrayList<>();
		this.loader = loader;
	}
	
	@Override
	public synchronized void addObserver(Observer o) {
		if (o == null)
			throw new NullPointerException();
		this.listenersList.add(o);
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
		Course newCourse = loader.loadCourse(name);
		if (!this.courseList.contains(newCourse))
			this.courseList.add(newCourse);
		setChanged();
		notifyObservers();
	}
	
	public List<String> getCoursesNames() {
		List<String> $ = new ArrayList<>();
		for(Course it:courseList)
			$.add(it.getName());
		return $;
	}
	
	// not sure what is more efficient- loading course from loadCourse and then use remove
	// or iterating all over course list and then remove
	public void dropCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		for(Course it:courseList)			
			if (it.getName().equals(name)) {
				this.courseList.remove(it);
				setChanged();
				notifyObservers();
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
