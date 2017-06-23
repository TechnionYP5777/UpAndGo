package upandgo.client.view;

import java.util.List;

import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.InlineCheckBox;
import org.gwtbootstrap3.client.ui.ListBox;
import org.gwtbootstrap3.client.ui.ModalComponent;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import upandgo.client.Resources;
import upandgo.client.Resources.SchedualerConstraintsStyle;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.course.Course;


/**
 * @author Yaniv Levinsky
 *
 */

public class SchedulerConstraintsView extends VerticalPanel implements ModalComponent{
	
	//InlineCheckBox daysOffCB = new InlineCheckBox("מספר מקסימלי של ימים חופשיים");
	
	boolean test = false;
	
	HorizontalPanel freeDaysPanel = new HorizontalPanel();
	Label freeDaysLabel = new Label("עפ\"י ימי חופש:");
	InlineCheckBox sundayBox = new InlineCheckBox("ראשון"); 
	InlineCheckBox mondayBox = new InlineCheckBox("שני"); 
	InlineCheckBox tuesdayBox = new InlineCheckBox("שלישי"); 
	InlineCheckBox wednesdayBox = new InlineCheckBox("רביעי"); 
	InlineCheckBox thursdayBox = new InlineCheckBox("חמישי"); 
	InlineCheckBox minWindowsCB = new InlineCheckBox("מספר מינימלי של חלונות בין שיעורים");
	
	//InlineCheckBox minWindowsCB = new InlineCheckBox("מספר מינימלי של חלונות בין שיעורים");
	
	InlineCheckBox startTimeCB = new InlineCheckBox("שעת התחלה");
	
	ListBox startTimeLB = new ListBox();
	
	InlineCheckBox finishTimeCB = new InlineCheckBox("שעת סיום");

	ListBox finishTimeLB = new ListBox();
	
	Label coursesConstraintsLabel = new Label("בחירת שיעורים ידנית");

	VerticalPanel coursesConstraintsPanel = new VerticalPanel();
	
	private SchedualerConstraintsStyle cStyle = Resources.INSTANCE.schedualerConstraintsStyle();

	public SchedulerConstraintsView(){
		
	    InitializeTimeLBs();
    	InitializePanel();
    	cStyle.ensureInjected();
    	
    }
	
	private void InitializeTimeLBs(){
    	for(int i = 1; i<13; i++){
    		startTimeLB.addItem(Integer.toString(i+7)+":30");
    		finishTimeLB.addItem(Integer.toString(i+7)+":30");
    	}
    	startTimeLB.setEnabled(false);
    	finishTimeLB.setEnabled(false);

	}
	    
    private void InitializePanel(){
    	this.setHorizontalAlignment(ALIGN_RIGHT);

    	//this.add(daysOffCB);
    	
    	freeDaysPanel.setVerticalAlignment(ALIGN_MIDDLE);
    	freeDaysPanel.add(freeDaysLabel);
    	freeDaysPanel.add(sundayBox);
    	freeDaysPanel.add(mondayBox);
    	freeDaysPanel.add(tuesdayBox);
    	freeDaysPanel.add(wednesdayBox);
    	freeDaysPanel.add(thursdayBox);
    	this.add(freeDaysPanel);
    	this.add(minWindowsCB);
    	
    	HorizontalPanel timeStartEndPanel = new HorizontalPanel();
    	timeStartEndPanel.addStyleName(cStyle.timeStartEndPanel());
    	timeStartEndPanel.setVerticalAlignment(ALIGN_MIDDLE);
    	timeStartEndPanel.add(startTimeCB);
    	timeStartEndPanel.add(startTimeLB);
    	SimplePanel spacerPanel = new SimplePanel();
    	spacerPanel.addStyleName(cStyle.spacerPanel());
    	timeStartEndPanel.add(spacerPanel);
    	timeStartEndPanel.add(finishTimeCB);
    	timeStartEndPanel.add(finishTimeLB);
    	this.add(timeStartEndPanel);
    	
    	freeDaysLabel.addStyleName(cStyle.onlyCheckBox());
    	sundayBox.addStyleName(cStyle.onlyCheckBox());
    	mondayBox.addStyleName(cStyle.onlyCheckBox());
    	tuesdayBox.addStyleName(cStyle.onlyCheckBox());
    	wednesdayBox.addStyleName(cStyle.onlyCheckBox());
    	thursdayBox.addStyleName(cStyle.onlyCheckBox());
    	
    	minWindowsCB.addStyleName(cStyle.onlyCheckBox());
    	
    	startTimeCB.addStyleName(cStyle.timeCheckBox());
    	finishTimeCB.addStyleName(cStyle.timeCheckBox());
    	
    	startTimeLB.addStyleName(cStyle.timeListBox());
    	finishTimeLB.addStyleName(cStyle.timeListBox());
    	
    	startTimeCB.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
		        Boolean checked = ((InlineCheckBox) event.getSource()).getValue();
		        test = checked;
		        startTimeLB.setEnabled(checked.booleanValue());
			}
    		
    	});
    	
    	finishTimeCB.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
		        Boolean checked = ((InlineCheckBox) event.getSource()).getValue();
		        finishTimeLB.setEnabled(checked.booleanValue());
			}
    		
    	});
        	
    	coursesConstraintsPanel.setHorizontalAlignment(ALIGN_RIGHT);
    	this.add(coursesConstraintsPanel);
    	this.setStyleName(cStyle.constraintsPanel());
    	
    }
    
    public void displayCoursesConstraints(List<Course> selectedCourses){
    	coursesConstraintsPanel.clear();
    	coursesConstraintsPanel.addStyleName(cStyle.coursesConstraintsPanel());
    	if (!selectedCourses.isEmpty()){
        	SimplePanel horizontalLine = new SimplePanel();
        	horizontalLine.setStyleName(cStyle.horizontalLine());
        	coursesConstraintsPanel.add(horizontalLine);
    		coursesConstraintsPanel.add(coursesConstraintsLabel);
        	coursesConstraintsLabel.addStyleName(cStyle.onlyCheckBox());
    	}
    	for (Course course : selectedCourses){
    		Label courseName = new Label(course.getName());
    		courseName.addStyleName(cStyle.onlyCheckBox());
    		HorizontalPanel courseConstraintsPanel = new HorizontalPanel();
    		courseConstraintsPanel.setHorizontalAlignment(ALIGN_RIGHT);
    		courseConstraintsPanel.setVerticalAlignment(ALIGN_MIDDLE);
    		courseConstraintsPanel.addStyleName(cStyle.courseConstraintsPanel());
    		
    		InlineCheckBox courseLectureCB = new InlineCheckBox("בחר הרצאה");
    		courseLectureCB.addStyleName(cStyle.courseCheckBox());
    		final ListBox lectureOptions = new ListBox();
    		lectureOptions.addStyleName(cStyle.courseListBox());
    		for (LessonGroup lessonGroup : course.getLectures()){
    			String listBoxOption = new String("קבוצה " + lessonGroup.getGroupNum() + ": ");
    			for (Lesson lesson : lessonGroup.getLessons()){
    				listBoxOption += lesson.getStartTime().toHebrewString() + ", ";
    			}
    			listBoxOption = listBoxOption.substring(0, listBoxOption.length()-2);
				lectureOptions.addItem(listBoxOption);
    		}
    		lectureOptions.addItem("ללא הרצאה");
    		lectureOptions.setEnabled(false);
    		
    		InlineCheckBox courseTutorialsCB = new InlineCheckBox("בחר תרגול");
    		courseTutorialsCB.addStyleName(cStyle.courseCheckBox());
    		final ListBox tutorialsOptions = new ListBox();
    		tutorialsOptions.addStyleName(cStyle.courseListBox());
    		for (LessonGroup lessonGroup : course.getTutorials()){
    			String listBoxOption = new String("קבוצה " + lessonGroup.getGroupNum() + ": ");
    			for (Lesson lesson : lessonGroup.getLessons()){
    				listBoxOption += lesson.getStartTime().toHebrewString() + ", ";
    			}
    			listBoxOption = listBoxOption.substring(0, listBoxOption.length()-2);
    			tutorialsOptions.addItem(listBoxOption);
    		}
    		lectureOptions.addItem("ללא תרגול");
    		tutorialsOptions.setEnabled(false);
    		courseLectureCB.addClickHandler(new ClickHandler(){

    			@Override
    			public void onClick(ClickEvent event) {
    		        Boolean checked = ((InlineCheckBox) event.getSource()).getValue();
    		        Log.info("lectures are " + checked);
    		        lectureOptions.setEnabled(checked.booleanValue());
    			}
        		
        	});
    		
    		courseTutorialsCB.addClickHandler(new ClickHandler(){

    			@Override
    			public void onClick(ClickEvent event) {
    		        Boolean checked = ((InlineCheckBox) event.getSource()).getValue();
    		        Log.info("tutorials are " + checked);
    		        tutorialsOptions.setEnabled(checked.booleanValue());
    			}
        		
        	});


    		courseConstraintsPanel.add(courseName);
    		
    		FormGroup lectureFormGroup = new FormGroup();
    		lectureFormGroup.add(courseLectureCB);
    		lectureFormGroup.add(lectureOptions);
    		courseConstraintsPanel.add(lectureFormGroup);
        	SimplePanel spacerPanel = new SimplePanel();
        	spacerPanel.addStyleName(cStyle.spacerPanel());
    		courseConstraintsPanel.add(spacerPanel);
    		FormGroup tutorialsFormGroup = new FormGroup();
    		tutorialsFormGroup.add(courseTutorialsCB);
    		tutorialsFormGroup.add(tutorialsOptions);
    		courseConstraintsPanel.add(tutorialsFormGroup);

    		coursesConstraintsPanel.add(courseConstraintsPanel);
    	}
    }
	

}
