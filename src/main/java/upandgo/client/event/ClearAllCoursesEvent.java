package upandgo.client.event;

/**
 * 
 * @author danabra
 * @since 15-06-17
 * 
 * An event class fires when "clear courses" button is pressed
 * 
 */

import com.google.gwt.event.shared.GwtEvent;

public class ClearAllCoursesEvent extends GwtEvent<ClearAllCoursesEventHandler> {
	public static Type<ClearAllCoursesEventHandler> TYPE = new Type<>();

	public ClearAllCoursesEvent() {
	 }

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ClearAllCoursesEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ClearAllCoursesEventHandler h) {
		h.onClearAllCourses();
		
	}

}
