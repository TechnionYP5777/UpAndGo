package upandgo.client.view;


import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.Well;
import org.gwtbootstrap3.client.ui.constants.WellSize;
import org.gwtbootstrap3.client.ui.html.Text;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

import upandgo.client.Resources;
import upandgo.client.Resources.SchedualerControlsStyle;
import upandgo.shared.entities.LocalTime;
import upandgo.shared.model.scedule.constraintsPool;

public class SchedulerControlsView extends VerticalPanel{

	private SchedualerControlsStyle scStyle = Resources.INSTANCE.schedualerControlsStyle();
	
	private constraintsPool cp;
	private LocalTime startTime;
	private LocalTime endTime;
	private List<Boolean> vectorDaysOff; 
	
	Button buildSchedule = new Button("<i class=\"fa fa-calendar\" aria-hidden=\"true\"></i>&nbsp;&nbsp;בנה מערכת");
	Button clearSchedule = new Button("<i class=\"fa fa-times\" aria-hidden=\"true\"></i>&nbsp;&nbsp;נקה מערכת");
	Button setConstrains = new Button("<i class=\"fa fa-exclamation-circle\" aria-hidden=\"true\"></i>&nbsp;&nbsp;הגדר אילוצים");
	
	Well scheduleWell = new Well();
	Button nextSchedule = new Button("מערכת באה&nbsp;&nbsp;<i class=\"fa fa-arrow-left\" aria-hidden=\"true\"></i>");
	Button prevSchedule = new Button("<i class=\"fa fa-arrow-right\" aria-hidden=\"true\"></i>&nbsp;&nbsp;מערכת קודמת");
	Button saveSchedule = new Button("<i class=\"fa fa-floppy-o\" aria-hidden=\"true\"></i>&nbsp;&nbsp;שמור מערכת");
	
	final Modal constraintsBox = new Modal();
		
	SchedulerConstraintsView schedualerConstraintsView;

	public SchedulerControlsView(){
		this.schedualerConstraintsView = new SchedulerConstraintsView();
    	InitializePanel();
    	scStyle.ensureInjected();
    }
	
	public SchedulerControlsView(SchedulerConstraintsView scv){
		this.schedualerConstraintsView = scv;
    	InitializePanel();
    	InitializeConstraintsBox();
    	scStyle.ensureInjected();
    }


	private void InitializePanel() {
		this.setHorizontalAlignment(ALIGN_CENTER);
		this.setStyleName(scStyle.SchedulerControlsPanel());
		this.add(buildSchedule);
		this.add(clearSchedule);
		this.add(setConstrains);
		
		this.add(scheduleWell);
		this.add(nextSchedule);
		this.add(prevSchedule);
		this.add(saveSchedule);
		//this.add(new SchedualerConstraintsView());
		
		buildSchedule.setStyleName("btn btn-success");
		clearSchedule.setStyleName("btn btn-danger");
		setConstrains.setStyleName("btn btn-warning");
		nextSchedule.setStyleName("btn btn-primary");
		prevSchedule.setStyleName("btn btn-primary");
		saveSchedule.setStyleName("btn btn-info");
		
		scheduleWell.addStyleName(scStyle.SchedulerIndexWell());
		scheduleWell.setSize(WellSize.SMALL);
		scheduleWell.add(new Text(" "));
	

		nextSchedule.setEnabled(false);
		prevSchedule.setEnabled(false);

		buildSchedule.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				buildSchedule.setHTML("<i class=\"fa fa-spinner fa-spin\" aria-hidden=\"true\"></i>&nbsp;&nbsp;בנה מערכת");
			}
    		
    	});
		
		setConstrains.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				constraintsBox.show();
			}
		});

	}
	
	private void InitializeConstraintsBox(){
		ModalFooter constraintsBoxFooter = new ModalFooter();
		Button constraintsBoxButton = new Button("שמור וסגור", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (schedualerConstraintsView.startTimeCB.getValue()) {
					int index = schedualerConstraintsView.startTimeLB.getSelectedIndex();
					startTime = LocalTime.of(index+8, 30);	
				} else {
					startTime = null;
				}
				if (schedualerConstraintsView.finishTimeCB.getValue()) {
					int index = schedualerConstraintsView.finishTimeLB.getSelectedIndex();
					endTime = LocalTime.of(index+8, 30);
				} else {
					endTime = null;
				}
								
				cp = new constraintsPool(false, schedualerConstraintsView.minWindowsCB.getValue(), startTime, endTime);
				vectorDaysOff = new ArrayList();
				vectorDaysOff.add(schedualerConstraintsView.sundayBox.getValue());
				vectorDaysOff.add(schedualerConstraintsView.mondayBox.getValue());
				vectorDaysOff.add(schedualerConstraintsView.tuesdayBox.getValue());
				vectorDaysOff.add(schedualerConstraintsView.wednesdayBox.getValue());
				vectorDaysOff.add(schedualerConstraintsView.thursdayBox.getValue());
				cp.addVectorDaysOff(vectorDaysOff);
				int test = schedualerConstraintsView.coursesConstraintsPanel.getWidgetCount();
				
				constraintsBox.hide();
				
			}
		});
		constraintsBoxFooter.add(constraintsBoxButton);
		constraintsBox.setFade(true);
		constraintsBox.setTitle("הגדרת אילוצים");
		ModalBody schedualerConstraintsViewModalBody = new ModalBody();
		schedualerConstraintsViewModalBody.add(schedualerConstraintsView);
		//constraintsBox.add(schedualerConstraintsView);
		constraintsBox.add(schedualerConstraintsViewModalBody);
		constraintsBox.add(constraintsBoxFooter);
	}
	
	public void setPrevEnable(boolean enable){
		prevSchedule.setEnabled(enable);;
	}
	
	public void setNextEnable(boolean enable){
		nextSchedule.setEnabled(enable);
	}
	
	public void setCurrentScheduleIndex(int index, int max){
		scheduleWell.clear();
		if (index!=0){
			scheduleWell.add(new Text(max + " / " + index));
		}
		
	}
	
	public void scheduleBuilt(){
		buildSchedule.setHTML("<i class=\"fa fa-calendar-check-o\" aria-hidden=\"true\"></i>&nbsp;&nbsp;בנה מערכת");
	}
	
}
