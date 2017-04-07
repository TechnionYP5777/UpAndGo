package presenter.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class Webapp implements EntryPoint {
	private HorizontalPanel p = new HorizontalPanel();
	@Override
	public void onModuleLoad() {
		p.add(new CourseSelectionGUI());
		RootPanel.get().add(p);
	}

}
