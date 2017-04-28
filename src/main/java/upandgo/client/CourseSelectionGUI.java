package upandgo.client;
import upandgo.client.presenter.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.FontWeight;

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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.HasMouseMoveHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
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
import upandgo.shared.entities.course.CourseId;

public class CourseSelectionGUI extends LayoutPanel implements CourseListPresenter.Display {
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
    	Resources.INSTANCE.courseListStyle().ensureInjected();

    }
    private void InitializePanel(){
    	// chosen course list initialization
    	ccl.setMultipleSelect(true);
    	ccl.addStyleName(Resources.INSTANCE.courseListStyle().ChosenCourses());
    	ccl.setWidth("100%");
    	ccl.setHeight("25em");
    	ccl.addMouseMoveHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				SelectElement selectElement = SelectElement.as(ccl.getElement());
		    	NodeList<OptionElement> options = selectElement.getOptions();
				int y = event.getY()-4;
				if(y<0)
					y=0;
				int row = y/options.getItem(0).getScrollHeight();
				Log.debug("y coor = " + (event.getY()-2));
				Log.debug("offsetheight = " + options.getItem(1).getClientHeight());
		    	for (int i = 0; i < options.getLength(); i++) {
		    		if(i == row){
		    			options.getItem(i).getStyle().setBackgroundColor("#577F92");
		    			//options.getItem(i).getStyle().setFontSize(1.13, Unit.EM);
		    			options.getItem(i).getStyle().setFontWeight(Style.FontWeight.BOLD);
		    		}
		    		else{
		    			options.getItem(i).getStyle().setBackgroundColor("white");
		    			//options.getItem(i).getStyle().setFontSize(1, Unit.EM);
		    			options.getItem(i).getStyle().setFontWeight(Style.FontWeight.NORMAL);
		    		}
		   	     }
				
			}
		});
    	ccl.addMouseOutHandler(new MouseOutHandler() {
    		SelectElement selectElement = SelectElement.as(ccl.getElement());
        	NodeList<OptionElement> options = selectElement.getOptions();
			@Override
			public void onMouseOut(MouseOutEvent event) {
				for (int i = 0; i < options.getLength(); i++) {
	    			options.getItem(i).getStyle().setBackgroundColor("white");
	    			options.getItem(i).getStyle().setFontWeight(Style.FontWeight.NORMAL);
		   	     }
				
				
			}
		});

    	//all courses list initialization
    	scl.setMultipleSelect(true);
    	for(String s: courses) //IMPORTANT : cent char crashes the app so don't sparatanize 
			scl.addItem(s);
    	scl.setWidth("100%");
    	scl.addMouseMoveHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				SelectElement selectElement = SelectElement.as(scl.getElement());
		    	NodeList<OptionElement> options = selectElement.getOptions();
				int y = event.getY()-4;
				if(y<0)
					y=0;
				int row = y/options.getItem(0).getScrollHeight();
				Log.debug("y coor = " + (event.getY()-2));
				Log.debug("offsetheight = " + options.getItem(1).getClientHeight());
		    	for (int i = 0; i < options.getLength(); i++) {
		    		if(i == row){
		    			options.getItem(i).getStyle().setBackgroundColor("#577F92");
		    			//options.getItem(i).getStyle().setFontSize(1.13, Unit.EM);
		    			options.getItem(i).getStyle().setFontWeight(Style.FontWeight.BOLD);
		    		}
		    		else{
		    			options.getItem(i).getStyle().setBackgroundColor("white");
		    			//options.getItem(i).getStyle().setFontSize(1, Unit.EM);
		    			options.getItem(i).getStyle().setFontWeight(Style.FontWeight.NORMAL);
		    		}
		   	     }
				
			}
		});
    	scl.addMouseOutHandler(new MouseOutHandler() {
    		SelectElement selectElement = SelectElement.as(scl.getElement());
        	NodeList<OptionElement> options = selectElement.getOptions();
			@Override
			public void onMouseOut(MouseOutEvent event) {
				for (int i = 0; i < options.getLength(); i++) {
	    			options.getItem(i).getStyle().setBackgroundColor("white");
	    			options.getItem(i).getStyle().setFontWeight(Style.FontWeight.NORMAL);
		   	     }
				
				
			}
		});
    	
    	SelectElement selectElement = SelectElement.as(scl.getElement());
    	NodeList<OptionElement> options = selectElement.getOptions();
    	//tooltip for now
    	for (int i = 0; i < options.getLength(); i++) {
    		options.getItem(i).getStyle().setBackgroundColor("white");
    		options.getItem(i).setDefaultSelected(false);
   	     	options.getItem(i).setTitle("helooo " + i);
   	     }
    	
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
    	searchCourse.setHeight("2em");
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
					coursesSugg.requestSuggestions(new Request(searchCourse.getValue()), new SuggestOracle.Callback() {//need to improve search
						@Override
						public void onSuggestionsReady(@SuppressWarnings({ "unused", "hiding" }) Request __, Response r) {
							for (Suggestion s : r.getSuggestions())//IMPORTANT : cent char crashes the app so don't sparatanize 
								scl.addItem(s.getReplacementString());
						}
					});
			}
		});
    	scl.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				scl.setSelectedIndex(scl.getSelectedIndex());
				
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
    
    // Implementation of Display
	@SuppressWarnings("unchecked")
	@Override
	public <T extends HasDoubleClickHandlers & HasMouseMoveHandlers & HasMouseOutHandlers> T getSelectedCoursesList() {
		return (T) ccl;
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T extends HasDoubleClickHandlers & HasMouseMoveHandlers & HasMouseOutHandlers> T getNotSelectedCoursesList() {
		return (T) scl;
	}
	@Override
	public HasChangeHandlers getFacultyDropList() {
		return faculties;
	}
	@Override
	public void setSelectedCourses(List<CourseId> courses) {
		ccl.clear();
		for(CourseId c : courses){
			ccl.addItem(c.getTitle());  // I would have used java stream but there is no addAll method in ListBox
		}
		
	}
	@Override
	public void setNotSelectedCourses(List<CourseId> courses) {
		scl.clear();
		for(CourseId c : courses){
			scl.addItem(c.getTitle());  // I would have used java stream but there is no addAll method in ListBox
		}
		
	}
	@Override
	public void setFaculties(List<String> faculties) {
		this.faculties.clear();
		for(String f : faculties){
			this.faculties.addItem(f);
		}
		
	}
	@Override
	public int getSelectedCourseRow(DoubleClickEvent event) {
		return ccl.getSelectedIndex();
	}
	@Override
	public int getUnselectedCourseRow(DoubleClickEvent event) {
		return scl.getSelectedIndex();
	}
	@Override
	public int getHoveredSelectedCourseRow(MouseMoveEvent event) {
    	SelectElement selectElement = SelectElement.as(ccl.getElement());
    	NodeList<OptionElement> options = selectElement.getOptions();
		return event.getY()/options.getItem(0).getOffsetHeight();
	}
	@Override
	public int getHoveredNotSelectedCourseRow(MouseMoveEvent event) {
    	SelectElement selectElement = SelectElement.as(ccl.getElement());
    	NodeList<OptionElement> options = selectElement.getOptions();
		return event.getY()/options.getItem(0).getOffsetHeight();
	}
	@Override
	public int getSelectedFacultyRow(ChangeEvent event) {
		return faculties.getSelectedIndex();
	}
}
