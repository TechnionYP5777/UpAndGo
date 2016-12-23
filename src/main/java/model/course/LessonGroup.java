package model.course;

import java.util.ArrayList;
import java.util.List;

public class LessonGroup {
	private List<Lesson> lessons;
	
	public LessonGroup(){
		lessons = new ArrayList<>();
	}
	
	public List<Lesson> getLessons(){
		return lessons;
	}
	
	public void addLesson(Lesson ¢){
		lessons.add(¢);
	}
}
