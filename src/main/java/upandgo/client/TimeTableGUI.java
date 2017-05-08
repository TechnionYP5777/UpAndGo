package upandgo.client;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import upandgo.client.Resources.TimeTableStyle;
import upandgo.shared.entities.Day;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.Lesson.Type;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.WeekTime;
import upandgo.shared.model.scedule.Schedule;
import upandgo.client.presenter.SchedulerPresenter;

public class TimeTableGUI extends HorizontalPanel implements SchedulerPresenter.Display{ 
	
	private final int DAYS_IN_WEEK = 7;
	
	static final int EMPTY_COL = 0;
	static final int HOURS_COL = 0;
	static final int LESSONS_COL = 0;
	
	private FlexTable t = new FlexTable();
	private FlexTable sundayTable = new FlexTable();
	private FlexTable mondayTable = new FlexTable();
	private FlexTable tuesdayTable = new FlexTable();
	private FlexTable wednesdayTable = new FlexTable();
	private FlexTable thursdayTable = new FlexTable();
	
	
	
	private TimeTableStyle ttStyle = Resources.INSTANCE.timeTableStyle();
	
	public TimeTableGUI(){
    	InitializePanel();
    	ttStyle.ensureInjected();
    }
	
    private void InitializePanel(){
    	drawHoursTable(t);
    	drawDayTable(sundayTable, "ראשון");
    	drawDayTable(mondayTable, "שני");
    	drawDayTable(tuesdayTable, "שלישי");
    	drawDayTable(wednesdayTable, "רביעי");
    	drawDayTable(thursdayTable, "חמישי");
    	
    	
	    
	    ArrayList<Lesson> lessons = new ArrayList<Lesson>();
	    Lesson l = new Lesson(null,
	    		new WeekTime(Day.WEDNESDAY, LocalTime.of(8, 30)),
	    		new WeekTime(Day.WEDNESDAY, LocalTime.of(9, 30)),
	    		"טאוב 5",
	    		Type.LECTURE,
	    		3,
	    		"123123",
	    		"אישים בתנך");
	    lessons.add(l);
	    l = new Lesson(null,
	    		new WeekTime(Day.WEDNESDAY, LocalTime.of(10, 30)),
	    		new WeekTime(Day.WEDNESDAY, LocalTime.of(12, 30)),
	    		"טאוב 7",
	    		Type.LECTURE,
	    		3,
	    		"123123",
	    		"OOP");
	    lessons.add(l);
	    l = new Lesson(null,
	    		new WeekTime(Day.WEDNESDAY, LocalTime.of(13, 30)),
	    		new WeekTime(Day.WEDNESDAY, LocalTime.of(15, 30)),
	    		"טאוב 8",
	    		Type.LECTURE,
	    		3,
	    		"123123",
	    		"מבוא לכלכלה");
	    lessons.add(l);
	    
	    ArrayList<LessonGroup> lgList = new ArrayList<>();
	    LessonGroup lg = new LessonGroup(14);
	    lg.addLesson(l);
	    lgList.add(lg);
	    
	    //displaySchedule(lgList);
	    drawDay(lessons, mondayTable);
	    drawDay(lessons, tuesdayTable);
	    //drawDay(lessons, thursdayTable);
	    
	    this.setStyleName(ttStyle.timeTable());
	    this.add(t);
	    this.add(sundayTable);
	    this.add(mondayTable);
	    this.add(tuesdayTable);
	    this.add(wednesdayTable);
	    this.add(thursdayTable);
	    
	    //clearTable();
    }
    
    private void clearTable(){
    	this.remove(t);
 	    this.remove(sundayTable);
 	    this.remove(mondayTable);
 	    this.remove(tuesdayTable);
 	    this.remove(wednesdayTable);
 	    this.remove(thursdayTable);
 	    
 	    t = new FlexTable();
 		sundayTable = new FlexTable();
 		mondayTable = new FlexTable();
 		tuesdayTable = new FlexTable();
 		wednesdayTable = new FlexTable();
 		thursdayTable = new FlexTable();
 		
 		drawHoursTable(t);
    	drawDayTable(sundayTable, "ראשון");
    	drawDayTable(mondayTable, "שני");
    	drawDayTable(tuesdayTable, "שלישי");
    	drawDayTable(wednesdayTable, "רביעי");
    	drawDayTable(thursdayTable, "חמישי");
 		
 		this.add(t);
 	    this.add(sundayTable);
 	    this.add(mondayTable);
 	    this.add(tuesdayTable);
 	    this.add(wednesdayTable);
 	    this.add(thursdayTable);
 		
 		
    }
    
    
    private void drawDayTable(FlexTable t, String header) {
    	t.setText(0, 0, "");
    	t.setText(0, 0, header);
    	
    	t.getRowFormatter().addStyleName(0, ttStyle.headerRow());
    	t.getCellFormatter().addStyleName(0, 0, ttStyle.arciCol());
    	t.getColumnFormatter().addStyleName(0, ttStyle.dayCol());
    	
    	/*
    	for(int i = 1; i<7; i++){;
    		t.getColumnFormatter().addStyleName(i, ttStyle.dayCol());
    	}*/
    	
    	
    	// invisible arcitcure column
    	//drawArciCol(t);
    	t.setText(25, 0, "");
    	
    	//drawCell(t, 1, LESSONS_COL, "", 1, ttStyle.noEvent());
    	//drawCell(t, 2, LESSONS_COL, "aaa3", 1, ttStyle.hasEvent());
    	
    	/*t.setText(1, 1, "aaa");
		t.getFlexCellFormatter().setRowSpan(1, 1, 1);
		t.getCellFormatter().addStyleName(1, 1, ttStyle.noEvent());
		*/
		//t.getCellFormatter().addStyleName(1, 1, ttStyle.noEvent());
	
    	
    	/*
    	for(int i = 2; i<7; i++){
    		t.setText(1, i, "");
    		t.getFlexCellFormatter().setRowSpan(1, i, 22);
    	}
    	*/
    	
	    t.addStyleName(ttStyle.dayTable());
	    
	}

    
    private void drawHoursTable(FlexTable t) {
    	drawHeaders(t);
    	
    	t.getRowFormatter().addStyleName(0, ttStyle.headerRow());
    	t.getCellFormatter().addStyleName(0, 0, ttStyle.arciCol());
    	//t.getColumnFormatter().addStyleName(0, ttStyle.hoursTable());
    	
    	/*
    	for(int i = 1; i<7; i++){;
    		t.getColumnFormatter().addStyleName(i, ttStyle.dayCol());
    	}*/
    	
    	
    	// invisible arcitcure column
    	//drawArciCol(t);
    	//t.setText(25, 0, "");
    	drawHoursCol(t);
    	
    	
    	/*
    	for(int i = 2; i<7; i++){
    		t.setText(1, i, "");
    		t.getFlexCellFormatter().setRowSpan(1, i, 22);
    	}
    	*/
    	
	    t.addStyleName(ttStyle.hoursTable());
	    
	}
    
    private void drawCell(FlexTable t, int row, int col, String text, int span, String styleName) {
    	
    	//Log.info("row: " + row + " col: " + col + " text: " + text);
    	t.setText(row, col, text);
		t.getFlexCellFormatter().setRowSpan(row, col, span);
		t.getCellFormatter().addStyleName(row, col, styleName);
	}

	private void drawArciCol(FlexTable t) {
    	for(int i = 1; i<12; i++){
    		t.setText(2*i-1, EMPTY_COL, "");
    		t.setText(2*i, EMPTY_COL, "");
    		t.getCellFormatter().addStyleName(2*i-1, EMPTY_COL, ttStyle.arciCol());
    		t.getCellFormatter().addStyleName(2*i, EMPTY_COL, ttStyle.arciCol());
    		t.getRowFormatter().setStyleName(2*i-1, ttStyle.tableRow());
    		t.getRowFormatter().setStyleName(2*i, ttStyle.tableRow());
    	}
    	
		
	}

	private void drawHoursCol(FlexTable t) {
    	for(int i = 1; i<13; i++){
    		t.setText(2*i-1, HOURS_COL, Integer.toString(i+7)+":30");
    		t.getFlexCellFormatter().setRowSpan(2*i-1, HOURS_COL, 2);
    		t.getCellFormatter().addStyleName(2*i-1, HOURS_COL, ttStyle.hoursCell());
    	}
    	t.setText(25,0,"20:30");
		t.getCellFormatter().addStyleName(25,0, ttStyle.hoursCell());

	}

	private void drawHeaders(FlexTable t) {
    	t.setText(0, 0, "");
    	t.setText(0, 0, "שעה");
    	
    	/*
    	t.setText(0, 2, "ראשון");
    	t.setText(0, 3, "שני");
    	t.setText(0, 4, "שלישי");
    	t.setText(0, 5, "רביעי");
    	t.setText(0, 6, "חמישי");*/
		
	}

	// this function receives a list of LessonGroup(which is a schedule) and
 	// displays the schedule in the GUI
 	public void displaySchedule(final List<LessonGroup> schedule) {
 		//resetTable();
 		
 		final ArrayList<ArrayList<Lesson>> lessonsOfDay = seperateLessonsByDay(schedule);
 		
 		for(int day = 0; day < DAYS_IN_WEEK; day++){
 			final ArrayList<Lesson> daySchedule = lessonsOfDay.get(day);
 			Collections.sort(daySchedule, (t1, t2) -> t1.getStartTime().compareTo(t2.getStartTime()));
			//drawDay(daySchedule, day);
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
 	private void drawDay(ArrayList<Lesson> lessons, FlexTable t){
 		int day = LESSONS_COL;
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
			previousEndTime = endTime;
			span = differenceInMinutes/30;
			Log.info("blank_span: " + span);
			
			if(span > 0){
				t.setText(currentCell, day, "");
				t.getFlexCellFormatter().setRowSpan(currentCell, day, span);
				t.getCellFormatter().addStyleName(currentCell, day, ttStyle.noEvent());
				currentCell += span;
			}
			
			Log.info("currentCell: " + currentCell);
			
			differenceInMinutes = WeekTime.difference(endTime, startTime);
			span = differenceInMinutes/30;
			Log.info("lesson_span: " + span);
			
			String displayString = l.getCourseName() + " " + l.getCourse() + " " + l.getPlace();
			
			SimplePanel eventCell = new SimplePanel();
			eventCell.add(new Label(displayString));
			eventCell.addStyleName(ttStyle.hasEventWrap());
			t.setWidget(currentCell, day, eventCell);
			//t.setText(currentCell, day, displayString);
			t.getFlexCellFormatter().setRowSpan(currentCell, day, span);
			t.getCellFormatter().addStyleName(currentCell, day, ttStyle.hasEvent());
			currentCell += span;
	 		
		}
 		
 	}

	@Override
	public <T extends HasClickHandlers> T clearSchedule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends HasClickHandlers> T buildSchedule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends HasClickHandlers> T nextSchedule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends HasClickHandlers> T prevSchedule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends HasClickHandlers> T saveSchedule() {
		// TODO Auto-generated method stub
		return null;
	}
 	
	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSchedule(Schedule schedule) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends HasClickHandlers> T getDaysOffValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isDayOffChecked(ClickEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T extends HasClickHandlers> T getMinWindowsValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isMinWindowsChecked(ClickEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T extends HasClickHandlers> T getStartTimeValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isStartTimeChecked(ClickEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getReqStartTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends HasClickHandlers> T getFinishTimeValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isFinishTimeChecked(ClickEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getReqFinishTime() {
		// TODO Auto-generated method stub
		return null;
	}
 	
}