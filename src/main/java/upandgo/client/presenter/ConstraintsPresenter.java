package upandgo.client.presenter;

import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.HasMouseMoveHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

import upandgo.client.CoursesServiceAsync;
import upandgo.client.presenter.SchedulerPresenter.Display;

public class ConstraintsPresenter implements Presenter {
	
	private final Display view;
	private final EventBus eventBus;
		
	public interface Display {
		public boolean isDayOffChecked();
		public boolean isMinWindowsChecked();
		public boolean isStartTimeChecked();
		public boolean isFinishTimeChecked();
		public String getReqStartTime(); // result in format HH:MM
		public String getReqFinishTime(); // result in format HH:MM
		public Widget asWidget();
	}
	
	public ConstraintsPresenter(Display view, EventBus eventBus) {
		this.eventBus = eventBus; 
		this.view = view;
	}
	
	@Override
	public void bind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unbind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void go(Panel panel) {
		// TODO Auto-generated method stub

	}

}
