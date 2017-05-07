package upandgo.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

import upandgo.client.Resources.SchedualerControlsStyle;

public class SchedualerControlsGUI extends HorizontalPanel{

	private SchedualerControlsStyle scStyle = Resources.INSTANCE.schedualerControlsStyle();

	Button buildSchedule = new Button("בנה מערכת");
	
	public SchedualerControlsGUI(){
    	InitializePanel();
    	scStyle.ensureInjected();
    }


	private void InitializePanel() {
		this.setHorizontalAlignment(ALIGN_RIGHT);
		this.setStyleName(scStyle.SchedualerControlsPanel());
		this.add(buildSchedule);
	}
	
}
