package controller;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

/**
 * It's the main class that speaks with View-part of the program.
 * It interacts with UI and updates Models.
 * 
 * The GUI parts which do not update when the model changes - are the responsibility of the controller.
 * When you try to modify a field in the view, the controller needs to pick up the editing event,
 * process it, and send it to the model; the model will then update the view if/when the value actually changes.
 * In many frameworks, the controller appears as a collection of methods and listeners built into both
 * the data model and the view.
 * */
public interface Controller extends ActionListener {
	void init();
	
	void registerListenerToProperty(PropertyChangeListener l, String p);
	
	void unregisterListenerToProperty(PropertyChangeListener l, String p);
}
