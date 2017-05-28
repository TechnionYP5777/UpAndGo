package upandgo.client.view;

import upandgo.shared.entities.LocalTime;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import upandgo.client.Resources;
import upandgo.client.Resources.MainStyle;
import upandgo.client.presenter.SchedulerPresenter;
import upandgo.shared.entities.LessonGroup;

public class SchedualerView extends LayoutPanel implements SchedulerPresenter.Display{

	private MainStyle style = Resources.INSTANCE.mainStyle();
	TimeTableView timeTableView = new TimeTableView();
	ScrollPanel scrollableTimeTable = new ScrollPanel(timeTableView);
	SchedualerControlsView schedualerControlsView = new SchedualerControlsView();

	public SchedualerView(){
		InitializePanel();
		style.ensureInjected();
	}
	
	private void InitializePanel(){
		// needs to be injected
		
		scrollableTimeTable.addStyleName(style.scrollableTimeTable());
		

		this.setHeight("100%");
		this.add(scrollableTimeTable);
		this.setWidgetLeftRight(scrollableTimeTable, 1, Unit.EM, 1, Unit.EM);
		this.setWidgetTopBottom(scrollableTimeTable, 1, Unit.EM, 8, Unit.EM);
		this.add(schedualerControlsView);
		this.setWidgetLeftRight(schedualerControlsView, 1, Unit.EM, 1, Unit.EM);
		this.setWidgetBottomHeight(schedualerControlsView, 1, Unit.EM, 3, Unit.EM);
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
	public void setSchedule(List<LessonGroup> schedule) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HasClickHandlers getDaysOffValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isDayOffChecked(ClickEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public HasClickHandlers getMinWindowsValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isMinWindowsChecked(ClickEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public HasClickHandlers getStartTimeValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isStartTimeChecked(ClickEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LocalTime getReqStartTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasClickHandlers getFinishTimeValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isFinishTimeChecked(ClickEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LocalTime getReqFinishTime() {
		// TODO Auto-generated method stub
		return null;
	}
}