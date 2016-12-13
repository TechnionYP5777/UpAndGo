package model.loader;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import model.course.Course;

/**
 * Abstract class for loading courses data from outside.
 * It can be implemented as e.g.: XmlCourseLoader, UrlCourseLoader, JsonLoader etc...
 * */
public abstract class CourseLoader {
	//TODO: I'm not sure, whether it should be a List or HashMap
	// apparently it's a hashMap with mapping (courseName -> course)
	protected final Course.CourseBuilder cb;
	protected final String path;
	
	public CourseLoader(String path) {
		this.cb = Course.giveCourseBuilderTo(this);
		this.path = path;
	}
	
	public abstract HashMap<String, Course> loadCourses(List<String> names);
	
	public abstract void updateCourse(Course c);
	
	public abstract Course loadCourse(String name);
	
	public abstract TreeMap<String, Course> loadAllCourses();
	
	public abstract List<String> loadAllCourseNames();
	
	public abstract void saveChosenCourseNames(List<String> names);
	
	public abstract List<String> loadChosenCourseNames(String filepath);
}