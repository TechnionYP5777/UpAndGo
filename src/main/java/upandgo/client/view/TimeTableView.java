package upandgo.client.view;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

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
		t.getCellFormatter().addStyleName(25,0, ttStyle.hoursCell());
    	
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
				
				Log.info("TimeTableView: span is " + span);
				
				String type = "";
				if(l.getType() == Type.LECTURE){
					type = "הרצאה";
				}else if(l.getType() == Type.TUTORIAL){
					type = "תרגול";
				}
				String displayString = l.getCourseName() + " " + type + ". " + l.getCourseId() + " " + l.getPlace();

				SimplePanel eventCell = new SimplePanel();
				eventCell.add(new Label(displayString));
				eventCell.addStyleName(ttStyle.hasEventWrap());
				if(colorMap != null){
					//Log.info("for course: " + l.getCourseId() + " use: " + colorMap.get(l.getCourseId()).name() );
					eventCell.getElement().setAttribute("eventNum", colorMap.get(l.getCourseId()).name() );
					
				}else{
					eventCell.getElement().setAttribute("eventNum", "ORANGERED");
	 			}
				
				
				daysTables.get(l.getDay()).setWidget(startCell, 0, eventCell);
				//t.setText(currentCell, day, displayString);
				daysTables.get(l.getDay()).getFlexCellFormatter().setRowSpan(startCell, 0, span);
				daysTables.get(l.getDay()).getCellFormatter().addStyleName(startCell, 0, ttStyle.hasEvent());
				
				while (span > 1){
					daysTables.get(l.getDay()).removeCell(startCell+span-1, 0);
					span--;
				}
				
			}
 		}
 		
 	}	
}