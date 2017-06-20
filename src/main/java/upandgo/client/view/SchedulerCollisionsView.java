package upandgo.client.view;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.InlineCheckBox;
import org.gwtbootstrap3.client.ui.ModalComponent;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import upandgo.client.Resources;
import upandgo.client.Resources.SchedualerConstraintsStyle;
import upandgo.shared.model.scedule.CourseTuple;


/**
 * @author Yaniv Levinsky
 *
 */

public class SchedulerCollisionsView extends VerticalPanel implements ModalComponent{
	
	//InlineCheckBox daysOffCB = new InlineCheckBox("מספר מקסימלי של ימים חופשיים");

	//HorizontalPanel freeDaysPanel = new HorizontalPanel();
	Label freeDaysLabel = new Label("קיימות התנגשויות שלא מאפשרות להשלים את בניית המערכת. אנא וותר על אחד מהקורסים הבאים על מנת לפתור אותן:");
	List<CourseTuple> solvers;
	List<RadioButton> radios;

	
	private SchedualerConstraintsStyle cStyle = Resources.INSTANCE.schedualerConstraintsStyle();

	public SchedulerCollisionsView(){
		
	    //InitializeTimeLBs();
		Log.info("SchedCol/1");
		radios = new ArrayList<RadioButton>();
    	InitializePanel();
    	cStyle.ensureInjected();
    	Log.info("SchedCol/2");
    	
    }
	
	public List<RadioButton> getRadios(){
		return radios;
	}
	
	public List<CourseTuple> getSolversTuples(){
		return solvers;
	}
	
	public void updateList(List<CourseTuple> solvers){
		Log.info("SchedCol/3");
		this.solvers = solvers;
		
		for(RadioButton r : radios){
			this.remove(r);
		}
		radios.clear();
		
		for(CourseTuple s : solvers){
    		RadioButton r = new RadioButton("radioGroup", s.getCourseId() + " - " + s.getCourseName());
    		radios.add(r);
    		this.add(r);
    	}
		
		// set first by default
		if(radios.get(0) != null)
			radios.get(0).setValue(true);
		 
		Log.info("SchedCol/4");
	}

	    
    private void InitializePanel(){
    	this.setHorizontalAlignment(ALIGN_RIGHT);

    	//this.add(daysOffCB);
    	
    	/*freeDaysPanel.add(freeDaysLabel);
    	freeDaysPanel.add(sundayBox);
    	freeDaysPanel.add(mondayBox);
    	freeDaysPanel.add(tuesdayBox);
    	freeDaysPanel.add(wednesdayBox);
    	freeDaysPanel.add(thursdayBox);
    	this.add(freeDaysPanel);*/
    	this.add(freeDaysLabel);
    	
    	
    	/*
    	HorizontalPanel startTimePanel = new HorizontalPanel();
    	startTimePanel.add(startTimeCB);
    	startTimePanel.add(startTimeLB);
    	
    	HorizontalPanel finishTimePanel = new HorizontalPanel();
    	finishTimePanel.add(finishTimeCB);
    	finishTimePanel.add(finishTimeLB);

    	this.add(startTimePanel);
    	this.add(finishTimePanel);
    	*/
    	
    	/*
    	minWindowsCB.addStyleName(cStyle.onlyCheckBox());
    	freeDaysPanel.addStyleName(cStyle.onlyCheckBox());

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
    	*/
    	this.setStyleName(cStyle.constraintsPanel());

    }
	

}
