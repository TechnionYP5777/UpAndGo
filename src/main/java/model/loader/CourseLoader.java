package model;

import java.util.HashMap;

import logic.course.Course;

/**
 * Interface for loading courses data from outside.
 * It can be implemented as e.g.: XmlCourseLoader, UrlCourseLoader, etc...
 * */
public interface CourseLoader {
	//TODO: I'm not sure, whether it should be a List or HashMap
	// apparently it's a hashMap with mapping (courseName -> course)
	HashMap<String, Course> loadCoursesFrom(String path);
}
