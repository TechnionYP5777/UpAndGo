package view;

import java.util.Observable;
import java.util.Observer;

public interface View extends Observer{
	// TODO: implement
	// I think GUI consists of these Views.
	// And maybe it will be good to use Composition Pattern for them.
	
	@Override
	void update(Observable obj, Object arg);
	
}
