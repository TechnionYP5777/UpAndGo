package upandgo.client.event;

/**
 * 
 * @author danabra
 * @since 20-06-17
 * 
 * An event class that fires when collided course was unselected
 * 
 */


import com.google.gwt.event.shared.GwtEvent;

import upandgo.shared.entities.course.CourseId;

public class CollidingCourseDeselectedEvent extends GwtEvent<CollidingCourseDeselectedEventHandler> {
	public static Type<CollidingCourseDeselectedEventHandler> TYPE = new Type<>();
	private CourseId deslectedCourse;
	
	public CollidingCourseDeselectedEvent(CourseId deslectedCourse){
		this.deslectedCourse = deslectedCourse;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CollidingCourseDeselectedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CollidingCourseDeselectedEventHandler h) {
		h.onCollidedCourseDeselected(deslectedCourse);
		
	}

}
