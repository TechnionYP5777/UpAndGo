package model.course;

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
	protected final int points;
	protected final LocalDateTime aTerm;
	protected final LocalDateTime bTerm;

	protected final List<StuffMember> stuff;
	protected final List<Lesson> lessons;
	
	protected int lectureHours;
	protected int tutorialHours;
	protected int laboratoryHours;
	protected int projectHours;
	
	protected final List<CourseListener> listeners;
	
	//TODO: create interface LessonGroup as mediator between Lessons and Course
	
	public Course(String name1, String id1, String faculty1, List<StuffMember> st, int acPoints,
																		LocalDateTime aT, LocalDateTime bT) {
		
		if((name1==null) || (faculty1==null))
			throw new NullPointerException();
		if(name1.isEmpty() || faculty1.isEmpty())
			throw new RuntimeException("The empty field was found!\n"); //$NON-NLS-1$
		
		this.name = name1;
		this.id = id1;
		this.faculty = faculty1;
		this.points = acPoints;
		this.aTerm = aT;
		this.bTerm = bT;
		
		this.stuff = new ArrayList<>(st);
		this.lessons = new ArrayList<>();
		
		this.listeners = new ArrayList<>();

		this.projectHours = this.laboratoryHours = this.tutorialHours = this.lectureHours = 0;
	}	
	
	protected void addLesson(Lesson ¢) {
		this.lessons.add(¢);
		addHours(¢);
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
	
	public int getPoints() {
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
	
	public List<Lesson> getLessons() {
		return new ArrayList<>(this.lessons);
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
		return this.laboratoryHours+this.lectureHours+this.projectHours+this.tutorialHours;
	}
	
	protected void addHours(Lesson ¢) {
		switch(¢.type) {
		case LABORATORY:
			this.laboratoryHours += ¢.duration;
			break;
		case LECTURE:
			this.lectureHours += ¢.duration;
			break;
		case PROJECT:
			this.projectHours += ¢.duration;
			break;
		case TUTORIAL:
			this.tutorialHours += ¢.duration;
			break;
		default:
			break;
		}
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
		for(CourseListener ¢: this.listeners)
			¢.getUpdate(this);
	}
	
	public static class CourseBuilder {
		
		protected String name = "";
		protected String id = "";
		protected String faculty = "";
		protected int points = 0;
		protected LocalDateTime aTerm;
		protected LocalDateTime bTerm;
		
		protected final List<StuffMember> stuff = new ArrayList<>();
		protected final List<Lesson> lessons = new ArrayList<>();

		public CourseBuilder setName(String ¢) {
			this.name = ¢;
			return this;
		}
		
		public CourseBuilder setId(String ¢) {
			this.id =¢;
			return this;
		}
		
		public CourseBuilder setFaculty(String ¢) {
			this.faculty =¢;
			return this;
		}
		
		public CourseBuilder setPoints(int ¢) {
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
		
		public CourseBuilder addStuffMember(StuffMember ¢) {
			if(!this.stuff.contains(¢))
				this.stuff.add(¢);
			return this;
		}
		public CourseBuilder addLesson(Lesson ¢) {
			this.lessons.add(¢);
			if(!this.stuff.contains(¢.representer))
				this.stuff.add(¢.representer);
			return this;
		}
		
		public Course build() {
			Course $ = new Course(this.name, this.id, this.faculty, this.stuff, this.points, this.aTerm, this.bTerm);
			for(Lesson ¢: this.lessons)
				$.addLesson(¢);
			return $;
		}
	}
	
}
