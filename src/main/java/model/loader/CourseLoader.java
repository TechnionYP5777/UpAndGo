package model.loader;

import java.util.HashMap;
import java.util.Optional;

import model.course.Course;

/**
 * Abstract class for loading courses data from outside.
 * It can be implemented as e.g.: XmlCourseLoader, UrlCourseLoader, JsonLoader etc...
 * */
public abstract class CourseLoader {
	//TODO: I'm not sure, whether it should be a List or HashMap
	// apparently it's a hashMap with mapping (courseName -> course)
	protected final HashMap<String, Course> courses;
	protected final Course.CourseBuilder cb;
	
	public CourseLoader() {
		this.courses = new HashMap<>();
		this.cb = Course.giveCourseBuilderTo(this);
		
	}
	
	public abstract HashMap<String, Course> loadCoursesFrom(String path);
	
	// TODO: make this method final?
	public Optional<Course> getLoadedCourse(String name) {
		return Optional.of(courses.get(name));
	}
	
	// TODO: make this method final?
	public HashMap<String, Course> getAllLoadedCourses() {
		return new HashMap<>(courses);
	}
	
}
