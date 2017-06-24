package upandgo.shared.entities.course;

import java.time.LocalDateTime;
/**
 * @author nikita
 * @author kobybs (made changes)
 * @since 25-12-16
 */
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

import upandgo.shared.entities.Exam;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.StuffMember;
import upandgo.shared.model.scedule.Collision;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-12-16
 * 
 * Class that holds information about specific course.
 * 
 */
public class Course implements IsSerializable {
	
	protected String name;
	protected String id;
	protected String faculty;
	protected double points;
	protected Exam aTerm;
	protected Exam bTerm;

	protected List<StuffMember> stuff;

	protected List<LessonGroup> lectures;
	protected List<LessonGroup> tutorials;

	protected int lectureHours;
	protected int tutorialHours;
	protected int laboratoryHours;
	protected int projectHours;

	@Deprecated
	final protected List<CourseListener> listeners;

	protected List<String> notes;
	
	@Deprecated
	final protected List<Course> prerequisites;
	@Deprecated
	final protected List<Course> corequisites;
	protected boolean done;
	protected boolean passThisSemester;

	@SuppressWarnings("unused")
	private Course() {
		// here because GWT needs it
		name = id = faculty = "";
		points = 0;
		aTerm = null;
		bTerm = null;

		stuff = null;
		listeners = null;
		prerequisites = null;
		corequisites = null;
		
		lectures = null;
		tutorials = null;
		
		notes = null;
	}
	
	public Course(final String name1, final String id1, final String faculty1, final List<StuffMember> st,
			final double acPoints, Exam aT, Exam bT, final List<Course> prerequisitesList, final List<Course> corequisitesList) {

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
		
		notes = new ArrayList<>();

		prerequisites = new ArrayList<>(prerequisitesList);
		corequisites = new ArrayList<>(corequisitesList);
		done = false;
		passThisSemester = true;

	}
	
	public Course(Course other){
		this.name = other.name;
		this.id = other.id;
		this.faculty = other.faculty;
		this.points = other.points;
		this.aTerm = other.aTerm;
		this.bTerm = other.bTerm;
		
		this.stuff = new ArrayList<>(other.getStuff());
		this.lectures = new ArrayList<>(other.getLectures());
		this.tutorials = new ArrayList<>(other.getTutorials());
		
		this.listeners = null;
		this.prerequisites = null;
		this.corequisites = null;
		
		this.laboratoryHours = other.getLectureHours();
		this.tutorialHours = other.getTutorialHours();
		this.laboratoryHours = other.getLaboratoryHours();
		this.projectHours = other.getProjectHours();
		
		this.notes = new ArrayList<>(other.getNotes());
		
		this.done = other.done;
		this.passThisSemester = other.passThisSemester;
	}

	public void addLecturesLessonGroup(final LessonGroup xxx) {
		lectures.add(xxx);
	}

	public void addTutorialLessonGroup(final LessonGroup xxx) {
		tutorials.add(xxx);
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

	public Exam getaTerm() {
		return aTerm;
	}

	public Exam getbTerm() {
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

/*	public static CourseBuilder giveCourseBuilderTo(@SuppressWarnings("unused") final CourseLoader xxx) {
		return new CourseBuilder();
	}*/

	public void addListener(final CourseListener xxx) {
		listeners.add(xxx);
	}

	public void removeListener(final CourseListener xxx) {
		listeners.remove(xxx);
	}

	public void updateListeners() {
		for (final CourseListener xxx : listeners)
			xxx.getUpdate(this);
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
	
	public void addNote(final String note){
		notes.add(note);
	}
	
	public List<String> getNotes() {
		return notes;
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
	
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Course))return false;
	    Course otherCourse = (Course)other;
	    return this.getId().equals(otherCourse.getId());
	}



}
