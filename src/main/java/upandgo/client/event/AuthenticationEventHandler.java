package upandgo.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * 
 * @author Nikita Dizhur
 * @since 11-06-17
 * 
 * Interface for handling the {@link AuthenticationEven}.
 * 
 */

public interface AuthenticationEventHandler extends EventHandler {
	void onAuthenticationChanged(AuthenticationEvent e);
}
