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
 * Abstract class for loading courses data from outside. It can be implemented as e.g.: XmlCourseLoader, UrlCourseLoader, JsonLoader etc...
 * 
 */
public abstract class CourseLoader {
	protected final CourseBuilder cb;
	protected final String path;

	public CourseLoader(final String path) {
		cb = new CourseBuilder();
		this.path = path;
	}

	public abstract HashMap<String, Course> loadCourses(List<String> names);

	public abstract void updateCourse(Course c);

	public abstract Course loadCourse(String name);

	public abstract TreeMap<String, Course> loadAllCoursesById();

	public abstract TreeMap<String, Course> loadAllCoursesByName();

	public abstract List<String> loadAllCourseNames();

	public abstract void saveChosenCourses(CoursesEntity courseEntity);

	public abstract CoursesEntity loadChosenCourses();

	public abstract void saveChosenLessonGroups(ScheduleEntity scheduleEntity);

	public abstract ScheduleEntity loadChosenLessonGroups();

	public abstract List<Faculty> loadFaculties();
}