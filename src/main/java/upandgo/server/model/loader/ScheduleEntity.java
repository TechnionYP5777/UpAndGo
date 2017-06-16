package upandgo.server.model.loader;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class ScheduleEntity {
	static class Lesson {

		public int groupNum;
		public String courseId;
		
		public Lesson() {
			groupNum = -1;
			courseId = "";
		}
		
		public Lesson(int gn, String cid) {
			groupNum = gn;
			courseId = cid;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((courseId == null) ? 0 : courseId.hashCode());
			result = prime * result + groupNum;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Lesson other = (Lesson) obj;
			if (courseId == null) {
				if (other.courseId != null)
					return false;
			} else if (!courseId.equals(other.courseId))
				return false;
			if (groupNum != other.groupNum)
				return false;
			return true;
		}
		
	}
	
	@Id public String id;	// user's userId
	public List<Lesson> lessons;
	
	ScheduleEntity() {
		lessons = new ArrayList<>();
	}
	
	ScheduleEntity(String i, List<Lesson> l) {
		lessons = l;
		id = i;
	}
}
