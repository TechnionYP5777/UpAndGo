package model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.google.common.collect.HashMultimap;

import model.course.Course;
import model.loader.CourseLoader;

/**
 * Interface for storing data inside the program.
 * The data typically should come from loader.
 * */
public class CourseModel implements Model  {
	
	protected TreeMap<String, Course> courseList;
	protected List<Course> pickedCourseList;
	protected HashMultimap<String, PropertyChangeListener> listenersMap;
	protected CourseLoader loader;
		
	public CourseModel(CourseLoader loader){
		this.pickedCourseList = new ArrayList<>();
		this.listenersMap = HashMultimap.create();
		this.loader = loader;
		this.courseList = loader.loadAllCourses();
	}
	
	public void pickCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		Course pickedCourse = loader.loadCourse(name);
		if (!this.pickedCourseList.contains(pickedCourse))
			this.pickedCourseList.add(pickedCourse);
		// TODO: implement
//		setChanged();
//		notifyObservers();
	}
	
	public void addCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		this.courseList.put(name, loader.loadCourse(name));
		// TODO: implement
//		setChanged();
//		notifyObservers();
	}
	
	public List<String> getCoursesNames() {
		return new ArrayList<>(this.courseList.keySet());
	}
	
	public void dropCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		if (this.courseList.remove(name) == null)
			return;
		// TODO: implement
//		setChanged();
//		notifyObservers();
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
	
	/*
	 * load needed courses (by name/subname) from DB
	 */
	public void loadQuery(String query) {
		// TODO: implement
	}

	@Override
	public void addPropertyChangeListener(String property, PropertyChangeListener l) {
		if(property == null || l == null)
			throw new NullPointerException();
		this.listenersMap.put(property, l);
	}

	@Override
	public void removePropertyChangeListener(String property, PropertyChangeListener l) {
		if (property != null && l != null && this.listenersMap.containsKey(property))
			this.listenersMap.remove(property, l);
		
	}
}
