package upandgo.client.presenter;

import com.google.gwt.event.shared.EventBus;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import upandgo.client.CoursesServiceAsync;
import upandgo.client.event.UnselectCourseEvent;
import upandgo.client.event.buildScheduleEvent;
import upandgo.client.event.clearScheduleEvent;
import upandgo.client.event.nextScheduleEvent;
import upandgo.client.event.prevScheduleEvent;
import upandgo.client.event.saveScheduleEvent;
import upandgo.client.view.CourseListView;
import upandgo.shared.entities.LocalTime;
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
	
	//constraints fields
	protected boolean isDaysoffCount;
	protected boolean isBlankSpaceCount;
	protected LocalTime minStartTime;
	protected LocalTime maxEndTime;
	
	private List<CourseId> selectedCourses;
	private List<TimeConstraint> constraintsList;
	Schedule schedule;
	
	public interface Display {
		
		public <T extends HasClickHandlers> T clearSchedule();
		public <T extends HasClickHandlers> T buildSchedule();
		public <T extends HasClickHandlers> T nextSchedule();
		public <T extends HasClickHandlers> T prevSchedule();
		public <T extends HasClickHandlers> T saveSchedule();
		
		public void setSchedule(Schedule schedule); // if (schedule = null) then clear schedule
				
		public <T extends HasClickHandlers> T getDaysOffValue();
		public int isDayOffChecked(ClickEvent event); // 1- if selected. 0- if not
		
		public <T extends HasClickHandlers> T getMinWindowsValue();
		public int isMinWindowsChecked(ClickEvent event); // 1- if selected. 0- if not
		
		public <T extends HasClickHandlers> T getStartTimeValue();
		public int isStartTimeChecked(ClickEvent event);
		public LocalTime getReqStartTime(); // result in format HH:MM
		
		public <T extends HasClickHandlers> T getFinishTimeValue();
		public int isFinishTimeChecked(ClickEvent event);
		public LocalTime getReqFinishTime(); // result in format HH:MM
		
		public Widget asWidget();
	}
	@Inject
	public SchedulerPresenter(Display view, EventBus eventBus, CoursesServiceAsync rpc) {
		this.eventBus = eventBus; 
		this.view = view;
		this.rpcService = rpc;
		this.isBlankSpaceCount = this.isDaysoffCount = false;
		this.minStartTime = null;
		this.maxEndTime = null;
		this.constraintsList = new ArrayList<>();
		this.selectedCourses = new ArrayList<>();
		this.schedule = new Schedule();
		bind();
	}
	
	
	@Override
	public void bind() {
		
		view.getDaysOffValue().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int res = view.isDayOffChecked(event);
				if (res == 1) {
					isDaysoffCount = true;
					Log.info("daysOff button was selected");
				} else {
					isDaysoffCount = false;
					Log.info("daysOff button was deselected");
				}
			}
		});	
		
		view.getMinWindowsValue().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int res = view.isMinWindowsChecked(event);
				if (res == 1) {
					isBlankSpaceCount = true;
					Log.info("minWindows button was selected");
				} else {
					isBlankSpaceCount = false;
					Log.info("minWindows button was deselected");
				}
				
			}
		});
		
		view.getStartTimeValue().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int res = view.isStartTimeChecked(event);
				if (res == 1) {
					minStartTime = view.getReqStartTime();
					Log.info("Start time value was selected");
				} else {
					minStartTime = null;
					Log.info("Start time value was deselected");
				}
			}
		});
		
		view.getFinishTimeValue().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int res = view.isFinishTimeChecked(event);
				if (res == 1) {
					maxEndTime = view.getReqFinishTime();
					Log.info("End time value was selected");
				} else {
					maxEndTime = null;
					Log.info("Start time value was deselected");
				}
			}
		});
		
		view.clearSchedule().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				view.setSchedule(null);
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
	public void go(LayoutPanel panel) {
		bind();
		//panel.clear();
		panel.add(view.asWidget());
		panel.setWidgetLeftWidth(view.asWidget(), 1, Unit.EM, 77, Unit.PCT);
		panel.setWidgetTopBottom(view.asWidget(), 4.5, Unit.EM, 1, Unit.EM);
	}
	
	public void setSelectedCourses(List<CourseId> selectedCourses) {
	    this.selectedCourses = selectedCourses;
	}
	
	public void setSelectedConstraints(List<TimeConstraint> constraintsList) {
	    this.constraintsList = constraintsList;
	}

}
