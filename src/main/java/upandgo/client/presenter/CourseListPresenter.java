package upandgo.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import upandgo.client.CoursesServiceAsync;
import upandgo.client.event.GetCourseDetailsEvent;
import upandgo.client.event.SelectCourseEvent;
import upandgo.client.event.UnselectCourseEvent;
import upandgo.client.view.CourseListView;
import com.allen_sauer.gwt.log.client.Log;
import com.gargoylesoftware.htmlunit.javascript.host.event.Event;

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

public class CourseListPresenter implements Presenter, MouseOverHandler {

	CoursesServiceAsync rpcService;
	Display display;
	EventBus eventBus;

	String selectedFaculty = "";
	Optional<CourseId> hoveredCourse = Optional.absent();

	public interface Display {
		<T extends HasDoubleClickHandlers & HasMouseOverHandlers & HasMouseOutHandlers> T getSelectedCoursesList();

		<T extends HasDoubleClickHandlers & HasMouseOverHandlers & HasMouseOutHandlers> T getNotSelectedCoursesList();

		HasChangeHandlers getFacultyDropList();

		void setSelectedCourses(List<CourseId> courses);

		void setNotSelectedCourses(List<CourseId> courses);
		
		void setFaculties(List<String> faculties);

		int getSelectedCourseRow(DoubleClickEvent event);	// pass -1 if none

		int getUnselectedCourseRow(DoubleClickEvent event);	// pass -1 if none
		
		int getHoveredSelectedCourseRow(MouseOverEvent event);	// pass -1 if none
		
		int getHoveredNotSelectedCourseRow(MouseOverEvent event);	// pass -1 if none

		int getSelectedFacultyRow(ChangeEvent event);	// pass -1 if there is no selectedFaculty chosen

		Widget asWidget();
	}
	
	List<CourseId> selectedCourses;
	List<CourseId> notSelectedCourses;
	List<String> faculties;
	

	public CourseListPresenter(CoursesServiceAsync rpc, EventBus eventBus, Display display) {
		this.rpcService = rpc;
		this.display = display;
		this.eventBus = eventBus;
		bind();
	}

	@Override
	public void bind() {
		display.getFacultyDropList().addChangeHandler(new ChangeHandler(){

			@Override
			public void onChange(ChangeEvent event) {
				int $ = display.getSelectedFacultyRow(event);
				if ($ >= 0)
					return;
				selectedFaculty = faculties.get($);
				fetchNotSelectedCourses();
				
			}});
		
		display.getSelectedCoursesList().addDoubleClickHandler(new DoubleClickHandler(){

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				int $ = display.getUnselectedCourseRow(event);
				if($ >= 0) {
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
		
		display.getSelectedCoursesList().addMouseOverHandler(new MouseOverHandler(){

			@Override
			public void onMouseOver(@SuppressWarnings("unused") MouseOverEvent event) {
				// TODO Auto-generated method stub
				
			}});
		
		display.getNotSelectedCoursesList().addDoubleClickHandler(new DoubleClickHandler(){

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				int $ = display.getSelectedCourseRow(event);
				if($ >= 0) {
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
			}});
		
		display.getNotSelectedCoursesList().addMouseOverHandler(new MouseOverHandler(){

			@Override
			public void onMouseOver(@SuppressWarnings("unused") MouseOverEvent event) {
				// TODO Auto-generated method stub
				
			}});
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

//	@Override
//	public void onSelectedCourseClicked(CourseId clickedCourse) {
//		rpcService.unselectCourse(clickedCourse, new AsyncCallback<Void>() {
//			@Override
//			public void onFailure(@SuppressWarnings("unused") Throwable caught) {
//				Window.alert("Error while unselecting course.");
//				Log.error("Error while unselecting course.");
//			}
//
//			@Override
//			public void onSuccess(@SuppressWarnings("unused") Void result) {
//				CourseListPresenter.this.fetchCourses();
//				CourseListPresenter.this.eventBus.fireEvent(new UnselectCourseEvent(clickedCourse));
//			}
//		});
//	}
//
//	@Override
//	public void onNotSelectedCourseClicked(CourseId clickedCourse) {
//		rpcService.selectCourse(clickedCourse, new AsyncCallback<Void>() {
//			@Override
//			public void onFailure(@SuppressWarnings("unused") Throwable caught) {
//				Window.alert("Error while selecting course.");
//				Log.error("Error while selecting course.");
//			}
//
//			@Override
//			public void onSuccess(@SuppressWarnings("unused") Void result) {
//				CourseListPresenter.this.fetchCourses();
//				CourseListPresenter.this.eventBus.fireEvent(new SelectCourseEvent(clickedCourse));
//			}
//		});
//	}
//
//	@Override
//	public void onCourseHighlighted(CourseId highlightedCourse) {
//		eventBus.fireEvent(new GetCourseDetailsEvent(highlightedCourse));
//	}

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
	public void onMouseOver(MouseOverEvent event) {
//		event.
//		display.getNotSelectedCoursesList().
//		Timer timer = new Timer() {
//			@Override
//			public void run() {
//				eventBus.fireEvent(new GetCourseDetailsEvent(event));
//			}
//		};
		
	}
}
