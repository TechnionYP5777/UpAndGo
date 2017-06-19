package upandgo.client.view;

import upandgo.shared.entities.LocalTime;
import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;
import upandgo.shared.model.scedule.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.InlineCheckBox;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.extras.select.client.ui.event.HasShowHandlers;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import upandgo.client.Resources;
import upandgo.client.Resources.MainStyle;
import upandgo.client.Resources.examsBarStyle;
import upandgo.client.presenter.SchedulerPresenter;
import upandgo.shared.entities.LessonGroup;

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

	public SchedulerView(){
		InitializePanel();
		style.ensureInjected();
	}
	
	private void InitializePanel(){
		
		
		//initializing exams bar inside a mgwt scroll panel
		examsBarStyle ebStyle = Resources.INSTANCE.examsBarStyle();
		ebStyle.ensureInjected();
		examsBar = new HTML("");
    	examsScrollPanel = new com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel();
    	examsScrollPanel.setScrollingEnabledX(true);
    	examsScrollPanel.setScrollingEnabledY(false);
    	examsScrollPanel.setShowVerticalScrollBar(false);
    	examsScrollPanel.setWidget(examsBar);
    	examsScrollPanel.addStyleName(ebStyle.examBarPanel());
    	examsBar.addStyleName("horizontal-scroll-wrapper");
		
		
		// needs to be injected
		
		scrollableTimeTable.addStyleName(style.scrollableTimeTable());

		this.setHeight("100%");
		
		this.add(scrollableTimeTable);
		this.setWidgetLeftRight(scrollableTimeTable, 12, Unit.EM, 0, Unit.EM);
		this.setWidgetTopBottom(scrollableTimeTable, 4.5, Unit.EM, 1, Unit.EM);
		this.getWidgetContainerElement(scrollableTimeTable).getStyle().setProperty("transition", "bottom 0.16s linear 0s");
		
		this.add(examsBar);
		this.setWidgetLeftRight(examsBar, 12, Unit.EM, 0, Unit.EM);
		this.setWidgetBottomHeight(examsBar, 2, Unit.EM, 0, Unit.EM);
		this.getWidgetContainerElement(examsBar).getStyle().setProperty("transition", "height 0.16s linear 0s");	
		
		this.add(schedualerControlsView);
		this.setWidgetLeftWidth(schedualerControlsView, 0, Unit.EM, 11, Unit.EM);
		this.setWidgetTopBottom(schedualerControlsView, 4.5, Unit.EM, 0, Unit.EM);
		
		this.add(examsControlsView);
		this.setWidgetLeftWidth(examsControlsView, 0, Unit.EM, 11, Unit.EM);
		this.setWidgetBottomHeight(examsControlsView, 1, Unit.EM, 3, Unit.EM);

		
		}
	
	@Override
	public void drawCollisionView(){
		Modal constraintsBox = new Modal();
		constraintsBox.setFade(true);
		constraintsBox.setTitle("פתרון התנגשויות");
		constraintsBox.add(schedulerCollisionsView);
		constraintsBox.show();
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
	public void setSchedule(List<LessonGroup> schedule, Map<String, Color> map) {
		timeTableView.displaySchedule(schedule, map);
		
	}
	
	@Override
	public void setSelectedCourses(List<Course> selectedCourses){
		schedualerConstraintsView.displayCoursesConstraints(selectedCourses);
	}
	
	public Modal getContraintsModal(){
		return schedualerControlsView.constraintsBox;
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
	public void updateExamsBar(List<CourseId> courses) {
		List<CourseId> is = new ArrayList<>(), isB = new ArrayList<>() ;
	    for(CourseId c : courses){
	    	if(c.aTerm()!=null)
	    		is.add(c);
	    	if(c.bTerm()!=null)
	    		isB.add(c);
	    }
	    Log.info("begin of updateLists/3");
	    Collections.sort(is,(new Comparator<CourseId>() { //sort courses by their final exam date

			@Override
			public int compare(CourseId o1, CourseId o2) {
				return o1.aTerm().compare(o2.aTerm());
			}
		}));
	    Collections.sort(isB,(new Comparator<CourseId>() { //sort courses by their final exam date

			@Override
			public int compare(CourseId o1, CourseId o2) {
				return o1.bTerm().compare(o2.bTerm());
			}
		}));
	    long width=0;
		String examsAlephBarHTML = "";
		for(int i=0; i < is.size(); i++){
			if(i == is.size()-1){
				examsAlephBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ffff80;\"> <b><u>" + is.get(i).aTerm().toString() + "</u></b><br>" + is.get(i).aTerm().getTimeToDisplay() + is.get(i).name() + "</div> ";
				width+=275;
				break;
			}
			int daysBetween = is.get(i+1).aTerm().daysBetweenExams(is.get(i).aTerm());
			if(daysBetween == 0 ){			
				examsAlephBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ff4d4d;\"> <b><u>" + is.get(i).aTerm().toString() + "</u></b><br>" + is.get(i).aTerm().getTimeToDisplay() + is.get(i).name();
				width+=275;
				while(daysBetween == 0 &&  i < is.size()-1){
					i++;
					examsAlephBarHTML+="<br>" + is.get(i).aTerm().getTimeToDisplay() + is.get(i).name();
					daysBetween = is.get(i+1).aTerm().daysBetweenExams(is.get(i).aTerm());
				}
				examsAlephBarHTML+="</div>";
				if(daysBetween > 0 ){
					for(int k = 0 ; k < daysBetween-1; k++){
						examsAlephBarHTML+="<div  class=\"child\" style=\"background-color:#85e085; \"></div>";
						width+=85;
					}
				}
				
			}
			else{
				examsAlephBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ffff80;\"> <b><u>" + is.get(i).aTerm().toString() + "</u></b><br>" + is.get(i).aTerm().getTimeToDisplay() + is.get(i).name() + "</div> ";
				width+=275;
				for(int k = 0 ; k < daysBetween-1; k++){
					examsAlephBarHTML+="<div class=\"child\" style=\"background-color:#85e085;\"></div>";
					width+=85;
				}
			}
		}
		long widthb=0;
		String examsBetBarHTML = "";
		for(int i=0; i < isB.size(); i++){
			if(i == isB.size()-1){
				examsBetBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ffff80;\"> <b><u>" + isB.get(i).bTerm().toString() + "</u></b><br>" + isB.get(i).bTerm().getTimeToDisplay() + isB.get(i).name() + "</div> ";
				widthb+=275;
				break;
			}
			int daysBetween = isB.get(i+1).bTerm().daysBetweenExams(isB.get(i).bTerm());
			Log.info("$$#$#$#" + daysBetween);
			if(daysBetween == 0 ){			
				examsBetBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ff4d4d;\"> <b><u>" + isB.get(i).bTerm().toString() + "</u></b><br>" + isB.get(i).bTerm().getTimeToDisplay() + isB.get(i).name();
				widthb+=275;
				while(daysBetween == 0 &&  i < isB.size()-1){
					i++;
					examsBetBarHTML+="<br>" + isB.get(i).bTerm().getTimeToDisplay() + isB.get(i).name();
					daysBetween = isB.get(i+1).bTerm().daysBetweenExams(isB.get(i).bTerm());
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
				examsBetBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ffff80;\"> <b><u>" + isB.get(i).bTerm().toString() + "</u></b><br>" + isB.get(i).bTerm().getTimeToDisplay() + isB.get(i).name() + "</div> ";
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
		this.setWidgetTopBottom(scrollableTimeTable, 4.5, Unit.EM, 1, Unit.EM);
		this.setWidgetBottomHeight(examsBar, 2, Unit.EM, 0, Unit.EM);
		
		
	}

	@Override
	public void openExamsBar() {
		this.setWidgetTopBottom(scrollableTimeTable, 4.5, Unit.EM, 7, Unit.EM);
		this.setWidgetBottomHeight(examsBar, 2, Unit.EM, 5, Unit.EM);
		
	}
}