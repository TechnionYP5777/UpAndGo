package upandgo.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * 
 * @author Nikita Dizhur
 * @since 25-04-16
 * 
 * Interface for handling the {@link HideCourseDetailsEvent}.
 * 
 */

public interface HideCourseDetailsEventHandler extends EventHandler {
	void onHideCourseDetails(HideCourseDetailsEvent event);
}
