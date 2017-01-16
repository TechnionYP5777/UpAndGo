package model.course;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.constraint.TimeConstraint;



public class LessonGroup implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4547987607914277140L;

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
	
	public String getCourseID(){
		return lessons.isEmpty() ? "000000" : lessons.get(0).getCourse();
	}
	
	public void addLesson(Lesson ¢){
		if (groupNum == UNINITIALIZED_GROUP_NUM)
			throw new IllegalArgumentException();
		this.lessons.add(¢);
	}
	
	public boolean isCLashWIth(LessonGroup g){
		if(g==this)
			return true;
		for(Lesson ls1 : lessons)
			for (Lesson ls2 : g.getLessons())
				if (ls1.IsClashWith(ls2))
					return true;
		return false;
	}
	public boolean isCLashWIth(TimeConstraint g){
		for(Lesson ls1 : lessons)
			if (ls1.IsClashWith(g))
				return true;
		return false;
	}
	
	@Override
	public boolean equals(Object other){
		return other != null && (other == this || (other instanceof LessonGroup && ((LessonGroup) other).groupNum == this.groupNum));
	}

	@Override
	public int hashCode() {
		@SuppressWarnings("boxing")
		Integer i = groupNum;
		return i.hashCode();
	}
	
	@Override
	public String toString(){
		//return "group number: " + groupNum + " lessons: " + lessons;
		String $ = "group number: " + groupNum + " lessons: ";
		//ret.concat("ok");
		//ret += "ok";
		for(Lesson ¢ : lessons)
			$ = $.concat("\n"+¢);
		return $ = $.concat("\n");
	}
}
