package upandgo.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface Resources extends ClientBundle {
	public static final Resources INSTANCE =  GWT.create(Resources.class);
	
	 interface MainStyle extends CssResource {
		   String scrollableTimeTable();
	 }
	 
	 interface CourseListStyle extends CssResource {
		   String ChosenCourses();
		   
		   String loadingLogo();
	 }
	 
	 interface DialogBoxStyle extends CssResource {	 
		 String timeTable();
		 
	 }
	 
	 interface TimeTableStyle extends CssResource {	 
		 String timeTable();
		 
		 String dayTable();
		 
		 String headerRow();
		 
		 String tableRow();
		 
		 String hoursTable();
		 
		 String hoursCell();
		 
		 String dayCol();

		 String noEvent();
		 
		 String hasEvent();
		 
		 String hasEventWrap();
		 
		 String hasEventContent();
		 
		 String userEvent();
		 
		 String userEventBoxGrid();
		 
		 String userEventDescTextBox();
		 
		 String userEventDuraListBox();

	 }
	 
	 interface SchedualerControlsStyle extends CssResource {	 
		 String SchedulerControlsPanel();
		 
		 String SchedulerIndexWell();
	 }
	 
	 interface ExamsControlsStyle extends CssResource {	 
		 String examsControlsPanel();
	 }
	 
	 interface SchedualerConstraintsStyle extends CssResource {	 		 
		 String constraintsPanel();
		 
		 String constraintsLabel();
		 
		 String timeStartEndPanel();
		 
		 String spacerPanel();
		 
		 String horizontalLine();
		 
		 String coursesConstraintsPanel();
		 
		 String courseConstraintsPanel();
		 
		 String onlyCheckBox();
		 
		 String timeCheckBox();
		 
		 String timeListBox();
		 
		 String courseCheckBox();
		 
		 String courseListBox();

	 }

	 
	 interface NavBarStyle extends CssResource {	 
		 //String navBarPanel();
		 
		 String choosenSemesterEntry();
		 
		 String navBarLink();
	 }
	 
	 interface ExamsBarStyle extends CssResource{
		 String examBarPanel();
	 }
	 
	 interface CourseListCellStyle extends CssResource{
		 String courseListCellPanel();
		 
	 }
	 
	 interface LessonDetailsStyle extends CssResource{
		 String lessonDetailsPanel();
	 }
	 
	@Source("resources/upandgo.css")
	public MainStyle mainStyle();

	@Source("resources/CourseListStyle.css")
	public CourseListStyle courseListStyle();
	
	@Source("resources/TimeTableStyle.css")
	public TimeTableStyle timeTableStyle();
	
	@Source("resources/SchedualerControlsStyle.css")
	public SchedualerControlsStyle schedualerControlsStyle();
	
	@Source("resources/ExamsControlsStyle.css")
	public ExamsControlsStyle examsControlsStyle();
	
	@Source("resources/SchedualerConstraintsStyle.css")
	public SchedualerConstraintsStyle schedualerConstraintsStyle();
	
	@Source("resources/NavBarStyle.css")
	public NavBarStyle navBarStyle();
	
	@Source("resources/DialogBoxStyle.css")
	public DialogBoxStyle dialogBoxStyle();
	
	@Source("resources/ExamsBarStyle.css")
	public ExamsBarStyle examsBarStyle();
	
	@Source("resources/CourseListCellStyle.css")
	public CourseListCellStyle courseListCellStyle();
	
	@Source("resources/LessonDetailsStyle.css")
	public LessonDetailsStyle lessonDetailsStyle();
	

}
