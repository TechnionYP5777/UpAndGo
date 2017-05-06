package upandgo.shared.entities.course;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @author nikita
 * @author kobybs (made changes)
 * @since 25-12-16
 */
import java.util.ArrayList;
import java.util.List;

import upandgo.server.model.loader.CourseBuilder;
import upandgo.server.model.loader.CourseLoader;
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
public class Course implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2572698212840839164L;
	
	protected final String name;
	protected final String id;
	protected final String faculty;
	protected final double points;
	protected final LocalDateTime aTerm;
	protected final LocalDateTime bTerm;

	protected final List<StuffMember> stuff;

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

	@SuppressWarnings("unused")
	private Course() {
		// here because GWT needs it
		name = id = faculty = "";
		points = 0;
		aTerm = bTerm = null;

		stuff = null;
		listeners = null;
		prerequisites = null;
		corequisites = null;
	}
	
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



}
