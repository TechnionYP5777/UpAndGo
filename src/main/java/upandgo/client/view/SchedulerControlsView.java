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

import upandgo.client.resources.Resources;
import upandgo.client.resources.Resources.SchedualerControlsStyle;
import upandgo.shared.entities.LocalTime;
import upandgo.shared.model.scedule.ConstraintsPool;

public class SchedulerControlsView extends VerticalPanel{

	private SchedualerControlsStyle scStyle = Resources.INSTANCE.schedualerControlsStyle();
	
	private ConstraintsPool cp;
	private LocalTime startTime;
	private LocalTime endTime;
	private List<Boolean> vectorDaysOff; 
	
	Button buildSchedule = new Button("<i class=\"fa fa-calendar\" aria-hidden=\"true\"></i>&nbsp;&nbsp;בנה מערכת");
	Button clearSchedule = new Button("<i class=\"fa fa-times\" aria-hidden=\"true\"></i>&nbsp;&nbsp;נקה מערכת");
	Button setConstrains = new Button("<i class=\"fa fa-exclamation-circle\" aria-hidden=\"true\"></i>&nbsp;&nbsp;הגדר אילוצים");
	
	Well scheduleWell = new Well();
	Button nextSchedule = new Button("למערכת הבאה&nbsp;&nbsp;<i class=\"fa fa-arrow-left\" aria-hidden=\"true\"></i>");
	Button prevSchedule = new Button("<i class=\"fa fa-arrow-right\" aria-hidden=\"true\"></i>&nbsp;&nbsp;למערכת הקודמת");
	Button saveSchedule = new Button("<i class=\"fa fa-floppy-o\" aria-hidden=\"true\"></i>&nbsp;&nbsp;שמור מערכת");
	
	final Modal constraintsBox = new Modal();
	Button constraintsBoxSaveButton = new Button("<i class=\"fa fa-floppy-o\" aria-hidden=\"true\"></i>&nbsp;&nbsp;שמור שינויים");
	Button constraintsBoxCancelButton = new Button("<i class=\"fa fa-times\" aria-hidden=\"true\"></i>&nbsp;&nbsp;בטל שינויים");

	SchedulerConstraintsView schedualerConstraintsView;

	Button exportSchedule = new Button("<i class=\"fa fa-calendar\" aria-hidden=\"true\"></i>&nbsp;&nbsp;יצוא מערכת");

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
		this.add(exportSchedule);
		//this.add(new SchedualerConstraintsView());
		
		buildSchedule.setStyleName("btn btn-success");
		clearSchedule.setStyleName("btn btn-danger");
		setConstrains.setStyleName("btn btn-warning");
		nextSchedule.setStyleName("btn btn-primary");
		prevSchedule.setStyleName("btn btn-primary");
		saveSchedule.setStyleName("btn btn-info");
		exportSchedule.setStyleName("btn btn-info");
		
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
		constraintsBoxSaveButton.setStyleName("btn btn-success");
		constraintsBoxCancelButton.setStyleName("btn btn-danger");
		 
		constraintsBoxCancelButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				constraintsBox.hide();
			}
		});
		constraintsBoxFooter.add(constraintsBoxCancelButton);
		constraintsBoxFooter.add(constraintsBoxSaveButton);
		constraintsBox.setFade(true);
		constraintsBox.setTitle("הגדרת אילוצים");
		ModalBody schedualerConstraintsViewModalBody = new ModalBody();
		schedualerConstraintsViewModalBody.add(schedualerConstraintsView);
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