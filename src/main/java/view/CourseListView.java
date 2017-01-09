package view;

public interface CourseListView extends View {
	String getQuery();	// returns string that user typed in search field
	String getHighlightedCourse();

	String getLastPickedCourse();
	String getLastDropedCourse();
}
