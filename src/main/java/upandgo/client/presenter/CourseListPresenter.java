package upandgo.client.presenter;

import java.util.ArrayList;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;

import upandgo.client.CoursesServiceAsync;
import upandgo.client.event.HighlightCourseEvent;
import upandgo.client.event.SelectCourseEvent;
import upandgo.client.event.UnselectCourseEvent;
import upandgo.client.view.CourseListView;
import upandgo.server.logger.Log;
import upandgo.shared.entities.course.CourseId;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-04-16
 * 
 * A concrete presenter for {@link CourseListView}.
 * 
 */

// TODO: add History management and Event management

public class CourseListPresenter implements Presenter, CourseListView.Presenter<CourseId> {

	CoursesServiceAsync rpcService;
	CourseListView<CourseId> view;
	EventBus eventBus;

	public CourseListPresenter(CoursesServiceAsync rpc, CourseListView<CourseId> display, EventBus eventBus) {
		this.rpcService = rpc;
		this.view = display;
		this.eventBus = eventBus;
		this.view.setPresenter(this);
//	    TODO: this.view.setColumnDefinitions(columnDefinitions);
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
		 eventBus.fireEvent(new SelectCourseEvent(clickedCourse));
		 fetchCourses();
	}

	@Override
	public void onNotSelectedCourseClicked(CourseId clickedCourse) {
		 eventBus.fireEvent(new UnselectCourseEvent(clickedCourse));
		 fetchCourses();
	}
	
	@Override
	public void onCourseHighlighted(CourseId highlightedCourse) {
		 eventBus.fireEvent(new HighlightCourseEvent(highlightedCourse));
	}

	@SuppressWarnings("unused")
	private void fetchCourses() {
		rpcService.getNotSelectedCourses(new AsyncCallback<ArrayList<CourseId>>() {
			@Override
			public void onSuccess(ArrayList<CourseId> result) {
				view.setNotSelectedCourses(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching not selected courses.");
				Log.error("Error fetching not selected courses.");
			}
		});
		
		rpcService.getSelectedCourses(new AsyncCallback<ArrayList<CourseId>>() {
			@Override
			public void onSuccess(ArrayList<CourseId> result) {
				view.setSelectedCourses(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching selected courses.");
				Log.error("Error fetching not selected courses.");
			}
		});
	}

}
 