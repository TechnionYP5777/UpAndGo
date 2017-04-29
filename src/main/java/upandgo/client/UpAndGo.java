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
		LayoutPanel container_cc = new LayoutPanel();
		container_cc.getElement().getStyle().setBackgroundColor("WhiteSmoke");
		container_cc.add(cc);
		t.getElement().getStyle().setMarginBottom(2, Unit.EM);
		p.add(container_cc);
		p.setWidgetRightWidth(container_cc, 1, Unit.EM, 20, Unit.PCT);
		p.setWidgetTopHeight(container_cc, 1, Unit.EM, 100, Unit.PCT);
		p.add(t);
		p.setWidgetLeftWidth(t, 1, Unit.EM, 78, Unit.PCT);
		p.setWidgetTopHeight(t, 1, Unit.EM, 100, Unit.PCT);
		RootLayoutPanel.get().add(p);
		Resources.INSTANCE.mainCss().ensureInjected();
	}

}
