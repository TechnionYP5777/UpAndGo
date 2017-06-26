package upandgo.shared.entities;

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

	public static final int UNINITIALIZED_GROUP_NUM = -1;

	private int groupNum;
	private boolean isConstrained;
	private List<Lesson> lessons;

	public LessonGroup() {
		groupNum = UNINITIALIZED_GROUP_NUM;
		isConstrained = false;
		lessons = new ArrayList<>();
	}
	
	public LessonGroup(LessonGroup other) {
		groupNum = other.getGroupNum();
		isConstrained = other.isConstrained();
		lessons = new ArrayList<>(other.getLessons().size());
		for (int i = 0 ; i < other.getLessons().size() ; ++i)
			lessons.add(new Lesson(other.getLessons().get(i)));
	}

	public LessonGroup(final int GroupNumber) {
		groupNum = GroupNumber;
		isConstrained = false;
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

	public boolean isConstrained() {
		return isConstrained;
	}

	public void setConstrained(boolean isConstrained) {
		this.isConstrained = isConstrained;
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
		String $ = "group number: " + groupNum + " lessons: ";
		for (final Lesson xxx : lessons)
			$ = $.concat("\n" + xxx);
		return $ = $.concat("\n");
	}
}
