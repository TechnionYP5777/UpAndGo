package upandgo.client.presenter;

import java.util.ArrayList;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;

import upandgo.client.CoursesServiceAsync;
import upandgo.client.event.HighlightCourseEvent;
import upandgo.client.event.SelectCourseEvent;
import upandgo.client.event.UnselectCourseEvent;
import upandgo.client.view.CourseListView;
import com.allen_sauer.gwt.log.client.Log;
import upandgo.shared.entities.course.CourseId;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-04-17
 * 
 *        A concrete presenter for {@link CourseListView}.
 * 
 */

// TODO: add History management and Event management

public class CourseListPresenter implements Presenter, CourseListView.Presenter<CourseId> {

	CoursesServiceAsync rpcService;
	CourseListView<CourseId> view;
	EventBus eventBus;

	String faculty = "";

	public CourseListPresenter(CoursesServiceAsync rpc, CourseListView<CourseId> display, EventBus eventBus) {
		this.rpcService = rpc;
		this.view = display;
		this.eventBus = eventBus;
		this.view.setPresenter(this);
		// TODO: this.view.setColumnDefinitions(columnDefinitions);
	}

	@Override
	public void bind() {
		// do nothing
	}

	@Override
	public void unbind() {
		// do nothing
	}

	@Override
	public void go(Panel panel) {
		panel.clear();
		panel.add(view.asWidget());

		fetchCourses();
	}

	@Override
	public void onSelectedCourseClicked(CourseId clickedCourse) {
		rpcService.unselectCourse(clickedCourse, new AsyncCallback<Void>() {
			@Override
			public void onFailure(@SuppressWarnings("unused") Throwable caught) {
				Window.alert("Error while unselecting course.");
				Log.error("Error while unselecting course.");
			}

			@Override
			public void onSuccess(@SuppressWarnings("unused") Void result) {
				CourseListPresenter.this.fetchCourses();
				CourseListPresenter.this.eventBus.fireEvent(new UnselectCourseEvent(clickedCourse));
			}
		});
	}

	@Override
	public void onNotSelectedCourseClicked(CourseId clickedCourse) {
		rpcService.selectCourse(clickedCourse, new AsyncCallback<Void>() {
			@Override
			public void onFailure(@SuppressWarnings("unused") Throwable caught) {
				Window.alert("Error while selecting course.");
				Log.error("Error while selecting course.");
			}

			@Override
			public void onSuccess(@SuppressWarnings("unused") Void result) {
				CourseListPresenter.this.fetchCourses();
				CourseListPresenter.this.eventBus.fireEvent(new SelectCourseEvent(clickedCourse));
			}
		});
	}

	@Override
	public void onCourseHighlighted(CourseId highlightedCourse) {
		eventBus.fireEvent(new HighlightCourseEvent(highlightedCourse));
	}

	void fetchCourses() {
		rpcService.getSelectedCourses(new AsyncCallback<ArrayList<CourseId>>() {
			@Override
			public void onSuccess(ArrayList<CourseId> result) {
				view.setSelectedCourses(result);
			}

			@Override
			public void onFailure(@SuppressWarnings("unused") Throwable caught) {
				Window.alert("Error fetching selected courses.");
				Log.error("Error fetching selected courses.");
			}
		});

		rpcService.getNotSelectedCourses(faculty, new AsyncCallback<ArrayList<CourseId>>() {
			@Override
			public void onSuccess(ArrayList<CourseId> result) {
				view.setNotSelectedCourses(result);
			}

			@Override
			public void onFailure(@SuppressWarnings("unused") Throwable caught) {
				Window.alert("Error fetching not selected courses.");
				Log.error("Error fetching not selected courses.");
			}
		});
	}

	@Override
	public void onFacultySelected(String f) {
		if (this.faculty.equals(f))
			return;
		this.faculty = f;
		fetchCourses();
	}

}
