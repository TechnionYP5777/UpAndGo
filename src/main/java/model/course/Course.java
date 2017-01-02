package model.course;
/**
 * @author nikita
 * @author kobybs (made changes)
 * @since 25-12-16
 */
import java.util.ArrayList;
import java.util.List;

import model.loader.CourseLoader;

import java.time.LocalDateTime;

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
	protected  List<LessonGroup> lectures;
	protected  List<LessonGroup> tutorials;

	protected int lectureHours;
	protected int tutorialHours;
	protected int laboratoryHours;
	protected int projectHours;

	protected final List<CourseListener> listeners;

	protected final List<Course> prerequisites;
	protected final List<Course> corequisites;

	// TODO: create interface LessonGroup as mediator between Lessons and Course

	public Course(String name1, String id1, String faculty1, List<StuffMember> st, double acPoints, LocalDateTime aT,
			LocalDateTime bT, List<Course> prerequisitesList, List<Course> corequisitesList) {

		if ((name1 == null) || (faculty1 == null))
			throw new NullPointerException();
		if (name1.isEmpty())
			throw new RuntimeException("The empty field was found!\n"); //$NON-NLS-1$

		this.name = name1;
		this.id = id1;
		this.faculty = faculty1;
		this.points = acPoints;
		this.aTerm = aT;
		this.bTerm = bT;

		this.stuff = new ArrayList<>(st);
				
		this.lectures = new ArrayList<>();
		this.tutorials = new ArrayList<>();
		
		this.listeners = new ArrayList<>();

		this.projectHours = this.laboratoryHours = this.tutorialHours = this.lectureHours = 0;

		this.prerequisites = new ArrayList<>(prerequisitesList);
		this.corequisites = new ArrayList<>(corequisitesList);

	}

	protected void addLecturesLessonGroup(LessonGroup ¢) {
		this.lectures.add(¢);
	}
	
	protected void addTutorialLessonGroup(LessonGroup ¢) {
		this.tutorials.add(¢);
	}
	
	public List<LessonGroup> getLectures(){
		return this.lectures;
	}
	
	public List<LessonGroup> getTutorials(){
		return this.tutorials;
	}

	public String getName() {
		return this.name;
	}

	public String getId() {
		return this.id;
	}

	public String getFaculty() {
		return this.faculty;
	}

	public double getPoints() {
		return this.points;
	}

	public LocalDateTime getaTerm() {
		return this.aTerm;
	}

	public LocalDateTime getbTerm() {
		return this.bTerm;
	}

	public List<StuffMember> getStuff() {
		return new ArrayList<>(this.stuff);
	}
	
	public List<LessonGroup> getLecturesLG() {
		return new ArrayList<>(this.lectures);
	}
	
	public List<LessonGroup> getTutorialsLG() {
		return new ArrayList<>(this.tutorials);
	}
	
	public int getLectureHours() {
		return this.lectureHours;
	}

	public int getTutorialHours() {
		return this.tutorialHours;
	}

	public int getLaboratoryHours() {
		return this.laboratoryHours;
	}

	public int getProjectHours() {
		return this.projectHours;
	}

	public int getTotalHours() {
		return this.laboratoryHours + this.lectureHours + this.projectHours + this.tutorialHours;
	}

	public static CourseBuilder giveCourseBuilderTo(@SuppressWarnings("unused") CourseLoader ¢) {
		return new CourseBuilder();
	}

	public void addListener(CourseListener ¢) {
		this.listeners.add(¢);
	}

	public void removeListener(CourseListener ¢) {
		this.listeners.remove(¢);
	}

	public void updateListeners() {
		for (CourseListener ¢ : this.listeners)
			¢.getUpdate(this);
	}
	
	@Override
	public String toString(){
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
		
		protected  List<LessonGroup> lectures = new ArrayList<>();
		protected  List<LessonGroup> tutorials = new ArrayList<>();

		protected final List<Course> prerequisites = new ArrayList<>();
		protected final List<Course> corequisites = new ArrayList<>();

		public CourseBuilder setName(String ¢) {
			this.name = ¢;
			return this;
		}

		public CourseBuilder setId(String ¢) {
			this.id = ¢;
			return this;
		}

		public CourseBuilder setFaculty(String ¢) {
			this.faculty = ¢;
			return this;
		}

		public CourseBuilder setPoints(double ¢) {
			this.points = ¢;
			return this;
		}

		public CourseBuilder setATerm(LocalDateTime ¢) {
			this.aTerm = ¢;
			return this;
		}

		public CourseBuilder setBTerm(LocalDateTime ¢) {
			this.bTerm = ¢;
			return this;
		}
		
		public CourseBuilder addLectureGroup(int ¢) {
			for (LessonGroup i : this.lectures)
				if (i.getGroupNum() == ¢)
					return this;
			this.lectures.add(new LessonGroup(¢));
			return this;
		}
		
		public CourseBuilder addTutorialGroup(int ¢) {
			for (LessonGroup i : this.tutorials)
				if (i.getGroupNum() == ¢)
					return this;
			this.tutorials.add(new LessonGroup(¢));
			return this;
		}
		
		public CourseBuilder addLessonToGroup(int ¢, Lesson l) {
			if (l.getType() == Lesson.Type.LECTURE) {
				for (LessonGroup i : this.lectures)
					if ((i.getGroupNum() == ¢) && (!i.getLessons().contains(l))) {
						i.addLesson(l);
						return this;
					}
				return this;
			}
			for (LessonGroup i : this.tutorials)
				if ((i.getGroupNum() == ¢) && (!i.getLessons().contains(l))) {
					i.addLesson(l);
					return this;
				}
			return this;
		}

		public CourseBuilder addStuffMember(StuffMember ¢) {
			if (!this.stuff.contains(¢))
				this.stuff.add(¢);
			return this;
		}
		
		public void clearStaffMembers() {
			this.stuff.clear();
		}
		
		public void clearlecturesGroups() {
			this.lectures.clear();
		}
		
		public void cleartutorialGroup() {
			this.tutorials.clear();
		}
		
		public List<StuffMember> getStaffList() {
			return this.stuff;
		}
		
		
		
		public CourseBuilder addLesson(Lesson ¢) {
			this.lessons.add(¢);
			if (!this.stuff.contains(¢.representer))
				this.stuff.add(¢.representer);
			return this;
		}
		
		public CourseBuilder addPrerequisitesCourse(Course ¢) {
			if (!this.prerequisites.contains(¢))
				this.prerequisites.add(¢);
			return this;
		}
		
		public CourseBuilder addCorequisitesCourse(Course ¢) {
			if (!this.corequisites.contains(¢))
				this.corequisites.add(¢);
			return this;
		}
		
		public Course build() {
			Course $ = new Course(this.name, this.id, this.faculty, this.stuff, this.points, this.aTerm, this.bTerm,
					this.prerequisites, this.corequisites);
			for (LessonGroup ¢ : this.lectures)
				$.addLecturesLessonGroup(¢);
			for (LessonGroup ¢ : this.tutorials)
				$.addTutorialLessonGroup(¢);
			return $;
		}
	}

}
