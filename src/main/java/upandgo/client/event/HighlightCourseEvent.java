package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class HighlightCourseEvent extends GwtEvent<HighlightCourseEventHandler> {
	public static Type<HighlightCourseEventHandler> TYPE = new Type<>();
	private final String idOrName;

	public HighlightCourseEvent(String idOrName) {
	    this.idOrName = idOrName;
	  }

	public String getIdOrName() {
		return idOrName;
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
