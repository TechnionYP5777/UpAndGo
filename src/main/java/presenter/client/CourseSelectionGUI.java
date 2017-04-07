package presenter.client;

/**
 * 
 * @author danabra
 * @since 7-04-17
 * 
 * GUI class for list of courses and course selection 
 * 
 */


import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CourseSelectionGUI extends VerticalPanel {
    private ListBox lb = new ListBox();
    public CourseSelectionGUI(){
    	InitializePanel();
    }
    private void InitializePanel(){
	    lb.addItem("foo");
	    lb.addItem("bar");
	    lb.addItem("baz");
	    lb.addItem("toto");
	    lb.addItem("tintin");
	    this.add(lb);
    }
}
