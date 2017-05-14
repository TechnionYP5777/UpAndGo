package upandgo.client;

import java.time.LocalTime;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.layout.client.Layout;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import upandgo.client.Resources.MainStyle;
import upandgo.client.presenter.SchedulerPresenter;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.model.scedule.Schedule;

public class SchedualerView extends LayoutPanel implements SchedulerPresenter.Display{

	private MainStyle style = Resources.INSTANCE.mainStyle();

	public SchedualerView(){
		InitializePanel();
		style.ensureInjected();
	}
	
	private void InitializePanel(){
		TimeTableView timeTableView = new TimeTableView();// needs to be injected
		ScrollPanel scrollableTimeTable = new ScrollPanel(timeTableView);
		scrollableTimeTable.addStyleName(style.scrollableTimeTable());
		SchedualerControlsView schedualerControlsView = new SchedualerControlsView();
		ConstraintsView constraintsView = new ConstraintsView();

		this.setHeight("100%");
		this.add(scrollableTimeTable);
		this.setWidgetLeftRight(scrollableTimeTable, 1, Unit.EM, 1, Unit.EM);
		this.setWidgetTopBottom(scrollableTimeTable, 1, Unit.EM, 8, Unit.EM);
		this.add(schedualerControlsView);
		this.setWidgetLeftRight(schedualerControlsView, 1, Unit.EM, 1, Unit.EM);
		this.setWidgetBottomHeight(schedualerControlsView, 4, Unit.EM, 3, Unit.EM);
		this.add(constraintsView);
		this.setWidgetLeftRight(constraintsView, 1, Unit.EM, 1, Unit.EM);
		this.setWidgetBottomHeight(constraintsView, 0, Unit.EM, 3, Unit.EM);
		this.setWidgetHorizontalPosition(constraintsView, Layout.Alignment.END);
		
		}
	@Override
	public <T extends HasClickHandlers> T clearSchedule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends HasClickHandlers> T buildSchedule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends HasClickHandlers> T nextSchedule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends HasClickHandlers> T prevSchedule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends HasClickHandlers> T saveSchedule() {
		// TODO Auto-generated method stub
		return null;
	}
 	
	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSchedule(List<LessonGroup> schedule) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends HasClickHandlers> T getDaysOffValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isDayOffChecked(ClickEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T extends HasClickHandlers> T getMinWindowsValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isMinWindowsChecked(ClickEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T extends HasClickHandlers> T getStartTimeValue() {
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
	public <T extends HasClickHandlers> T getFinishTimeValue() {
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
