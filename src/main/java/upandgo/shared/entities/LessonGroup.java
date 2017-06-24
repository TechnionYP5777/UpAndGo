package upandgo.shared.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

import upandgo.shared.entities.constraint.TimeConstraint;

/**
 * 
 * @author kobybs
 * @since 23-12-16
 * 
 * Class that holds information about specific lesson group.
 * 
 */
public class LessonGroup implements IsSerializable {

	private static final long serialVersionUID = 4547987607914277140L;

	public static final int UNINITIALIZED_GROUP_NUM = -1;

	private int groupNum;
	private List<Lesson> lessons;

	public LessonGroup() {
		groupNum = UNINITIALIZED_GROUP_NUM;
		lessons = new ArrayList<>();
	}
	
	public LessonGroup(LessonGroup other) {
		groupNum = other.getGroupNum();
		lessons = new ArrayList<>(other.getLessons());
	}

	public LessonGroup(final int GroupNumber) {
		groupNum = GroupNumber;
		lessons = new ArrayList<>();
	}

	public void setGroupNum(final int GroupNumber) {
		groupNum = GroupNumber;
	}

	public int getGroupNum() {
		return groupNum;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public String getCourseID() {
		return lessons.isEmpty() ? "000000" : lessons.get(0).getCourseId();
	}

	public void addLesson(final Lesson xxx) {
		if (groupNum == UNINITIALIZED_GROUP_NUM)
			throw new IllegalArgumentException();
		lessons.add(xxx);
	}

	public boolean isClashWith(final LessonGroup g) {
		if (g == this)
			return true;
		for (final Lesson ls1 : lessons)
			for (final Lesson ls2 : g.getLessons())
				if (ls1.IsClashWith(ls2))
					return true;
		return false;
	}

	public boolean isClashWith(final TimeConstraint g) {
		for (final Lesson ls1 : lessons)
			if (ls1.IsClashWith(g))
				return true;
		return false;
	}

	@Override
	public boolean equals(final Object other) {
		return other != null
				&& (other == this || other instanceof LessonGroup && ((LessonGroup) other).groupNum == groupNum)
				&& ((LessonGroup) other).getCourseID() == this.getCourseID();
	}

	@Override
	public int hashCode() {
		@SuppressWarnings("boxing")
		final Integer $ = groupNum;
		return $.hashCode();
	}

	@Override
	public String toString() {
		// return "group number: " + groupNum + " lessons: " + lessons;
		String $ = "group number: " + groupNum + " lessons: ";
		// ret.concat("ok");
		// ret += "ok";
		for (final Lesson xxx : lessons)
			$ = $.concat("\n" + xxx);
		return $ = $.concat("\n");
	}
}
