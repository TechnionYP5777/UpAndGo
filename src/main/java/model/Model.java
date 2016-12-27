package model;

import java.beans.PropertyChangeListener;

public interface Model {
	/*
	 * Adds a PropertyChangeListener to the listener list for a specific property
	 */
	void addPropertyChangeListener(String property, PropertyChangeListener l);
	
	/* 
	 * Removes a PropertyChangeListener from the listener list for a specific property
	 */
	void removePropertyChangeListener(String property, PropertyChangeListener l);
}
