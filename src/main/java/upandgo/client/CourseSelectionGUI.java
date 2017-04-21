package upandgo.client;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

import upandgo.client.common.ColumnDefinition;
import upandgo.client.view.CourseListView;
import upandgo.shared.entities.course.Course;

public class CourseSelectionGUI extends LayoutPanel implements CourseListView<Course> {
    private ListBox ccl = new ListBox(); //chosen courses
    private Label cc = new Label("קורסים שנבחרו:");
    private ListBox scl = new ListBox(); //all courses list
    private Label sc = new Label("בחר קורסים:");
    private ListBox faculties = new ListBox(); //chosen courses
    private MultiWordSuggestOracle coursesSugg = new MultiWordSuggestOracle();//course suggestion by name
    private TextBox searchCourse = new TextBox();
    private Collection<String> courses; //all the courses that user didn't choose yet
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
					courses.add(ccl.getSelectedValue());
					ccl.removeItem(ccl.getSelectedIndex());
					coursesSugg.clear();
					coursesSugg.addAll(courses);
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
					courses.remove(scl.getSelectedValue());
					scl.removeItem(scl.getSelectedIndex());
					coursesSugg.clear();
					coursesSugg.addAll(courses);
					
				}
				
			}
		});
    	
    	//initializing course suggestion
    	coursesSugg.addAll(courses);
    	
    	//initializing faculty selection
    	faculties.setWidth("100%");
    	faculties.setHeight("2em");
    	faculties.addItem("פקולטה");
    	faculties.addItem("מדעי המחשב");
    	faculties.addItem("חשמל");
    	faculties.addItem("פיזיקה");
    	faculties.addItem("מתמטיקה");
    	
    	//search course text field initialization
    	searchCourse.setHeight("1em");
    	searchCourse.setWidth("97%");
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
					coursesSugg.requestSuggestions(new Request(searchCourse.getValue()), new SuggestOracle.Callback() {//need to improve search
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
	    this.add(faculties);
	    this.add(searchCourse);
	    this.add(scl);
	    this.setWidgetTopBottom(cc, 0, Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(ccl, 1.5,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(sc, 30,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(faculties, 31.5,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(searchCourse, 34.5,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(scl, 37.5,  Unit.EM, 2, Unit.EM);
    }
	@Override
	public void setPresenter(upandgo.client.view.CourseListView.Presenter<Course> presenter) {
		// TODO Auto-generated method stub
	}
	@Override
	public void setColumnDefinitions(List<ColumnDefinition<Course>> columnDefinitions) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSelectedCourses(List<Course> selectedCourses) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setNotSelectedCourses(List<Course> notSelectedCourses) {
		// TODO Auto-generated method stub
		
	}
}
