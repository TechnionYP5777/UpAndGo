package upandgo.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-04-16
 * 
 * Interface for handling the {@link GetCourseDetailsEvent}.
 * 
 */

public interface GetCourseDetailsEventHandler extends EventHandler {
	void onHighlightCourse(GetCourseDetailsEvent event);
}
