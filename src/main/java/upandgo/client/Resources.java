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
	 }
	 
	 interface SchedualerControlsStyle extends CssResource {	 
		 String SchedualerControlsPanel();
	 }
	 
	 interface SchedualerConstraintsStyle extends CssResource {	 		 
		 String constraintsPanel();
		 
		 String constraintsLabel();
		 
		 String onlyCheckBox();
		 
		 String timeCheckBox();
	 }

	 
	 interface NavBarStyle extends CssResource {	 
		 String NavBarPanel();
	 }
	 
	@Source("resources/upandgo.css")
	public MainStyle mainStyle();

	@Source("resources/CourseListStyle.css")
	public CourseListStyle courseListStyle();
	
	@Source("resources/TimeTableStyle.css")
	public TimeTableStyle timeTableStyle();
	
	@Source("resources/SchedualerControlsStyle.css")
	public SchedualerControlsStyle schedualerControlsStyle();
	
	@Source("resources/SchedualerConstraintsStyle.css")
	public SchedualerConstraintsStyle schedualerConstraintsStyle();
	
	@Source("resources/NavBarStyle.css")
	public NavBarStyle navBarStyle();
	
	@Source("resources/DialogBoxStyle.css")
	public DialogBoxStyle dialogBoxStyle();
	

}
