package upandgo.server.model.loader;

import java.util.ArrayList;
import java.util.List;

import upandgo.shared.entities.Exam;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.StuffMember;
import upandgo.shared.entities.course.Course;

public class CourseBuilder {

	protected String name = "";
	protected String id = "";
	protected String faculty = "";
	protected double points;
	protected Exam aTerm = new Exam();
	protected Exam bTerm = new Exam();

	protected final List<StuffMember> stuff = new ArrayList<>();
	protected final List<Lesson> lessons = new ArrayList<>();

	protected List<LessonGroup> lectures = new ArrayList<>();
	protected List<LessonGroup> tutorials = new ArrayList<>();

	protected final List<String> notes = new ArrayList<>();

	protected final List<Course> prerequisites = new ArrayList<>();
	protected final List<Course> corequisites = new ArrayList<>();

	public CourseBuilder setName(final String xxx) {
		name = xxx;
		return this;
	}

	public CourseBuilder setId(final String xxx) {
		id = xxx;
		return this;
	}

	public CourseBuilder setFaculty(final String xxx) {
		faculty = xxx;
		return this;
	}

	public CourseBuilder setPoints(final double xxx) {
		points = xxx;
		return this;
	}

	public CourseBuilder setATerm(Exam xxx) {
		aTerm = xxx;
		return this;
	}

	public CourseBuilder setBTerm(Exam xxx) {
		bTerm = xxx;
		return this;
	}

	public CourseBuilder addLectureGroup(final int xxx) {
		for (final LessonGroup i : lectures)
			if (i.getGroupNum() == xxx)
				return this;
		lectures.add(new LessonGroup(xxx));
		return this;
	}

	public CourseBuilder addTutorialGroup(final int xxx) {
		for (final LessonGroup i : tutorials)
			if (i.getGroupNum() == xxx)
				return this;
		tutorials.add(new LessonGroup(xxx));
		return this;
	}

	public CourseBuilder addLessonToGroup(final int xxx, final Lesson l) {
		if (l.getType() == Lesson.Type.LECTURE) {
			for (final LessonGroup i : lectures)
				if (i.getGroupNum() == xxx && !i.getLessons().contains(l)) {
					i.addLesson(l);
					return this;
				}
			return this;
		}
		for (final LessonGroup i : tutorials)
			if (i.getGroupNum() == xxx && !i.getLessons().contains(l)) {
				i.addLesson(l);
				return this;
			}
		return this;
	}

	public CourseBuilder addStuffMember(final StuffMember xxx) {
		if (!stuff.contains(xxx))
			stuff.add(xxx);
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
	
	public void clearNotes() {
		notes.clear();
	}

	public List<StuffMember> getStaffList() {
		return stuff;
	}

	public CourseBuilder addLesson(final Lesson xxx) {
		lessons.add(xxx);
		if (!stuff.contains(xxx.getRepresenter()))
			stuff.add(xxx.getRepresenter());
		return this;
	}

	public CourseBuilder addNote(final String note){
		if (!notes.contains(note))
			notes.add(note);
		return this;
	}
	
	public Course build() {
		final Course $ = new Course(name, id, faculty, stuff, points, aTerm, bTerm);
		for (final LessonGroup xxx : lectures)
			$.addLecturesLessonGroup(xxx);
		for (final LessonGroup xxx : tutorials)
			$.addTutorialLessonGroup(xxx);
		for (final String xxx : notes)
			$.addNote(xxx);
		return $;
	}
}