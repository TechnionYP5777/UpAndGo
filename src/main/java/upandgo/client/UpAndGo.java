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
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;


public class UpAndGo implements EntryPoint {

	
	@Override
	public void onModuleLoad() {
		final Injector injector = Injector.INSTANCE;

		AppController appViewer = new AppController((CoursesServiceAsync)GWT.create(CoursesService.class), injector.getEventBus());

		appViewer.go(RootLayoutPanel.get());
			
		
				
	}

}
