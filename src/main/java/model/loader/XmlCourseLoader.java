package model.loader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import model.course.Course;
import parse.RepFile;

public class XmlCourseLoader extends CourseLoader {
	List<Course> coursesList;
	
	public XmlCourseLoader(String path) {
		super(path);
		File xmlPath = new File(path);
		if (!xmlPath.exists()){
			RepFile.initialize();
			RepFile.getCoursesFromRepFile();
		}
		coursesList = xmlParser.getCourses(path);
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
	public TreeMap<String, Course> loadAllCourses() {
		TreeMap<String, Course> $ = new TreeMap<>();
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