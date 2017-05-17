package upandgo.client.presenter;

import com.google.gwt.event.shared.EventBus;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.common.collect.Lists;
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
import upandgo.client.event.clearScheduleEvent;
import upandgo.server.logic.Scheduler;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.Timetable;

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
	
	private List<Course> selectedCourses;
	protected List<List<LessonGroup>> lessonGroupsList;
	protected int sched_index;
	
	public interface Display {
		
		public <T extends HasClickHandlers> T clearSchedule();
		public <T extends HasClickHandlers> T buildSchedule();
		public <T extends HasClickHandlers> T nextSchedule();
		public <T extends HasClickHandlers> T prevSchedule();
		public <T extends HasClickHandlers> T saveSchedule();
		
		public void setSchedule(List<LessonGroup> schedule); // if (schedule = null) then clear schedule
				
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
		this.selectedCourses = new ArrayList<>();
		lessonGroupsList = new ArrayList<>();
		sched_index = 0;
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
			@Override
			public void onClick(ClickEvent event) {
				rpcService.getChosenCoursesList(new AsyncCallback<List<Course>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error while retrieving selected courses.");
						Log.error("Error while retrieving selected courses.");
					}
					@Override
					public void onSuccess(List<Course> result) {
						if (result.isEmpty()) {
							view.setSchedule(null);
							return;
						}
						selectedCourses = new ArrayList<Course>(result);
						final List<Timetable> tables = Lists.newArrayList(Scheduler.sortedBy(Scheduler.getTimetablesList(result, null),
								isDaysoffCount, isBlankSpaceCount, minStartTime, maxEndTime));
						if (tables.isEmpty()) {
							Window.alert("Error - There are no possible schedule.");
							Log.error("Error - There are no possible schedule.");
						} else {
							lessonGroupsList.clear();
							sched_index = 0;
							tables.forEach(λ -> lessonGroupsList.add(λ.getLessonGroups()));
							Log.info("A schedule was build");
							view.setSchedule(lessonGroupsList.get(sched_index));
						}
					}
				}); 
			}
		});
				
		view.nextSchedule().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (lessonGroupsList.size() <= sched_index + 1) {
					return;
				}
				++sched_index;
				Log.info("Next schedule was requested");
				view.setSchedule(lessonGroupsList.get(sched_index));
			}
		});
		
		view.prevSchedule().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (sched_index <= 0) {
					return;
				}
				--sched_index;
				Log.info("Previous schedule was requested");
				view.setSchedule(lessonGroupsList.get(sched_index));
			}
		});
		
		view.saveSchedule().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				rpcService.saveSchedule(lessonGroupsList.get(sched_index), new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error while saving schedule.");
						Log.error("Error while saving schedule.");
					}
					@Override
					public void onSuccess(Void result) {
						Log.info("schedule was saved successfully");
					}
				});
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
	
}
