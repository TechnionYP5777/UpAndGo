package model.loader;
// DONT USE THIS ONE, DEPREACTED
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import model.course.Course;

@Deprecated
@SuppressWarnings({"deprecation","unused"})
public class KLoader extends CourseLoader {
	List<Course> coursesList;
	
	public KLoader(String path) {
		super(path);
		coursesList = xmlParser.getCourses();
		// TODO Auto-generated constructor stub
	}

	@Override
	public HashMap<String, Course> loadCourses(List<String> names) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCourse(Course c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Course loadCourse(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> loadAllCourseNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/*public HashMap<String, Course> loadAllCourses() {
		HashMap<String, Course> $ = new HashMap<>();
		for(Course ¢ : coursesList)
			$.put(¢.getId(), ¢);

		return $;
	}*/
	
	public TreeMap<String, Course> loadAllCourses() {
		TreeMap<String, Course> $ = new TreeMap<>();
		for(Course ¢ : coursesList)
			$.put(¢.getId(), ¢);
		return $;
	}

	@Override
	public void saveChosenCourseNames(List<String> names) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> loadChosenCourseNames() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}