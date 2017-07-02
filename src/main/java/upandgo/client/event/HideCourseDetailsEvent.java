package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * @author Nikita Dizhur
 * @since 25-04-16
 * 
 * Event that happens when user wants to stop seeing course details. Is handled by {@link HideCourseDetailsEventHandler}.
 * 
 */

public class HideCourseDetailsEvent extends GwtEvent<HideCourseDetailsEventHandler> {
	public static Type<HideCourseDetailsEventHandler> TYPE = new Type<>();

	public HideCourseDetailsEvent() {
	}

	@Override
	public Type<HideCourseDetailsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(HideCourseDetailsEventHandler h) {
		h.onHideCourseDetails(this);

	}
}
