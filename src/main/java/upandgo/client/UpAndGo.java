package upandgo.client;
import org.gwtbootstrap3.client.ui.Navbar;
import org.gwtbootstrap3.client.ui.NavbarBrand;
import org.gwtbootstrap3.client.ui.NavbarLink;
import org.gwtbootstrap3.client.ui.NavbarText;
import org.gwtbootstrap3.client.ui.constants.NavbarPosition;
import org.gwtbootstrap3.client.ui.constants.Pull;

import com.allen_sauer.gwt.log.client.Log;
import com.gargoylesoftware.htmlunit.javascript.host.SimpleArray;
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
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.event.shared.SimpleEventBus;

import upandgo.client.presenter.CourseListPresenter;

public class UpAndGo implements EntryPoint {
	
	public static SimpleEventBus eventBus = new SimpleEventBus();
	public static CoursesServiceAsync rpc = GWT.create(CoursesService.class);
	
	@Override
	public void onModuleLoad() {
		
		Log.warn("aaaaaaaaaaaaaaaaaaaaaaaa");
		
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
		NavBarGUI navBarView = new NavBarGUI();
		
		mainView.add(navBarView);
		mainView.setWidgetLeftRight(navBarView, 0, Unit.EM, 0, Unit.EM);
		mainView.setWidgetTopHeight(navBarView, 0, Unit.EM, 4, Unit.EM);

		mainView.add(courseSelectionView);
		mainView.setWidgetRightWidth(courseSelectionView, 1, Unit.EM, 20, Unit.PCT);
		mainView.setWidgetTopBottom(courseSelectionView, 4.5, Unit.EM, 1, Unit.EM);
		mainView.add(timeTableView);
		mainView.setWidgetLeftWidth(timeTableView, 1, Unit.EM, 77, Unit.PCT);
		mainView.setWidgetTopBottom(timeTableView, 4.5, Unit.EM, 10, Unit.EM);
		mainView.add(schedualerControlsView);
		mainView.setWidgetLeftWidth(schedualerControlsView, 1, Unit.EM, 77, Unit.PCT);
		mainView.setWidgetBottomHeight(schedualerControlsView, 6, Unit.EM, 3, Unit.EM);
		mainView.add(constraintsView);
		mainView.setWidgetLeftWidth(constraintsView, 1, Unit.EM, 77, Unit.PCT);
		mainView.setWidgetBottomHeight(constraintsView, 2, Unit.EM, 3, Unit.EM);
		
		Resources.INSTANCE.mainCss().ensureInjected();
		
		CourseListPresenter clp = new CourseListPresenter(rpc, eventBus, courseSelectionView);
		//clp.go(RootLayoutPanel.get());
		
		RootLayoutPanel.get().add(mainView);
	}

}
