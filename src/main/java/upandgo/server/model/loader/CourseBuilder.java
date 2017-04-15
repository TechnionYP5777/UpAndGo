package upandgo.server.model.loader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.StuffMember;
import upandgo.shared.entities.Lesson.Type;
import upandgo.shared.entities.course.Course;

public class CourseBuilder {

	protected String name = "";
	protected String id = "";
	protected String faculty = "";
	protected double points;
	protected LocalDateTime aTerm;
	protected LocalDateTime bTerm;

	protected final List<StuffMember> stuff = new ArrayList<>();
	protected final List<Lesson> lessons = new ArrayList<>();

	protected List<LessonGroup> lectures = new ArrayList<>();
	protected List<LessonGroup> tutorials = new ArrayList<>();

	protected final List<Course> prerequisites = new ArrayList<>();
	protected final List<Course> corequisites = new ArrayList<>();

	public CourseBuilder setName(final String ¢) {
		name = ¢;
		return this;
	}

	public CourseBuilder setId(final String ¢) {
		id = ¢;
		return this;
	}

	public CourseBuilder setFaculty(final String ¢) {
		faculty = ¢;
		return this;
	}

	public CourseBuilder setPoints(final double ¢) {
		points = ¢;
		return this;
	}

	public CourseBuilder setATerm(final LocalDateTime ¢) {
		aTerm = ¢;
		return this;
	}

	public CourseBuilder setBTerm(final LocalDateTime ¢) {
		bTerm = ¢;
		return this;
	}

	public CourseBuilder addLectureGroup(final int ¢) {
		for (final LessonGroup i : lectures)
			if (i.getGroupNum() == ¢)
				return this;
		lectures.add(new LessonGroup(¢));
		return this;
	}

	public CourseBuilder addTutorialGroup(final int ¢) {
		for (final LessonGroup i : tutorials)
			if (i.getGroupNum() == ¢)
				return this;
		tutorials.add(new LessonGroup(¢));
		return this;
	}

	public CourseBuilder addLessonToGroup(final int ¢, final Lesson l) {
		if (l.getType() == Lesson.Type.LECTURE) {
			for (final LessonGroup i : lectures)
				if (i.getGroupNum() == ¢ && !i.getLessons().contains(l)) {
					i.addLesson(l);
					return this;
				}
			return this;
		}
		for (final LessonGroup i : tutorials)
			if (i.getGroupNum() == ¢ && !i.getLessons().contains(l)) {
				i.addLesson(l);
				return this;
			}
		return this;
	}

	public CourseBuilder addStuffMember(final StuffMember ¢) {
		if (!stuff.contains(¢))
			stuff.add(¢);
		return this;
	}

	public void clearStaffMembers() {
		stuff.clear();
	}

	public void clearlecturesGroups() {
		lectures.clear();
	}

	public void cleartutorialGroup() {
		tutorials.clear();
	}

	public List<StuffMember> getStaffList() {
		return stuff;
	}

	public CourseBuilder addLesson(final Lesson ¢) {
		lessons.add(¢);
		if (!stuff.contains(¢.getRepresenter()))
			stuff.add(¢.getRepresenter());
		return this;
	}

	public CourseBuilder addPrerequisitesCourse(final Course ¢) {
		if (!prerequisites.contains(¢))
			prerequisites.add(¢);
		return this;
	}

	public CourseBuilder addCorequisitesCourse(final Course ¢) {
		if (!corequisites.contains(¢))
			corequisites.add(¢);
		return this;
	}

	public Course build() {
		final Course $ = new Course(name, id, faculty, stuff, points, aTerm, bTerm, prerequisites, corequisites);
		for (final LessonGroup ¢ : lectures)
			$.addLecturesLessonGroup(¢);
		for (final LessonGroup ¢ : tutorials)
			$.addTutorialLessonGroup(¢);
		return $;
	}
}