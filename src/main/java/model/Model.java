package model;

import java.beans.PropertyChangeListener;

/**
 * 
 * @author Nikita Dizhur
 * @since 30-11-16
 * 
 * Interface that defines classes that are in charge of business logic and data storage.
 * 
 */
public interface Model {
	/*
	 * adds a PropertyChangeListener to the listener list for a specific
	 * property. here listeners are views.
	 */
	void addPropertyChangeListener(String property, PropertyChangeListener l);

	/*
	 * removes a PropertyChangeListener from the listener list for a specific
	 * property. here listeners are views.
	 */
	void removePropertyChangeListener(String property, PropertyChangeListener l);
}
