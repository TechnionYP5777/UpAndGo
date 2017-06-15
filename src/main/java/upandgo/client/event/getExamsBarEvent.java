package upandgo.client.event;

/**
 * 
 * @author danabra
 * @since 15-06-17
 * 
 * An event class that helps send and receive ExamsBar
 * 
 */

import com.google.gwt.event.shared.GwtEvent;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;

public class getExamsBarEvent extends GwtEvent<getExamsBarEventHandler> {
	public static Type<getExamsBarEventHandler> TYPE = new Type<>();
	private final ScrollPanel examsBar;

	public getExamsBarEvent(ScrollPanel examsBar) {
	    this.examsBar = examsBar;
	  }

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<getExamsBarEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(getExamsBarEventHandler handler) {
		handler.getExamsBar(examsBar);
		
	}

}
