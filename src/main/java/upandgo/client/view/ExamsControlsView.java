package upandgo.client.view;


import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

import upandgo.client.Resources;
import upandgo.client.Resources.ExamsControlsStyle;

public class ExamsControlsView extends VerticalPanel{

	private ExamsControlsStyle ecStyle = Resources.INSTANCE.examsControlsStyle();

	Button examsButton = new Button("לוח מבחנים");
	
	public ExamsControlsView(){
    	InitializePanel();
    	ecStyle.ensureInjected();
    }

	private void InitializePanel() {
		this.setStyleName(ecStyle.examsControlsPanel());

		this.setVerticalAlignment(ALIGN_BOTTOM);
		this.setHorizontalAlignment(ALIGN_CENTER);
		//this.setStyleName(scStyle.SchedulerControlsPanel());
		examsButton.setStyleName("btn btn-warning");

		this.add(examsButton);


	}
	
}
