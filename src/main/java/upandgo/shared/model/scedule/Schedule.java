package upandgo.shared.model.scedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.constraint.TimeConstraint;
import upandgo.shared.entities.course.Course;

/**
 * @author kobybs
 * @author Nikita Dizhur	/ probably here by mistake?
 * @since 12-12-16
 * 
 * Class that represents study schedule.
 * 
 */
public class Schedule implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8446677507440908429L;
	
	private final List<LessonGroup> lessons;
	private final List<TimeConstraint> constraints;
	//private int collisions;
	//private Collision lastCollisionFound;
	private List<Collision> collisions;
	private Map<String, Integer> edgeCounter;
	private String collisionSolver;

	public Schedule() {
		lessons = new ArrayList<>();
		constraints = new ArrayList<>();
		
		collisions = new ArrayList<>();
		edgeCounter = new HashMap<>();
		
		collisionSolver = null;
		//collisions = 0;
		
	}
	
	/*
	public int getCollisionsCount(){
		return collisions;
	}
	*/
	
	public boolean addConstraintsList(final List<TimeConstraint> constraintsList) {
		if(constraintsList == null)
			return true;
		
		for(final TimeConstraint newc : constraintsList){
			if (constraints.isEmpty()) {
				constraints.add(newc);
				continue;
			}
			for (final TimeConstraint oldc : constraints)
				if (oldc.isClashWith(newc))
					return false;
			constraints.add(newc);
		}
		return true;
	}
	
	/**
	 * 
	 * @param xxx
	 * @return true if lessonGroup can be added to lessond without causing a
	 *         collision && if first cllision already happend, else - seting firstCo.. to true
	 */
	public boolean addLesson(final LessonGroup xxx) {
		/*
		 * if(!lessons.contains(xxx)) // add equals to lessonsgroup
		 * lessons.add(xxx);
		 */
		/*if (lessons.isEmpty()) {
			lessons.add(xxx);
			return true;
		}*/
		//System.out.println("addLesson: " + xxx);
		
		
		edgeCounter.put(xxx.getCourseID(), 0);
		
		/*for (final TimeConstraint c : constraints)
			if (c.isClashWith(xxx))
				
				//collisions.add
				//collisions++;
				//return false;*/
		
		for (final LessonGroup l : lessons){
			//System.out.println("check of " + l + " against " + xxx);
			if (l.isClashWith(xxx)){
				collisions.add(new Collision(l.getCourseID(), xxx.getCourseID()));
				edgeCounter.put(l.getCourseID(), edgeCounter.get(l.getCourseID())+1);
				edgeCounter.put(xxx.getCourseID(), edgeCounter.get(xxx.getCourseID())+1);
				
				//collisions++;
				//lastCollisionFound = new Collision(l.getCourseID(), xxx.getCourseID());
		
				//l.getCourseID()
			}
		}
		
		
		//if(lessons.size() > 1)
		//	return false;
		lessons.add(xxx);
		return true;
	}
	
	
	/**
	 * 
	 * @return true if there are no collisions or if there is one course that
	 * removing him can solve all collisions. in that case this function will update
	 * the collision variable to be that course.
	 */
	public boolean collisionSolver(){
		// this code is equivalent of finding a vertex cover of size 1. 
		///System.out.println("collisions: " + collisions);
		// step 0: if there are no collisions(edges) so there is no conflict 
		// 			and there is no need to solve it. 
		if(collisions.isEmpty())
			return true;
		
		// step 1: find the vertex with the highest degree
		String maxID = null;
		int max = 0;
		for(String key : edgeCounter.keySet()){
			if(edgeCounter.get(key) > max || maxID == null){
				maxID = key;
				max = edgeCounter.get(key);
			}
		}
		
		// step 2: remove all edges of that vertex.
		/*List<Collision> remove = new ArrayList<>();
		for(Collision c : collisions){
			if(c.c1.equals(maxID) || c.c2.equals(maxID))
				remove.add(c);
		}
		List<Collision> collisionDup = new ArrayList<>(collisions);
		for(Collision c : remove){
			collisionDup.remove(c);
		}
		
		// step 3: if there are any more edges than conflict cannot be resolved.
		if(collisionDup.isEmpty()){
			collisionSolver = maxID;
			return true;
		}*/
		if(collisions.size() == max){
			collisionSolver = maxID;
			return true;
		}
		
		
		return false;
	}
	
	public boolean hasCollision(){
		return collisions.size() > 0;
	}
	
	public String getCollisionSolver(){
		return collisionSolver;
	}
	/*
	public Collision getLastCollision(){
		return lastCollisionFound;
	}*/
	
	/*
	/**
	 * 
	 * @param xxx
	 * @return true if lessonGroup can be added to lessond without causing a
	 *         collision
	 */
//	public boolean addLesson(final LessonGroup xxx) {
//		/*
//		 * if(!lessons.contains(xxx)) // add equals to lessonsgroup
//		 * lessons.add(xxx);
//		 */
//		/*if (lessons.isEmpty()) {
//			lessons.add(xxx);
//			return true;
//		}*/
//		
//		
//		for (final TimeConstraint c : constraints)
//			if (c.isClashWith(xxx))
//				return false;
//		
//		for (final LessonGroup l : lessons)
//			if (l.isClashWith(xxx))
//				return false;
//		lessons.add(xxx);
//		return true;
//	}*/
	
	public Timetable getTimetable() {
		return new Timetable(lessons);

	}
	
	
	@Deprecated		// get it from the TimeTable not from here!
	public List<LessonGroup> getLessonGroups() {
		return lessons;
	}

	// to be removed after some progress on the project assures that there is no
	// need for that functionality
	/*public void removeLesson(final LessonGroup xxx) {
		lessons.remove(xxx);
	}

	public void addConstraint(final TimeConstraint xxx) {
		if (!constraints.contains(xxx))
			constraints.add(xxx);
	}

	public void removeConstraint(final TimeConstraint xxx) {
		constraints.remove(xxx);
	}

	public List<TimeConstraint> getConstraints() {
		return constraints;
	}

	public List<LessonGroup> getLessonGroups() {
		return lessons;
	}

	

	public boolean hasLesson(final LessonGroup xxx) {
		return lessons.contains(xxx);
	}

	public boolean hasConstraint(final TimeConstraint xxx) {
		return constraints.contains(xxx);
	}

	public boolean isLegalSchedule() {
		for (int i = 0; i < lessons.size(); ++i) {
			for (int j = i + 1; j < lessons.size(); ++j)
				if (lessons.get(i).isCLashWIth(lessons.get(j)))
					return false;
			for (final TimeConstraint xxx : constraints)
				if (lessons.get(i).isCLashWIth(xxx))
					return false;
		}
		return true;
	}*/

	@Override
	public String toString() {
		return lessons + "";
	}
	

}
