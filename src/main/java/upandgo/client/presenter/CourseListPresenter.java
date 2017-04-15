package upandgo.client.presenter;

import java.util.ArrayList;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;

import upandgo.client.CoursesServiceAsync;
import upandgo.client.view.CourseListView;
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

	// public interface Display extends HasValue<List<CourseId>> {
	// public void setData(List<CourseId> notSelectedCourses, List<CourseId>
	// selectedCourses);
	//
	// public void clear();
	//
	// HasClickHandlers getSelectedCoursesList();
	//
	// HasClickHandlers getNotSelectedCoursesList();
	//
	// int getClickedSelectedCoursesRow(ClickEvent event);
	//
	// int getClickedNotSelectedCoursesRow(ClickEvent event);
	//
	// public Widget asWidget();
	//
	// public void setPresenter(CourseListPresenter p);
	// }

	public CourseListPresenter(CoursesServiceAsync rpc, CourseListView<CourseId> display, EventBus eventBus) {
		this.rpcService = rpc;
		this.view = display;
		this.eventBus = eventBus;
		this.view.setPresenter(this);
	}

	@Override
	public void bind() {
		// // register to events
		// view.setPresenter(this);
		// // TODO: display.populateCourseList();
		//
		// view.getSelectedCoursesList().addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// int selectedRow = view.getClickedSelectedCoursesRow(event);
		// if (selectedRow >= 0) {
		// // TODO:
		// // String id = contacts.get(selectedRow).getId();
		// // eventBus.fireEvent(new EditContactEvent(id));
		// }
		// }
		// });
		//
		// view.getNotSelectedCoursesList().addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// int selectedRow = view.getClickedNotSelectedCoursesRow(event);
		// if (selectedRow >= 0) {
		// // TODO:
		// // String id = contacts.get(selectedRow).getId();
		// // eventBus.fireEvent(new EditContactEvent(id));
		// return;
		// }
		// }
		// });
		//
	}

	@Override
	public void go(Panel panel) {
		panel.add(view.asWidget());

	}

	@Override
	public void unbind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSelectedCourseClicked(@SuppressWarnings("unused") CourseId clickedCourse) {
		// TODO Auto-generated method stub
		// eventBus.fireEvent(new EditContactEvent(contactDetails.getId()));

	}

	@Override
	public void onCourseHighlighted(@SuppressWarnings("unused") CourseId highlightedCourse) {
		// TODO Auto-generated method stub
		// eventBus.fireEvent(new EditContactEvent(contactDetails.getId()));

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
				//TODO Can't user logger on client side
				//Log.error("Error fetching not selected courses.");
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
				//TODO Can't user logger on client side
				//Log.error("Error fetching not selected courses.");
			}
		});
	}

	@Override
	public void onNotSelectedCourseClicked(@SuppressWarnings("unused") CourseId clickedCourse) {
		// TODO Auto-generated method stub
		// eventBus.fireEvent(new EditContactEvent(contactDetails.getId()));
		
	}

}
 