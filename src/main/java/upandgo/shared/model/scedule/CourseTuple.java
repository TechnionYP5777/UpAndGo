package upandgo.shared.model.scedule;

public class CourseTuple {
	public final String courseId;
	public final String courseName;
	
	public CourseTuple(String courseId, String courseName) { 
	    this.courseId = courseId; 
	    this.courseName = courseName; 
	} 
	
	public String getCourseId(){
		return courseId;
	}
	
	public String getCourseName(){
		return courseName;
	}
}
