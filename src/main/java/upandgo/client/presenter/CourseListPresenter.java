package upandgo.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.web.bindery.event.shared.EventBus;
import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.HasMouseMoveHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

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

public class CourseListPresenter implements Presenter, MouseOutHandler {

	CoursesServiceAsync rpcService;
	Display display;
	EventBus eventBus;

	String selectedFaculty = "";
	Optional<CourseId> hoveredCourse = Optional.absent();

	Optional<Timer> timer = Optional.absent();

	public interface Display {
		<T extends HasDoubleClickHandlers & HasMouseMoveHandlers & HasMouseOutHandlers> T getSelectedCoursesList();

		<T extends HasDoubleClickHandlers & HasMouseMoveHandlers & HasMouseOutHandlers> T getNotSelectedCoursesList();

		HasChangeHandlers getFacultyDropList();

		void setSelectedCourses(List<CourseId> courses);

		void setNotSelectedCourses(List<CourseId> courses);

		void setFaculties(List<String> faculties);

		int getSelectedCourseRow(DoubleClickEvent event); // pass -1 if none

		int getUnselectedCourseRow(DoubleClickEvent event); // pass -1 if none

		int getHoveredSelectedCourseRow(MouseMoveEvent event); // pass -1 if
																// none

		int getHoveredNotSelectedCourseRow(MouseMoveEvent event); // pass -1 if
																	// none

		int getSelectedFacultyRow(ChangeEvent event); // pass -1 if there is no
														// selectedFaculty
														// chosen

		Widget asWidget();
	}

	List<CourseId> selectedCourses;
	List<CourseId> notSelectedCourses;
	List<String> faculties;

	final int courseDetailsExposingDelay = 2000;

	public CourseListPresenter(CoursesServiceAsync rpc, EventBus eventBus, Display display) {
		this.rpcService = rpc;
		this.display = display;
		this.eventBus = eventBus;
		bind();
	}

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
				fetchNotSelectedCourses();

			}
		});

		// define selected courses list functionality
		display.getSelectedCoursesList().addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
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
							fetchAllCourses();
							eventBus.fireEvent(new UnselectCourseEvent(course));
						}
					});
				}
			}
		});

		display.getSelectedCoursesList().addMouseMoveHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				stopHoveredTimer();
				
				int $ = display.getHoveredSelectedCourseRow(event);
				if ($ < 0) {
					return;
				}
				
				hoveredCourse = Optional.of(selectedCourses.get($));
				timer = Optional.of(new Timer() {
					@Override
					public void run() {
							eventBus.fireEvent(new GetCourseDetailsEvent(hoveredCourse.get()));
					}
				});
				
				timer.get().schedule(courseDetailsExposingDelay);
			}
		});
		
		display.getSelectedCoursesList().addMouseOutHandler(this);

		// define not selected courses list functionality
		display.getNotSelectedCoursesList().addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
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
							fetchAllCourses();
							eventBus.fireEvent(new SelectCourseEvent(course));
						}
					});
				}
			}
		});

		display.getNotSelectedCoursesList().addMouseMoveHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				stopHoveredTimer();
				int $ = display.getHoveredNotSelectedCourseRow(event);
				if ($ < 0) {
					return;
				}
				
				hoveredCourse = Optional.of(notSelectedCourses.get($));
				
				timer = Optional.of(new Timer() {
					@Override
					public void run() {
							eventBus.fireEvent(new GetCourseDetailsEvent(hoveredCourse.get()));
					}
				});
				
				timer.get().schedule(courseDetailsExposingDelay);
			}
		});
		
		display.getNotSelectedCoursesList().addMouseOutHandler(this);
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

		fetchFaculties();
		fetchAllCourses();
	}

	void fetchAllCourses() {
		fetchSelectedCourses();
		fetchNotSelectedCourses();
	}

	void fetchNotSelectedCourses() {
		rpcService.getNotSelectedCourses(selectedFaculty, new AsyncCallback<ArrayList<CourseId>>() {
			@Override
			public void onSuccess(ArrayList<CourseId> result) {
				notSelectedCourses = result;
				display.setNotSelectedCourses(notSelectedCourses);
			}

			@Override
			public void onFailure(@SuppressWarnings("unused") Throwable caught) {
				Window.alert("Error fetching not selected courses.");
				Log.error("Error fetching not selected courses.");
			}
		});
	}

	void fetchSelectedCourses() {
		rpcService.getSelectedCourses(new AsyncCallback<ArrayList<CourseId>>() {
			@Override
			public void onSuccess(ArrayList<CourseId> result) {
				selectedCourses = result;
				display.setSelectedCourses(selectedCourses);
			}

			@Override
			public void onFailure(@SuppressWarnings("unused") Throwable caught) {
				Window.alert("Error fetching selected courses.");
				Log.error("Error fetching selected courses.");
			}
		});
	}

	void fetchFaculties() {
		rpcService.getFaculties(new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				faculties = result;
				display.setFaculties(faculties);
			}

			@Override
			public void onFailure(@SuppressWarnings("unused") Throwable caught) {
				Window.alert("Error fetching faculties.");
				Log.error("Error fetching faculties.");
			}
		});
	}

	@Override
	public void onMouseOut(@SuppressWarnings("unused") MouseOutEvent event) {
		stopHoveredTimer();
	}
	
	void stopHoveredTimer() {
		if(!timer.isPresent())
			return;
		timer.get().cancel();
		timer = Optional.absent();
		hoveredCourse = Optional.absent();
	}
}
