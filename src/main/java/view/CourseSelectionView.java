package view;

/**
 * 
 * @author Nikita Dizhur
 * @since 27-12-2016
 * 
 * Interface that defines part of UI that is in charge of displaying overall course list and chosen courses' list
 * (isn't used anymore).
 * 
 */
public interface CourseSelectionView extends View {
	String getLastPickedCourse();

	String getLastDropedCourse();
}
