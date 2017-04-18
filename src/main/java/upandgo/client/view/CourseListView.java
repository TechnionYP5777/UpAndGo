package upandgo.client.view;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;

import upandgo.client.common.ColumnDefinition;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-04-16
 * 
 * Class that is in charge of creating UI with list of existing courses and choosing the ones that we want to schedule.
 * 
 * The presenter can pass the model untouched and the view has no rendering code.
 */

public interface CourseListView<T> {

	public interface Presenter<T> {
		void onSelectedCourseClicked(T clickedCourse);

		void onNotSelectedCourseClicked(T clickedCourse);

		void onCourseHighlighted(T highlightedCourse);
		
		void onFacultySelected(String faculty);
	}

	void setPresenter(Presenter<T> presenter);

	void setColumnDefinitions(List<ColumnDefinition<T>> columnDefinitions);

	void setSelectedCourses(List<T> selectedCourses);

	void setNotSelectedCourses(List<T> notSelectedCourses);

	Widget asWidget();
}
