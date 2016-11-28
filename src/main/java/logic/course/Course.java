package logic.course;

import java.util.ArrayList;
import java.util.List;
import java.lang.RuntimeException;
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
	
	public Course(String name1, String id1, String faculty1, List<StuffMember> st, int acPoints,
																		LocalDateTime aT, LocalDateTime bT) {
		
		if((name1==null) || (id1==null) || (faculty1==null))
			throw new NullPointerException();
		if((name1.isEmpty()) || (id1.isEmpty()) || (faculty1.isEmpty()) || (st.isEmpty()))
			throw new RuntimeException("The empty field was found!\n"); //$NON-NLS-1$
		
		this.name = name1;
		this.id = id1;
		this.faculty = faculty1;
		this.points = acPoints;
		this.aTerm = aT;
		this.bTerm = bT;
		
		this.stuff = new ArrayList<>(st);
		this.lessons = new ArrayList<>();

		this.projectHours = this.laboratoryHours = this.tutorialHours = this.lectureHours = 0;
	}
	
	public void addLesson(Lesson ¢) {
		this.lessons.add(¢);
		¢.assignTo(this);
		addHours(¢);
	}
	
	public void removeLesson(Lesson ¢) {
		this.lessons.add(¢);
		¢.removeFromCourse();
		removeHours(¢);
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
	
	protected void addHours(@SuppressWarnings("unused") Lesson ¢) {
		//TODO: add number of hours in course with respect to the lesson type
	}
	
	protected void removeHours(@SuppressWarnings("unused") Lesson ¢) {
		//TODO: remove number of hours in course with respect to the lesson type
	}
	
}
