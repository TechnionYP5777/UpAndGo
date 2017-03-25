package view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

/**
 * 
 * @author Nikita Dizhur
 * @since 30-11-2016
 * 
 * Interface that defines classes that are in charge of UI.
 * 
 */
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
