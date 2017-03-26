package model.loader;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import model.Faculty;
import model.course.Course;
import model.course.LessonGroup;


/**
 * 
 * @author Nikita Dizhur
 * @since 06-12-16
 * 
 * Abstract class for loading courses data from outside. It can be implemented as e.g.: XmlCourseLoader, UrlCourseLoader, JsonLoader etc...
 * 
 */
public abstract class CourseLoader {
	protected final Course.CourseBuilder cb;
	protected final String path;

	public CourseLoader(final String path) {
		cb = Course.giveCourseBuilderTo(this);
		this.path = path;
	}

	public abstract HashMap<String, Course> loadCourses(List<String> names);

	public abstract void updateCourse(Course c);

	public abstract Course loadCourse(String name);

	public abstract TreeMap<String, Course> loadAllCoursesById();

	public abstract TreeMap<String, Course> loadAllCoursesByName();

	public abstract List<String> loadAllCourseNames();

	public abstract void saveChosenCourseNames(List<String> names);

	public abstract List<String> loadChosenCourseNames();

	public abstract void saveChosenLessonGroups(List<LessonGroup> gs);

	public abstract List<LessonGroup> loadChosenLessonGroups();

	public abstract List<Faculty> loadFaculties();
}