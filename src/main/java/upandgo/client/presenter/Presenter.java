package upandgo.client.presenter;

import com.google.gwt.user.client.ui.LayoutPanel;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-04-17
 * 
 * A presenter contains all of the logic for application (usually including history management, view transition
 * and data sync via RPCs back to the server).
 * 
 * As a general rule, for every view you’ll want a presenter to drive the view and handle events that are sourced from the UI widgets within the view.
 * 
 */

public abstract interface Presenter {
	void bind();
	void unbind();
	public abstract void go(LayoutPanel p);
}
