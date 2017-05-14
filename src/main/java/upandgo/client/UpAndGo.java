package upandgo.client;
import com.allen_sauer.gwt.log.client.Log;
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
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import upandgo.server.model.loader.XmlCourseLoader;



public class UpAndGo implements EntryPoint {

	
	@Override
	public void onModuleLoad() {
		
		Log.warn("aaaaaaaaaaaaaaaaaaaaaaaa");
		final Injector injector = Injector.INSTANCE;

		AppController appViewer = new AppController(GWT.create(CoursesService.class), injector.getEventBus());

		appViewer.go(RootLayoutPanel.get());
			
		
				
	}

}
