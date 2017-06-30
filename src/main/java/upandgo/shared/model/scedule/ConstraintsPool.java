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
import upandgo.shared.entities.Lesson.Type;
import upandgo.shared.entities.LocalTime;

public class ConstraintsPool {
	
	public class CourseConstraint {
		
		public static final int NO_LESSON = -1;
		
		private boolean specificLecture;
		private int lectureLessonGroup;
		
		private boolean specificTutorial; 
		private int tutorialLessonGroup;
		
		public CourseConstraint(){
			this.specificLecture = false;
			this.lectureLessonGroup = 0;
			this.specificTutorial = false;
			this.tutorialLessonGroup = 0;
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
		
		public void setSpecificLesson(Type lessonType, boolean specific, int lessonGroup){
			if (lessonType == Type.LECTURE){
				specificLecture = specific;
				lectureLessonGroup = lessonGroup;
			} else {
				specificTutorial = specific;
				tutorialLessonGroup = lessonGroup;
			}
			
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
		
		@Override
		public String toString(){
			String result = new String();
			if (specificLecture)
				result += "Specific Lecture Group: " + lectureLessonGroup + " ";
			if (specificTutorial)
				result += "Specific Tutorial Group: " + tutorialLessonGroup;
			return result;
		}
			
	}
	private boolean isDaysoffCount;
	private boolean isBlankSpaceCount;
	private LocalTime minStartTime;
	private LocalTime maxFinishTime;
	private List<Boolean> vectorDaysOff; 
	private Map<String,CourseConstraint> courseConstraints;
	
	public ConstraintsPool() {
		this.isDaysoffCount = false;
		this.isBlankSpaceCount = false;
		this.minStartTime = null;
		this.maxFinishTime = null;
		this.vectorDaysOff = new ArrayList<>();
		for (int i = 0 ; i < 5 ; ++i)
			this.vectorDaysOff.add(Boolean.FALSE);
		this.courseConstraints = new HashMap<>();
	}
	
	public ConstraintsPool(boolean isDaysoffCount, boolean isBlankSpaceCount, 
			LocalTime minStartTime, LocalTime maxFinishTime) {
		this.isDaysoffCount = isDaysoffCount;
		this.isBlankSpaceCount = isBlankSpaceCount;
		this.minStartTime = minStartTime;
		this.maxFinishTime = maxFinishTime;
		this.vectorDaysOff = new ArrayList<>();
		for (int i = 0 ; i < 5 ; ++i)
			this.vectorDaysOff.add(Boolean.FALSE);
		this.courseConstraints = new HashMap<>();
	}
	
	public ConstraintsPool(ConstraintsPool otherConstraintsPool){
		this.isDaysoffCount = otherConstraintsPool.isDaysoffCount();
		this.isBlankSpaceCount = otherConstraintsPool.isBlankSpaceCount();
		this.minStartTime = otherConstraintsPool.getMinStartTime();
		this.maxFinishTime = otherConstraintsPool.getMaxFinishTime();
		this.vectorDaysOff = new ArrayList<>(otherConstraintsPool.getVectorDaysOff());
		this.courseConstraints = new HashMap<>();
		for (Entry<String, CourseConstraint> entry : otherConstraintsPool.getCourseConstraints().entrySet())
			this.courseConstraints.put(entry.getKey(), new CourseConstraint(entry.getValue()));
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

	public void setCourseConstraint(String courseId, boolean specificLecture, int lectureLessonGroup, 
			boolean specificTutorial, int tutorialLessonGroup) {
		courseConstraints.put(courseId,
				new CourseConstraint(specificLecture, lectureLessonGroup, specificTutorial, tutorialLessonGroup));
	}
	
	public void setCourseConstraint(String courseId, Type lessonType, boolean specific, int lessonGroup) {
		if (courseConstraints.containsKey(courseId))
			courseConstraints.get(courseId).setSpecificLesson(lessonType, specific, lessonGroup);
		else {
			CourseConstraint cc = new CourseConstraint();
			cc.setSpecificLesson(lessonType, specific, lessonGroup);
			courseConstraints.put(courseId,cc);
		}
	}
	
	public void removeCourseConstraint(String courseId) {
		if (courseConstraints.containsKey(courseId))
			courseConstraints.remove(courseId);
	}
	
	public Map<String,CourseConstraint> getCourseConstraints() {
		return courseConstraints;
	}

	public void addVectorDaysOff(List<Boolean> list) {
		vectorDaysOff = list;
	}
	
	public void setDayOff(Day d, Boolean dayOff) {
		vectorDaysOff.set(d.ordinal(), dayOff);
	}
	
	public List<Boolean> getVectorDaysOff() {
		return vectorDaysOff;
	}
	
}
