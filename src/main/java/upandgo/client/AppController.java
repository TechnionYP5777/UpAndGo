package upandgo.client;

import com.google.gwt.user.client.ui.Panel;
import com.google.web.bindery.event.shared.EventBus;

import upandgo.client.event.HighlightCourseEvent;
import upandgo.client.event.HighlightCourseEventHandler;
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
import upandgo.client.presenter.Presenter;

/**
 * 
 * @author Nikita Dizhur
 * @since 22-04-17
 * 
 *        A class that creates views, presenters, history and bind them all
 *        together with event bus and rpc .
 * 
 */

class AppController implements Presenter {
	
	Panel panel;

	private EventBus eventBus;
	private CoursesServiceAsync rpcService;

	public AppController(CoursesServiceAsync rpcService, EventBus eventBus) {
		this.eventBus = eventBus;
		this.rpcService = rpcService;
		bind();
	}

	@Override
	public void bind() {
		eventBus.addHandler(buildScheduleEvent.TYPE, new buildScheduleEventHandler() {

			@Override
			public void onBuildSchedule(@SuppressWarnings("unused") buildScheduleEvent event) {
				// TODO Auto-generated method stub
				
			}
		});

		eventBus.addHandler(clearScheduleEvent.TYPE, new clearScheduleEventHandler() {

			@Override
			public void onClearSchedule(@SuppressWarnings("unused") clearScheduleEvent event) {
				// TODO Auto-generated method stub
				
			}
		});

		eventBus.addHandler(HighlightCourseEvent.TYPE, new HighlightCourseEventHandler() {

			@Override
			public void onHighlightCourse(@SuppressWarnings("unused") HighlightCourseEvent event) {
				// TODO Auto-generated method stub
				
			}
		});

		eventBus.addHandler(nextScheduleEvent.TYPE, new nextScheduleEventHandler() {

			@Override
			public void onNextSchedule(@SuppressWarnings("unused") nextScheduleEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		
		eventBus.addHandler(prevScheduleEvent.TYPE, new prevScheduleEventHandler() {

			@Override
			public void onPrevSchedule(@SuppressWarnings("unused") prevScheduleEvent event) {
				// TODO Auto-generated method stub
				
			}
		});

		eventBus.addHandler(saveScheduleEvent.TYPE, new saveScheduleEventHandler() {

			@Override
			public void onSaveSchedule(@SuppressWarnings("unused") saveScheduleEvent event) {
				// TODO Auto-generated method stub
				
			}
		});

		eventBus.addHandler(SelectCourseEvent.TYPE, new SelectCourseEventHandler() {

			@Override
			public void onSelectCourse(@SuppressWarnings("unused") SelectCourseEvent event) {
				// TODO Auto-generated method stub
				
			}
		});

		eventBus.addHandler(UnselectCourseEvent.TYPE, new UnselectCourseEventHandler() {

			@Override
			public void onUnselectCourse(@SuppressWarnings("unused") UnselectCourseEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void unbind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void go(Panel panel) {
		// TODO Auto-generated method stub
		this.panel = panel;

	}

}
