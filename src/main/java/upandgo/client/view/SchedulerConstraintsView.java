package upandgo.client.view;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.InlineCheckBox;
import org.gwtbootstrap3.client.ui.ModalComponent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import upandgo.client.Resources;
import upandgo.client.Resources.SchedualerConstraintsStyle;
import upandgo.shared.entities.course.Course;


/**
 * @author Yaniv Levinsky
 *
 */

public class SchedulerConstraintsView extends VerticalPanel implements ModalComponent{
	
	//InlineCheckBox daysOffCB = new InlineCheckBox("מספר מקסימלי של ימים חופשיים");

	HorizontalPanel freeDaysPanel = new HorizontalPanel();
	Label freeDaysLabel = new Label("עפ\"י ימי חופש:");
	InlineCheckBox sundayBox = new InlineCheckBox("ראשון"); 
	InlineCheckBox mondayBox = new InlineCheckBox("שני"); 
	InlineCheckBox tuesdayBox = new InlineCheckBox("שלישי"); 
	InlineCheckBox wednesdayBox = new InlineCheckBox("רביעי"); 
	InlineCheckBox thursdayBox = new InlineCheckBox("חמישי"); 
	InlineCheckBox minWindowsCB = new InlineCheckBox("מספר מינימלי של חלונות בין שיעורים");
	
	//InlineCheckBox minWindowsCB = new InlineCheckBox("מספר מינימלי של חלונות בין שיעורים");
	
	InlineCheckBox startTimeCB = new InlineCheckBox("שעת התחלה");
	
	ListBox startTimeLB = new ListBox();

	InlineCheckBox finishTimeCB = new InlineCheckBox("שעת סיום");

	ListBox finishTimeLB = new ListBox();

	VerticalPanel courseConstraintsPanel = new VerticalPanel();
	
	private SchedualerConstraintsStyle cStyle = Resources.INSTANCE.schedualerConstraintsStyle();

	public SchedulerConstraintsView(){
		
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

    	//this.add(daysOffCB);
    	
    	freeDaysPanel.add(freeDaysLabel);
    	freeDaysPanel.add(sundayBox);
    	freeDaysPanel.add(mondayBox);
    	freeDaysPanel.add(tuesdayBox);
    	freeDaysPanel.add(wednesdayBox);
    	freeDaysPanel.add(thursdayBox);
    	this.add(freeDaysPanel);
    	this.add(minWindowsCB);
    	
    	HorizontalPanel startTimePanel = new HorizontalPanel();
    	startTimePanel.add(startTimeCB);
    	startTimePanel.add(startTimeLB);
    	
    	HorizontalPanel finishTimePanel = new HorizontalPanel();
    	finishTimePanel.add(finishTimeCB);
    	finishTimePanel.add(finishTimeLB);

    	this.add(startTimePanel);
    	this.add(finishTimePanel);
    	
    	freeDaysLabel.addStyleName(cStyle.onlyCheckBox());
    	sundayBox.addStyleName(cStyle.onlyCheckBox());
    	mondayBox.addStyleName(cStyle.onlyCheckBox());
    	tuesdayBox.addStyleName(cStyle.onlyCheckBox());
    	wednesdayBox.addStyleName(cStyle.onlyCheckBox());
    	thursdayBox.addStyleName(cStyle.onlyCheckBox());
    	
    	minWindowsCB.addStyleName(cStyle.onlyCheckBox());
    	
    	startTimeCB.addStyleName(cStyle.timeCheckBox());
    	finishTimeCB.addStyleName(cStyle.timeCheckBox());
    	
    	startTimeLB.addStyleName(cStyle.timeListBox());
    	finishTimeLB.addStyleName(cStyle.timeListBox());
    	
    	startTimeCB.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
		        Boolean checked = ((InlineCheckBox) event.getSource()).getValue();
		        startTimeLB.setEnabled(checked.booleanValue());
			}
    		
    	});
    	
    	finishTimeCB.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
		        Boolean checked = ((InlineCheckBox) event.getSource()).getValue();
		        finishTimeLB.setEnabled(checked.booleanValue());
			}
    		
    	});
    	
    	this.setStyleName(cStyle.constraintsPanel());

    }
    
    public void displayCoursesConstraints(List<Course> selectedCourses){
    	for (Course course : selectedCourses){
    		Label courseName = new Label(course.getName());
    		courseConstraintsPanel.add(courseName);
    	}
    }
	

}
