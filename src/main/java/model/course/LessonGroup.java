package model.course;

import java.util.ArrayList;
import java.util.List;



public class LessonGroup {
	
	public static final int UNINITIALIZED_GROUP_NUM = -1;
	
	private int groupNum;
	private List<Lesson> lessons;
	
	public LessonGroup(){
		this.groupNum = UNINITIALIZED_GROUP_NUM;
		this.lessons = new ArrayList<>();
	}
	
	public LessonGroup(int GroupNumber){
		this.groupNum = GroupNumber;
		this.lessons = new ArrayList<>();
	}
	
	public void setGroupNum(int GroupNumber){
		this.groupNum = GroupNumber;
	}
	
	public int getGroupNum(){
		return this.groupNum;
	}
	
	public List<Lesson> getLessons(){
		return lessons;
	}
	
	public void addLesson(Lesson ¢){
		if (groupNum == UNINITIALIZED_GROUP_NUM)
			throw new IllegalArgumentException();
		this.lessons.add(¢);
	}
}
