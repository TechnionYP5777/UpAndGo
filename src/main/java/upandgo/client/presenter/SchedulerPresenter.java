package upandgo.client.presenter;

import com.google.gwt.event.shared.EventBus;

import upandgo.shared.entities.LocalTime;
import upandgo.shared.entities.StuffMember;
import upandgo.shared.entities.UserEvent;
import upandgo.shared.entities.WeekTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
//import java.util.function.Consumer;
import java.util.Map;
import java.util.Map.Entry;

import org.gwtbootstrap3.client.shared.event.ModalShowEvent;
import org.gwtbootstrap3.client.shared.event.ModalShowHandler;
import org.gwtbootstrap3.client.ui.Modal;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;

import upandgo.client.CoursesServiceAsync;
import upandgo.client.event.AuthenticationEvent;
import upandgo.client.event.AuthenticationEventHandler;
import upandgo.client.event.SelectCourseEvent;
import upandgo.client.event.SelectCourseEventHandler;
import upandgo.client.event.UnselectCourseEvent;
import upandgo.client.event.UnselectCourseEventHandler;
import upandgo.client.event.clearScheduleEvent;
import upandgo.client.presenter.CourseListPresenter.GetSomeStringAsyncCallback;
import upandgo.client.event.ClearAllCoursesEvent;
import upandgo.client.event.ClearAllCoursesEventHandler;
import upandgo.client.event.CollidingCourseDeselectedEvent;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.Lesson.Type;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;
import upandgo.shared.model.scedule.Color;
import upandgo.shared.model.scedule.ConstraintsPool;
import upandgo.shared.model.scedule.ConstraintsPool.CourseConstraint;
import upandgo.shared.model.scedule.CourseTuple;
import upandgo.shared.model.scedule.Scheduler;
import upandgo.shared.model.scedule.Timetable;

/**
 * 
 * @author Omri Ben Shmuel
 * @since 18-04-17
 * 
 *        A concrete presenter for {@link schedulerView}.
 * 
 */

public class SchedulerPresenter implements Presenter {

	Display view;
	EventBus eventBus;
	CoursesServiceAsync rpcService;
	
	protected boolean examBarVisable;		

	protected List<Course> selectedCourses;
	protected ConstraintsPool constraintsPool = new ConstraintsPool();
	protected List<List<LessonGroup>> lessonGroupsList;
	protected Map<WeekTime,UserEvent> userEvents = new HashMap<>();
	protected int sched_index;
	protected Map<String, Color> colorMap;

	protected boolean isSignedIn = false;
	
	ScrollPanel examsBar;
	
	boolean isMoedAExams = true;

	public interface Display {

		//TODO: some refactoring here
		public HasClickHandlers clearSchedule();

		public HasClickHandlers buildSchedule();

		public HasClickHandlers nextSchedule();

		public HasClickHandlers prevSchedule();

		public HasClickHandlers saveSchedule();

		public void displaySchedule(List<LessonGroup> lessons, Map<String, Color> map, List<UserEvent> events);
		
		public void drawCollisionView(List<CourseTuple> solvers);
		
		public void setConstraintsPool(List<Course> selectedCourses, ConstraintsPool constraintsPool);
		
		public ConstraintsPool getConstraintsPool();
		
		public Modal getConstraintsModal();
		
		public HasClickHandlers getConstraintsBoxSaveButton();
		
		public void setNotesOnLessonModal(String courseId, List<String> courseNotes);
		
		public Modal getLessonModal();
		public Button getLessonModalCreateConstraintButton();
		public Button getLessonModalRemoveConstraintButton();
		public Lesson getLessonModelCurrentLesson();
				
		public Modal getCollisionModal();
		
		public Button getCollisionModalButton();
		public List<RadioButton> getCollisionRadios();
		public List<CourseTuple> getCollisionSolversTuples();
		
		public void setPrevEnable(boolean enable);

		public void setNextEnable(boolean enable);
		public void setCurrentScheduleIndex(int index, int max);
		public void scheduleBuilt();
		
		
		public void updateExamsBar(List<Course> courses, boolean isMoedA);
		
		public HasClickHandlers getExamButton();

		public Widget getAsWidget();
		
		public void collapseExamsBar();
		
		public void openExamsBar();
			
		public HasClickHandlers getMoedAButton();
		
		public HasClickHandlers getMoedBButton();

		public UserEvent getUserEvent();
		
		public Modal getUserEventBox();
		
		public HasClickHandlers getUserEventBoxSaveButton();
		
		public HasClickHandlers getUserEventBoxDeleteButton();

		public HasClickHandlers exportScheduleButton();
	}

	@Inject
	public SchedulerPresenter( Display display, EventBus eventBus, CoursesServiceAsync rpc) {
		this.eventBus = eventBus;
		this.view = display;
		this.rpcService = rpc;
		//this.isBlankSpaceCount = this.isDaysoffCount = false;
		//this.minStartTime = null;
		//this.maxFinishTime = null;
		this.selectedCourses = new ArrayList<>();
		
		
		eventBus.addHandler(ClearAllCoursesEvent.TYPE, new ClearAllCoursesEventHandler() {

			@Override
			public void onClearAllCourses() {
				selectedCourses.clear();
				view.updateExamsBar(selectedCourses, isMoedAExams);
			}
			
		});
		lessonGroupsList = new ArrayList<>();
		sched_index = 0;

		this.eventBus.addHandler(AuthenticationEvent.TYPE, new AuthenticationEventHandler() {

			@Override
			public void onAuthenticationChanged(AuthenticationEvent event) {
				isSignedIn = event.isSignedIn();
				if (isSignedIn) {
					updateScheduleAndChosenLessons();
				}
			}
		});

		this.eventBus.addHandler(UnselectCourseEvent.TYPE, new UnselectCourseEventHandler() {

			@Override
			public void onUnselectCourse(UnselectCourseEvent event) {
				Iterator<Course> it = selectedCourses.iterator();
				while (it.hasNext()) {
					Course c = it.next();
					if (c.getId() == event.getId().number()) {
						selectedCourses.remove(c);
						break;
					}
				}
				view.updateExamsBar(selectedCourses, isMoedAExams);
			}
		});

		this.eventBus.addHandler(SelectCourseEvent.TYPE, new SelectCourseEventHandler() {

			@Override
			public void onSelectCourse(SelectCourseEvent event) {
				Log.info("SchedulerPresenter: SelectCourseEvent: " + event.getId().number());
				rpcService.getCourseDetails(event.getId(), new AsyncCallback<Course>() {

					@Override
					public void onSuccess(Course result) {
						selectedCourses.add(result);
						view.updateExamsBar(selectedCourses, isMoedAExams);
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error while selecting course for scheduling.");
						Log.error("Error while selecting course for scheduling.");
					}
				});
			}
		});
	}

	@Override
	public void bind() {

		view.clearSchedule().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				lessonGroupsList.clear();
				displaySchedule();
				eventBus.fireEvent(new clearScheduleEvent());
			}
		});

		view.buildSchedule().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Log.info("Build schedule: requested");
				buildSchedule();
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
				displaySchedule();
				

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
				displaySchedule();

			}
		});

		view.saveSchedule().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Log.info("Save schedule was requested");
				if (!isSignedIn) {
					Window.alert("Please, sign in first!");
					return;
				}
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
		
		view.getConstraintsModal().addShowHandler(new ModalShowHandler(){
			@Override
			public void onShow(ModalShowEvent evt) {
				Log.info("SchedulerPresenter: Constraints modal show event");
/*				constraintsPool = new ConstraintsPool(true,true,LocalTime.of(9,0),LocalTime.of(12,0));
				constraintsPool.setDayOff(Day.MONDAY, true);
				constraintsPool.setDayOff(Day.THURSDAY, true);
				constraintsPool.setCourseConstraint("014003",true, CourseConstraint.NO_LESSON, true, 12);*/
				view.setConstraintsPool(selectedCourses, constraintsPool);
				//view.setSelectedCourses(selectedCourses);
			}
		});	
		
		view.getConstraintsBoxSaveButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				constraintsPool = new ConstraintsPool(view.getConstraintsPool());
				Log.info("SchedulerPresenter: constraintsPool recived ");
				for (Entry<String,CourseConstraint> entry : constraintsPool.getCourseConstraints().entrySet()){
					Log.info("SchedulerPresenter: CourseConstraint " + entry.getKey() + " " + entry.getValue());
				}
				view.getConstraintsModal().hide();
				if (!lessonGroupsList.isEmpty()){
					buildSchedule();
				}
			}
		});
		
		view.getLessonModal().addShowHandler(new ModalShowHandler() {
			
			@Override
			public void onShow(ModalShowEvent evt) {
				Lesson lesson = view.getLessonModelCurrentLesson();
				if (lesson == null){
					return;
				}
				CourseConstraint courseConstraint = constraintsPool.getCourseConstraints().get(lesson.getCourseId());
				if (courseConstraint == null){
					return;
				}
				if (lesson.getType()==Type.LECTURE && courseConstraint.isSpecificLecture()
						|| lesson.getType()==Type.TUTORIAL && courseConstraint.isSpecificTutorial() ){
					view.getLessonModalCreateConstraintButton().setVisible(false);
					view.getLessonModalRemoveConstraintButton().setVisible(true);
				} else {
					view.getLessonModalCreateConstraintButton().setVisible(true);
					view.getLessonModalRemoveConstraintButton().setVisible(false);
				}
			}
		});
		
		view.getLessonModalCreateConstraintButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Lesson lesson = view.getLessonModelCurrentLesson();
				if (lesson == null){
					return;
				}
				constraintsPool.setCourseConstraint(lesson.getCourseId(), lesson.getType(), true, lesson.getGroup());
				view.getLessonModal().hide();
				buildScheduleAndSearchForCurrentOne();
			}
		});
		
		view.getLessonModalRemoveConstraintButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Lesson lesson = view.getLessonModelCurrentLesson();
				if (lesson == null){
					return;
				}
				constraintsPool.setCourseConstraint(lesson.getCourseId(), lesson.getType(), false, 0);
				view.getLessonModal().hide();
				buildScheduleAndSearchForCurrentOne();
			}
		});
		
		view.getCollisionModalButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Log.info("god damn1");
				Log.info("radios: " + view.getCollisionRadios());
				
				/*for(RadioButton r : view.getCollisionRadios()){
					//if()
					Log.info(r.getText() + " " + r.getValue());
				}*/
				
				// find choosen course to drop
				List<RadioButton> radios = view.getCollisionRadios();
				String dropId = null;
				for(int i = 0; i < radios.size(); i++){
					Log.info(radios.get(i).getText() + " " + radios.get(i).getValue());
					if(radios.get(i).getValue())
						dropId = view.getCollisionSolversTuples().get(i).getCourseId();
				}
				if(dropId == null)
					Log.error("Drop id is null");
				
				Log.info("Asked to drop course id: " + dropId);
				
				Course droppedCourse = null;
				
				for(Course c : selectedCourses){
					if(c.getId().equals(dropId)){
						droppedCourse = c;
						selectedCourses.remove(c);
						break;
					}
				}
				
				eventBus.fireEvent(new CollidingCourseDeselectedEvent(new CourseId(droppedCourse.getId(), droppedCourse.getName(), droppedCourse.getaTerm(), droppedCourse.getbTerm())));
				
				view.getCollisionModal().hide();
				
				buildSchedule();
			}
		});
		
		view.getExamButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (examBarVisable){
					examBarVisable = false;
					view.collapseExamsBar();
				} else {
					examBarVisable = true;
					view.openExamsBar();
				}
			}
		});
		
		view.getMoedAButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				isMoedAExams = true;
				view.updateExamsBar(selectedCourses, isMoedAExams);
			}
		});
		
		view.getMoedBButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				isMoedAExams = false;
				view.updateExamsBar(selectedCourses, isMoedAExams);
			}
		});
		
		view.getUserEventBoxSaveButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				userEvents.put(view.getUserEvent().getWeekTime(), view.getUserEvent());
				Log.info("SchedulerPresenter: saved user event on " + view.getUserEvent().getWeekTime());
				view.getUserEventBox().hide();
				if (lessonGroupsList.isEmpty()){
					displaySchedule();
				} else {
					buildScheduleAndSearchForCurrentOne();
				}
				
			}
		});
				
		view.getUserEventBoxDeleteButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				userEvents.remove(view.getUserEvent().getWeekTime());
				Log.info("SchedulerPresenter: removed user event on " + view.getUserEvent().getWeekTime());
				view.getUserEventBox().hide();
				if (lessonGroupsList.isEmpty()){
					displaySchedule();
				} else {
					buildScheduleAndSearchForCurrentOne();
				}
			}
		});
		
		view.exportScheduleButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Log.info("export schedule was requested");
				if (!isSignedIn) {
					Window.alert("Please, sign in first!");
					return;
				}
				rpcService.exportSchedule(lessonGroupsList.get(sched_index), new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						if(caught instanceof IOException) {
							Window.alert("Please, grant us permission. Then try again.");
							Window.Location.assign(caught.getMessage());
							return;
						}
						Window.alert("Error while exporting schedule:\n"+caught.getMessage());
						Log.error("Error while exporting schedule.");
						Log.error(caught.getMessage());
						rpcService.getSomeString(new GetSomeStringAsyncCallback());
						
					}

					@Override
					public void onSuccess(Void result) {
						Log.info("schedule was exported successfully");
						rpcService.getSomeString(new GetSomeStringAsyncCallback());
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
	public void go(final LayoutPanel panel) {
		bind();
				
		examBarVisable = false;			
		
		panel.add(view.getAsWidget());
		panel.setWidgetLeftRight(view.getAsWidget(), 1, Unit.EM, 22, Unit.PCT);

		if (isSignedIn) {
			updateScheduleAndChosenLessons();
			
		}
	}

	void buildScheduleAndSearchForCurrentOne(){
		List<LessonGroup> currentSchedule = new ArrayList<LessonGroup>(lessonGroupsList.get(sched_index));
		buildSchedule();
		sched_index = findIndexOfLessonGroupList(currentSchedule);
		Log.info("SchedulerPresenter: found currentSchedule index " + sched_index);
		displaySchedule();
	}
	
	void buildSchedule() {
		Log.info("Build schedule: getChosenCoursesList success");
		if (selectedCourses.isEmpty()) {
			Log.info("Build schedule: no chosen courses");
			lessonGroupsList.clear();
			displaySchedule();
			view.scheduleBuilt();
			return;
		}
		
		// Create new list of courses based on user's constraints
		List<Course> constrainedCourses = new ArrayList<>(selectedCourses.size());
		for (int i = 0 ; i < selectedCourses.size() ; i++){
			constrainedCourses.add(new Course(selectedCourses.get(i)));
		}
		
		for (Course course : constrainedCourses){
			if (constraintsPool.getCourseConstraints().containsKey(course.getId())){
				CourseConstraint courseConstraint = constraintsPool.getCourseConstraints().get(course.getId());
				if (courseConstraint.isSpecificLecture()){
					for (LessonGroup lessonGroup : new ArrayList<>(course.getLectures())){
						if (lessonGroup.getGroupNum() != courseConstraint.getLectureLessonGroup())
							//lessonGroup.setConstrained(false);
							course.getLectures().remove(lessonGroup);
					}
					for (LessonGroup lessonGroup : course.getLectures()){
						lessonGroup.setConstrained(true);
					}
				}
				if (courseConstraint.isSpecificTutorial()){
					for (LessonGroup lessonGroup : new ArrayList<>(course.getTutorials())){
						if (lessonGroup.getGroupNum() != courseConstraint.getTutorialLessonGroup())
							//lessonGroup.setConstrained(false);
							course.getTutorials().remove(lessonGroup);
					}
					for (LessonGroup lessonGroup : course.getTutorials()){
						lessonGroup.setConstrained(true);
					}
				}
			}
		}
		
		Log.info("SchedulerPresenter: selectedCourses " + selectedCourses.get(0).getTutorials());
		
		// This creates a dummy course for the scheduler that contains all user events
		LessonGroup userEventsLessonGroup = new LessonGroup(999);
		for (UserEvent userEvent : getUserEvents()){
			userEventsLessonGroup.addLesson(userEvent.getAsLesson());
		}
		Course userEventCourse = new Course("user events","999999","user events",new ArrayList<StuffMember>(),0.0,null,null,new ArrayList<Course>(),new ArrayList<Course>());
		userEventCourse.addLecturesLessonGroup(userEventsLessonGroup);
		userEventCourse.addTutorialLessonGroup(new LessonGroup(999));
		
		List<Course> selectedCoursesAndEvents = new ArrayList<Course>(selectedCourses);
		selectedCoursesAndEvents.add(userEventCourse);
		
		List<Course> constrainedCoursesAndEvents = new ArrayList<Course>(constrainedCourses);
		constrainedCoursesAndEvents.add(userEventCourse);

		
		Log.info("Build schedule: before Scheduler.getTimetablesList");
		//final List<Timetable> unsortedTables= Scheduler.getTimetablesList(selectedCoursesAndEvents, null);
		final List<Timetable> unsortedTables= Scheduler.getTimetablesList(constrainedCoursesAndEvents, null);
		
		//Map<Course, Color> colorMap = Scheduler.getColorMap();
		colorMap = Scheduler.getColorMap();
		Log.info("color map: " + colorMap);
		//Log.info("unsorted tables size: " + unsortedTables.size());
		//Log.info("unsorted tables: " + unsortedTables);
		//Log.info("Build schedule: before Scheduler.sortedBy");		
		
		final List<Timetable> sorted = Scheduler.ListSortedBy(unsortedTables,true, constraintsPool.isBlankSpaceCount(),
				constraintsPool.getMinStartTime(), constraintsPool.getMaxFinishTime(), constraintsPool.getVectorDaysOff());
		Log.info("corrrect sorted tables size: " + sorted.size());
		//Log.info("*** found time table: " + sorted.get(0) + " ***");
		//Log.info("correct sorted tables: " + sorted);
		
		/*final List<Timetable> tables = newArrayList(Scheduler.sortedBy(unsortedTables,isDaysoffCount, isBlankSpaceCount, minStartTime, maxFinishTime));
		Log.info("sorted tables size: " + tables.size());
		Log.info("sorted tables: " + tables);
		*/
		
		Log.info("Build schedule: after Scheduler.sortedBy");
		if (sorted.isEmpty()) {
			//Window.alert("Error - There are no possible schedule.");
			//Log.error("Error - There are no possible schedule.");
			Log.info("check if can solve it");
			if(!Scheduler.getCollisionSolvers().isEmpty()){
				Log.info("Draw Collision View");
				Log.info("Col solvers: " + Scheduler.getCollisionSolvers());
			view.drawCollisionView(Scheduler.getCollisionSolvers());
			}else{
				Log.info("cannot solver it");
			}
		} else {
			lessonGroupsList.clear();
			//lessonGroupsList = sorted;
			sched_index = 0;
			for (Timetable table : sorted){
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

			displaySchedule();

			if (lessonGroupsList.size()>1){
				view.setNextEnable(true);
			}
		}
		view.scheduleBuilt();
		view.setCurrentScheduleIndex(sched_index+1, lessonGroupsList.size());
	}
	
	void updateScheduleAndChosenLessons() {
		Log.info("updating schedule and chosen lessons...");
		rpcService.getChosenCoursesList(new AsyncCallback<List<Course>>() {
			
			@Override
			public void onSuccess(List<Course> result) {
				for (Course course : result){
					selectedCourses.add(course);
				}
				view.updateExamsBar(selectedCourses, isMoedAExams);
				Log.info("chosen lessons were updated.");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Log.warn("Uh-oh, couldn't load selected courses into scheduler!");
			}
		});

		rpcService.getSchedule(new AsyncCallback<List<LessonGroup>>() {

			@Override
			public void onFailure(Throwable caught) {
				Log.warn("Uh-oh, couldn't load schedule!");
			}

			@Override
			public void onSuccess(List<LessonGroup> result) {
				lessonGroupsList.clear();
				lessonGroupsList.add(result);
				sched_index = 0;
				displaySchedule();
				view.scheduleBuilt();
				view.setCurrentScheduleIndex(sched_index+1, lessonGroupsList.size());
				Log.info("schedule was updated. it has " + String.valueOf(result.size()) + " LessonGroups.");
			}
		});
	}
	
	void displaySchedule(){
		if (lessonGroupsList.isEmpty() || lessonGroupsList.size() < sched_index){
			view.displaySchedule(null, colorMap, getUserEvents());
			view.setCurrentScheduleIndex(0, 0);
			return;
		}
		view.displaySchedule(lessonGroupsList.get(sched_index), colorMap, getUserEvents());
		setNotesOnLessonsModals();	
		view.setCurrentScheduleIndex(sched_index+1, lessonGroupsList.size());
		if (lessonGroupsList.size() <= 1){
			view.setNextEnable(false);
			view.setPrevEnable(false);
		} else if (lessonGroupsList.size() <= sched_index + 1) {
			view.setNextEnable(false);
			view.setPrevEnable(true);
		} else if (sched_index <= 0) {
			view.setNextEnable(true);
			view.setPrevEnable(false);
		} else {
			view.setNextEnable(true);
			view.setPrevEnable(true);
		}
		
	}
	
	
	void setNotesOnLessonsModals(){
		for(Course course : selectedCourses){
			//Log.info("SchedulerPresenter: course " + course.getId() + " has " + course.getNotes().size() + " notes");
			view.setNotesOnLessonModal(course.getId(), course.getNotes());
		}
	}
	
	List<UserEvent> getUserEvents(){
		return new ArrayList<UserEvent>(userEvents.values());
	}
	
	
	int findIndexOfLessonGroupList(List<LessonGroup> listToFind){
		for (int i = 0 ; i < lessonGroupsList.size() ; i++){
			List<LessonGroup> lessonGroupList = lessonGroupsList.get(i);
		
			if (compareListOfLessonGroup(lessonGroupList, listToFind)){
				return i;
			}
		}
		return 0; 
	}

	static boolean compareListOfLessonGroup(List<LessonGroup> list1, List<LessonGroup> list2){
		for (LessonGroup lessonGroup : list1){
			if (lessonGroup.getGroupNum() != 999){
				if (!list2.contains(lessonGroup)){
					return false;
				}
			}
		}
		return true;
	}
	
	class GetSomeStringAsyncCallback implements AsyncCallback<String> {
		@Override
		public void onSuccess(String result) {
			// Window.alert(result);
			Log.info(result);
		}

		@Override
		public void onFailure(@SuppressWarnings("unused") Throwable caught) {
			Window.alert("Cthulhu has awoken!!!!!!!!!");
			Log.error("Cthulhu has awoken!!!!!!!!");
			Log.error("**+++++++++++" + caught.getLocalizedMessage() + "**+++++++++++" + caught.getMessage());

		}
	}
	
}