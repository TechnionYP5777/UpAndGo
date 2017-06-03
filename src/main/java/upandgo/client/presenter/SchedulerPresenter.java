package upandgo.client.presenter;

import com.google.gwt.event.shared.EventBus;

import upandgo.shared.entities.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import java.util.function.Consumer;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import upandgo.client.CoursesServiceAsync;
import upandgo.client.event.clearScheduleEvent;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.Scheduler;
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
	protected LocalTime maxFinishTime;
	
	private List<Course> selectedCourses;
	protected List<List<LessonGroup>> lessonGroupsList;
	protected int sched_index;
	
	public interface Display {
		
		public HasClickHandlers clearSchedule();
		public HasClickHandlers buildSchedule();
		public HasClickHandlers nextSchedule();
		public HasClickHandlers prevSchedule();
		public HasClickHandlers saveSchedule();
		
		public void setSchedule(List<LessonGroup> schedule); // if (schedule = null) then clear schedule
				
		public HasClickHandlers getDaysOffElement();
		public boolean isDayOffChecked(ClickEvent event); 
		
		public HasClickHandlers getMinWindowsElement();
		public boolean isMinWindowsChecked(ClickEvent event); 
		
		public HasClickHandlers getStartTimeElement();
		public HasChangeHandlers getStartTimeList();
		public boolean isStartTimeChecked(ClickEvent event);
		public LocalTime getReqStartTime(); // result in format HH:MM
		
		public HasClickHandlers getFinishTimeElement();
		public HasChangeHandlers getFinishTimeList();
		public boolean isFinishTimeChecked(ClickEvent event);
		public LocalTime getReqFinishTime(); // result in format HH:MM
		
		public Widget getAsWidget();
	}
	@Inject
	public SchedulerPresenter(Display view, EventBus eventBus, CoursesServiceAsync rpc) {
		this.eventBus = eventBus; 
		this.view = view;
		this.rpcService = rpc;
		this.isBlankSpaceCount = this.isDaysoffCount = false;
		this.minStartTime = null;
		this.maxFinishTime = null;
		this.selectedCourses = new ArrayList<>();
		lessonGroupsList = new ArrayList<>();
		sched_index = 0;
	}
	
	
	@Override
	public void bind() {
		
		view.getDaysOffElement().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (view.isDayOffChecked(event)) {
					isDaysoffCount = true;
					Log.info("daysOff button was selected");
				} else {
					isDaysoffCount = false;
					Log.info("daysOff button was deselected");
				}
			}
		});	
		
		view.getMinWindowsElement().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (view.isMinWindowsChecked(event)) {
					isBlankSpaceCount = true;
					Log.info("minWindows button was selected");
				} else {
					isBlankSpaceCount = false;
					Log.info("minWindows button was deselected");
				}
				
			}
		});
		
		view.getStartTimeElement().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (view.isStartTimeChecked(event)) {
					minStartTime = view.getReqStartTime();
					Log.info("Start time value was selected and it is "+ minStartTime.toString());
				} else {
					minStartTime = null;
					Log.info("Start time value was deselected");
				}
			}
		});
		
		view.getStartTimeList().addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent arg0) {
				minStartTime = view.getReqStartTime();
				Log.info("Start time value was changed to "+ minStartTime.toString());	
			}
		});
		
		view.getFinishTimeElement().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (view.isFinishTimeChecked(event)) {
					maxFinishTime = view.getReqFinishTime();
					Log.info("End time value was selected and it is "+ maxFinishTime.toString());
				} else {
					maxFinishTime = null;
					Log.info("Start time value was deselected");
				}
			}
		});
		
		view.getFinishTimeList().addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent arg0) {
				maxFinishTime = view.getReqFinishTime();
				Log.info("Finish time value was changed to "+ maxFinishTime.toString());	
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
				Log.info("Build schedule: requested");
				rpcService.getChosenCoursesList(new AsyncCallback<List<Course>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error while retrieving selected courses.");
						Log.error("Error while retrieving selected courses.");
					}
					@Override
					public void onSuccess(List<Course> result) {
						Log.info("Build schedule: getChosenCoursesList success");
						if (result.isEmpty()) {
							Log.info("Build schedule: no chosen courses");
							view.setSchedule(null);
							return;
						}
						selectedCourses = new ArrayList<Course>(result);

						Log.info("Build schedule: before Scheduler.getTimetablesList");
						final List<Timetable> unsortedTables= Scheduler.getTimetablesList(result, null);
						Log.info("unsorted tables size: " + unsortedTables.size());
						Log.info("unsorted tables: " + unsortedTables);
						Log.info("Build schedule: before Scheduler.sortedBy");
						final List<Timetable> tables = newArrayList(Scheduler.sortedBy(unsortedTables,isDaysoffCount, isBlankSpaceCount, minStartTime, maxFinishTime));
						Log.info("sorted tables size: " + tables.size());
						Log.info("sorted tables: " + tables);
						
						Log.info("Build schedule: after Scheduler.sortedBy");
						if (tables.isEmpty()) {
							Window.alert("Error - There are no possible schedule.");
							Log.error("Error - There are no possible schedule.");
						} else {
							lessonGroupsList.clear();
							sched_index = 0;
							for (Timetable table : tables){
								lessonGroupsList.add(table.getLessonGroups());
							}
							//TODO: Consumer can't compile on client side
/*							tables.forEach(new Consumer<Timetable>() {
								@Override
								public void accept(Timetable λ) {
									lessonGroupsList.add(λ.getLessonGroups());
								}
							});*/
							Log.info("Build schedule: A schedule was build");
							view.setSchedule(lessonGroupsList.get(sched_index));
						}
					}
					private <E> ArrayList<E> newArrayList(Iterator<Timetable> iterator) {
					    return new ArrayList<>();
					}
				}); 
			}
		});
				
		view.nextSchedule().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Log.info("Next schedule was requested");
				if (lessonGroupsList.size() <= sched_index + 1) {
					return;
				}
				++sched_index;
				view.setSchedule(lessonGroupsList.get(sched_index));
			}
		});
		
		view.prevSchedule().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Log.info("Previous schedule was requested");
				if (sched_index <= 0) {
					return;
				}
				--sched_index;
				view.setSchedule(lessonGroupsList.get(sched_index));
			}
		});
		
		view.saveSchedule().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Log.info("Save schedule was requested");
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
		panel.add(view.getAsWidget());
		panel.setWidgetLeftWidth(view.getAsWidget(), 1, Unit.EM, 77, Unit.PCT);
		panel.setWidgetTopBottom(view.getAsWidget(), 4.5, Unit.EM, 1, Unit.EM);
	}
	
}