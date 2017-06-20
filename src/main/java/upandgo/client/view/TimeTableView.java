package upandgo.client.view;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import upandgo.shared.entities.LocalTime;
import upandgo.client.Resources;
import upandgo.client.Resources.TimeTableStyle;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.Lesson.Type;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.WeekTime;
import upandgo.shared.model.scedule.Color;


public class TimeTableView extends HorizontalPanel { 
	
	static final int MAX_TABLE_SIZE = 25;
	
	static final int EMPTY_COL = 0;
	static final int HOURS_COL = 0;
	static final int LESSONS_COL = 0;
	
	static final String[] daysHebrew = {"ראשון", "שני", "שלישי", "רביעי", "חמישי"};
	
	private FlexTable hoursTable = new FlexTable();
	private FlexTable sundayTable = new FlexTable();
	private FlexTable mondayTable = new FlexTable();
	private FlexTable tuesdayTable = new FlexTable();
	private FlexTable wednesdayTable = new FlexTable();
	private FlexTable thursdayTable = new FlexTable();
	private List<FlexTable> daysTables = new ArrayList<>();
	private Map<String, Color> colorMap;
		
	
	private TimeTableStyle ttStyle = Resources.INSTANCE.timeTableStyle();
	
	public TimeTableView(){
    	InitializePanel();
    	ttStyle.ensureInjected();
    }
	
    private void InitializePanel(){
    	this.setStyleName(ttStyle.timeTable());
    	generateEmptyTable();
    }
    
    private void clearTable(){
    	this.remove(hoursTable);
	    this.remove(daysTables.get(0));
	    this.remove(daysTables.get(1));
	    this.remove(daysTables.get(2));
	    this.remove(daysTables.get(3));
	    this.remove(daysTables.get(4));

	    generateEmptyTable();
    }
    
    private void generateEmptyTable(){
	    hoursTable = new FlexTable();
 		sundayTable = new FlexTable();
 		mondayTable = new FlexTable();
 		tuesdayTable = new FlexTable();
 		wednesdayTable = new FlexTable();
 		thursdayTable = new FlexTable();
 		daysTables = new ArrayList<>();
 		
    	daysTables.add(sundayTable);
    	daysTables.add(mondayTable);
    	daysTables.add(tuesdayTable);
    	daysTables.add(wednesdayTable);
    	daysTables.add(thursdayTable);
 		
    	drawHoursTable(hoursTable);
    	drawDaysTable(daysTables);
    	
	    this.add(hoursTable);
	    this.add(daysTables.get(0));
	    this.add(daysTables.get(1));
	    this.add(daysTables.get(2));
	    this.add(daysTables.get(3));
	    this.add(daysTables.get(4));
    }
    
    private void drawDaysTable(List<FlexTable> tables) {
    	for(int day = 0; day < 5; day++){
    		tables.get(day).setText(0, 0, daysHebrew[day]);
    		for (int timeSlot = 1; timeSlot <= MAX_TABLE_SIZE; timeSlot++){
            	tables.get(day).setText(timeSlot, 0, "");
        		tables.get(day).getCellFormatter().addStyleName(timeSlot,0, ttStyle.noEvent());
    		}
        	tables.get(day).getRowFormatter().addStyleName(0, ttStyle.headerRow());
    	    tables.get(day).addStyleName(ttStyle.dayTable());
    	}
	}
    
    private void drawHoursTable(FlexTable t) {
    	t.setText(0, 0, "שעה");
    	
    	t.getRowFormatter().addStyleName(0, ttStyle.headerRow());

    	for(int i = 1; i<13; i++){
    		t.setText(2*i-1, HOURS_COL, Integer.toString(i+7)+":30");
    		t.getFlexCellFormatter().setRowSpan(2*i-1, HOURS_COL, 2);
    		t.getCellFormatter().addStyleName(2*i-1, HOURS_COL, ttStyle.hoursCell());
    	}
    	t.setText(25,0,"20:30");
		t.getCellFormatter().setStyleName(25,0, ttStyle.hoursCell());
    	
	    t.addStyleName(ttStyle.hoursTable());
	}
    
    
	// this function receives a list of LessonGroup(which is a schedule) and
 	// displays the schedule in the GUI
 	public void displaySchedule(final List<LessonGroup> schedule, Map<String, Color> map) {
 		colorMap = map;
 		
 		clearTable();
 		Log.info("TimeTableView: display was called with: " + schedule);

 		
 		for (final LessonGroup lg : schedule){
			for (final Lesson l : lg.getLessons()) {
				LocalTime startTime = l.getStartTime().getTime();
				LocalTime endTime = l.getEndTime().getTime();
				int startCell = (WeekTime.difference(startTime, LocalTime.parse("08:30"))/30) + 1;
				int span = WeekTime.difference(endTime, startTime)/30;

				VerticalPanel eventContent = new VerticalPanel();
				eventContent.setStyleName(ttStyle.hasEventContent());
				eventContent.setHorizontalAlignment(ALIGN_CENTER);

				if(l.getType() == Type.LECTURE){
					eventContent.add(new Label("הרצאה - "  + l.getCourseId()));
				}else if(l.getType() == Type.TUTORIAL){
					eventContent.add(new Label("תרגול - "  + l.getCourseId()));
				}
				eventContent.setHorizontalAlignment(ALIGN_RIGHT);
				Grid eventContentGrid = new Grid(4,2);
				eventContentGrid.getCellFormatter().setHorizontalAlignment(0, 0, ALIGN_CENTER);
				eventContentGrid.getCellFormatter().setHorizontalAlignment(1, 0, ALIGN_CENTER);
				eventContentGrid.getCellFormatter().setHorizontalAlignment(2, 0, ALIGN_CENTER);
				eventContentGrid.getCellFormatter().setHorizontalAlignment(3, 0, ALIGN_CENTER);
				eventContentGrid.setHTML(0, 0, "<i class=\"fa fa-book\" aria-hidden=\"true\"></i>&nbsp;&nbsp;");
				eventContentGrid.setHTML(0, 1, l.getCourseName());
				eventContentGrid.setHTML(1, 0, "<i class=\"fa fa-building-o\" aria-hidden=\"true\"></i>&nbsp;&nbsp;");
				eventContentGrid.setHTML(1, 1, l.getPlace());
				eventContentGrid.setHTML(2, 0, "<i class=\"fa fa-users\" aria-hidden=\"true\"></i>&nbsp;&nbsp;");
				eventContentGrid.setHTML(2, 1, "קבוצה&nbsp;" + String.valueOf(l.getGroup()));
				eventContentGrid.setHTML(3, 0, "<i class=\"fa fa-graduation-cap\" aria-hidden=\"true\"></i>&nbsp;&nbsp;");
				eventContentGrid.setHTML(3, 1, l.getRepresenter().getTitle() + "&nbsp;" + l.getRepresenter().getLastName() + "&nbsp;" + l.getRepresenter().getFirstName());
				
				eventContent.add(eventContentGrid);
				
				SimplePanel eventWrap = new SimplePanel();
				eventWrap.add(eventContent);
				eventWrap.addStyleName(ttStyle.hasEventWrap());
				if(colorMap != null){
					//Log.info("for course: " + l.getCourseId() + " use: " + colorMap.get(l.getCourseId()).name() );
					eventWrap.getElement().setAttribute("eventNum", colorMap.get(l.getCourseId()).name() );
					
				}else{
					eventWrap.getElement().setAttribute("eventNum", "ORANGERED");
	 			}

				final Modal lessonDetailsBox = InitializeLessonDetailsBox(l);
				eventWrap.sinkEvents(Event.ONCLICK);
				eventWrap.addHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						lessonDetailsBox.show();
						
					}
				}, ClickEvent.getType());
				
				daysTables.get(l.getDay()).setWidget(startCell, 0, eventWrap);
				daysTables.get(l.getDay()).getFlexCellFormatter().setRowSpan(startCell, 0, span);
				daysTables.get(l.getDay()).getCellFormatter().setStyleName(startCell, 0, ttStyle.hasEvent());
				
				while (span > 1){
					daysTables.get(l.getDay()).removeCell(startCell+span-1, 0);
					span--;
				}
				
			}
 		}
 		
 	}
 	
	private Modal InitializeLessonDetailsBox(Lesson lesson){
		final Modal lessonDetailsBox = new Modal();
		ModalFooter lessonDetailsBoxFooter = new ModalFooter();
		Button lessonDetailsBoxButton = new Button("סגור", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				lessonDetailsBox.hide();
				
			}
		});
		lessonDetailsBoxFooter.add(lessonDetailsBoxButton);
		lessonDetailsBox.setFade(true);
		if(lesson.getType() == Type.LECTURE){
			lessonDetailsBox.setTitle("הרצאה - "  + lesson.getCourseId());
		}else if(lesson.getType() == Type.TUTORIAL){
			lessonDetailsBox.setTitle("תרגול - "  + lesson.getCourseId());
		}
		ModalBody lessonDetailsBoxBody = new ModalBody();
		lessonDetailsBoxBody.add(new LessonDetailsView(lesson));
		lessonDetailsBox.add(lessonDetailsBoxBody);
		lessonDetailsBox.add(lessonDetailsBoxFooter);
		return lessonDetailsBox;
	}
}