package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * 
 * @author Omri Ben- Shmuel
 * @since 19-04-17
 * 
 * Event that happens when user clear the schedule is handled by {@link clearScheduleEventHandler}.
 * 
 */


public class saveScheduleEvent extends GwtEvent<saveScheduleEventHandler>{
	public static Type<saveScheduleEventHandler> TYPE = new Type<>();
	
	@Override
	public Type<saveScheduleEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(saveScheduleEventHandler handler) {
		handler.onSaveSchedule(this);
		
	}
}

