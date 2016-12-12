package view;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public abstract class View extends Observable implements Observer {
	// TODO: implement
	// I think GUI consists of these Views.
	// And maybe it will be good to use Composition Pattern for them.
	
	protected List<Observer> listenersList;
	
	@Override
	public abstract void update(Observable obj, Object arg);
	
	@Override
	public synchronized void addObserver(Observer ¢) {
		if (¢ == null)
			throw new NullPointerException();
		this.listenersList.add(¢);
	}
}
