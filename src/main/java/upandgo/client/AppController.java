package upandgo.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.gwt.event.shared.EventBus;

import upandgo.client.event.SelectCourseEvent;
import upandgo.client.event.SelectCourseEventHandler;
import upandgo.client.event.UnselectCourseEvent;
import upandgo.client.event.UnselectCourseEventHandler;
import upandgo.client.presenter.CourseListPresenter;
import upandgo.client.presenter.NavBarPresenter;
import upandgo.client.presenter.Presenter;
import upandgo.client.presenter.SchedulerPresenter;
import upandgo.client.resources.Resources;
import upandgo.client.view.NavBarView;
import upandgo.shared.entities.Semester;

/**
 * 
 * @author Nikita Dizhur and danabra
 * @since 22-04-17
 * 
 *        A class that creates views, presenters, history and bind them all
 *        together with event bus and rpc .
 * 
 */

class AppController implements Presenter {
	
	Panel panel;

	private EventBus eventBus;
	private CoursesServiceAsync coursesService;
	private LoginServiceAsync loginService;
	private LayoutPanel mainView;
		
	@Inject
	public AppController(CoursesServiceAsync coursesService, LoginServiceAsync loginService, EventBus eventBus) {
		this.eventBus = eventBus;
		this.coursesService = coursesService;
		this.loginService = loginService;
		
		initMainView();
	}

	@Override
	public void bind() {

		eventBus.addHandler(SelectCourseEvent.TYPE, new SelectCourseEventHandler() {

			@Override
			public void onSelectCourse(SelectCourseEvent event) {
				Log.info("SelectCourseEvent: Course " + event.getId().getTitle() + " Selected");
				
			}
		});

		eventBus.addHandler(UnselectCourseEvent.TYPE, new UnselectCourseEventHandler() {

			@Override
			public void onUnselectCourse(UnselectCourseEvent event) {
				Log.info("UnselectCourseEvent: Course " + event.getId().getTitle() + " Unselected");
				
			}
		});
	}

	@Override
	public void unbind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void go(LayoutPanel pnl) {
		bind();
		
		panel = pnl;
		panel.add(mainView);

	}
	


	public void initMainView(){
		final Injector injector = Injector.INSTANCE;
		mainView = new LayoutPanel();		
		
		NavBarPresenter.Display navBarView = injector.getNavBarPresenterDisplay();
		CourseListPresenter.Display courseSelectionView = injector.getCourseListPresenterDisplay();
		SchedulerPresenter.Display schedualerView = injector.getSchedulerPresenterDisplay();
		Semester defaultSemester = Semester.WINTER17;
		
		NavBarPresenter nbPresenter = new NavBarPresenter(loginService, coursesService, eventBus, navBarView, defaultSemester);
		
		CourseListPresenter clPresenter = new CourseListPresenter(coursesService,eventBus,courseSelectionView, defaultSemester);
		
		SchedulerPresenter sPresenter = new SchedulerPresenter(schedualerView, eventBus, coursesService, defaultSemester);
		
		Resources.INSTANCE.mainStyle().ensureInjected();
		
		nbPresenter.go(mainView);
		clPresenter.go(mainView);
		sPresenter.go(mainView);
			
		
	}
}
