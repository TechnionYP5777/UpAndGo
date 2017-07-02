package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * @author Omri Ben- Shmuel
 * @since 19-04-17
 * 
 * Event that happens when user request previous schedule, is handled by {@link clearScheduleEventHandler}.
 * 
 */


public class prevScheduleEvent extends GwtEvent<prevScheduleEventHandler>{
	public static Type<prevScheduleEventHandler> TYPE = new Type<>();
	
	@Override
	public Type<prevScheduleEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(prevScheduleEventHandler h) {
		h.onPrevSchedule(this);
		
	}
}


