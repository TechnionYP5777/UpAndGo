package model.schedule;

import java.util.ArrayList;
import java.util.List;

import model.course.LessonGroup;

/**
 * 
 * this class holds a list of LessonGroup that do not clash between them
 * hence they form a legel Schedule
 *
 */
public class Timetable {
	private List<LessonGroup> lessons;
	
	public Timetable(){
		lessons=new ArrayList<>();
	}
/**
 * this function keeps the invariant of Timetable, which is that they're no clashing lessons
 * @param l - lesson to be added
 * 
 */
	public void addLesson(LessonGroup ¢){
		if(lessons.contains(¢)) // add equals to lessonsgroup
			return;
		lessons.add(¢);
		if(!isNoClash())
			lessons.remove(¢);
	}
	public void addLessons(List<LessonGroup> lessons){
		this.lessons.addAll(lessons);
		if(!isNoClash())
			this.lessons.removeAll(lessons);
	}
	public void removeLesson(LessonGroup ¢) {
		lessons.remove(¢);
	}
	
	public List<LessonGroup> getLessons(){
		return lessons;
	}
	
	private boolean isNoClash(){
		for(int i=0; i < lessons.size(); ++i){
			for(int j=i+1; j < lessons.size(); ++j)
				if (!lessons.get(i).isCLashWIth(lessons.get(j)))
					return false;
		}
		return true;
	}
	
}
