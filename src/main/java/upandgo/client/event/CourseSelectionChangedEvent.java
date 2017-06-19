package upandgo.client.event;

/**
 * 
 * @author danabra
 * @since 15-06-17
 * 
 * An event class that helps send and receive ExamsBar
 * 
 */

import com.google.gwt.event.shared.GwtEvent;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;

public class CourseSelectionChangedEvent extends GwtEvent<CourseSelectionChangedEventHandler> {
	public static Type<CourseSelectionChangedEventHandler> TYPE = new Type<>();

	public CourseSelectionChangedEvent() {
	  }

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CourseSelectionChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CourseSelectionChangedEventHandler handler) {
		handler.go();
		
	}

}
