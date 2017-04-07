package presenter.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Webapp implements EntryPoint {
	private Label l = new Label("Hello World!!!");
	private Label l2 = new Label("Hello World!!!");
	private HorizontalPanel p = new HorizontalPanel();
	@Override
	public void onModuleLoad() {
		p.add(l);
		p.add(l2);
		RootPanel.get().add(p);
	}

}
