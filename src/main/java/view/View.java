package view;

import java.awt.event.ActionListener;

public interface View {
	// TODO: implement
	// I think GUI consists of these Views.
	// And maybe it will be good to use Composition Pattern for them.
	
	void addListener(ActionListener l);
	
}
