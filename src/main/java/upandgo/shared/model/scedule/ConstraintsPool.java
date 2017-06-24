package upandgo.shared.model.scedule;

/**
 * 
 * @author Omri Ben Shmuel
 * @since 22-06-17
 * 
 *      class for course constraints
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import upandgo.shared.entities.Day;
import upandgo.shared.entities.LocalTime;

public class ConstraintsPool {
	
	public class CourseConstraint {
		
		public static final int NO_LESSON = -1;
		
		private boolean specificLecture = false;
		private int lectureLessonGroup = 0;
		
		private boolean specificTutorial = false; 
		private int tutorialLessonGroup = 0;
		
		public CourseConstraint(){
			
		}
		
		public CourseConstraint(boolean specificLecture, int lectureLessonGroup, 
				boolean specificTutorial, int tutorialLessonGroup) {
			this.specificLecture = specificLecture;
			this.lectureLessonGroup = lectureLessonGroup;
			this.specificTutorial = specificTutorial;
			this.tutorialLessonGroup = tutorialLessonGroup;
		}
		
		public CourseConstraint(CourseConstraint otherCourseConstraint) {
			this.specificLecture = otherCourseConstraint.isSpecificLecture();
			this.lectureLessonGroup = otherCourseConstraint.getLectureLessonGroup();
			this.specificTutorial = otherCourseConstraint.isSpecificTutorial();
			this.tutorialLessonGroup = otherCourseConstraint.getTutorialLessonGroup();
		}
		
		public boolean isSpecificLecture() {
			return specificLecture;
		}
		
		public boolean isSpecificTutorial() {
			return specificTutorial;		
		}
		
		public int getLectureLessonGroup() {
			return lectureLessonGroup;
		}
		
		public int getTutorialLessonGroup() {
			return tutorialLessonGroup;
		}
			
	}
	private boolean isDaysoffCount = false;
	private boolean isBlankSpaceCount = false;
	private LocalTime minStartTime = null;
	private LocalTime maxFinishTime = null;
	private List<Boolean> vectorDaysOff; 
	private Map<String,CourseConstraint> courseConstraints;
	
	public ConstraintsPool() {
		this.isDaysoffCount = false;
		this.isBlankSpaceCount = false;
		this.minStartTime = null;
		this.maxFinishTime = null;
		this.vectorDaysOff = new ArrayList<>();
		for (int i = 0 ; i < 5 ; i++){
			this.vectorDaysOff.add(Boolean.FALSE);
		}
		this.courseConstraints = new HashMap<>();
	}
	
	public ConstraintsPool(boolean isDaysoffCount, boolean isBlankSpaceCount, 
			LocalTime minStartTime, LocalTime maxFinishTime) {
		this.isDaysoffCount = isDaysoffCount;
		this.isBlankSpaceCount = isBlankSpaceCount;
		this.minStartTime = minStartTime;
		this.maxFinishTime = maxFinishTime;
		this.vectorDaysOff = new ArrayList<>();
		for (int i = 0 ; i < 5 ; i++){
			this.vectorDaysOff.add(Boolean.FALSE);
		}
		this.courseConstraints = new HashMap<>();
	}
	
	public ConstraintsPool(ConstraintsPool otherConstraintsPool){
		this.isDaysoffCount = otherConstraintsPool.isDaysoffCount();
		this.isBlankSpaceCount = otherConstraintsPool.isBlankSpaceCount();
		this.minStartTime = otherConstraintsPool.getMinStartTime();
		this.maxFinishTime = otherConstraintsPool.getMaxFinishTime();
		this.vectorDaysOff = new ArrayList<>(otherConstraintsPool.getVectorDaysOff());
		this.courseConstraints = new HashMap<>();
		for (Entry<String, CourseConstraint> entry : otherConstraintsPool.getCourseConstraints().entrySet()){
			this.courseConstraints.put(entry.getKey(), new CourseConstraint(entry.getValue()));
		}
	}
	
	public boolean isDaysoffCount() {
		return isDaysoffCount;
	}
	
	public void setDaysoffCount(boolean isDaysoffCount) {
		this.isDaysoffCount = isDaysoffCount;
	}
	
	public boolean isBlankSpaceCount() {
		return isBlankSpaceCount;
	}

	public void setBlankSpaceCount(boolean isBlankSpaceCount) {
		this.isBlankSpaceCount = isBlankSpaceCount;
	}

	public LocalTime getMinStartTime() {
		return minStartTime;
	}

	public void setMinStartTime(LocalTime minStartTime) {
		this.minStartTime = minStartTime;
	}

	public LocalTime getMaxFinishTime() {
		return maxFinishTime;
	}

	public void setMaxFinishTime(LocalTime maxFinishTime) {
		this.maxFinishTime = maxFinishTime;
	}

	public void addCourseConstraint(String courseId, boolean specificLecture, int lectureLessonGroup, 
			boolean specificTutorial, int tutorialLessonGroup) {
		CourseConstraint cc = new CourseConstraint(specificLecture, lectureLessonGroup, specificTutorial, tutorialLessonGroup);
		courseConstraints.put(courseId,cc);
	}
	
	public Map<String,CourseConstraint> getCourseConstraints() {
		return courseConstraints;
	}

	public void addVectorDaysOff(List<Boolean> list) {
		vectorDaysOff = list;
	}
	
	public void setDayOff(Day day, Boolean dayOff) {
		vectorDaysOff.set(day.ordinal(), dayOff);
	}
	
	public List<Boolean> getVectorDaysOff() {
		return vectorDaysOff;
	}
	
}
