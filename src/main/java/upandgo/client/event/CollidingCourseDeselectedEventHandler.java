package upandgo.client.event;

/**
 * 
 * @author danabra
 * @since 20-06-17
 * 
 * A handler for CollidingCourseDeselectedEvent
 * 
 */


import com.google.gwt.event.shared.EventHandler;

import upandgo.shared.entities.course.CourseId;

public interface CollidingCourseDeselectedEventHandler extends EventHandler {
	void onCollidedCourseDeselected(CourseId c);
}
