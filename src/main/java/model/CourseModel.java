package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

import com.google.common.collect.HashMultimap;

import model.course.Course;
import model.course.CourseId;
import model.loader.CourseLoader;
import property.CourseProperty;

/**
 * Interface for storing data inside the program. The data typically should come
 * from loader.
 */
public class CourseModel implements Model {

	protected TreeMap<String, Course> courseList;
	protected List<Course> pickedCourseList;
	protected HashMultimap<String, PropertyChangeListener> listenersMap;
	protected CourseLoader loader;
	protected List<Faculty> facultyList;

	public CourseModel(CourseLoader loader) {
		this.pickedCourseList = new ArrayList<>();
		this.listenersMap = HashMultimap.create();
		this.loader = loader;
		this.courseList = loader.loadAllCourses();
		this.facultyList = loader.loadFaculties();
	}

	public void pickCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		Course pickedCourse = this.getCourseByName(name);
		if (this.pickedCourseList.contains(pickedCourse))
			return;

		// save picking in DB
		HashSet<CourseId> pickedList = new HashSet<>();
		List<String> pickedIds = new ArrayList<>();
		this.pickedCourseList.add(pickedCourse);
		this.pickedCourseList.forEach((course) -> {
			pickedList.add(new CourseId(course.getId(), course.getName()));
			pickedIds.add(course.getId());
		});
		this.loader.saveChosenCourseNames(pickedIds);

		// notify listeners
		this.listenersMap.get(CourseProperty.CHOSEN_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.CHOSEN_LIST, null, new ArrayList<>(pickedList)))));
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

		// save picking in DB
		HashSet<CourseId> pickedList = new HashSet<>();
		List<String> pickedIds = new ArrayList<>();
		this.pickedCourseList.remove(droppedCourse);
		this.pickedCourseList.forEach((course) -> {
			pickedList.add(new CourseId(course.getId(), course.getName()));
			pickedIds.add(course.getId());
		});
		this.loader.saveChosenCourseNames(pickedIds);

		// notify listeners
		this.listenersMap.get(CourseProperty.CHOSEN_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.CHOSEN_LIST, null, new ArrayList<>(pickedList)))));
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
		if (this.courseList.containsKey(name))
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
	 * load needed courses (by name / subname) from DB if empty, load all of
	 * them
	 */
	public void loadQuery(String query) {
		HashSet<CourseId> matchingIds = new HashSet<>();
		this.courseList.forEach(query.isEmpty() ? (key, course) -> {
			matchingIds.add(new CourseId(course.getId(), course.getName()));
		} : (key, course) -> {
			if (key.toLowerCase().contains(query.toLowerCase()))
				matchingIds.add(new CourseId(course.getId(), course.getName()));
		});
		this.listenersMap.get(CourseProperty.COURSE_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.COURSE_LIST, null, new ArrayList<>(matchingIds)))));
	}

	/*
	 * load faculty Names
	 */
	public void getFacultyNames() {
		List<String> facultynames = new ArrayList<>();
		this.facultyList.forEach(x-> facultynames.add(x.getName()));
		this.listenersMap.get(CourseProperty.FACULTY_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.FACULTY_LIST, null, facultynames))));
	}	
	/*
	 * load needed courses by faculty
	 */
	public void loadFacultyNames(String facultyName) {
		HashSet<CourseId> matchingIds = new HashSet<>();
		this.courseList.forEach(facultyName.isEmpty() ? (key, course) -> {
			matchingIds.add(new CourseId(course.getId(), course.getName()));
		} : (key, course) -> {
			if (course.getFaculty().toLowerCase().contains(facultyName.toLowerCase()))
				matchingIds.add(new CourseId(course.getId(), course.getName()));
		});
		this.listenersMap.get(CourseProperty.FACULTY_LIST).forEach((x) -> x.propertyChange(
				(new PropertyChangeEvent(this, CourseProperty.FACULTY_LIST, null, new ArrayList<>(matchingIds)))));
	}
	@Override
	public void addPropertyChangeListener(String property, PropertyChangeListener l) {
		if (property == null || l == null)
			throw new NullPointerException();
		this.listenersMap.put(property, l);
	}

	@Override
	public void removePropertyChangeListener(String property, PropertyChangeListener l) {
		if (property != null && l != null && this.listenersMap.containsKey(property))
			this.listenersMap.remove(property, l);

	}
}
