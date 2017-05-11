package upandgo.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

import upandgo.client.Resources.ConstraintsStyle;
import upandgo.client.presenter.SchedulerPresenter;
import upandgo.shared.entities.LocalTime;
import upandgo.shared.model.scedule.Schedule;


/**
 * @author Yaniv Levinsky
 *
 */

public class ConstraintsGUI extends HorizontalPanel implements SchedulerPresenter.Display{

	Label constraintsTitle = new Label("העדפות לבניית המערכת:");
	
	CheckBox daysOffCB = new CheckBox("ימי חופש");

	CheckBox minWindowsCB = new CheckBox("מספר מינימלי של חלונות");

	CheckBox startTimeCB = new CheckBox("שעת התחלה");
	
	ListBox startTimeLB = new ListBox();

	CheckBox finishTimeCB = new CheckBox("שעת סיום");

	ListBox finishTimeLB = new ListBox();

	
	private ConstraintsStyle cStyle = Resources.INSTANCE.constraintsStyle();

	public ConstraintsGUI(){
	    InitializeTimeLBs();
    	InitializePanel();
    	cStyle.ensureInjected();
    }
	
	private void InitializeTimeLBs(){
    	for(int i = 1; i<13; i++){
    		startTimeLB.addItem(Integer.toString(i+7)+":30");
    		finishTimeLB.addItem(Integer.toString(i+7)+":30");
    	}
    	startTimeLB.setEnabled(false);
    	finishTimeLB.setEnabled(false);

	}
	    
    private void InitializePanel(){
    	this.setHorizontalAlignment(ALIGN_RIGHT);
    	//this.setVerticalAlignment(ALIGN_BOTTOM);

    	this.add(constraintsTitle);
    	this.add(daysOffCB);
    	this.add(minWindowsCB);
    	this.add(startTimeCB);
    	this.add(startTimeLB);
    	this.add(finishTimeCB);
    	this.add(finishTimeLB);
    	
    	daysOffCB.addStyleName(cStyle.onlyCheckBox());
    	minWindowsCB.addStyleName(cStyle.onlyCheckBox());

    	startTimeCB.addStyleName(cStyle.timeCheckBox());
    	finishTimeCB.addStyleName(cStyle.timeCheckBox());
    	
    	startTimeCB.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
		        boolean checked = ((CheckBox) event.getSource()).getValue();
		        startTimeLB.setEnabled(checked);
			}
    		
    	});
    	
    	finishTimeCB.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
		        boolean checked = ((CheckBox) event.getSource()).getValue();
		        finishTimeLB.setEnabled(checked);
			}
    		
    	});
    	
    	this.setStyleName(cStyle.constraintsPanel());
    	//this.getElement().getStyle().setBackgroundColor("WhiteSmoke");
    	//this.getElement().getStyle().setWidth(100, Unit.PCT);

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
	public void setSchedule(Schedule schedule) {
		// TODO Auto-generated method stub
		
	}

}
