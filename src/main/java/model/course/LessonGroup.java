package model.course;

import java.util.ArrayList;
import java.util.List;

import model.constraint.TimeConstraint;



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
	
	public boolean isCLashWIth(LessonGroup g){
		if(g==this)
			return false;
		for(Lesson ls1 : lessons)
			for (Lesson ls2 : g.getLessons())
				if (!ls1.IsClashWith(ls2))
					return false;
		return true;
	}
	public boolean isCLashWIth(TimeConstraint g){
		for(Lesson ls1 : lessons)
			if (!ls1.IsClashWith(g))
				return false;
		return true;
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
}
