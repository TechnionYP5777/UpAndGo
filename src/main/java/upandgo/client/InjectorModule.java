package upandgo.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import upandgo.client.presenter.CourseListPresenter;
import upandgo.client.presenter.SchedulerPresenter;
import upandgo.client.view.CourseSelectionView;
import upandgo.client.view.SchedualerView;

public class InjectorModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(CourseListPresenter.Display.class).to(CourseSelectionView.class).in(Singleton.class);
		bind(SchedulerPresenter.Display.class).to(SchedualerView.class);

	}

}
