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
import java.util.List;

import upandgo.shared.entities.LocalTime;

public class constraintsPool {
	
	public class courseConstraint {
		private String courseName = "";
				
		private boolean specificLecture = false;
		private String lectureDetails = "";
		
		private boolean specificTutorial = false; 
		private String tutorialDetails = "";
		
		public courseConstraint(String name, boolean specificLecture, String lectureDetails, 
				boolean specificTutorial, String tutorialDetails) {
			this.courseName = name;
			this.specificLecture = specificLecture;
			this.lectureDetails = lectureDetails;
			this.specificTutorial = specificTutorial;
			this.tutorialDetails = tutorialDetails;
		}
		
		public String getCourseName() {
			return courseName;
		}
		
		public boolean isSpecificLecture() {
			return specificLecture;
		}
		
		public boolean isSpecificTutorial() {
			return specificTutorial;		
		}
		
		public String getLectureDetails() {
			return lectureDetails;
		}
		
		public String getTutorialDetails() {
			return tutorialDetails;
		}
			
	}
	private boolean isDaysoffCount = false;
	private boolean isBlankSpaceCount = false;
	private LocalTime minStartTime = null;
	private LocalTime maxFinishTime = null;
	private List<Boolean> vectorDaysOff; 
	private List<courseConstraint> courseList;
	
	public constraintsPool(boolean isDaysoffCount, boolean isBlankSpaceCount, 
			LocalTime minStartTime, LocalTime maxFinishTime) {
		this.isDaysoffCount = isDaysoffCount;
		this.isBlankSpaceCount = isBlankSpaceCount;
		this.minStartTime = minStartTime;
		this.maxFinishTime = maxFinishTime;
		this.vectorDaysOff = new ArrayList<>();
		this.courseList = new ArrayList<>();
	}
	
	public boolean getIsDaysoffCount() {
		return isDaysoffCount;
	}
	
	public boolean getIsBlankSpaceCount() {
		return isBlankSpaceCount;
	}
	
	public LocalTime getStartTime() {
		return minStartTime;
	}
	
	public LocalTime getFinishTime() {
		return maxFinishTime;
	}
	
	public List<courseConstraint> getCourseConstraintList () {
		return courseList;
	}
	
	public void addCourseConstraits(courseConstraint cc) {
		courseList.add(cc);
	}
	
	public void addVectorDaysOff(List<Boolean> list) {
		vectorDaysOff = list;
	}
	
	public List<Boolean> getVectorDaysOff() {
		return vectorDaysOff;
	}
	
	

}
