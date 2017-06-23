package upandgo.client.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.InlineCheckBox;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

import upandgo.client.Resources;
import upandgo.client.Resources.MainStyle;
import upandgo.client.Resources.ExamsBarStyle;
import upandgo.client.presenter.SchedulerPresenter;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.LocalTime;
import upandgo.shared.entities.UserEvent;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.Color;
import upandgo.shared.model.scedule.CourseTuple;

public class SchedulerView extends LayoutPanel implements SchedulerPresenter.Display{

	private MainStyle style = Resources.INSTANCE.mainStyle();
    private HTML examsBar;
    private HTML examsBarB;
    private com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel examsScrollPanel;
	TimeTableView timeTableView = new TimeTableView();
	ScrollPanel scrollableTimeTable = new ScrollPanel(timeTableView);
	SchedulerConstraintsView schedualerConstraintsView = new SchedulerConstraintsView();
	SchedulerCollisionsView schedulerCollisionsView = new SchedulerCollisionsView();
	
	SchedulerControlsView schedualerControlsView = new SchedulerControlsView(schedualerConstraintsView);
	ExamsControlsView examsControlsView = new ExamsControlsView();
	Modal collisionBox = new Modal();
	Button collisionBoxButton = new Button("הסר קורס");
	List<RadioButton> radios;
	List<CourseTuple> solversTuples;
	

	public SchedulerView(){
		InitializePanel();
		InitializeCollisionBox();
		style.ensureInjected();
	}
	
	
	private void InitializePanel(){
		
		
		//initializing exams bar inside a mgwt scroll panel
		ExamsBarStyle ebStyle = Resources.INSTANCE.examsBarStyle();
		ebStyle.ensureInjected();
		examsBar = new HTML("");
    	examsScrollPanel = new com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel();
    	examsScrollPanel.setScrollingEnabledX(true);
    	examsScrollPanel.setScrollingEnabledY(false);
    	examsScrollPanel.setShowVerticalScrollBar(false);
    	examsScrollPanel.setShowHorizontalScrollBar(false);
    	examsScrollPanel.setWidget(examsBar);
    	examsScrollPanel.addStyleName(ebStyle.examBarPanel());
    	examsBar.addStyleName("horizontal-scroll-wrapper");
		
		
		// needs to be injected
		
		scrollableTimeTable.addStyleName(style.scrollableTimeTable());

		this.setHeight("100%");
		
		this.add(scrollableTimeTable);
		this.setWidgetLeftRight(scrollableTimeTable, 11, Unit.EM, 0, Unit.EM);
		this.setWidgetTopBottom(scrollableTimeTable, 5, Unit.EM, 1, Unit.EM);
		this.getWidgetContainerElement(scrollableTimeTable).getStyle().setProperty("transition", "bottom 0.3s linear 0s");
		
		this.add(examsScrollPanel);
		this.setWidgetLeftRight(examsScrollPanel, 11, Unit.EM, 0, Unit.EM);
		this.setWidgetBottomHeight(examsScrollPanel, 0, Unit.EM, 0, Unit.EM);
		this.getWidgetContainerElement(examsScrollPanel).getStyle().setProperty("transition", "height 0.3s linear 0s");	
		
		this.add(schedualerControlsView);
		this.setWidgetLeftWidth(schedualerControlsView, 0, Unit.EM, 10, Unit.EM);
		this.setWidgetTopBottom(schedualerControlsView, 5, Unit.EM, 1, Unit.EM);
		
		this.add(examsControlsView);
		this.setWidgetLeftWidth(examsControlsView, 0, Unit.EM, 10, Unit.EM);
		this.setWidgetBottomHeight(examsControlsView, 1, Unit.EM, 2.5, Unit.EM);

		
		}
	
	

	private void InitializeCollisionBox(){
		ModalFooter collisionBoxFooter = new ModalFooter();
		//collisionBoxButton = new Button("הסר קורס");
		/*Button collisionBoxButton = new Button("שמור וסגור", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				collisionBox.hide();
				
			}
		});*/
		
		
		
		
		
		collisionBoxFooter.add(collisionBoxButton);
		collisionBox.setFade(true);
		collisionBox.setTitle("פתרון התנגשויות");
		//schedulerCollisionsView.updateList(solvers);
		
		ModalBody collisionBoxBody = new ModalBody();
		collisionBoxBody.add(schedulerCollisionsView);
		collisionBox.add(collisionBoxBody);
		
		//collisionBox.add(schedulerCollisionsView);
		collisionBox.add(collisionBoxFooter);
		
		
		
	}
	
	@Override
	public void drawCollisionView(List<CourseTuple> solvers){
		schedulerCollisionsView.updateList(solvers);
		radios = schedulerCollisionsView.getRadios();
		solversTuples = schedulerCollisionsView.getSolversTuples();
		collisionBox.show();
		/*Modal constraintsBox = new Modal();
		constraintsBox.setFade(true);
		constraintsBox.setTitle("פתרון התנגשויות");
		Log.info("SchedCol/x0");
		schedulerCollisionsView.updateList(solvers);
		Log.info("SchedCol/x1");
		constraintsBox.add(schedulerCollisionsView);
		Log.info("SchedCol/x2");
		
		ModalFooter constraintsBoxFooter = new ModalFooter();
		Button constraintsBoxButton = new Button("הסר קורס", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				constraintsBox.hide();
				
			}
		});
		constraintsBoxFooter.add(constraintsBoxButton);
		constraintsBox.add(constraintsBoxFooter);
		
		constraintsBox.show();
		Log.info("was here/1");*/
	}
	
	
	@Override
	public HasClickHandlers clearSchedule() {
		return schedualerControlsView.clearSchedule;
	}

	@Override
	public HasClickHandlers buildSchedule() {
		return schedualerControlsView.buildSchedule;
	}

	@Override
	public HasClickHandlers nextSchedule() {
		return schedualerControlsView.nextSchedule;
	}

	@Override
	public HasClickHandlers prevSchedule() {
		return schedualerControlsView.prevSchedule;
	}

	@Override
	public HasClickHandlers saveSchedule() {
		return schedualerControlsView.saveSchedule;
	}
 	
	@Override
	public Widget getAsWidget() {
		return this.asWidget();
	}

	@Override
	public void displaySchedule(List<LessonGroup> lessons, Map<String, Color> map, List<UserEvent> events) {
		timeTableView.displaySchedule(lessons, map, events);
		
	}
	
	@Override
	public void setSelectedCourses(List<Course> selectedCourses){
		schedualerConstraintsView.displayCoursesConstraints(selectedCourses);
	}
	
	@Override
	public Modal getContraintsModal(){
		return schedualerControlsView.constraintsBox;
	}
	
	@Override
	public Modal getCollisionModal(){
		return collisionBox;
	}
	
	@Override
	public Button getCollisionModalButton(){
		return collisionBoxButton;
	}
	
	@Override
	public List<RadioButton> getCollisionRadios(){
		return radios;
	}
	
	@Override
	public List<CourseTuple> getCollisionSolversTuples(){
		return solversTuples;
	}

	/*
	@Override
	public HasClickHandlers getDaysOffElement() {
		return schedualerConstraintsView.daysOffCB;
	}*/

	@Override
	public boolean isDayOffChecked(ClickEvent event) {
		return ((InlineCheckBox) event.getSource()).getValue();
	}

	@Override
	public HasClickHandlers getMinWindowsElement() {
		return schedualerConstraintsView.minWindowsCB;
		//return null;
	}
	@Override
	public InlineCheckBox getSundayCheckbox(){
		return schedualerConstraintsView.sundayBox;
	}
	public InlineCheckBox getMondayCheckbox(){
		return schedualerConstraintsView.mondayBox;
	}
	public InlineCheckBox getTuesdayCheckbox(){
		return schedualerConstraintsView.tuesdayBox;
	}
	public InlineCheckBox getWednesdayCheckbox(){
		return schedualerConstraintsView.wednesdayBox;
	}
	public InlineCheckBox getThursdayCheckbox(){
		return schedualerConstraintsView.thursdayBox;
	}

	@Override
	public boolean isMinWindowsChecked(ClickEvent event) {
		return ((InlineCheckBox) event.getSource()).getValue();
	}

	@Override
	public HasClickHandlers getStartTimeElement() {
		return schedualerConstraintsView.startTimeCB;
	}

	@Override
	public boolean isStartTimeChecked(ClickEvent event) {
		return ((InlineCheckBox) event.getSource()).getValue();
	}

	@Override
	public LocalTime getReqStartTime() {
		int index = schedualerConstraintsView.startTimeLB.getSelectedIndex();
		return LocalTime.of(index+8, 30);
	}

	@Override
	public HasClickHandlers getFinishTimeElement() {
		return schedualerConstraintsView.finishTimeLB;
	}

	@Override
	public boolean isFinishTimeChecked(ClickEvent event) {
		return ((InlineCheckBox) event.getSource()).getValue();
	}

	@Override
	public LocalTime getReqFinishTime() {
		int index = schedualerConstraintsView.finishTimeLB.getSelectedIndex();
		return LocalTime.of(index+8, 30);
	}

	@Override
	public HasChangeHandlers getStartTimeList() {
		return schedualerConstraintsView.startTimeLB;
	}

	@Override
	public HasChangeHandlers getFinishTimeList() {
		return schedualerConstraintsView.finishTimeLB;
	}
	
	@Override
	public void setPrevEnable(boolean enable){
		schedualerControlsView.setPrevEnable(enable);
	}
	
	@Override
	public void setNextEnable(boolean enable){
		schedualerControlsView.setNextEnable(enable);
	}
	
	@Override
	public void setCurrentScheduleIndex(int index, int max){
		schedualerControlsView.setCurrentScheduleIndex(index,max);
	}
	
	@Override
	public void scheduleBuilt(){
		schedualerControlsView.scheduleBuilt();
	}
	
	@Override
	public HasClickHandlers getExamButton(){
		return examsControlsView.examsButton;
	}

	@Override
	public void updateExamsBar(List<Course> courses) {
		List<Course> is = new ArrayList<>(), isB = new ArrayList<>();
		Map<Course, String> courseColors = new HashMap<>();
	    for(int i = 0; i < courses.size(); i++){
	    	Course c = courses.get(i);
	    	if(c.getaTerm()!=null){
	    		is.add(c);
	    		courseColors.put(c,Color.valueOf(i).toString());
	    	}
	    	if(c.getaTerm()!=null)
	    		isB.add(c);
	    }
	    Log.info("updating exams");
	    Collections.sort(is,(new Comparator<Course>() { //sort courses by their final exam date

			@Override
			public int compare(Course o1, Course o2) {
				return o1.getaTerm().compare(o2.getaTerm());
			}
		}));
	    Collections.sort(isB,(new Comparator<Course>() { //sort courses by their final exam date

			@Override
			public int compare(Course o1, Course o2) {
				return o1.getbTerm().compare(o2.getbTerm());
			}
		}));
	    long width=0;
		String examsAlephBarHTML = "";
		for(int i=0; i < is.size(); i++){
			if(i == is.size()-1){
				examsAlephBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:" + courseColors.get(is.get(i)) +";\"> <b><u>" + is.get(i).getaTerm().toString() + "</u></b><br>" + is.get(i).getaTerm().getTimeToDisplay() + is.get(i).getName() + "</div> ";
				width+=275;
				break;
			}
			int daysBetween = is.get(i+1).getaTerm().daysBetweenExams(is.get(i).getaTerm());
			if(daysBetween == 0 ){			
				examsAlephBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ff0000;\"> <b><u>" + is.get(i).getaTerm().toString() + "</u></b><br>" + is.get(i).getaTerm().getTimeToDisplay() + is.get(i).getName();
				width+=275;
				while(daysBetween == 0 &&  i < is.size()-1){
					i++;
					examsAlephBarHTML+="<br>" + is.get(i).getaTerm().getTimeToDisplay() + is.get(i).getName();
					daysBetween = is.get(i+1).getaTerm().daysBetweenExams(is.get(i).getaTerm());
				}
				examsAlephBarHTML+="</div>";
				if(daysBetween > 0 ){
					for(String k : is.get(i+1).getaTerm().datesBetweenExams(is.get(i).getaTerm())){
						examsAlephBarHTML+="<div align=\"left\" class=\"child\" style=\"background-color:#9ae59a;\">" + k + "</div>";
						width+=85;
					}
				}
				
			}
			else{
				examsAlephBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:" + courseColors.get(is.get(i)) +"\"> <b><u>" + is.get(i).getaTerm().toString() + "</u></b><br>" + is.get(i).getaTerm().getTimeToDisplay() + is.get(i).getName() + "</div> ";
				width+=275;
				for(String k : is.get(i+1).getaTerm().datesBetweenExams(is.get(i).getaTerm())){
					examsAlephBarHTML+="<div align=\"left\" class=\"child\" style=\"background-color:#9ae59a;\">" + k + "</div>";
					width+=85;
				}
			}
		}
		long widthb=0;
		String examsBetBarHTML = "";
		for(int i=0; i < isB.size(); i++){
			if(i == isB.size()-1){
				examsBetBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ffff80;\"> <b><u>" + isB.get(i).getaTerm().toString() + "</u></b><br>" + isB.get(i).getaTerm().getTimeToDisplay() + isB.get(i).getName() + "</div> ";
				widthb+=275;
				break;
			}
			int daysBetween = isB.get(i+1).getaTerm().daysBetweenExams(isB.get(i).getaTerm());
			Log.info("$$#$#$#" + daysBetween);
			if(daysBetween == 0 ){			
				examsBetBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ff4d4d;\"> <b><u>" + isB.get(i).getaTerm().toString() + "</u></b><br><b>" + isB.get(i).getaTerm().getTimeToDisplay() + isB.get(i).getName()+"</b>";
				widthb+=275;
				while(daysBetween == 0 &&  i < isB.size()-1){
					i++;
					examsBetBarHTML+="<br><b>" + isB.get(i).getaTerm().getTimeToDisplay() + isB.get(i).getName() + "</b>";
					daysBetween = isB.get(i+1).getaTerm().daysBetweenExams(isB.get(i).getaTerm());
				}
				examsBetBarHTML+="</div>";
				if(daysBetween > 0 ){
					for(int k = 0 ; k < daysBetween-1; k++){
						examsBetBarHTML+="<div  class=\"child\" style=\"background-color:#85e085; \"></div>";
						widthb+=85;
					}
				}
				
			}
			else{
				examsBetBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ffff80;\"> <b><u>" + isB.get(i).getaTerm().toString() + "</u></b><br>" + isB.get(i).getaTerm().getTimeToDisplay() + isB.get(i).getName() + "</div> ";
				widthb+=275;
				for(int k = 0 ; k < daysBetween-1; k++){
					examsBetBarHTML+="<div class=\"child\" style=\"background-color:#85e085;\"></div>";
					widthb+=85;
				}
			}
		}
		Log.info("begin of updateLists/7");
		examsBar = new HTML(examsAlephBarHTML);
		examsBar.addStyleName("horizontal-scroll-wrapper");
		examsBar.getElement().getStyle().setWidth(width, Unit.PX);
		examsScrollPanel.setWidget(examsBar);
//			examsBarB = new HTML(examsBetBarHTML);
//			examsBarB.addStyleName("horizontal-scroll-wrapper");
//			examsBarB.getElement().getStyle().setWidth(widthb, Unit.PX);
//			examsScrollPanel.setWidget(examsBarB);

		
		
	}

	@Override
	public void collapseExamsBar() {
		this.setWidgetTopBottom(scrollableTimeTable, 5, Unit.EM, 1, Unit.EM);
		this.setWidgetBottomHeight(examsScrollPanel, 1, Unit.EM, 0, Unit.EM);
		
		
	}

	@Override
	public void openExamsBar() {
		this.setWidgetTopBottom(scrollableTimeTable, 5, Unit.EM, 7, Unit.EM);
		this.setWidgetBottomHeight(examsScrollPanel, 1, Unit.EM, 5, Unit.EM);
		
	}


	@Override
	public void setNotesOnLessonModal(String courseId, List<String> courseNotes) {
		timeTableView.setNotesOnLessonModal(courseId, courseNotes);
		
	}
	
	@Override
	public Modal getUserEventBox(){
		return timeTableView.userEventBox;
	}
	
	@Override
	public UserEvent getUserEvent(){
		return timeTableView.getUserEvent();
	}
	
	@Override
	public HasClickHandlers getUserEventBoxSaveButton() {
		return timeTableView.userEventBoxSaveButton;
	}
	
	@Override
	public HasClickHandlers getUserEventBoxDeleteButton() {
		return timeTableView.userEventBoxDeleteButton;
	}

}