package presenter.client;


import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author danabra
 * @since 7-04-17
 * 
 * GUI class for list of courses and course selection 
 * 
 */

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;

public class CourseSelectionGUI extends LayoutPanel {
    private ListBox ccl = new ListBox(); //chosen courses
    private Label cc = new Label("קורסים שנבחרו:");
    private ListBox scl = new ListBox(); //all courses list
    private Label sc = new Label("בחר קורסים:");
    private MultiWordSuggestOracle coursesSugg = new MultiWordSuggestOracle();//course suggestion by name
    private TextBox searchCourse = new TextBox();
    private Collection<String> courses;
    @SuppressWarnings("unused")
	public CourseSelectionGUI(){
    	courses = new ArrayList<String>();
    	courses.add("אקורס1");
    	courses.add("אקורס2");
    	courses.add("אקורס3");
    	courses.add("בקורס4");
    	courses.add("בקורס5");
    	courses.add("גקורס6");
    	courses.add("גקורס7");
    	courses.add("גקורס8");
    	InitializePanel();
    }
    private void InitializePanel(){
    	// chosen course list initialization
    	ccl.setMultipleSelect(true);

    	ccl.setWidth("100%");
    	ccl.setHeight("25em");
    	ccl.addDoubleClickHandler(new DoubleClickHandler() {
			
    		//need to keep list sorted
			@SuppressWarnings("synthetic-access")
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				if(ccl.getSelectedValue() != null){
					scl.addItem(ccl.getSelectedValue());
					ccl.removeItem(ccl.getSelectedIndex());
				}
				
			}
		});
    	
    	//all courses list initialization
    	scl.setMultipleSelect(true);
    	for(String s: courses) //IMPORTANT : cent char crashes the app so don't sparatanize 
			scl.addItem(s);
    	scl.setWidth("100%");
    	scl.addDoubleClickHandler(new DoubleClickHandler() {
			
    		//need to keep list sorted
			@SuppressWarnings("synthetic-access")
			@Override
			public void onDoubleClick(@SuppressWarnings("unused") DoubleClickEvent e) { // maybe use button?
				if(scl.getSelectedValue() != null){
					ccl.addItem(scl.getSelectedValue());
					scl.removeItem(scl.getSelectedIndex());
				}
				
			}
		});
    	
    	//initializing course suggestion
    	coursesSugg.addAll(courses);
    	
    	//search course text field initialization
    	searchCourse.setHeight("1em");
    	searchCourse.setWidth("100%");
    	searchCourse.setTitle("חפש קורסים");
    	searchCourse.getElement().setPropertyString("placeholder", "חפש קורסים...");
    	searchCourse.addKeyUpHandler(new KeyUpHandler() {
			
			@SuppressWarnings("synthetic-access")
			@Override
			public void onKeyUp(@SuppressWarnings("unused") KeyUpEvent __) {
				scl.clear();
				if("".equals(searchCourse.getValue()))
					for (String s : courses)//IMPORTANT : cent char crashes the app so don't sparatanize 
						scl.addItem(s);
				else
					coursesSugg.requestSuggestions(new Request(searchCourse.getValue()), new SuggestOracle.Callback() {
						@Override
						public void onSuggestionsReady(@SuppressWarnings({ "unused", "hiding" }) Request __, Response r) {
							for (Suggestion s : r.getSuggestions())//IMPORTANT : cent char crashes the app so don't sparatanize 
								scl.addItem(s.getReplacementString());
						}
					});
			}
		});
    	
    	//adding widgets to panel
    	this.add(cc);
	    this.add(ccl);
	    this.add(sc);
	    this.add(searchCourse);
	    this.add(scl);
	    this.setWidgetTopBottom(cc, 0, Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(ccl, 1.5,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(sc, 30,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(searchCourse, 31.5,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(scl, 34.5,  Unit.EM, 2, Unit.EM);
    }
}
