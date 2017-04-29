package upandgo.client.presenter;

import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import upandgo.client.CoursesServiceAsync;
import upandgo.client.event.UnselectCourseEvent;
import upandgo.client.event.buildScheduleEvent;
import upandgo.client.event.clearScheduleEvent;
import upandgo.client.event.nextScheduleEvent;
import upandgo.client.event.prevScheduleEvent;
import upandgo.client.event.saveScheduleEvent;
import upandgo.client.view.CourseListView;

/**
 * 
 * @author Omri Ben Shmuel
 * @since 18-04-17
 * 
 * A concrete presenter for {@link schedulerView}.
 * 
 */

public class SchedulerPresenter implements Presenter {
	
	private final Display view;
	private final EventBus eventBus;
	CoursesServiceAsync rpcService;
	
	public interface Display {
		public <T extends HasClickHandlers> T clearSchedule();
		public void nextSchedule();
		public void prevSchedule();
		public void buildSchedule();
		public void saveSchedule();
		public Widget asWidget();
	}
	
	public SchedulerPresenter(Display view, EventBus eventBus, CoursesServiceAsync rpc) {
		this.eventBus = eventBus; 
		this.view = view;
		this.rpcService = rpc;
		bind();
	}
	
	
	@Override
	public void bind() {

		view.clearSchedule().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new clearScheduleEvent());
			}
		});

	}

	@Override
	public void unbind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void go(Panel panel) {
		panel.clear();
		panel.add(view.asWidget());
	}
	
	public void onClearSchedule() {
		eventBus.fireEvent(new clearScheduleEvent());
		this.view.clearSchedule();
	}
	
	public void onNextSchedule() {
		eventBus.fireEvent(new nextScheduleEvent());
		this.view.nextSchedule();
	}
	
	public void onPrevSchedule() {
		eventBus.fireEvent(new prevScheduleEvent());
		this.view.prevSchedule();
	}
	
	public void onBuildSchedule() {
		eventBus.fireEvent(new buildScheduleEvent());
		this.view.buildSchedule();
	}
	
	public void onSaveSchedule() {
		eventBus.fireEvent(new saveScheduleEvent());
		this.view.saveSchedule();
	}

}
