package model.course;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.constraint.TimeConstraint;



public class LessonGroup implements Serializable{
	
	private static final long serialVersionUID = 4547987607914277140L;

	public static final int UNINITIALIZED_GROUP_NUM = -1;
	
	private int groupNum;
	private final List<Lesson> lessons;
	
	public LessonGroup(){
		groupNum = UNINITIALIZED_GROUP_NUM;
		lessons = new ArrayList<>();
	}
	
	public LessonGroup(final int GroupNumber){
		groupNum = GroupNumber;
		lessons = new ArrayList<>();
	}
	
	public void setGroupNum(final int GroupNumber){
		groupNum = GroupNumber;
	}
	
	public int getGroupNum(){
		return groupNum;
	}
	
	public List<Lesson> getLessons(){
		return lessons;
	}
	
	public String getCourseID(){
		return lessons.isEmpty() ? "000000" : lessons.get(0).getCourse();
	}
	
	public void addLesson(final Lesson ¢){
		if (groupNum == UNINITIALIZED_GROUP_NUM)
			throw new IllegalArgumentException();
		lessons.add(¢);
	}
	
	public boolean isCLashWIth(final LessonGroup g){
		if(g==this)
			return true;
		for(final Lesson ls1 : lessons)
			for (final Lesson ls2 : g.getLessons())
				if (ls1.IsClashWith(ls2))
					return true;
		return false;
	}
	public boolean isCLashWIth(final TimeConstraint g){
		for(final Lesson ls1 : lessons)
			if (ls1.IsClashWith(g))
				return true;
		return false;
	}
	
	@Override
	public boolean equals(final Object other){
		return other != null && (other == this || other instanceof LessonGroup && ((LessonGroup) other).groupNum == groupNum);
	}

	@Override
	public int hashCode() {
		@SuppressWarnings("boxing")
		final
		Integer i = groupNum;
		return i.hashCode();
	}
	
	@Override
	public String toString(){
		//return "group number: " + groupNum + " lessons: " + lessons;
		String $ = "group number: " + groupNum + " lessons: ";
		//ret.concat("ok");
		//ret += "ok";
		for(final Lesson ¢ : lessons)
			$ = $.concat("\n"+¢);
		return $ = $.concat("\n");
	}
}
