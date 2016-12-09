package model;

import java.awt.event.ActionListener;

/**
 * Interface for storing data inside the program.
 * The data typically should come from loader.
 * */
public interface Model {
	// TODO: implement
	// Maybe it will be good to use Observer pattern to notify views when the Model changes.
	// smth like:
	// addObservers(), notifyObservers()
	
	void addListener(ActionListener l);
}
