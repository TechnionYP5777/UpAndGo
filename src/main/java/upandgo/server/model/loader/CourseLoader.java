package upandgo.server.model.loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import upandgo.shared.entities.Faculty;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.course.Course;


/**
 * 
 * @author Nikita Dizhur
 * @since 06-12-16
 * 
 * interface for loading courses data from outside. It can be implemented as e.g.: XmlCourseLoader, UrlCourseLoader, JsonLoader etc...
 * 
 */
public interface CourseLoader {

	public Course loadCourse(String name);

	public TreeMap<String, Course> loadAllCoursesById();

	public TreeMap<String, Course> loadAllCoursesByName();

	public List<Faculty> loadFaculties();
}