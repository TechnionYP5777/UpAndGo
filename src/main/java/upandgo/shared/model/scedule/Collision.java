package upandgo.shared.model.scedule;


import upandgo.shared.entities.course.CourseId;

public class Collision {
	public final String c1;
	public final String c2;
	
	public Collision(String c1, String c2) { 
	    this.c1 = c1; 
	    this.c2 = c2; 
	} 
	
	/*
	@Override
	public */
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Collision))return false;
	    Collision otherCollsion = (Collision)other;
	    return c1.equals(otherCollsion.c1) && c2.equals(otherCollsion.c2);
	}
	
}
