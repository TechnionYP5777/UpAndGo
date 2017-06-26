package upandgo.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * 
 * @author Yaniv Levinsky
 * 
 * Interface for handling the {@link ChangeSemesterEvent}.
 * 
 */

public interface ChangeSemesterEventHandler extends EventHandler {
	void onSemesterChange(ChangeSemesterEvent event);
}
