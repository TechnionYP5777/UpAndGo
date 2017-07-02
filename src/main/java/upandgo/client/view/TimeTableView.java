package upandgo.client.view;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.ListBox;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.html.Text;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import upandgo.shared.entities.LocalTime;
import upandgo.shared.entities.UserEvent;
import upandgo.client.Resources;
import upandgo.client.Resources.TimeTableStyle;
import upandgo.shared.entities.Day;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.WeekTime;
import upandgo.shared.model.scedule.Color;


public class TimeTableView extends HorizontalPanel { 
	
	static final int FIRST_HOUR = 8;
	static final int LAST_HOUR = 20;
	static final int HOUR_CHUNKS = 2;
	static final int MAX_TABLE_SIZE = 25;
	
	static final int EMPTY_COL = 0;
	static final int HOURS_COL = 0;
	static final int LESSONS_COL = 0;
	
	static final String USER_EVENTS_COURSE_ID = "999999";
	
	static final String[] daysHebrew = {"ראשון", "שני", "שלישי", "רביעי", "חמישי"};
	
	private FlexTable hoursTable = new FlexTable();
	private FlexTable sundayTable = new FlexTable();
	private FlexTable mondayTable = new FlexTable();
	private FlexTable tuesdayTable = new FlexTable();
	private FlexTable wednesdayTable = new FlexTable();
	private FlexTable thursdayTable = new FlexTable();
	
	Modal lessonDetailsBox;
	ModalBody lessonDetailsBoxBody;
	Button lessonDetailsBoxCreateConstraintButton = new Button("<i class=\"fa fa-external-link\" aria-hidden=\"true\"></i>&nbsp;&nbsp;צור אילוץ");
	Button lessonDetailsBoxRemoveConstraintButton = new Button("<i class=\"fa fa-trash\" aria-hidden=\"true\"></i>&nbsp;&nbsp;הסר אילוץ");
	Lesson lessonDetailsBoxCurrentLesson;
	String lessonDetailCurrentCourseId;
	int lessonDetailCurrentGroup;

	Map<WeekTime,UserEvent> userEvents = new HashMap<>();
	Modal userEventBox;
	WeekTime userEventTime = new WeekTime();
	TextBox userEventDescBox = new TextBox();
	ListBox userEventDurationListBox = new ListBox();
	Button userEventBoxSaveButton = new Button("<i class=\"fa fa-floppy-o\" aria-hidden=\"true\"></i>&nbsp;&nbsp;שמור");
	Button userEventBoxDeleteButton = new Button("<i class=\"fa fa-times\" aria-hidden=\"true\"></i>&nbsp;&nbsp;מחק");

	private List<FlexTable> daysTables = new ArrayList<>();
	private List<LessonDetailsView> lessonsDetailsViews = new ArrayList<>();
	private Map<String, Color> colorMap;
		
	
	private TimeTableStyle ttStyle = Resources.INSTANCE.timeTableStyle();
	
	public TimeTableView(){
    	InitializePanel();
    	InitializeLessonDetailsBox();
    	InitializeUserEventBox();
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
    
    static LocalTime rowIndexToLocalTime (int rowIndex){
    	return LocalTime.of(rowIndex / HOUR_CHUNKS + FIRST_HOUR, 60 * rowIndex / HOUR_CHUNKS % HOUR_CHUNKS);
    }
    
    static LocalTime spanToLocalTime (int span){
    	return LocalTime.of(span/HOUR_CHUNKS,60 * span / HOUR_CHUNKS % HOUR_CHUNKS);
    }
    
    static int localTimeToRowIndex (LocalTime t){
    	return t.getMinute() / (60 / HOUR_CHUNKS) + HOUR_CHUNKS * (t.getHour() - FIRST_HOUR);
    }
    
    static int localTimeToSpan (LocalTime t){
    	return HOUR_CHUNKS * t.getHour() + t.getMinute() / (60 / HOUR_CHUNKS);
    }
    
    void setUserEventDurationListBox(LocalTime userEventTime, LocalTime userEventDuration, FlexTable dayTable){
        userEventDurationListBox.clear();
        int availableSpans = WeekTime.difference(userEventDuration,LocalTime.of(00,00))/(60/HOUR_CHUNKS);
        for (int i = 1 ; i <= availableSpans ; ++i)
			userEventDurationListBox.addItem(spanToLocalTime(i).toString());
        for (int spanIndex = availableSpans + 1, tableIndex = localTimeToRowIndex(userEventTime)
				+ localTimeToSpan(userEventDuration); dayTable.getCellFormatter().getStyleName(tableIndex++, 0)
						.contains(ttStyle.noEvent()) && tableIndex <= MAX_TABLE_SIZE;)
			userEventDurationListBox.addItem(spanToLocalTime(spanIndex++).toString());
        
    }
    
    private void drawDaysTable(List<FlexTable> ts) {
    	for(int day = 0; day < 5; ++day){
    		final FlexTable dayTable = ts.get(day);
    		final Day dayFinal = Day.values()[day];
    		dayTable.setText(0, 0, daysHebrew[day]);
    		for (int timeSlot = 1; timeSlot <= MAX_TABLE_SIZE; ++timeSlot){
    			dayTable.setText(timeSlot, 0, "");
    			dayTable.getCellFormatter().addStyleName(timeSlot,0, ttStyle.noEvent());
    		}
    		dayTable.getRowFormatter().addStyleName(0, ttStyle.headerRow());
    		dayTable.addStyleName(ttStyle.dayTable());
    		dayTable.addClickHandler(new ClickHandler() {
				
				@Override
				@SuppressWarnings("synthetic-access")
				public void onClick(ClickEvent e) {
					Cell cell = dayTable.getCellForEvent(e);
					int rowIndex = cell.getRowIndex();
					userEventTime = new WeekTime(dayFinal, rowIndexToLocalTime(rowIndex));
					if (cell.getElement().hasClassName(ttStyle.noEvent())) {
						Log.info("TimeTableView clicked on " + dayFinal + " " + rowIndex);
						userEventBox.setTitle("הוספת אירוע ב" + userEventTime.toHebrewString());
						userEventBoxDeleteButton.setVisible(false);
						userEventDescBox.clear();
						setUserEventDurationListBox(userEventTime.getTime(), LocalTime.of(0, 0), dayTable);
						userEventBox.show();
					} else if (!cell.getElement().hasClassName(ttStyle.userEvent()))
						Log.info("TimeTableView clicked on event cell");
					else {
						UserEvent userEvent = userEvents.get(userEventTime);
						if (userEvent != null) {
							Log.info("TimeTableView clicked on user event cell " + userEvent.getDescription());
							userEventBox.setTitle("עריכת אירוע ב" + userEventTime.toHebrewString());
							userEventBoxDeleteButton.setVisible(true);
							userEventDescBox.setText(userEvent.getDescription());
							setUserEventDurationListBox(userEvent.getWeekTime().getTime(), userEvent.getDuration(),
									dayTable);
							userEventDurationListBox.setSelectedIndex(localTimeToSpan(userEvent.getDuration()) - 1);
							userEventBox.show();
						}
					}
				}
			});
    	}
	}
    
    private void drawHoursTable(FlexTable t) {
    	t.setText(0, 0, "שעה");
    	
    	t.getRowFormatter().addStyleName(0, ttStyle.headerRow());

    	for(int i = 1; i<13; ++i){
    		t.setText(2*i-1, HOURS_COL, Integer.toString(i+7)+":30");
    		t.getFlexCellFormatter().setRowSpan(2*i-1, HOURS_COL, 2);
    		t.getCellFormatter().addStyleName(2*i-1, HOURS_COL, ttStyle.hoursCell());
    	}
    	t.setText(25,0,"20:30");
		t.getCellFormatter().setStyleName(25,0, ttStyle.hoursCell());
    	
	    t.addStyleName(ttStyle.hoursTable());
	}
    
    public void displaySchedule(final List<LessonGroup> gs, Map<String, Color> m, final List<UserEvent> es){
    	clearTable();
    	displayLessons(gs,m);
    	
    	//I don't like this walkaround but for now there has to be
    	//a local copy of userEvents so it would be possible to
    	//assign click events on the time table
    	userEvents.clear();
    	for (UserEvent event : es)
			userEvents.put(event.getWeekTime(), event);
    	
    	
    	displayUserEvents(es);
    }
    
    public void displayUserEvents(final List<UserEvent> es){
    	if (es != null)
			for (UserEvent userEvent : es) {
				Log.info("TimeTableView: displaying event " + userEvent.getDescription());
				int startCell = localTimeToRowIndex(userEvent.getWeekTime().getTime()),
						span = localTimeToSpan(userEvent.getDuration());
				FlexTable dayTable = daysTables.get(userEvent.getWeekTime().getDay().ordinal());
				if ("".equals(dayTable.getText(startCell, 0))) {
					VerticalPanel eventContent = new VerticalPanel();
					eventContent.setStyleName(ttStyle.hasEventContent());
					eventContent.setHorizontalAlignment(ALIGN_CENTER);
					eventContent.add(new Label(userEvent.getDescription()));
					SimplePanel eventWrap = new SimplePanel();
					eventWrap.add(eventContent);
					eventWrap.addStyleName(ttStyle.hasEventWrap());
					dayTable.setWidget(startCell, 0, eventWrap);
					dayTable.getFlexCellFormatter().setRowSpan(startCell, 0, span);
					dayTable.getCellFormatter().setStyleName(startCell, 0, ttStyle.hasEvent());
					dayTable.getCellFormatter().addStyleName(startCell, 0, ttStyle.userEvent());
					for (; span > 1; --span)
						dayTable.removeCell(span + startCell - 1, 0);
				}
			}
    }
    
    
	// this function receives a list of LessonGroup(which is a schedule) and
 	// displays the schedule in the GUI
 	public void displayLessons(final List<LessonGroup> schedule, Map<String, Color> m) {
 		colorMap = m;
  		lessonsDetailsViews.clear();
 		
 		if (schedule != null)
			for (final LessonGroup lg : schedule)
				for (final Lesson l : lg.getLessons()) {
					Log.info("TimeTableView: displaying lesson " + l.toString());
					if (l.getCourseId().equals(USER_EVENTS_COURSE_ID))
						continue;
					LocalTime startTime = l.getStartTime().getTime(), endTime = l.getEndTime().getTime();
					int startCell = WeekTime.difference(startTime, LocalTime.parse("08:30")) / 30 + 1,
							span = WeekTime.difference(endTime, startTime) / 30;
					boolean isTooEarly = false;
					if (startCell < 1) {
						span -= (1 - startCell);
						startCell = 1;
						isTooEarly = true;
					}
					Log.info("TimeTableView: startCell: " + startCell + " span: " + span);
					if ("".equals(daysTables.get(l.getDay()).getText(startCell, 0))) {
						SimplePanel eventWrap = createLessonPanel(l, lg.isConstrained(), isTooEarly);
						daysTables.get(l.getDay()).setWidget(startCell, 0, eventWrap);
						daysTables.get(l.getDay()).getFlexCellFormatter().setRowSpan(startCell, 0, span);
						daysTables.get(l.getDay()).getCellFormatter().setStyleName(startCell, 0, ttStyle.hasEvent());
						for (; span > 1; --span)
							daysTables.get(l.getDay()).removeCell(span + startCell - 1, 0);
					}
				}		
 	}
 	
 	private SimplePanel createLessonPanel(final Lesson l, boolean isConstrained, boolean isTooEarly){
		VerticalPanel eventContent = new VerticalPanel();
		eventContent.setStyleName(ttStyle.hasEventContent());
		eventContent.setHorizontalAlignment(ALIGN_CENTER);
		
		String courseTitle = l.getType().toString() + " - " + l.getCourseId();
		if (isConstrained)
			courseTitle = "<i class=\"fa fa-thumb-tack\" aria-hidden=\"true\"></i>"
					+ "<i class=\"fa fa-thumb-tack\" aria-hidden=\"true\"></i>"
					+ "<i class=\"fa fa-thumb-tack\" aria-hidden=\"true\"></i> " + courseTitle + " "
					+ "<i class=\"fa fa-thumb-tack\" aria-hidden=\"true\"></i>"
					+ "<i class=\"fa fa-thumb-tack\" aria-hidden=\"true\"></i>"
					+ "<i class=\"fa fa-thumb-tack\" aria-hidden=\"true\"></i>";
		else if (isTooEarly)
			courseTitle = "<i class=\"fa fa-angle-double-up\" aria-hidden=\"true\"></i>"
					+ "<i class=\"fa fa-angle-double-up\" aria-hidden=\"true\"></i>"
					+ "<i class=\"fa fa-angle-double-up\" aria-hidden=\"true\"></i> " + courseTitle + " "
					+ "<i class=\"fa fa-angle-double-up\" aria-hidden=\"true\"></i>"
					+ "<i class=\"fa fa-angle-double-up\" aria-hidden=\"true\"></i>"
					+ "<i class=\"fa fa-angle-double-up\" aria-hidden=\"true\"></i>";

		eventContent.add(new HTML(courseTitle));


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
		if (l.getRepresenter()!=null){
			eventContentGrid.setHTML(3, 0, "<i class=\"fa fa-graduation-cap\" aria-hidden=\"true\"></i>&nbsp;&nbsp;");
			eventContentGrid.setHTML(3, 1, l.getRepresenter().getTitle() + "&nbsp;" + l.getRepresenter().getLastName() + "&nbsp;" + l.getRepresenter().getFirstName());
		}
		
		eventContent.add(eventContentGrid);
		
		SimplePanel eventWrap = new SimplePanel();
		eventWrap.add(eventContent);
		eventWrap.addStyleName(ttStyle.hasEventWrap());
		eventWrap.getElement().setAttribute("eventNum", colorMap == null || !colorMap.containsKey(l.getCourseId()) ? "ORANGERED" : colorMap.get(l.getCourseId()).name());
		
		final LessonDetailsView lessonDetailsView = new LessonDetailsView(l);
		lessonsDetailsViews.add(lessonDetailsView);

		eventWrap.sinkEvents(Event.ONCLICK);
		eventWrap.addHandler(new ClickHandler() {
			
			@Override
			public void onClick(@SuppressWarnings("unused") ClickEvent e) {
				lessonDetailsBox.setTitle(l.getType().toString() + " - " + l.getCourseId());
				lessonDetailsBoxCurrentLesson = l;
				lessonDetailsBoxRemoveConstraintButton.setVisible(false);
				lessonDetailsBoxBody.clear();
				lessonDetailsBoxBody.add(lessonDetailsView);
				lessonDetailsBox.show();
				
			}
		}, ClickEvent.getType());
		
		
		return eventWrap;
 	}
 	
 	private void InitializeLessonDetailsBox(){
 		lessonDetailsBox = new Modal();
 		ModalFooter lessonDetailsBoxFooter = new ModalFooter();
		Button lessonDetailsBoxCloseButton = new Button("סגור", new ClickHandler() {
			
			@Override
			public void onClick(@SuppressWarnings("unused") ClickEvent e) {
				lessonDetailsBox.hide();
				
			}
		});
		lessonDetailsBoxCloseButton.setStyleName("btn btn-default");
		lessonDetailsBoxCreateConstraintButton.setStyleName("btn btn-success");
		lessonDetailsBoxRemoveConstraintButton.setStyleName("btn btn-danger");
		lessonDetailsBoxFooter.add(lessonDetailsBoxCreateConstraintButton);
		lessonDetailsBoxFooter.add(lessonDetailsBoxRemoveConstraintButton);
		lessonDetailsBoxFooter.add(lessonDetailsBoxCloseButton);
		lessonDetailsBox.setFade(true);
		
		lessonDetailsBoxBody = new ModalBody();
		
		lessonDetailsBox.add(lessonDetailsBoxBody);
		lessonDetailsBox.add(lessonDetailsBoxFooter);

 	}
 	
	
	public void setNotesOnLessonModal(String courseId, List<String> courseNotes){
		for (LessonDetailsView lessonDetailsView : lessonsDetailsViews)
			if (courseId.contains(lessonDetailsView.getCourseId()))
				for (String note : courseNotes)
					lessonDetailsView.addNote(note);

	}
	
	private void InitializeUserEventBox(){
		userEventBox = new Modal();
		ModalFooter userEventBoxFooter = new ModalFooter();
		
		userEventBoxSaveButton.setStyleName("btn btn-success");
		userEventBoxDeleteButton.setStyleName("btn btn-danger");
				
		userEventBoxFooter.add(userEventBoxDeleteButton);
		userEventBoxFooter.add(userEventBoxSaveButton);
		userEventBox.setFade(true);
		userEventBox.setDataKeyboard(true);
		
		userEventBox.setTitle("הוספת אירוע");
		
		userEventDescBox.addStyleName(ttStyle.userEventDescTextBox());
		userEventDurationListBox.addStyleName(ttStyle.userEventDuraListBox());
		
		userEventDescBox.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent e) {
				if (e.getNativeKeyCode() == KeyCodes.KEY_ENTER)
					userEventBoxSaveButton.click();
				
			}
		});
		
		ModalBody userEventBoxBody = new ModalBody();
		Grid userEventBoxBodyGrid = new Grid(2,2);
		userEventBoxBodyGrid.setStyleName(ttStyle.userEventBoxGrid());
		userEventBoxBodyGrid.setWidget(0, 0, new Text("תיאור"));
		userEventBoxBodyGrid.setWidget(0, 1, userEventDescBox);
		userEventBoxBodyGrid.setWidget(1, 0, new Text("משך"));
		userEventBoxBodyGrid.setWidget(1, 1, userEventDurationListBox);

		userEventBoxBody.add(userEventBoxBodyGrid);
		
		userEventBox.add(userEventBoxBody);
		userEventBox.add(userEventBoxFooter);
	}
	
	public UserEvent getUserEvent(){
		return new UserEvent(userEventTime, userEventDescBox.getText(), spanToLocalTime(userEventDurationListBox.getSelectedIndex()+1));
	}
	
	public Button getUserEventSaveButton(){
		return userEventBoxSaveButton;
	}
	
	@SuppressWarnings("unused")
	public List<UserEvent> getUserEvents(){
		return new ArrayList<UserEvent>(userEvents.values());
	}	
	
	
}