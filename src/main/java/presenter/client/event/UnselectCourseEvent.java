package presenter.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class UnselectCourseEvent extends GwtEvent<UnselectCourseEventHandler> {
	public static Type<UnselectCourseEventHandler> TYPE = new Type<>();
	private final String idOrName;

	public UnselectCourseEvent(String idOrName) {
	    this.idOrName = idOrName;
	  }

	public String getIdOrName() {
		return idOrName;
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
