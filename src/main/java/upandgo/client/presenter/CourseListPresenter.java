package upandgo.client.presenter;

import java.util.List;

import com.google.web.bindery.event.shared.EventBus;
import com.google.common.base.Optional;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.HasCellPreviewHandlers;

import upandgo.client.CoursesServiceAsync;
import upandgo.client.event.GetCourseDetailsEvent;
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

// TODO: add History management

public class CourseListPresenter implements Presenter {

	public interface Display {
		HasCellPreviewHandlers<CourseId> getSelectedCoursesList();

		HasCellPreviewHandlers<CourseId> getNotSelectedCoursesList();

		HasChangeHandlers getFacultyDropList();

		HasKeyUpHandlers getCourseSearch();

		void setSelectedCourses(List<CourseId> courses);

		void setNotSelectedCourses(List<CourseId> courses);

		void setFaculties(List<String> faculties);

		int getSelectedCourseRow(CellPreviewEvent<CourseId> event); // pass -1 if none

		int getUnselectedCourseRow(CellPreviewEvent<CourseId> event); // pass -1 if none

		int getHoveredSelectedCourseRow(CellPreviewEvent<CourseId> event); // pass -1 if none

		int getHoveredNotSelectedCourseRow(CellPreviewEvent<CourseId> event); // pass -1 if none

		int getSelectedFacultyRow(ChangeEvent event); // pass -1 if there is no selectedFaculty chosen

		String getCourseQuery(KeyUpEvent event);

		Widget asWidget();
	}
	
	final int courseDetailsExposingDelay = 2000;
	final CourseDetailsTimer courseDetailsTimer = new CourseDetailsTimer();

	public CourseListPresenter(CoursesServiceAsync rpc, EventBus eventBus, Display display) {
		this.rpcService = rpc;
		this.display = display;
		this.eventBus = eventBus;
		bind();
	}

	CoursesServiceAsync rpcService;
	Display display;
	EventBus eventBus;
	
	List<CourseId> selectedCourses;
	List<CourseId> notSelectedCourses;
	List<String> faculties;
	String courseQuery = "";

	String selectedFaculty = "";
	Optional<CourseId> hoveredCourse = Optional.absent();


	@Override
	public void bind() {

		// define faculty list functionality
		display.getFacultyDropList().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				int $ = display.getSelectedFacultyRow(event);
				if ($ >= 0)
					return;
				selectedFaculty = faculties.get($);
				rpcService.getNotSelectedCourses(courseQuery, selectedFaculty,
						new FetchNotSelectedCoursesAsyncCallback());

			}
		});

		// define selected courses list functionality
		display.getSelectedCoursesList().addCellPreviewHandler(new Handler<CourseId>() {

			@Override
			public void onCellPreview(CellPreviewEvent<CourseId> event) {
				boolean isDbClick = BrowserEvents.DBLCLICK.equals(event.getNativeEvent().getType());
				boolean isMouseMove = BrowserEvents.MOUSEMOVE.equals(event.getNativeEvent().getType());
				boolean isMouseOut = BrowserEvents.MOUSEOUT.equals(event.getNativeEvent().getType());

				if (isDbClick) {
					int $ = display.getUnselectedCourseRow(event);
					if ($ >= 0) {
						CourseId course = selectedCourses.get($);
						rpcService.unselectCourse(course, new AsyncCallback<Void>() {
							@Override
							public void onFailure(@SuppressWarnings("unused") Throwable caught) {
								Window.alert("Error while unselecting course.");
								Log.error("Error while unselecting course.");
							}

							@Override
							public void onSuccess(@SuppressWarnings("unused") Void result) {
								rpcService.getSelectedCourses(new FetchSelectedCoursesAsyncCallback());
								rpcService.getNotSelectedCourses(courseQuery, selectedFaculty,
										new FetchNotSelectedCoursesAsyncCallback());

								eventBus.fireEvent(new UnselectCourseEvent(course));
							}
						});
					}
				}

				if (isMouseMove) {
					stopHoveredTimer();

					int $ = display.getHoveredSelectedCourseRow(event);
					if ($ < 0) {
						return;
					}

					hoveredCourse = Optional.of(selectedCourses.get($));
					courseDetailsTimer.schedule(courseDetailsExposingDelay);
				}

				if (isMouseOut)
					stopHoveredTimer();

			}
		});

		// define not selected courses list functionality
		display.getNotSelectedCoursesList().addCellPreviewHandler(new Handler<CourseId>() {

			@Override
			public void onCellPreview(CellPreviewEvent<CourseId> event) {
				boolean isDbClick = BrowserEvents.DBLCLICK.equals(event.getNativeEvent().getType());
				boolean isMouseMove = BrowserEvents.MOUSEMOVE.equals(event.getNativeEvent().getType());
				boolean isMouseOut = BrowserEvents.MOUSEOUT.equals(event.getNativeEvent().getType());

				if (isDbClick) {
					int $ = display.getSelectedCourseRow(event);
					if ($ >= 0) {
						CourseId course = selectedCourses.get($);
						rpcService.selectCourse(course, new AsyncCallback<Void>() {
							@Override
							public void onFailure(@SuppressWarnings("unused") Throwable caught) {
								Window.alert("Error while selecting course.");
								Log.error("Error while selecting course.");
							}

							@Override
							public void onSuccess(@SuppressWarnings("unused") Void result) {
								rpcService.getSelectedCourses(new FetchSelectedCoursesAsyncCallback());
								rpcService.getNotSelectedCourses(courseQuery, selectedFaculty,
										new FetchNotSelectedCoursesAsyncCallback());

								eventBus.fireEvent(new SelectCourseEvent(course));
							}
						});
					}
				}

				if (isMouseMove) {
					stopHoveredTimer();
					int $ = display.getHoveredNotSelectedCourseRow(event);
					if ($ < 0) {
						return;
					}

					hoveredCourse = Optional.of(notSelectedCourses.get($));

					courseDetailsTimer.schedule(courseDetailsExposingDelay);
				}

				if (isMouseOut)
					stopHoveredTimer();

			}
		});

		display.getCourseSearch().addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				courseQuery = display.getCourseQuery(event);
				rpcService.getNotSelectedCourses(courseQuery, selectedFaculty,
						new FetchNotSelectedCoursesAsyncCallback());
			}
		});
	}

	@Override
	public void unbind() {
		// TODO
	}

	@Override
	public void go(Panel panel) {
		bind();

		panel.clear();
		panel.add(display.asWidget());

		rpcService.getFaculties(new FetchFacultiesAsyncCallback());
		rpcService.getSelectedCourses(new FetchSelectedCoursesAsyncCallback());
		rpcService.getNotSelectedCourses(courseQuery, selectedFaculty, new FetchNotSelectedCoursesAsyncCallback());
	}

	void stopHoveredTimer() {
		courseDetailsTimer.cancel();
		hoveredCourse = Optional.absent();
	}

	class FetchSelectedCoursesAsyncCallback implements AsyncCallback<List<CourseId>> {
		@Override
		public void onSuccess(List<CourseId> result) {
			selectedCourses = result;
			display.setSelectedCourses(selectedCourses);
		}

		@Override
		public void onFailure(@SuppressWarnings("unused") Throwable caught) {
			Window.alert("Error fetching selected courses.");
			Log.error("Error fetching selected courses.");
		}
	}

	class FetchNotSelectedCoursesAsyncCallback implements AsyncCallback<List<CourseId>> {
		@Override
		public void onSuccess(List<CourseId> result) {
			notSelectedCourses = result;
			display.setNotSelectedCourses(notSelectedCourses);
		}

		@Override
		public void onFailure(@SuppressWarnings("unused") Throwable caught) {
			Window.alert("Error fetching not selected courses.");
			Log.error("Error fetching not selected courses.");
		}
	}

	class FetchFacultiesAsyncCallback implements AsyncCallback<List<String>> {
		@Override
		public void onSuccess(List<String> result) {
			faculties = result;
			display.setFaculties(faculties);
		}

		@Override
		public void onFailure(@SuppressWarnings("unused") Throwable caught) {
			Window.alert("Error fetching faculties.");
			Log.error("Error fetching faculties.");
		}
	}

	class CourseDetailsTimer extends Timer {
		@Override
		public void run() {
			eventBus.fireEvent(new GetCourseDetailsEvent(hoveredCourse.get()));
		}
	}
}
