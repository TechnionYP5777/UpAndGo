package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;

import upandgo.shared.entities.course.CourseId;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-04-16
 * 
 * Event that happens when user highlights some course in table or list. Is handled by {@link GetCourseDetailsEventHandler}.
 * 
 */

public class GetCourseDetailsEvent extends GwtEvent<GetCourseDetailsEventHandler> {
	public static Type<GetCourseDetailsEventHandler> TYPE = new Type<>();
	private final CourseId id;

	public GetCourseDetailsEvent(CourseId id) {
	    this.id = id;
	  }

	public CourseId getId() {
		return id;
	}

	@Override
	public Type<GetCourseDetailsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GetCourseDetailsEventHandler handler) {
		handler.onHighlightCourse(this);

	}
}
