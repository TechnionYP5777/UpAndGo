package presenter.client;


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
    private ListBox ccl = new ListBox();
    private Label cc = new Label("קורסים שנבחרו:");
    private ListBox scl = new ListBox();
    private Label sc = new Label("בחר קורסים:");
    private MultiWordSuggestOracle courses = new MultiWordSuggestOracle();
    private TextBox searchCourse = new TextBox();
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
    	searchCourse.setHeight("1em");
    	searchCourse.setWidth("100%");
    	searchCourse.setTitle("חפש קורסים");
    	searchCourse.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				scl.clear();
				if(searchCourse.getValue().equals("")){
			    	scl.addItem("קורס5");
			    	scl.addItem("קורס6");
			    	scl.addItem("קורס7");
			    	scl.addItem("קורס8");
				}
				else{
					courses.requestSuggestions(new Request(searchCourse.getValue()), new SuggestOracle.Callback(){

						@Override
						public void onSuggestionsReady(Request request, Response response) {
							for(Suggestion s : response.getSuggestions()){
								scl.addItem(s.getReplacementString());
							}
							
						}
						
					});
				}
			}
		});
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
