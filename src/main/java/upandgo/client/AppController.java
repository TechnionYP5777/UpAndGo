package upandgo.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;

import upandgo.client.event.GetCourseDetailsEvent;
import upandgo.client.event.GetCourseDetailsEventHandler;
import upandgo.client.event.SelectCourseEvent;
import upandgo.client.event.SelectCourseEventHandler;
import upandgo.client.event.UnselectCourseEvent;
import upandgo.client.event.UnselectCourseEventHandler;
import upandgo.client.event.buildScheduleEvent;
import upandgo.client.event.buildScheduleEventHandler;
import upandgo.client.event.clearScheduleEvent;
import upandgo.client.event.clearScheduleEventHandler;
import upandgo.client.event.nextScheduleEvent;
import upandgo.client.event.nextScheduleEventHandler;
import upandgo.client.event.prevScheduleEvent;
import upandgo.client.event.prevScheduleEventHandler;
import upandgo.client.event.saveScheduleEvent;
import upandgo.client.event.saveScheduleEventHandler;
import upandgo.client.presenter.CourseListPresenter;
import upandgo.client.presenter.NavBarPresenter;
import upandgo.client.presenter.Presenter;
import upandgo.client.presenter.SchedulerPresenter;
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
		eventBus.addHandler(buildScheduleEvent.TYPE, new buildScheduleEventHandler() {

			@Override
			public void onBuildSchedule(@SuppressWarnings("unused") buildScheduleEvent e) {
				Log.info("Build Schedule Event");
				
			}
		});

		eventBus.addHandler(clearScheduleEvent.TYPE, new clearScheduleEventHandler() {

			@Override
			public void onClearSchedule(@SuppressWarnings("unused") clearScheduleEvent e) {
				Log.info("Clear Schedule Event");
				
			}
		});

		eventBus.addHandler(GetCourseDetailsEvent.TYPE, new GetCourseDetailsEventHandler() {

			@Override
			public void onHighlightCourse(@SuppressWarnings("unused") GetCourseDetailsEvent e) {//need to be implemented with DI
				
			}
		});

		eventBus.addHandler(nextScheduleEvent.TYPE, new nextScheduleEventHandler() {

			@Override
			public void onNextSchedule(@SuppressWarnings("unused") nextScheduleEvent e) {
				Log.info("Next Schedule Event");
				
			}
		});
		
		eventBus.addHandler(prevScheduleEvent.TYPE, new prevScheduleEventHandler() {

			@Override
			public void onPrevSchedule(@SuppressWarnings("unused") prevScheduleEvent e) {
				Log.info("Previous Schedule Event");
				
			}
		});

		eventBus.addHandler(saveScheduleEvent.TYPE, new saveScheduleEventHandler() {

			@Override
			public void onSaveSchedule(@SuppressWarnings("unused") saveScheduleEvent e) {
				Log.info("Save Schedule Event");
				
			}
		});

		eventBus.addHandler(SelectCourseEvent.TYPE, new SelectCourseEventHandler() {

			@Override
			public void onSelectCourse(SelectCourseEvent e) {
				Log.info("Course " + e.getId().getTitle() + " Selected");
				
			}
		});

		eventBus.addHandler(UnselectCourseEvent.TYPE, new UnselectCourseEventHandler() {

			@Override
			public void onUnselectCourse(UnselectCourseEvent e) {
				Log.info("Course " + e.getId().getTitle() + " Unselected");
				
			}
		});
	}

	@Override
	public void unbind() {
		// Auto-generated method stub

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
