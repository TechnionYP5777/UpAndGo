package presenter.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CourseSelectionGUI extends LayoutPanel {
    private ListBox ccl = new ListBox();
    private Label cc = new Label("קורסים שנבחרו:");
    private ListBox scl = new ListBox();
    private Label sc = new Label("בחר קורסים:");
    private MultiWordSuggestOracle courses = new MultiWordSuggestOracle();
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
    	scl.addItem("קורס5");
    	scl.addItem("קורס6");
    	scl.addItem("קורס7");
    	scl.addItem("קורס8");
    	scl.setWidth("100%");
    	courses.add("קורס5");
    	courses.add("קורס6");
    	courses.add("קורס7");
    	courses.add("קורס8");
    	SuggestBox sb = new SuggestBox(courses);
    	sb.setHeight("1em");
    	sb.setWidth("100%");

    	this.add(cc);
	    this.add(ccl);
	    this.add(sc);
	    this.add(sb);
	    this.add(scl);
	    this.setWidgetTopBottom(cc, 0, Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(ccl, 1.5,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(sc, 30,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(sb, 31.5,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(scl, 34.5,  Unit.EM, 2, Unit.EM);
    }
}
