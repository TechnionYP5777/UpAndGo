package upandgo.client.view;

import org.gwtbootstrap3.client.ui.ModalComponent;
import org.gwtbootstrap3.client.ui.TextArea;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AdminView extends VerticalPanel implements ModalComponent {

	Button updateDatabaseButton = new Button("עדכן מסד נתונים");
	TextArea updateDatabaseTextArea = new TextArea();
	
	public AdminView(){
    	InitializePanel(); 	
    }
	
    private void InitializePanel(){
    	this.add(updateDatabaseButton);
    	this.add(updateDatabaseTextArea);

    }
    

}
