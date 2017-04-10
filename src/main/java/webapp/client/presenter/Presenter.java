package webapp.client.presenter;

import com.google.gwt.user.client.ui.Panel;

public abstract interface Presenter {
	void bind();	// TODO: do I need this???
	void unbind();	// TODO: do I need this???
	public abstract void go(Panel panel);
}
