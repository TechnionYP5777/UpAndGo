package upandgo.client.presenter;

import com.google.web.bindery.event.shared.EventBus;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import upandgo.shared.entities.constraint.TimeConstraint;
import upandgo.shared.entities.course.CourseId;
import upandgo.shared.model.scedule.Schedule;

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
	
	private List<CourseId> selectedCourses;
	private List<TimeConstraint> constraintsList;
	Schedule schedule;
	
	public interface Display {
		
		public <T extends HasClickHandlers> T clearSchedule();
		public <T extends HasClickHandlers> T buildSchedule();
		public <T extends HasClickHandlers> T nextSchedule();
		public <T extends HasClickHandlers> T prevSchedule();
		public <T extends HasClickHandlers> T saveSchedule();
		public void setSchedule(Schedule schedule);
				
		public <T extends HasClickHandlers> T getDaysOffValue();
		public int isDayOffChecked(ClickEvent event); // 1- if selected. 0- if not
		
		public <T extends HasClickHandlers> T getMinWindowsValue();
		public int isMinWindowsChecked(ClickEvent event); // 1- if selected. 0- if not
		
		public <T extends HasClickHandlers> T getStartTimeValue();
		public int isStartTimeChecked(ClickEvent event);
		public String getReqStartTime(); // result in format HH:MM
		
		public <T extends HasClickHandlers> T getFinishTimeValue();
		public int isFinishTimeChecked(ClickEvent event);
		public String getReqFinishTime(); // result in format HH:MM
		
		public Widget asWidget();
	}
	
	public SchedulerPresenter(Display view, EventBus eventBus, CoursesServiceAsync rpc) {
		this.eventBus = eventBus; 
		this.view = view;
		this.rpcService = rpc;
		this.constraintsList = new ArrayList<>();
		this.selectedCourses = new ArrayList<>();
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
		
		view.buildSchedule().addClickHandler(new ClickHandler() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void onClick(ClickEvent event) {
				rpcService.getSchedule(selectedCourses, constraintsList, new AsyncCallback<Schedule>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error while building schedule.");
						Log.error("Error while building schedule.");
					}
						
					@Override
					public void onSuccess(Schedule result) {
						schedule = result;
						eventBus.fireEvent(new buildScheduleEvent());
					}
				});
			}
		});
		
		view.nextSchedule().addClickHandler(new ClickHandler() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void onClick(ClickEvent event) {
				rpcService.getNextSchedule(schedule , new AsyncCallback<Schedule>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error while retrieving next schedule.");
						Log.error("Error while retrieving next schedule.");
					}
						
					@Override
					public void onSuccess(Schedule result) {
						schedule = result;
						eventBus.fireEvent(new nextScheduleEvent());
					}
				});
			}
		});
		
		view.prevSchedule().addClickHandler(new ClickHandler() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void onClick(ClickEvent event) {
				rpcService.getPreviousSchedule(schedule , new AsyncCallback<Schedule>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error while retrieving previous schedule.");
						Log.error("Error while retrieving previous schedule.");
					}
						
					@Override
					public void onSuccess(Schedule result) {
						schedule = result;
						eventBus.fireEvent(new prevScheduleEvent());
					}
				});
			}
		});
		
		view.saveSchedule().addClickHandler(new ClickHandler() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new saveScheduleEvent());
			}
		});
	}

	@Override
	public void unbind() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void go(Panel panel) {
		bind();
		panel.clear();
		panel.add(view.asWidget());
	}
	
	public void setSelectedCourses(List<CourseId> selectedCourses) {
	    this.selectedCourses = selectedCourses;
	}
	
	public void setSelectedConstraints(List<TimeConstraint> constraintsList) {
	    this.constraintsList = constraintsList;
	}

}
