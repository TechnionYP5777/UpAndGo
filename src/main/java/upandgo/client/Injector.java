package upandgo.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import upandgo.client.presenter.CourseListPresenter;
import upandgo.client.presenter.NavBarPresenter;
import upandgo.client.presenter.SchedulerPresenter;

@GinModules(InjectorModule.class)
public interface Injector extends Ginjector {

	public static final Injector INSTANCE = GWT.create(Injector.class);
	
	
	
	public EventBus getEventBus();
	public NavBarPresenter.Display getNavBarPresenterDisplay();
	public CourseListPresenter.Display getCourseListPresenterDisplay();
	public SchedulerPresenter.Display getSchedulerPresenterDisplay();
	public CourseListPresenter getCourseListPresenter();
	public SchedulerPresenter getSchedulerPresenter();
	public AppController getAppController();
}
