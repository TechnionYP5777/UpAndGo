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
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
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
	
	static final String[] daysHebrew = {"ראשון", "שני", "שלישי", "רביעי", "חמישי"};
	
	private FlexTable hoursTable = new FlexTable();
	private FlexTable sundayTable = new FlexTable();
	private FlexTable mondayTable = new FlexTable();
	private FlexTable tuesdayTable = new FlexTable();
	private FlexTable wednesdayTable = new FlexTable();
	private FlexTable thursdayTable = new FlexTable();
	
	Map<WeekTime,UserEvent> userEvents = new HashMap<>();
	Modal userEventBox = null;
	WeekTime userEventTime = new WeekTime();
	TextBox userEventDescBox = new TextBox();
	ListBox userEventDurationListBox = new ListBox();
	private Button userEventBoxSaveButton = new Button("שמור");
	private Button userEventBoxDeleteButton = new Button("מחק");

	private List<FlexTable> daysTables = new ArrayList<>();
	private List<LessonDetailsView> lessonsDetailsViews = new ArrayList<>();
	private Map<String, Color> colorMap;
		
	
	private TimeTableStyle ttStyle = Resources.INSTANCE.timeTableStyle();
	
	public TimeTableView(){
    	InitializePanel();
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
    	return LocalTime.of((rowIndex/HOUR_CHUNKS)+FIRST_HOUR, (rowIndex%HOUR_CHUNKS)*(60/HOUR_CHUNKS));
    }
    
    static LocalTime spanToLocalTime (int span){
    	return LocalTime.of(span/HOUR_CHUNKS,(span%HOUR_CHUNKS)*(60/HOUR_CHUNKS));
    }
    
    static int localTimeToRowIndex (LocalTime time){
    	return ((time.getHour()-FIRST_HOUR)*HOUR_CHUNKS)+(time.getMinute()/(60/HOUR_CHUNKS));
    }
    
    static int localTimeToSpan (LocalTime time){
    	return (time.getHour()*HOUR_CHUNKS)+(time.getMinute()/(60/HOUR_CHUNKS));
    }
    
    void setUserEventDurationListBox(LocalTime userEventTime, LocalTime userEventDuration, FlexTable dayTable){
        userEventDurationListBox.clear();
        int availableSpans = WeekTime.difference(userEventDuration,LocalTime.of(00,00))/(60/HOUR_CHUNKS);
        for (int i = 1 ; i <= availableSpans ; i++){
        	LocalTime spanTime = spanToLocalTime(i);
        	userEventDurationListBox.addItem(spanTime.toString());
        }
        int spanIndex = availableSpans+1;
        int tableIndex = localTimeToRowIndex(userEventTime)+localTimeToSpan(userEventDuration);
        while (dayTable.getCellFormatter().getStyleName(tableIndex++, 0).contains(ttStyle.noEvent()) && tableIndex <= MAX_TABLE_SIZE){
        	LocalTime spanTime = spanToLocalTime(spanIndex++);
        	userEventDurationListBox.addItem(spanTime.toString());
        }
        
    }
    
    private void drawDaysTable(List<FlexTable> tables) {
    	for(int day = 0; day < 5; day++){
    		final FlexTable dayTable = tables.get(day);
    		final Day dayFinal = Day.values()[day];
    		dayTable.setText(0, 0, daysHebrew[day]);
    		for (int timeSlot = 1; timeSlot <= MAX_TABLE_SIZE; timeSlot++){
    			dayTable.setText(timeSlot, 0, "");
    			dayTable.getCellFormatter().addStyleName(timeSlot,0, ttStyle.noEvent());
    		}
    		dayTable.getRowFormatter().addStyleName(0, ttStyle.headerRow());
    		dayTable.addStyleName(ttStyle.dayTable());
    		dayTable.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
		            //gets the index of the cell you clicked on
					Cell cell = dayTable.getCellForEvent(event);
		            int rowIndex = cell.getRowIndex();
		            userEventTime = new WeekTime(dayFinal,rowIndexToLocalTime(rowIndex));

					if (cell.getElement().hasClassName(ttStyle.noEvent())){
			            Log.info("TimeTableView clicked on " + dayFinal + " " + rowIndex);
			            userEventBox.setTitle("הוספת אירוע ב" + userEventTime.toHebrewString());
			            
			            userEventDescBox.clear();
			            setUserEventDurationListBox(userEventTime.getTime(),LocalTime.of(0,0),dayTable);
			            
			            userEventBox.show();
					} else if (cell.getElement().hasClassName(ttStyle.userEvent())){
						UserEvent userEvent = userEvents.get(userEventTime);
						if (userEvent!=null){
							Log.info("TimeTableView clicked on user event cell " + userEvent.getDescription());
				            userEventBox.setTitle("עריכת אירוע ב" + userEventTime.toHebrewString());
							userEventDescBox.setText(userEvent.getDescription());
							setUserEventDurationListBox(userEvent.getWeekTime().getTime(),userEvent.getDuration(),dayTable);
							userEventDurationListBox.setSelectedIndex(localTimeToSpan(userEvent.getDuration())-1);
							userEventBox.show();
						}				
						
					} else {
			            Log.info("TimeTableView clicked on event cell");
					}
					
				}
			});
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
    
    public void displayUserEvents(){
    	clearTable();
    	for (UserEvent userEvent : userEvents.values()){
			Log.info("TimeTableView: displaying event " + userEvent.getDescription());
			int startCell = localTimeToRowIndex(userEvent.getWeekTime().getTime());
			int span = localTimeToSpan(userEvent.getDuration());
			FlexTable dayTable = daysTables.get(userEvent.getWeekTime().getDay().ordinal());
			if (dayTable.getText(startCell, 0).equals("")){
				VerticalPanel eventContent = new VerticalPanel();
				eventContent.setStyleName(ttStyle.hasEventContent());
				eventContent.setHorizontalAlignment(ALIGN_CENTER);
				eventContent.add(new Label("אירוע"));
				eventContent.add(new Label(userEvent.getDescription()));
				
				SimplePanel eventWrap = new SimplePanel();
				eventWrap.add(eventContent);
				eventWrap.addStyleName(ttStyle.hasEventWrap());

				dayTable.setWidget(startCell, 0, eventWrap);
				dayTable.getFlexCellFormatter().setRowSpan(startCell, 0, span);
				dayTable.getCellFormatter().setStyleName(startCell, 0, ttStyle.hasEvent());
				dayTable.getCellFormatter().addStyleName(startCell, 0, ttStyle.userEvent());
		
				
				while (span > 1){
					dayTable.removeCell(startCell+span-1, 0);
					span--;
				}
			}
    	}
    }
    
    
	// this function receives a list of LessonGroup(which is a schedule) and
 	// displays the schedule in the GUI
 	public void displaySchedule(final List<LessonGroup> schedule, Map<String, Color> map) {
 		colorMap = map;
 		
 		clearTable();
 		lessonsDetailsViews.clear();
 		
 		if (schedule==null){
 			return;
 		}
 		
 		for (final LessonGroup lg : schedule){
			for (final Lesson l : lg.getLessons()) {
				Log.info("TimeTableView: displaying lesson " + l.toString());
				LocalTime startTime = l.getStartTime().getTime();
				LocalTime endTime = l.getEndTime().getTime();
				int startCell = (WeekTime.difference(startTime, LocalTime.parse("08:30"))/30) + 1;
				int span = WeekTime.difference(endTime, startTime)/30;
				Log.info("TimeTableView: startCell: " + startCell + " span: " + span);

				if (daysTables.get(l.getDay()).getText(startCell, 0).equals("")){
					SimplePanel eventWrap = createEventPanel(l);

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
 		
 	}
 	
 	private SimplePanel createEventPanel(Lesson l){
		VerticalPanel eventContent = new VerticalPanel();
		eventContent.setStyleName(ttStyle.hasEventContent());
		eventContent.setHorizontalAlignment(ALIGN_CENTER);
		
		switch (l.getType()) {
			case LABORATORY:
				eventContent.add(new Label("מעבדה - "  + l.getCourseId()));
				break;
			case LECTURE:
				eventContent.add(new Label("הרצאה - "  + l.getCourseId()));
				break;
			case PROJECT:
				eventContent.add(new Label("פרויקט - "  + l.getCourseId()));
				break;
			case SPORT:
				eventContent.add(new Label("ספורט - "  + l.getCourseId()));
				break;
			case TUTORIAL:
				eventContent.add(new Label("תרגול - "  + l.getCourseId()));
				break;
			default:
				eventContent.add(new Label(l.getCourseId()));
				break;
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
		if (l.getRepresenter()!=null){
			eventContentGrid.setHTML(3, 0, "<i class=\"fa fa-graduation-cap\" aria-hidden=\"true\"></i>&nbsp;&nbsp;");
			eventContentGrid.setHTML(3, 1, l.getRepresenter().getTitle() + "&nbsp;" + l.getRepresenter().getLastName() + "&nbsp;" + l.getRepresenter().getFirstName());
		}
		
		eventContent.add(eventContentGrid);
		
		SimplePanel eventWrap = new SimplePanel();
		eventWrap.add(eventContent);
		eventWrap.addStyleName(ttStyle.hasEventWrap());
		if(colorMap != null && colorMap.containsKey(l.getCourseId())){
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
		
		
		return eventWrap;
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
		
		switch (lesson.getType()) {
			case LABORATORY:
				lessonDetailsBox.setTitle("מעבדה - "  + lesson.getCourseId());
				break;
			case LECTURE:
				lessonDetailsBox.setTitle("הרצאה - "  + lesson.getCourseId());
				break;
			case PROJECT:
				lessonDetailsBox.setTitle("פרויקט - "  + lesson.getCourseId());
				break;
			case SPORT:
				lessonDetailsBox.setTitle("ספורט - "  + lesson.getCourseId());
				break;
			case TUTORIAL:
				lessonDetailsBox.setTitle("תרגול - "  + lesson.getCourseId());
				break;
			default:
				lessonDetailsBox.setTitle(lesson.getCourseId());
				break;
		}
		
		LessonDetailsView lessonDetailsView = new LessonDetailsView(lesson);
		lessonsDetailsViews.add(lessonDetailsView);

		ModalBody lessonDetailsBoxBody = new ModalBody();
		lessonDetailsBoxBody.add(lessonDetailsView);
		lessonDetailsBox.add(lessonDetailsBoxBody);
		lessonDetailsBox.add(lessonDetailsBoxFooter);
		return lessonDetailsBox;
	}
	
	public void setNotesOnLessonModal(String courseId, List<String> courseNotes){
		for (LessonDetailsView lessonDetailsView : lessonsDetailsViews){
			if (courseId.contains(lessonDetailsView.getCourseId())){
				for (String note : courseNotes){
					//Log.info("TimeTableView: putting note " + note);
					lessonDetailsView.addNote(note);
				}
			}
		}

	}
	
	private Modal InitializeUserEventBox(){
		userEventBox = new Modal();
		ModalFooter userEventBoxFooter = new ModalFooter();
		userEventBoxFooter.add(userEventBoxSaveButton);
		userEventBoxFooter.add(userEventBoxDeleteButton);
		userEventBox.setFade(true);
		
		userEventBoxSaveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				userEvents.put(userEventTime, getUserEvent());
				Log.info("TimeTableView: saved user event on " + userEventTime.toString());
				//daysTables.get(userEventTime.getDay().ordinal()).getCellFormatter().setStyleName(localTimeToRowIndex(userEventTime.getTime()), 0, ttStyle.userEvent());
				displayUserEvents();
				userEventBox.hide();
			}
		});
		
		userEventBoxDeleteButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				userEvents.remove(userEventTime);
				Log.info("TimeTableView: removed user event on " + userEventTime.toString());
				displayUserEvents();
				userEventBox.hide();
			}
		});
		
		userEventBox.setTitle("הוספת אירוע");
		
		ModalBody userEventBoxBody = new ModalBody();
		Grid userEventBoxBodyGrid = new Grid(2,2);
		userEventBoxBodyGrid.setWidget(0, 0, new Text("תיאור"));
		userEventBoxBodyGrid.setWidget(0, 1, userEventDescBox);
		userEventBoxBodyGrid.setWidget(1, 0, new Text("משך"));
		userEventBoxBodyGrid.setWidget(1, 1, userEventDurationListBox);

		userEventBoxBody.add(userEventBoxBodyGrid);
		
		userEventBox.add(userEventBoxBody);
		userEventBox.add(userEventBoxFooter);

		return userEventBox;
	}
	
	public UserEvent getUserEvent(){
		return new UserEvent(userEventTime, userEventDescBox.getText(), spanToLocalTime(userEventDurationListBox.getSelectedIndex()+1));
	}
	
	public Button getUserEventSaveButton(){
		return userEventBoxSaveButton;
	}
	
	public List<UserEvent> getUserEvents(){
		return new ArrayList<UserEvent>(userEvents.values());
	}
	
}