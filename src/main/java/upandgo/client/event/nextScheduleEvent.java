package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * 
 * @author Omri Ben- Shmuel
 * @since 19-04-17
 * 
 * Event that happens when user request next schedule, is handled by {@link clearScheduleEventHandler}.
 * 
 */


public class nextScheduleEvent extends GwtEvent<nextScheduleEventHandler>{
	public static Type<nextScheduleEventHandler> TYPE = new Type<>();
	
	@Override
	public Type<nextScheduleEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(nextScheduleEventHandler handler) {
		handler.onNextSchedule(this);
		
	}
}


