package upandgo.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

import upandgo.client.Resources.SchedualerControlsStyle;

public class SchedualerControlsGUI extends HorizontalPanel{

	private SchedualerControlsStyle scStyle = Resources.INSTANCE.schedualerControlsStyle();

	Button buildSchedule = new Button("בנה מערכת");
	Button clearSchedule = new Button("נקה מערכת");
	Button nextSchedule = new Button("למערכת הבאה");
	Button prevSchedule = new Button("למערכת הקודמת");
	Button saveSchedule = new Button("שמור מערכת");

	public SchedualerControlsGUI(){
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

	}
	
	
}
