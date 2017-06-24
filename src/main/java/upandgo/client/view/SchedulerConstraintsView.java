package upandgo.client.view;

import java.util.List;

import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.InlineCheckBox;
import org.gwtbootstrap3.client.ui.ListBox;
import org.gwtbootstrap3.client.ui.ModalComponent;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import upandgo.client.Resources;
import upandgo.client.Resources.SchedualerConstraintsStyle;
import upandgo.shared.entities.Day;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.Lesson.Type;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.ConstraintsPool;
import upandgo.shared.model.scedule.ConstraintsPool.CourseConstraint;


/**
 * @author Yaniv Levinsky
 *
 */

public class SchedulerConstraintsView extends VerticalPanel implements ModalComponent{
	
	private ConstraintsPool localConstraintsPool = new ConstraintsPool();
	
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
    
    public void setConstraintsPool(List<Course> selectedCourses, ConstraintsPool constraintsPool){
    	localConstraintsPool = new ConstraintsPool(constraintsPool);
    	
    	sundayBox.setValue(localConstraintsPool.getVectorDaysOff().get(Day.SUNDAY.ordinal()));
    	mondayBox.setValue(localConstraintsPool.getVectorDaysOff().get(Day.MONDAY.ordinal()));
    	tuesdayBox.setValue(localConstraintsPool.getVectorDaysOff().get(Day.TUESDAY.ordinal()));
    	wednesdayBox.setValue(localConstraintsPool.getVectorDaysOff().get(Day.WEDNESDAY.ordinal()));
    	thursdayBox.setValue(localConstraintsPool.getVectorDaysOff().get(Day.THURSDAY.ordinal()));
    	
    	minWindowsCB.setValue(localConstraintsPool.isBlankSpaceCount());
    	
    	if (localConstraintsPool.getMinStartTime()!=null){
    		startTimeCB.setValue(true);
    		startTimeLB.setSelectedIndex(TimeTableView.localTimeToRowIndex(localConstraintsPool.getMinStartTime()));
    	} else {
    		startTimeCB.setValue(false);
    	}
    	
    	if (localConstraintsPool.getMaxFinishTime()!=null){
    		finishTimeCB.setValue(true);
    		finishTimeLB.setSelectedIndex(TimeTableView.localTimeToRowIndex(localConstraintsPool.getMaxFinishTime()));
    	} else {
    		finishTimeCB.setValue(false);
    	}
    	
    	displayCoursesConstraints(selectedCourses);
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
    		

    		courseConstraintsPanel.add(courseName);

    		courseConstraintsPanel.add(getCourseConstraintsListBox(course.getId(),course.getLectures(),Type.LECTURE));
    		
        	SimplePanel spacerPanel = new SimplePanel();
        	spacerPanel.addStyleName(cStyle.spacerPanel());
    		courseConstraintsPanel.add(spacerPanel);
    		
    		
    		courseConstraintsPanel.add(getCourseConstraintsListBox(course.getId(),course.getTutorials(),Type.TUTORIAL));

    		coursesConstraintsPanel.add(courseConstraintsPanel);
    	}
    }
    
    private FormGroup getCourseConstraintsListBox(final String courseId, List<LessonGroup> lessonGroups, final Type lessonType){
    	
		InlineCheckBox courseConstraintCB = new InlineCheckBox("בחר " + lessonType);
		courseConstraintCB.addStyleName(cStyle.courseCheckBox());
		final ListBox courseConstraintOptions = new ListBox();
		courseConstraintOptions.setId(courseId);
		courseConstraintOptions.addStyleName(cStyle.courseListBox());
		for (LessonGroup lessonGroup : lessonGroups){
			String listBoxOption = new String("קבוצה " + lessonGroup.getGroupNum() + ": ");
			for (Lesson lesson : lessonGroup.getLessons()){
				listBoxOption += lesson.getStartTime().toHebrewString() + ", ";
			}
			listBoxOption = listBoxOption.substring(0, listBoxOption.length()-2);
			courseConstraintOptions.addItem(listBoxOption,String.valueOf(lessonGroup.getGroupNum()));
		}
		courseConstraintOptions.addItem("ללא " + lessonType,String.valueOf(CourseConstraint.NO_LESSON));
		courseConstraintOptions.setEnabled(false);
		
		
		if (localConstraintsPool.getCourseConstraints().containsKey(courseId)){
			CourseConstraint courseConstraint = localConstraintsPool.getCourseConstraints().get(courseId);
			if (lessonType == Type.LECTURE){
				courseConstraintCB.setValue(courseConstraint.isSpecificLecture());
				courseConstraintOptions.setEnabled(courseConstraint.isSpecificLecture());
				if (courseConstraint.isSpecificLecture() == true){
					for (int i=0; i < courseConstraintOptions.getItemCount(); i++) {
						if (courseConstraintOptions.getValue(i).equals(String.valueOf(courseConstraint.getLectureLessonGroup()))){
							courseConstraintOptions.setSelectedIndex(i);
							break;
					    }
					}
				}
			} else if (lessonType == Type.TUTORIAL){
				courseConstraintCB.setValue(courseConstraint.isSpecificTutorial());
				courseConstraintOptions.setEnabled(courseConstraint.isSpecificTutorial());
				if (courseConstraint.isSpecificTutorial() == true){
					for (int i=0; i < courseConstraintOptions.getItemCount(); i++) {
						if (courseConstraintOptions.getValue(i).equals(String.valueOf(courseConstraint.getTutorialLessonGroup()))){
							courseConstraintOptions.setSelectedIndex(i);
							break;
					    }
					}
				}
			}
		}
		
		
		courseConstraintCB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Boolean checked = ((InlineCheckBox) event.getSource()).getValue();
				Log.info("SchedulerConstraintsView: course " + courseConstraintOptions.getId() + " specific " + checked + " group " + courseConstraintOptions.getSelectedValue());
				localConstraintsPool.setCourseConstraint(courseId, lessonType, checked, Integer.parseInt(courseConstraintOptions.getSelectedValue()));
				courseConstraintOptions.setEnabled(checked.booleanValue());
			}
		});
		
		courseConstraintOptions.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				Log.info("SchedulerConstraintsView: course " + courseConstraintOptions.getId() + " group " + courseConstraintOptions.getSelectedValue());
				localConstraintsPool.setCourseConstraint(courseId, lessonType, true, Integer.parseInt(courseConstraintOptions.getSelectedValue()));
			}
		});  
		
		FormGroup courseConstraintFormGroup = new FormGroup();
		courseConstraintFormGroup.add(courseConstraintCB);
		courseConstraintFormGroup.add(courseConstraintOptions);
		return courseConstraintFormGroup;
    }
	
    public ConstraintsPool getConstraintsPool(){
    	return localConstraintsPool;
    }

}
