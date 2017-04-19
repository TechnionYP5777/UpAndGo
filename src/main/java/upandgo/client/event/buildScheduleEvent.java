package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * 
 * @author Omri Ben- Shmuel
 * @since 19-04-17
 * 
 * Event that happens when user build schedule is handled by {@link buildScheduleEventHandler}.
 * 
 */


public class buildScheduleEvent extends GwtEvent<buildScheduleEventHandler>{
	public static Type<buildScheduleEventHandler> TYPE = new Type<>();
	
	@Override
	public Type<buildScheduleEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(buildScheduleEventHandler handler) {
		handler.onBuildSchedule(this);
		
	}
}

