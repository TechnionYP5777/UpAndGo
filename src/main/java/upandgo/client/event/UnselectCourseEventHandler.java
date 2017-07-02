package upandgo.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-04-16
 * 
 * Interface for handling the {@link UnselectCourseEvent}.
 * 
 */

public interface UnselectCourseEventHandler extends EventHandler {
	void onUnselectCourse(UnselectCourseEvent e);
}
