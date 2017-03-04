package view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public interface View extends PropertyChangeListener {

	/*
	 * add controller as listener
	 */
	void addActionListener(ActionListener l);

	/*
	 * unsubscribe from controller
	 */
	void removeActionListener(ActionListener l);
}
