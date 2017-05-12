package upandgo.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.AbstractModule;

import upandgo.client.presenter.CourseListPresenter;
import upandgo.client.presenter.SchedulerPresenter;

public class PresentersModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(EventBus.class).to(SimpleEventBus.class);
		bind(CourseListPresenter.Display.class).to(CourseSelectionView.class);
		bind(SchedulerPresenter.Display.class).to(SchedualerView.class);

	}

}
