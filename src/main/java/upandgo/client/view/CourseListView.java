package upandgo.client.view;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;

import upandgo.client.common.ColumnDefinition;

public interface CourseListView<T> {

	public interface Presenter<T> {
		void onSelectedCourseClicked(T clickedCourse);

		void onNotSelectedCourseClicked(T clickedCourse);

		void onCourseHighlighted(T highlightedCourse);
	}

	void setPresenter(Presenter<T> presenter);

	void setColumnDefinitions(List<ColumnDefinition<T>> columnDefinitions);

	void setSelectedCourses(List<T> selectedCourses);

	void setNotSelectedCourses(List<T> notSelectedCourses);

	Widget asWidget();
}
