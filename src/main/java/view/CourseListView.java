package view;

/**
 * 
 * @author Nikita Dizhur
 * @since 27-12-2016
 * 
 * Interface that defines part of UI that is in charge of displaying overall course list and chosen courses' list.
 * 
 */
public interface CourseListView extends View {

	String getQuery(); // returns string that user typed in search field

	String getHighlightedCourse(); // gets the course that asked for details

	boolean getIsKdamimChecked(); // is prerequirements check box is checked

	String getFaculty(); // is faculty courses check box is checked

	boolean getIsTakenChecked(); // is taken courses check box is checked

	String getLastPickedCourse();

	String getLastDropedCourse();

	String ALL_FACULTIES_STRING = "כל הפקולטות";
}
