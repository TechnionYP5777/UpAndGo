package upandgo.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import upandgo.client.presenter.CourseListPresenter;
import upandgo.client.presenter.NavBarPresenter;
import upandgo.client.presenter.SchedulerPresenter;
import upandgo.client.view.CourseSelectionView;
import upandgo.client.view.NavBarView;
import upandgo.client.view.SchedulerView;
import upandgo.shared.entities.Semester;

public class InjectorModule extends AbstractGinModule {
	
	@Provides
	public Semester provideDefaultSemester() {
	  return Semester.SPRING16;
	}

	@Override
	protected void configure() {
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(CourseListPresenter.Display.class).to(CourseSelectionView.class).in(Singleton.class);
		bind(SchedulerPresenter.Display.class).to(SchedulerView.class).in(Singleton.class);
		bind(NavBarPresenter.Display.class).to(NavBarView.class).in(Singleton.class);


	}

}
