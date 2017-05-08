package upandgo.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface Resources extends ClientBundle {
	public static final Resources INSTANCE =  GWT.create(Resources.class);
	
	 interface CourseListStyle extends CssResource {
		   String ChosenCourses();
	 }
	 
	 interface TimeTableStyle extends CssResource {	 
		 String timeTable();
		 
		 String dayTable();
		 
		 String headerRow();
		 
		 String tableRow();
		 
		 String arciCol();
		 
		 String arciCol2();
		 
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

	 interface ConstraintsStyle extends CssResource {	 
		 String constraintsPanel();
		 
		 String onlyCheckBox();
		 
		 String timeCheckBox();
	 }
	 
	 interface NavBarStyle extends CssResource {	 
		 String NavBarPanel();
	 }
	 
	@Source("resources/upandgo.css")
	public CssResource mainCss();

	@Source("resources/CourseListStyle.css")
	public CourseListStyle courseListStyle();
	
	@Source("resources/TimeTableStyle.css")
	public TimeTableStyle timeTableStyle();
	
	@Source("resources/SchedualerControlsStyle.css")
	public SchedualerControlsStyle schedualerControlsStyle();
	
	@Source("resources/ConstraintsStyle.css")
	public ConstraintsStyle constraintsStyle();
	
	@Source("resources/NavBarStyle.css")
	public NavBarStyle navBarStyle();

}
