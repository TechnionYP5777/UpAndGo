package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;

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
