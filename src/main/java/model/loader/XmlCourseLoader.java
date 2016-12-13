package model.loader;

import java.util.HashMap;
import java.util.List;

import model.course.Course;

public class XmlCourseLoader extends CourseLoader {
	List<Course> coursesList;
	
	public XmlCourseLoader(String path) {
		super(path);
		coursesList = xmlParser.getCourses();
	}

	@Override
	public HashMap<String, Course> loadCourses(@SuppressWarnings("unused") List<String> names) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCourse(@SuppressWarnings("unused") Course __) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Course loadCourse(@SuppressWarnings("unused") String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> loadAllCourseNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Course> loadAllCourses() {
		HashMap<String, Course> $ = new HashMap<>();
		for(Course ¢ : coursesList)
			$.put(¢.getId(), ¢);

		return $;
	}
	
	@Override
	public void saveChosenCourseNames(List<String> names){
		xmlParser.setChosenCouseNames(names, "data/ChosenCourses.xml");

	}

	@Override
	public List<String> loadChosenCourseNames(String filepath) {
		return xmlParser.getChosenCourseNames(filepath);		
	}
	

}