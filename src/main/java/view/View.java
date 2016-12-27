package view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public interface View extends PropertyChangeListener {

	/*
	 * add controller as listener
	 */
	void addPropertyChangeListener(ActionListener l);
	
	/*
	 * unsubscribe from controller
	 */
	void removePropertyChangeListener(String property, ActionListener l);
}
