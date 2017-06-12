package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;

import upandgo.shared.entities.course.CourseId;

/**
 * 
 * @author Nikita Dizhur
 * @since 11-06-17
 * 
 * Event that happens when user signs in. Is handled by {@link AuthenticationEventHandler}.
 * 
 */

public class AuthenticationEvent extends GwtEvent<AuthenticationEventHandler> {
	public static Type<AuthenticationEventHandler> TYPE = new Type<>();
	private final boolean isSignedIn;

	public AuthenticationEvent(boolean isSignedIn) {
	    this.isSignedIn = isSignedIn;
	  }

	public boolean isSignedIn() {
		return isSignedIn;
	}
	@Override
	public Type<AuthenticationEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AuthenticationEventHandler handler) {
		handler.onAuthenticationChanged(this);
		
	}
}
