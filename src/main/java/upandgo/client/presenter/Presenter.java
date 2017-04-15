package upandgo.client.presenter;

import com.google.gwt.user.client.ui.Panel;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-04-16
 * 
 * A presenter contains all of the logic for application (usually including history management, view transition
 * and data sync via RPCs back to the server).
 * 
 * As a general rule, for every view you’ll want a presenter to drive the view and handle events that are sourced from the UI widgets within the view.
 * 
 */

public abstract interface Presenter {
	void bind();	// TODO: do I need this???
	void unbind();	// TODO: do I need this???
	public abstract void go(Panel panel);
}
