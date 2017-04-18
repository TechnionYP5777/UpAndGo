package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;

import upandgo.shared.entities.course.CourseId;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-04-16
 * 
 * Event that happens when user highlights some course in table or list. Is handled by {@link HighlightCourseEventHandler}.
 * 
 */

public class HighlightCourseEvent extends GwtEvent<HighlightCourseEventHandler> {
	public static Type<HighlightCourseEventHandler> TYPE = new Type<>();
	private final CourseId id;

	public HighlightCourseEvent(CourseId id) {
	    this.id = id;
	  }

	public CourseId getId() {
		return id;
	}

	@Override
	public Type<HighlightCourseEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(HighlightCourseEventHandler handler) {
		handler.onHighlightCourse(this);

	}
}
