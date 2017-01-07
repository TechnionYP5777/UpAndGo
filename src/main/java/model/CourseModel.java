package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.google.common.collect.HashMultimap;

import model.course.Course;
import model.loader.CourseLoader;
import property.CourseProperty;

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
		Course pickedCourse = this.getCourseByName(name);
		if (this.pickedCourseList.contains(pickedCourse))
			return;
		
		// save picking in DB
		List<String> prevPickedList = new ArrayList<>();
		for(Course ¢: this.pickedCourseList)
			prevPickedList.add(¢.getName());
		List<String> curPickedList = new ArrayList<>(prevPickedList);
		curPickedList.add(name);
		this.pickedCourseList.add(pickedCourse);
		this.loader.saveChosenCourseNames(curPickedList);
		
		// notify listeners
		this.listenersMap.get(CourseProperty.CHOSEN_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.CHOSEN_LIST, prevPickedList, curPickedList))));
	}
	
	public void addCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		List<String> prevCourseList = this.getCoursesNames();
		this.courseList.put(name, loader.loadCourse(name));
		
		List<String> curCourseList = new ArrayList<>(prevCourseList);
		curCourseList.remove(name);
		
		// notify listeners
		this.listenersMap.get(CourseProperty.CHOSEN_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.COURSE_LIST, prevCourseList, curCourseList))));
	}
	
	public List<String> getCoursesNames() {
		return new ArrayList<>(this.courseList.keySet());
	}
	
	public void dropCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		Course droppedCourse = this.getCourseByName(name);
		if (!this.pickedCourseList.contains(droppedCourse))
			return;
		
		// save dropping in DB
		List<String> prevPickedList = new ArrayList<>();
		for(Course ¢: this.pickedCourseList)
			prevPickedList.add(¢.getName());
		List<String> curPickedList = new ArrayList<>(prevPickedList);
		curPickedList.remove(name);
		this.pickedCourseList.remove(droppedCourse);
		this.loader.saveChosenCourseNames(curPickedList);
		
		// notify listeners
		this.listenersMap.get(CourseProperty.CHOSEN_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.CHOSEN_LIST, prevPickedList, curPickedList))));
	}
	
	/* 
	 * expose course to listeners for "course_list" property
	 */
	public void exposeCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		this.listenersMap.get(CourseProperty.DETAILS).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.DETAILS, null, this.getCourseByName(name)))));
	}
	
	public Course getCourseByName(String name) {
		if (name == null)
			throw new NullPointerException();
		if(this.courseList.containsKey(name))
			return this.courseList.get(name);
		Course $ = loader.loadCourse(name);
		this.courseList.put(name, $);
		return $;
	}
	
	public List<String> getChosenCourseNames() {
		return this.loader.loadChosenCourseNames();
	}
	
	public void choseCourses(List<String> names) {
		this.loader.saveChosenCourseNames(names);
	}
	
	/*
	 * load needed courses (by name/subname) from DB
	 * if empty, load all of them
	 */
	public void loadQuery(@SuppressWarnings("unused") String query) {
		// TODO: implement
		List<String> l = this.getCoursesNames();
		this.listenersMap.get(CourseProperty.COURSE_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.COURSE_LIST, null, l))));
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
