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
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class UpAndGo implements EntryPoint {
	
	@Override
	public void onModuleLoad() {
		
		//not using appcontroller until rpc is implemented 
		/*CoursesServiceAsync service = GWT.create(CoursesServiceAsync.class);
		EventBus eventBus = new SimpleEventBus();

		AppController appViewer = new AppController(service, eventBus);

		appViewer.go(RootPanel.get());*/
		
		//Log.warn("message");
		
		LayoutPanel mainView = new LayoutPanel(); // needs to be injected
		CourseSelectionGUI courseSelectionView = new CourseSelectionGUI();// needs to be injected
		TimeTableGUI timeTableView = new TimeTableGUI();// needs to be injected
		SchedualerControlsGUI schedualerControlsView = new SchedualerControlsGUI();
		ConstraintsGUI constraintsView = new ConstraintsGUI();
		
		timeTableView.getElement().getStyle().setMarginBottom(2, Unit.EM);
		mainView.add(courseSelectionView);
		mainView.setWidgetRightWidth(courseSelectionView, 1, Unit.EM, 20, Unit.PCT);
		mainView.setWidgetTopHeight(courseSelectionView, 1, Unit.EM, 100, Unit.PCT);
		mainView.add(timeTableView);
		mainView.setWidgetLeftWidth(timeTableView, 1, Unit.EM, 77, Unit.PCT);
		mainView.setWidgetTopBottom(timeTableView, 1, Unit.EM, 10, Unit.EM);
		mainView.add(schedualerControlsView);
		mainView.setWidgetLeftWidth(schedualerControlsView, 1, Unit.EM, 77, Unit.PCT);
		mainView.setWidgetBottomHeight(schedualerControlsView, 6, Unit.EM, 3, Unit.EM);
		mainView.add(constraintsView);
		mainView.setWidgetLeftWidth(constraintsView, 1, Unit.EM, 77, Unit.PCT);
		mainView.setWidgetBottomHeight(constraintsView, 2, Unit.EM, 3, Unit.EM);
		
		Resources.INSTANCE.mainCss().ensureInjected();
		
		RootLayoutPanel.get().add(mainView);
	}

}
