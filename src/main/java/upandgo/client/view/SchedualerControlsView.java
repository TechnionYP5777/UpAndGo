package upandgo.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

import upandgo.client.Resources;
import upandgo.client.Resources.SchedualerControlsStyle;

public class SchedualerControlsView extends HorizontalPanel{

	private SchedualerControlsStyle scStyle = Resources.INSTANCE.schedualerControlsStyle();

	Button buildSchedule = new Button("<i class=\"fa fa-calendar\" aria-hidden=\"true\"></i>&nbsp;&nbsp;בנה מערכת");
	Button clearSchedule = new Button("<i class=\"fa fa-times\" aria-hidden=\"true\"></i>&nbsp;&nbsp;נקה מערכת");
	Button nextSchedule = new Button("<i class=\"fa fa-arrow-right\" aria-hidden=\"true\"></i>&nbsp;&nbsp;למערכת הבאה");
	Button prevSchedule = new Button("למערכת הקודמת&nbsp;&nbsp;<i class=\"fa fa-arrow-left\" aria-hidden=\"true\"></i>");
	Button saveSchedule = new Button("<i class=\"fa fa-floppy-o\" aria-hidden=\"true\"></i>&nbsp;&nbsp;שמור מערכת");

	public SchedualerControlsView(){
    	InitializePanel();
    	scStyle.ensureInjected();
    }


	private void InitializePanel() {
		this.setHorizontalAlignment(ALIGN_CENTER);
		this.setStyleName(scStyle.SchedualerControlsPanel());
		this.add(buildSchedule);
		this.add(clearSchedule);
		this.add(nextSchedule);
		this.add(prevSchedule);
		this.add(saveSchedule);
		
		buildSchedule.removeStyleName("gwt-Button");
		buildSchedule.addStyleName("btn btn-success");
		clearSchedule.removeStyleName("gwt-Button");
		clearSchedule.addStyleName("btn btn-danger");
		nextSchedule.removeStyleName("gwt-Button");
		nextSchedule.addStyleName("btn btn-primary");
		prevSchedule.removeStyleName("gwt-Button");
		prevSchedule.addStyleName("btn btn-primary");
		saveSchedule.removeStyleName("gwt-Button");
		saveSchedule.addStyleName("btn btn-info");
		
		buildSchedule.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				buildSchedule.setHTML("<i class=\"fa fa-spinner fa-spin\" aria-hidden=\"true\"></i>&nbsp;&nbsp;בנה מערכת");
				Timer timer = new Timer() {
					@Override
				    public void run() {
						buildSchedule.setHTML("<i class=\"fa fa-calendar-check-o\" aria-hidden=\"true\"></i>&nbsp;&nbsp;בנה מערכת");
				    }
				};
				timer.schedule(2000);
			}
    		
    	});

	}
	
	
}
