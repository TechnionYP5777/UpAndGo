package presenter.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class Webapp implements EntryPoint {
	private LayoutPanel p = new LayoutPanel();
	@Override
	public void onModuleLoad() {
		CourseSelectionGUI cc = new CourseSelectionGUI();
		p.add(cc);
		p.setWidgetRightWidth(cc, 1, Unit.EM, 20, Unit.PCT);
		p.setWidgetTopHeight(cc, 1, Unit.EM, 100, Unit.PCT);
		RootLayoutPanel.get().add(p);
	}

}
