package upandgo.server.model.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class ScheduleEntity {
	public static class Lesson {

		public int groupNum;
		public String courseId;
		
		public Lesson() {
			groupNum = -1;
			courseId = "";
		}
		
		public Lesson(String cid, int gn) {
			groupNum = gn;
			courseId = cid;
		}
		
		public int getGroupNum() {
			return groupNum;
		}

		public String getCourseId() {
			return courseId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			return groupNum + prime * (prime + ((courseId == null) ? 0 : courseId.hashCode()));
		}
		@Override
		public boolean equals(Object o) {
			if (o == this)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			Lesson other = (Lesson) o;
			if (courseId == null) {
				if (other.courseId != null)
					return false;
			} else if (!courseId.equals(other.courseId))
				return false;
			return groupNum == other.groupNum;
		}
		
	}
	
	@Id public String id;	// user's userId
	public Map<String,List<Lesson>> lessons;
	
	public ScheduleEntity() {
		id = "";
		lessons = new TreeMap<>();
	}
	
	public ScheduleEntity(String id) {
		this.id = id;
		this.lessons = new TreeMap<>();
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public List<Lesson> getLessons(String semesterId){
		if (lessons.containsKey(semesterId)){
			return lessons.get(semesterId);
		}
		return new ArrayList<>();
	}
	
	public void addLesson(String semesterId, String courseId, int groupNum){
		Lesson newLesson = new Lesson(courseId, groupNum);
		if (lessons.containsKey(semesterId)){
			lessons.get(semesterId).add(newLesson);
		}
		else{
			List<Lesson> lessonsList = new ArrayList<>();
			lessonsList.add(newLesson);
			lessons.put(semesterId, lessonsList);
		}
	}
	
	public void removeAllLessons(String semesterId){
		if (lessons.containsKey(semesterId)){
			lessons.remove(semesterId);
		}
	}
}
