package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;

import upandgo.shared.entities.course.CourseId;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-04-16
 * 
 * Event that happens when user unselects some course in list. Is handled by {@link UnselectCourseEventHandler}.
 * 
 */

public class UnselectCourseEvent extends GwtEvent<UnselectCourseEventHandler> {
	public static Type<UnselectCourseEventHandler> TYPE = new Type<>();
	private final CourseId id;

	public UnselectCourseEvent(CourseId id) {
	    this.id = id;
	  }

	public CourseId getId() {
		return id;
	}
	@Override
	public Type<UnselectCourseEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UnselectCourseEventHandler handler) {
		handler.onUnselectCourse(this);
		
	}
}
