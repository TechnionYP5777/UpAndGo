package upandgo.server.model.loader;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class CoursesEntity {
	
	@Id public String id;	// user's userId
	public List<String> courses;
	
	CoursesEntity() {
		courses = new ArrayList<>();
	}
	
	CoursesEntity(String i, List<String> c) {
		courses = c;
		id = i;
	}
}

