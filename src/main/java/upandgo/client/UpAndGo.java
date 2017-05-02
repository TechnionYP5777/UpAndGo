package upandgo.client;
/**
 * 
 * @author danabra
 * @since 02-05-17
 * 
 * THis code is first to execute when the application is loaded
 * 
 */
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.RootPanel;

public class UpAndGo implements EntryPoint {
	
	@Override
	public void onModuleLoad() {
		CoursesServiceAsync service = GWT.create(CoursesServiceAsync.class);
		EventBus eventBus = new SimpleEventBus();

		AppController appViewer = new AppController(service, eventBus);

		appViewer.go(RootPanel.get());
	}

}
