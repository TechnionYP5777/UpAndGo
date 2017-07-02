package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * @author Omri Ben- Shmuel
 * @since 19-04-17
 * 
 * Event that happens when user clear the schedule is handled by {@link clearScheduleEventHandler}.
 * 
 */


public class clearScheduleEvent extends GwtEvent<clearScheduleEventHandler>{
	public static Type<clearScheduleEventHandler> TYPE = new Type<>();
	
	@Override
	public Type<clearScheduleEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(clearScheduleEventHandler h) {
		h.onClearSchedule(this);
		
	}
}
