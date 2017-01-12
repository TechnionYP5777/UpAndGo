package view;

public interface CourseListView extends View {
	
	String getQuery(); // returns string that user typed in search field

	String getHighlightedCourse(); // gets the course that asked for details

	boolean getIsKdamimChecked(); // is prerequirements check box is checked

	String getFaculty();  // is faculty courses check box is checked

	boolean getIsTakenChecked();  // is taken courses check box is checked

	
	String getLastPickedCourse();

	String getLastDropedCourse();
	
}
