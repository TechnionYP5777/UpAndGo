package upandgo.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class UpAndGo implements EntryPoint {
	private LayoutPanel p = new LayoutPanel();
	@Override
	public void onModuleLoad() {
		CourseSelectionGUI cc = new CourseSelectionGUI();
		TimeTableGUI t = new TimeTableGUI();
		cc.getElement().getStyle().setBackgroundColor("WhiteSmoke");
		p.add(cc);
		p.setWidgetRightWidth(cc, 1, Unit.EM, 20, Unit.PCT);
		p.setWidgetTopHeight(cc, 1, Unit.EM, 100, Unit.PCT);
		p.add(t);
		p.setWidgetLeftWidth(t, 1, Unit.EM, 70, Unit.PCT);
		p.setWidgetTopHeight(t, 1, Unit.EM, 100, Unit.PCT);
		RootLayoutPanel.get().add(p);
		Resources.INSTANCE.mainCss().ensureInjected();
	}

}
