package upandgo.shared.model.scedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.constraint.TimeConstraint;

/**
 * @author kobybs
 * @author Nikita Dizhur	/ probably here by mistake?
 * @since 12-12-16
 * 
 * Class that represents study schedule.
 * 
 */
public class Schedule implements Serializable {
	private static final long serialVersionUID = -0x7538A070CB4C188DL;
	
	private final List<LessonGroup> lessons;
	private final List<TimeConstraint> constraints;
	private List<Collision> collisions;
	private Map<String, Integer> edgeCounter;
	private String collisionSolver;
	private String collisionSolver2;

	public Schedule() {
		lessons = new ArrayList<>();
		constraints = new ArrayList<>();
		
		collisions = new ArrayList<>();
		edgeCounter = new HashMap<>();
		
		collisionSolver = null;
		collisionSolver2 = null;
		
	}
		
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
	@SuppressWarnings("boxing")
	public boolean addLesson(final LessonGroup xxx) {
			
		edgeCounter.put(xxx.getCourseID(), 0);
		
		for (final LessonGroup l : lessons){
			if (l.isClashWith(xxx)){
				collisions.add(new Collision(l.getCourseID(), xxx.getCourseID()));
				edgeCounter.put(l.getCourseID(), edgeCounter.get(l.getCourseID())+1);
				edgeCounter.put(xxx.getCourseID(), edgeCounter.get(xxx.getCourseID())+1);
			}
		}
		
		
		lessons.add(xxx);
		return true;
	}
	
	
	/**
	 * 
	 * @return true if there are no collisions or if there is one course that
	 * removing him can solve all collisions. in that case this function will update
	 * the collision variable to be that course.
	 */
	@SuppressWarnings("boxing")
	public boolean collisionSolver(){
		// this code is equivalent of finding a vertex cover of size 1. 
		///System.out.println("collisions: " + collisions);
		// step 0: if there are no collisions(edges) so there is no conflict 
		// 			and there is no need to solve it. 
		if(collisions.isEmpty())
			return true;
		if(collisions.size() == 1){
			collisionSolver = collisions.get(0).c1;
			collisionSolver2 = collisions.get(0).c2;
			return true;
		}
		
		// step 1: find the vertex with the highest degree
		String maxID = null;
		int max = 0;
		for(String key : edgeCounter.keySet()){
			if(edgeCounter.get(key) > max || maxID == null){
				maxID = key;
				max = edgeCounter.get(key);
			}
		}
		
		
		
		// step 2 : if the collision degree is 
		if (collisions.size() != max)
			return false;
		collisionSolver = maxID;
		return true;
	}
	
	public boolean hasCollision(){
		return !collisions.isEmpty();
	}
	
	public boolean hasDualCollision(){
		return collisions.size() == 1;
	}
	public String getCollisionSolver(){
		return collisionSolver;
	}
	public String getCollisionSolver2(){
		return collisionSolver2;
	}
		
	public Timetable getTimetable() {
		return new Timetable(lessons);

	}
	
	
	@Deprecated		// get it from the TimeTable not from here!
	public List<LessonGroup> getLessonGroups() {
		return lessons;
	}

	
	@Override
	public String toString() {
		return lessons + "";
	}
	

}
