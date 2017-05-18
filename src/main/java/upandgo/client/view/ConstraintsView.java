package upandgo.client.view;

import org.gwtbootstrap3.client.ui.InlineCheckBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

import upandgo.client.Resources;
import upandgo.client.Resources.ConstraintsStyle;


/**
 * @author Yaniv Levinsky
 *
 */

public class ConstraintsView extends HorizontalPanel{

	Label constraintsTitle = new Label("העדפות לבניית המערכת:");
	
	InlineCheckBox daysOffCB = new InlineCheckBox("ימי חופש");

	InlineCheckBox minWindowsCB = new InlineCheckBox("מספר מינימלי של חלונות");

	InlineCheckBox startTimeCB = new InlineCheckBox("שעת התחלה");
	
	ListBox startTimeLB = new ListBox();

	InlineCheckBox finishTimeCB = new InlineCheckBox("שעת סיום");

	ListBox finishTimeLB = new ListBox();

	
	private ConstraintsStyle cStyle = Resources.INSTANCE.constraintsStyle();

	public ConstraintsView(){
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

    	this.add(constraintsTitle);
    	this.add(daysOffCB);
    	this.add(minWindowsCB);
    	this.add(startTimeCB);
    	this.add(startTimeLB);
    	this.add(finishTimeCB);
    	this.add(finishTimeLB);
    	
    	constraintsTitle.addStyleName(cStyle.constraintsLabel());
    	daysOffCB.addStyleName(cStyle.onlyCheckBox());
    	minWindowsCB.addStyleName(cStyle.onlyCheckBox());

    	startTimeCB.addStyleName(cStyle.timeCheckBox());
    	finishTimeCB.addStyleName(cStyle.timeCheckBox());
    	
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
    	//this.getElement().getStyle().setBackgroundColor("WhiteSmoke");
    	//this.getElement().getStyle().setWidth(100, Unit.PCT);

    }
	

}
