package upandgo.client.event;

/**
 * 
 * @author danabra
 * @since 15-06-17
 * 
 * A handler for ClearAllCoursesEvent
 * 
 */


import com.google.gwt.event.shared.EventHandler;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;

public interface ClearAllCoursesEventHandler extends EventHandler {
	void onClearAllCourses();

}
