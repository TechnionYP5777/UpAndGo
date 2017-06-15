package upandgo.client.view;

import upandgo.shared.entities.LocalTime;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.Color;

import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.InlineCheckBox;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import upandgo.client.Resources;
import upandgo.client.Resources.MainStyle;
import upandgo.client.presenter.SchedulerPresenter;
import upandgo.shared.entities.LessonGroup;

public class SchedulerView extends LayoutPanel implements SchedulerPresenter.Display{

	private MainStyle style = Resources.INSTANCE.mainStyle();
	TimeTableView timeTableView = new TimeTableView();
	ScrollPanel scrollableTimeTable = new ScrollPanel(timeTableView);
	SchedulerConstraintsView schedualerConstraintsView = new SchedulerConstraintsView();
	SchedulerControlsView schedualerControlsView = new SchedulerControlsView(schedualerConstraintsView);

	public SchedulerView(){
		InitializePanel();
		style.ensureInjected();
	}
	
	private void InitializePanel(){
		// needs to be injected
		
		scrollableTimeTable.addStyleName(style.scrollableTimeTable());

		this.setHeight("100%");
		this.add(scrollableTimeTable);
		this.setWidgetLeftRight(scrollableTimeTable, 13, Unit.EM, 0, Unit.EM);
		this.setWidgetTopBottom(scrollableTimeTable, 0.5, Unit.EM, 0, Unit.EM);
		
		this.add(schedualerControlsView);
		this.setWidgetLeftWidth(schedualerControlsView, 0, Unit.EM, 11, Unit.EM);
		this.setWidgetTopBottom(schedualerControlsView, 0.5, Unit.EM, 0, Unit.EM);
/*		this.add(constraintsView);
		this.setWidgetLeftRight(constraintsView, 1, Unit.EM, 1, Unit.EM);
		this.setWidgetBottomHeight(constraintsView, 0, Unit.EM, 3, Unit.EM);
		this.setWidgetHorizontalPosition(constraintsView, Layout.Alignment.END);
*/		
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
	public CheckBox getSundayCheckbox(){
		return schedualerConstraintsView.sundayBox;
	}
	public CheckBox getMondayCheckbox(){
		return schedualerConstraintsView.mondayBox;
	}
	public CheckBox getTuesdayCheckbox(){
		return schedualerConstraintsView.tuesdayBox;
	}
	public CheckBox getWednesdayCheckbox(){
		return schedualerConstraintsView.wednesdayBox;
	}
	public CheckBox getThursdayCheckbox(){
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
	
	public void setCurrentScheduleIndex(int index, int max){
		schedualerControlsView.setCurrentScheduleIndex(index,max);
	}
	
	@Override
	public void scheduleBuilt(){
		schedualerControlsView.scheduleBuilt();
	}
}