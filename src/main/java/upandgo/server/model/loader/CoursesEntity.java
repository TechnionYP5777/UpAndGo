package upandgo.server.model.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class CoursesEntity {
	
	@Id public String id;	// user's userId
	public Map<String,List<String>> courses;
	
	public CoursesEntity(){
		this.id = "";
		this.courses = new TreeMap<>();
	}
	
	CoursesEntity(String id) {
		this.id = id;
		this.courses = new TreeMap<>();
	}

	public void setId(String id){
		this.id = id;
	}
	
	public List<String> getCourses(String semesterId){
		if (courses.containsKey(semesterId)){
			return courses.get(semesterId);
		}
		return new ArrayList<>();
	}
	
	public void addCourse(String semesterId, String courseId){
		if (courses.containsKey(semesterId)){
			courses.get(semesterId).add(courseId);
		}
		else{
			List<String> couresesList = new ArrayList<>();
			couresesList.add(courseId);
			courses.put(semesterId, couresesList);
		}
	}
	
	public void removeCourse(String semesterId, String courseId){
		if (courses.containsKey(semesterId)){
			courses.get(semesterId).remove(courseId);
		}
	}
	
	public void removeAllCourses(String semesterId){
		if (courses.containsKey(semesterId)){
			courses.remove(semesterId);
		}
	}
	
	@Override
	public String toString(){
		return courses.toString();
	}

}

