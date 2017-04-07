package presenter.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;

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

public class CourseSelectionGUI extends LayoutPanel {
    private ListBox ccl = new ListBox();
    private Label cc = new Label("קורסים שנבחרו:");
    private ListBox scl = new ListBox();
    private Label sc = new Label("בחר קורסים:");
    public CourseSelectionGUI(){
    	InitializePanel();
    }
    private void InitializePanel(){
    	ccl.setMultipleSelect(true);
    	ccl.addItem("קורס1");
    	ccl.addItem("קורס2");
    	ccl.addItem("קורס3");
    	ccl.addItem("קורס4");
    	ccl.setWidth("100%");
    	ccl.setHeight("25em");
    	scl.setMultipleSelect(true);
    	scl.addItem("קורס1");
    	scl.addItem("קורס2");
    	scl.addItem("קורס3");
    	scl.addItem("קורס4");
    	scl.setWidth("100%");
    	this.add(cc);
	    this.add(ccl);
	    this.add(sc);
	    this.add(scl);
	    this.setWidgetTopBottom(cc, 0, Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(ccl, 1.5,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(sc, 30,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(scl, 31.5,  Unit.EM, 0, Unit.EM);
    }
}
