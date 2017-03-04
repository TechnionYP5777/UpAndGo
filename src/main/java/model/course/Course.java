package model.course;

import java.time.LocalDateTime;
/**
 * @author nikita
 * @author kobybs (made changes)
 * @since 25-12-16
 */
import java.util.ArrayList;
import java.util.List;

import model.loader.CourseLoader;

// after object creation one can only add or remove lessons
// TODO: do we need to change it?
public class Course {

	protected final String name;
	protected final String id;
	protected final String faculty;
	protected final double points;
	protected final LocalDateTime aTerm;
	protected final LocalDateTime bTerm;

	protected final List<StuffMember> stuff;

	// TODO: this should replace List lessons
	protected List<LessonGroup> lectures;
	protected List<LessonGroup> tutorials;

	protected int lectureHours;
	protected int tutorialHours;
	protected int laboratoryHours;
	protected int projectHours;

	protected final List<CourseListener> listeners;

	protected final List<Course> prerequisites;
	protected final List<Course> corequisites;
	protected boolean done;
	protected boolean passThisSemester;

	// TODO: create interface LessonGroup as mediator between Lessons and Course

	public Course(final String name1, final String id1, final String faculty1, final List<StuffMember> st,
			final double acPoints, final LocalDateTime aT, final LocalDateTime bT, final List<Course> prerequisitesList,
			final List<Course> corequisitesList) {

		if (name1 == null || faculty1 == null)
			throw new NullPointerException();
		/*
		 * if (name1.isEmpty()) throw new
		 * RuntimeException("The empty field was found!\n"); //$NON-NLS-1$
		 */

		name = name1;
		id = id1;
		faculty = faculty1;
		points = acPoints;
		aTerm = aT;
		bTerm = bT;

		stuff = new ArrayList<>(st);

		lectures = new ArrayList<>();
		tutorials = new ArrayList<>();

		listeners = new ArrayList<>();

		projectHours = laboratoryHours = tutorialHours = lectureHours = 0;

		prerequisites = new ArrayList<>(prerequisitesList);
		corequisites = new ArrayList<>(corequisitesList);
		done = false;
		passThisSemester = true;

	}

	protected void addLecturesLessonGroup(final LessonGroup ¢) {
		lectures.add(¢);
	}

	protected void addTutorialLessonGroup(final LessonGroup ¢) {
		tutorials.add(¢);
	}

	public List<LessonGroup> getLectures() {
		return lectures;
	}

	public List<LessonGroup> getTutorials() {
		return tutorials;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getFaculty() {
		return faculty;
	}

	public double getPoints() {
		return points;
	}

	public LocalDateTime getaTerm() {
		return aTerm;
	}

	public LocalDateTime getbTerm() {
		return bTerm;
	}

	public List<StuffMember> getStuff() {
		return new ArrayList<>(stuff);
	}

	public List<LessonGroup> getLecturesLG() {
		return new ArrayList<>(lectures);
	}

	public List<LessonGroup> getTutorialsLG() {
		return new ArrayList<>(tutorials);
	}

	public int getLectureHours() {
		return lectureHours;
	}

	public int getTutorialHours() {
		return tutorialHours;
	}

	public int getLaboratoryHours() {
		return laboratoryHours;
	}

	public int getProjectHours() {
		return projectHours;
	}

	public int getTotalHours() {
		return laboratoryHours + lectureHours + projectHours + tutorialHours;
	}

	public static CourseBuilder giveCourseBuilderTo(@SuppressWarnings("unused") final CourseLoader ¢) {
		return new CourseBuilder();
	}

	public void addListener(final CourseListener ¢) {
		listeners.add(¢);
	}

	public void removeListener(final CourseListener ¢) {
		listeners.remove(¢);
	}

	public void updateListeners() {
		for (final CourseListener ¢ : listeners)
			¢.getUpdate(this);
	}

	public boolean getDone() {
		return done;
	}

	public void MarkDone() {
		done = true;
	}

	public boolean isPassThisSemester() {
		return passThisSemester;
	}

	public void markAsNotPass() {
		passThisSemester = false;
	}

	public List<Course> getPrerequisites() {
		return prerequisites;
	}

	public List<Course> getCorequisites() {
		return corequisites;
	}

	@Override
	public String toString() {
		return name + " " + id;
	}

	public static class CourseBuilder {

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
			if (!stuff.contains(¢.representer))
				stuff.add(¢.representer);
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

}
