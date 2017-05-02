package upandgo.client;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import upandgo.client.Resources.TimeTableStyle;
import upandgo.shared.entities.Day;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.Lesson.Type;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.WeekTime;

public class TimeTableGUI extends LayoutPanel { 
	
	// LEARNING SOME NEW STUFF, so comments will stay for a few days
	private final int DAYS_IN_WEEK = 7;
	
	static final int EMPTY_COL = 0;
	static final int HOURS_COL = 1;
	
	private FlexTable t = new FlexTable();
	private TimeTableStyle ttStyle = Resources.INSTANCE.timeTableStyle();
	
	public TimeTableGUI(){
    	InitializePanel();
    	ttStyle.ensureInjected();
    }
	
    private void InitializePanel(){
    	drawHeaders();
    	
    	t.getRowFormatter().addStyleName(0, ttStyle.headerRow());
    	
    	
    	t.getCellFormatter().addStyleName(0, 0, ttStyle.arciCol());
    	t.getCellFormatter().addStyleName(0, 2, ttStyle.arciCol());
    	t.getCellFormatter().addStyleName(0, 4, ttStyle.arciCol());
    	t.getCellFormatter().addStyleName(0, 6, ttStyle.arciCol());
    	t.getCellFormatter().addStyleName(0, 8, ttStyle.arciCol());
    	t.getCellFormatter().addStyleName(0, 10, ttStyle.arciCol());
    	t.getCellFormatter().addStyleName(0, 12, ttStyle.arciCol());
    	
    	/*t.getCellFormatter().addStyleName(1, 0, ttStyle.arciCol());
    	t.getCellFormatter().addStyleName(1, 1, ttStyle.arciCol());
    	t.getCellFormatter().addStyleName(2, 0, ttStyle.arciCol());
    	t.getCellFormatter().addStyleName(2, 1, ttStyle.arciCol());*/
    	/*t.getCellFormatter().addStyleName(4, 0, ttStyle.arciCol());
    	t.getCellFormatter().addStyleName(6, 0, ttStyle.arciCol());
    	t.getCellFormatter().addStyleName(8, 0, ttStyle.arciCol());
    	t.getCellFormatter().addStyleName(10, 0, ttStyle.arciCol());*/
    	
    	
    	
    	
    	for(int i = 1; i<=5; i++){; // return
    		t.getColumnFormatter().addStyleName(i, ttStyle.dayCol());
    	}
    	
    	
    	
    	
    	//t.getColumnFormatter().addStyleName(1, ttStyle.dayCol());
    	//t.getColumnFormatter().addStyleName(2, ttStyle.dayCol());
    	/*t.getColumnFormatter().addStyleName(5, ttStyle.dayCol());
    	t.getColumnFormatter().addStyleName(7, ttStyle.dayCol());
    	t.getColumnFormatter().addStyleName(9, ttStyle.dayCol());
    	t.getColumnFormatter().addStyleName(11, ttStyle.dayCol());*/
    	
    	
    	drawArciCol();  // invisible architecture column
    	drawHoursCol();
    	
    	//t.getFlexCellFormatter().setRowSpan(1, 1, 22);
    	
    	/*
    	for(int i = 2; i<7; i++){
    		t.setText(1, i, "");
    		t.getFlexCellFormatter().setRowSpan(1, i, 22);
    	}
    	*/
    	
    	
    	/*t.setText(1, 3, "aa");
    	t.setText(2, 2, "aa");
    	t.setText(3, 3, "aa");
    	t.removeCell(2, 2);*/
    	
    	
    	drawCell(1, 3, "", 2, ttStyle.noEvent());
    	
		
		
    	drawCell(3, 3, "קורס כלשהו", 2, ttStyle.hasEvent());
		
		
		t.setText(5, 3, "");
		clearBeforeSpan(5,3,4);
		t.getFlexCellFormatter().setRowSpan(5, 3, 4);
		t.getCellFormatter().addStyleName(5, 3, ttStyle.noEvent());
		
		
		t.setText(9, 3, "מבוא לכלכלה, ניהול 306");
		clearBeforeSpan(9,3,4);
		t.getFlexCellFormatter().setRowSpan(9, 3, 4);
		t.getCellFormatter().addStyleName(9, 3, ttStyle.hasEvent());
		
		
		/*
		t.setText(5, 2, "מבוא לכלכלה, ניהול 306");
		t.getFlexCellFormatter().setRowSpan(5, 2, 4);
		t.getCellFormatter().addStyleName(5, 2, ttStyle.hasEvent());
		
		t.setText(9, 2, "");
		t.getFlexCellFormatter().setRowSpan(9, 2, 2);
		t.getCellFormatter().addStyleName(9, 2, ttStyle.noEvent());
*/
		
		/*SimplePanel eventCell = new SimplePanel();
		t.setWidget(11,	2, eventCell);
		eventCell.add(new Label("מבוא לכלכה, תרגול, כיתה 301 ועוד דברים בדיקת חריגה מגבולות בדיקת חריגה מגבולות בדיקת חריגה"));
		eventCell.addStyleName(ttStyle.hasEventWrap());*/
		
		/*
    	t.setText(11, 2, "מבוא לכלכה, תרגול, כיתה 301 ועוד דברים בדיקת חריגה מגבולות בדיקת חריגה מגבולות בדיקת חריגה");
		t.getFlexCellFormatter().setRowSpan(11, 2, 2);
		t.getCellFormatter().addStyleName(11, 2, ttStyle.hasEvent());
		
		t.setText(13, 2, "");
		t.getFlexCellFormatter().setRowSpan(13, 2, 10);
		t.getCellFormatter().addStyleName(13, 2, ttStyle.noEvent());
		*/
		
		
		//*******************************
		
    	
		
		
		/*t.setText(1, 3, "מבוא לאישים בתנך, בית מדרש 1");
		t.getFlexCellFormatter().setRowSpan(1, 3, 4);
		t.getCellFormatter().addStyleName(1, 3, ttStyle.hasEvent());
		
		t.setText(5, 3, "תיכון תוכנה, טאוב 10");
		t.getFlexCellFormatter().setRowSpan(5, 3, 4);
		t.getCellFormatter().addStyleName(5, 3, ttStyle.hasEvent());
		
		t.setText(9, 3, "תיכון תוכנה, טאוב 10");
		t.getFlexCellFormatter().setRowSpan(9, 3, 4);
		t.getCellFormatter().addStyleName(9, 3, ttStyle.hasEvent());
		
		t.setText(13, 3, "תיכון תוכנה, טאוב 10");
		t.getFlexCellFormatter().setRowSpan(13, 3, 1);
		t.getCellFormatter().addStyleName(13, 3, ttStyle.hasEvent());
		*/
		
		/*
		t.setText(9, 3, "");
		t.getFlexCellFormatter().setRowSpan(9, 3, 14);
		t.getCellFormatter().addStyleName(9, 3, ttStyle.noEvent());
		*/
		
    	/*t.getColumnFormatter().addStyleName(0, ttStyle.hoursCol());
    	t.getColumnFormatter().addStyleName(2, ttStyle.hoursCol());
    	t.getColumnFormatter().addStyleName(4, ttStyle.hoursCol());
    	t.getColumnFormatter().addStyleName(6, ttStyle.hoursCol());
    	t.getColumnFormatter().addStyleName(8, ttStyle.hoursCol());
    	t.getColumnFormatter().addStyleName(10, ttStyle.hoursCol());*/

	    t.addStyleName(ttStyle.timeTable());
	    
	    
	    /*ArrayList<Lesson> lessons = new ArrayList<Lesson>();
	    Lesson l = new Lesson(null,
	    		new WeekTime(Day.WEDNESDAY, LocalTime.of(10, 30)),
	    		new WeekTime(Day.WEDNESDAY, LocalTime.of(12, 30)),
	    		"טאוב 7",
	    		Type.LECTURE,
	    		3,
	    		"123123",
	    		"OOP");
	    lessons.add(l);
	    
	    ArrayList<LessonGroup> lgList = new ArrayList<>();
	    LessonGroup lg = new LessonGroup(14);
	    lg.addLesson(l);
	    lgList.add(lg);*/
	    
	    //displaySchedule(lgList);
	    //drawDay(lessons, 4);

	    this.add(t);
    }
    
    
    
    private void drawCell(int row, int col, String text, int span, String styleName) {
    	t.setText(row, col, text);
    	clearBeforeSpan(row,col,span);
		t.getFlexCellFormatter().setRowSpan(row, col, span);
		t.getCellFormatter().addStyleName(row, col, styleName);
		
		
	}

	private void clearBeforeSpan(int r, int c, int span) {
		// TODO Auto-generated method stub
    	
    	for(int i = r+1; i < r+span; i++){
    		if(i % 2 == 0){
    			t.removeCell(i, c-1);
    		}else{
    			t.removeCell(i, c);
    		}
		}
	}

	private void drawArciCol() {
    	for(int i = 1; i<12; i++){
    		t.setText(2*i-1, EMPTY_COL, "");
    		t.setText(2*i, EMPTY_COL, "");
    		t.getCellFormatter().addStyleName(2*i-1, EMPTY_COL, ttStyle.arciCol());
    		t.getCellFormatter().addStyleName(2*i, EMPTY_COL, ttStyle.arciCol());
    		t.getRowFormatter().setStyleName(2*i-1, ttStyle.tableRow());
    		t.getRowFormatter().setStyleName(2*i, ttStyle.tableRow());
    	}
    	
    	for(int col = 2; col<=12; col+=2){
	    	for(int i = 1; i<12; i++){
	    		t.setText(2*i-1, col, "");
	    		t.setText(2*i, col-1, "");
	    		t.getCellFormatter().addStyleName(2*i-1, col, ttStyle.arciCol());
	    		t.getCellFormatter().addStyleName(2*i, col-1, ttStyle.arciCol());
	    		t.getRowFormatter().setStyleName(2*i-1, ttStyle.tableRow());
	    		t.getRowFormatter().setStyleName(2*i, ttStyle.tableRow());
	    	}
    	}
    	
		
	}

	private void drawHoursCol() {
    	for(int i = 1; i<12; i++){
    		t.setText(2*i-1, HOURS_COL, Integer.toString(i+7)+":30");
    		t.getFlexCellFormatter().setRowSpan(2*i-1, HOURS_COL, 2);
    		t.getCellFormatter().addStyleName(2*i-1, HOURS_COL, ttStyle.hoursCell());

    	}
	}

	private void drawHeaders() {
    	t.setText(0, 0, "");
    	t.setText(0, 1, "שעה");
    	t.setText(0, 2, "");
    	t.setText(0, 3, "ראשון");
    	t.setText(0, 4, "");
    	t.setText(0, 5, "שני");
    	t.setText(0, 6, "");
    	t.setText(0, 7, "שלישי");
    	t.setText(0, 8, "");
    	t.setText(0, 9, "רביעי");
    	t.setText(0, 10, "");
    	t.setText(0, 11, "חמישי");
		
	}

	// this function receives a list of LessonGroup(which is a schedule) and
 	// displays the schedule in the GUI
 	public void displaySchedule(final List<LessonGroup> schedule) {
 		//resetTable();
 		
 		final ArrayList<ArrayList<Lesson>> lessonsOfDay = seperateLessonsByDay(schedule);
 		
 		for(int day = 0; day < DAYS_IN_WEEK; day++){
 			final ArrayList<Lesson> daySchedule = lessonsOfDay.get(day);
 			Collections.sort(daySchedule, (t1, t2) -> t1.getStartTime().compareTo(t2.getStartTime()));
			drawDay(daySchedule, day);
 		}
 	}
 	
 	private ArrayList<ArrayList<Lesson>> seperateLessonsByDay(final List<LessonGroup> schedule){
 		final ArrayList<ArrayList<Lesson>> lessonsOfDay = new ArrayList<>();
 		
 		for (int i = 0; i < DAYS_IN_WEEK; ++i)
 			lessonsOfDay.add(new ArrayList<>());
 		
 		lessonsOfDay.add(new ArrayList<>());
 		for (final LessonGroup lg : schedule){
			for (final Lesson l : lg.getLessons()) {
				lessonsOfDay.get(l.getDay()).add(l);
			}
 		}
 		return lessonsOfDay;
 	}
 	
 	
 	//gets a group of lessond and a day and draw the schedule for that day.
 	private void drawDay(ArrayList<Lesson> lessons, int day){
 		if(lessons.isEmpty()){
 			t.setText(1, day, "");
 			t.getFlexCellFormatter().setRowSpan(1, day, 22);
 			t.getCellFormatter().addStyleName(1, day, ttStyle.noEvent());
 			return;
 		}

 		int differenceInMinutes;
 		int currentCell = 1;
 		int span;
 		
		LocalTime startTime;
		LocalTime endTime;
		LocalTime previousEndTime = LocalTime.of(8, 30);
		
 		for(Lesson l : lessons){
 			startTime = l.getStartTime().getTime();
 			endTime = l.getEndTime().getTime();
	 		
			differenceInMinutes = WeekTime.difference(startTime, previousEndTime);
			span = differenceInMinutes/30;
			
			t.setText(currentCell, day, "");
			t.getFlexCellFormatter().setRowSpan(currentCell, day, span);
			t.getCellFormatter().addStyleName(currentCell, day, ttStyle.noEvent());
			currentCell += span;
			
			differenceInMinutes = WeekTime.difference(endTime, startTime);
			span = differenceInMinutes/30;
			
			String displayString = l.getCourseName() + " " + l.getCourse() + " " + l.getPlace();
			
			
			t.setText(currentCell, day, displayString);
			t.getFlexCellFormatter().setRowSpan(currentCell, day, span);
			t.getCellFormatter().addStyleName(currentCell, day, ttStyle.hasEvent());
			
	 		
		}
 		
 	}
 	
 	
}
