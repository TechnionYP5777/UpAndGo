package upandgo.shared.entities.course;


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

	protected List<String> notes;
	
	@SuppressWarnings("unused")
	public Course() {
		// here because GWT needs it
		name = id = faculty = "";
		points = 0;
		aTerm = null;
		bTerm = null;

		stuff = null;
	
		lectures = null;
		tutorials = null;
		
		notes = null;
	}
	
	public Course(final String name1, final String id1, final String faculty1, final List<StuffMember> st,
			final double acPoints, Exam aT, Exam bT/*, final List<Course> prerequisitesList, final List<Course> corequisitesList*/) {

		if (name1 == null || faculty1 == null)
			throw new NullPointerException();
	
		name = name1;
		id = id1;
		faculty = faculty1;
		points = acPoints;
		aTerm = aT;
		bTerm = bT;

		stuff = new ArrayList<>(st);

		lectures = new ArrayList<>();
		tutorials = new ArrayList<>();

		notes = new ArrayList<>();

	}
	
	public Course(Course other){
		this.name = other.name;
		this.id = other.id;
		this.faculty = other.faculty;
		this.points = other.points;
		this.aTerm = other.aTerm;
		this.bTerm = other.bTerm;
		
		this.stuff = new ArrayList<>(other.getStuff());
		this.lectures = new ArrayList<>(other.getLectures().size());
		for (int i = 0 ; i < other.getLectures().size() ; ++i)
			this.lectures.add(new LessonGroup(other.getLectures().get(i)));
		this.tutorials = new ArrayList<>(other.getTutorials().size());
		for (int i = 0 ; i < other.getTutorials().size() ; ++i)
			this.tutorials.add(new LessonGroup(other.getTutorials().get(i)));
		this.notes = new ArrayList<>(other.getNotes());
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
		if (stuff == null) {
			return new ArrayList<>(); 
		}
		return new ArrayList<>(stuff);
	}

	public List<LessonGroup> getLecturesLG() {
		return new ArrayList<>(lectures);
	}

	public List<LessonGroup> getTutorialsLG() {
		return new ArrayList<>(tutorials);
	}

	public void addNote(final String note){
		notes.add(note);
	}
	
	public List<String> getNotes() {
		return notes;
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
