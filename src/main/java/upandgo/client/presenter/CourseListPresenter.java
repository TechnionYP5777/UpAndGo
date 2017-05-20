package upandgo.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.common.base.Optional;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.inject.Inject;

import upandgo.client.CoursesServiceAsync;
import upandgo.client.event.GetCourseDetailsEvent;
import upandgo.client.event.SelectCourseEvent;
import upandgo.client.event.UnselectCourseEvent;
import com.allen_sauer.gwt.log.client.Log;

import upandgo.shared.entities.course.Course;
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
		CellTable<CourseId> getSelectedCoursesList();

		CellTable<CourseId> getNotSelectedCoursesList();

		HasChangeHandlers getFacultyDropList();

		HasKeyUpHandlers getCourseSearch();

		void setSelectedCourses(List<CourseId> courses);

		void setNotSelectedCourses(List<CourseId> courses);

		void setFaculties(List<String> faculties);
		
		void setHoveredRow(int row);
		
		
		void setHoveredCourseDetail(String detail);

		CourseId getSelectedCourse();

		CourseId getUnselectedCourse();

		int getHoveredSelectedCourseRow(CellPreviewEvent<CourseId> event); // pass -1 if none

		int getHoveredNotSelectedCourseRow(CellPreviewEvent<CourseId> event); // pass -1 if none

		int getSelectedFacultyRow(ChangeEvent event); // pass -1 if there is no selectedFaculty chosen

		String getCourseQuery(KeyUpEvent event);

		Widget getAsWidget();
	}
	
	final int courseDetailsExposingDelay = 2000;
	final CourseDetailsTimer courseDetailsTimer = new CourseDetailsTimer();

	@Inject
	public CourseListPresenter(CoursesServiceAsync rpc, EventBus eventBus, Display display) {
		this.rpcService = rpc;
		this.display = display;
		this.eventBus = eventBus;
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
	int hoveredRow = -1;


	@Override
	public void bind() {

		// define faculty list functionality
		display.getFacultyDropList().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				int $ = display.getSelectedFacultyRow(event);
				if ($ < 0)
					return;
				selectedFaculty = faculties.get($);
				rpcService.getNotSelectedCourses(courseQuery, selectedFaculty,
						new FetchNotSelectedCoursesAsyncCallback());

			}
		});

		// define selected courses list functionality
		display.getSelectedCoursesList().addDomHandler(new DoubleClickHandler() {

	        @Override
	        public void onDoubleClick(@SuppressWarnings("unused") final DoubleClickEvent event) {
				final CourseId $ = display.getUnselectedCourse();
				if ($ != null) {
					rpcService.unselectCourse($, new AsyncCallback<Void>() {
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

							eventBus.fireEvent(new UnselectCourseEvent($));
						}
					});
				}
	        }
	    }, DoubleClickEvent.getType());
		
		display.getSelectedCoursesList().addCellPreviewHandler(new CellPreviewEvent.Handler<CourseId>() {

			@Override
			public void onCellPreview(CellPreviewEvent<CourseId> event) {
				boolean isMouseOver = BrowserEvents.MOUSEOVER.equals(event.getNativeEvent().getType());
				boolean isMouseOut = BrowserEvents.MOUSEOUT.equals(event.getNativeEvent().getType());
				
				if (isMouseOver) {
					hoveredRow = display.getHoveredNotSelectedCourseRow(event);
					if (hoveredRow < 0) {
						return;
					}

					Optional<CourseId> newCourseId = Optional.of(notSelectedCourses.get(hoveredRow));
					if(!hoveredCourse.equals(newCourseId)) {
						hoveredCourse = newCourseId;
						rpcService.getCourseDetails(hoveredCourse.get(), new GetCourseDetailsCallback());
					}
				}

				if (isMouseOut) {
					hoveredCourse = Optional.absent();
					hoveredRow = -1;
					display.setHoveredRow(-1);
					display.setHoveredCourseDetail("");
				}
			}
		});

		// define not selected courses list functionality
		display.getNotSelectedCoursesList().addDomHandler(new DoubleClickHandler() {

	        @Override
	        public void onDoubleClick(@SuppressWarnings("unused") final DoubleClickEvent event) {
				final CourseId $ = display.getSelectedCourse();
				if ($ != null) {
					rpcService.selectCourse($, new AsyncCallback<Void>() {
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

							eventBus.fireEvent(new SelectCourseEvent($));
						}
					});
				}
	        }
	    }, DoubleClickEvent.getType());
		
		
		display.getNotSelectedCoursesList().addCellPreviewHandler(new CellPreviewEvent.Handler<CourseId>() {

			@Override
			public void onCellPreview(CellPreviewEvent<CourseId> event) {
				boolean isMouseOver = BrowserEvents.MOUSEOVER.equals(event.getNativeEvent().getType());
				boolean isMouseOut = BrowserEvents.MOUSEOUT.equals(event.getNativeEvent().getType());

				if (isMouseOver) {
					hoveredRow = display.getHoveredNotSelectedCourseRow(event);
					if (hoveredRow < 0) {
						return;
					}

					Optional<CourseId> newCourseId = Optional.of(notSelectedCourses.get(hoveredRow));
					if(!hoveredCourse.equals(newCourseId)) {
						hoveredCourse = newCourseId;
						rpcService.getCourseDetails(hoveredCourse.get(), new GetCourseDetailsCallback());
					}
				}

				if (isMouseOut) {
					hoveredCourse = Optional.absent();
					hoveredRow = -1;
					display.setHoveredRow(-1);
					display.setHoveredCourseDetail("");
				}

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
	public void go(LayoutPanel panel) {
		bind();

		//panel.clear();
		panel.add(display.getAsWidget());
		panel.setWidgetRightWidth(display.getAsWidget(), 1, Unit.EM, 20, Unit.PCT);
		panel.setWidgetTopBottom(display.getAsWidget(), 4.5, Unit.EM, 1, Unit.EM);
		
		rpcService.getFaculties(new FetchFacultiesAsyncCallback());
		rpcService.getSelectedCourses(new FetchSelectedCoursesAsyncCallback());
		rpcService.getNotSelectedCourses(courseQuery, selectedFaculty, new FetchNotSelectedCoursesAsyncCallback());
	}

	@Deprecated
	void stopHoveredTimer() {
		courseDetailsTimer.cancel();
		hoveredCourse = Optional.absent();
	}

	class FetchSelectedCoursesAsyncCallback implements AsyncCallback<ArrayList<CourseId>> {
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
	}

	class FetchNotSelectedCoursesAsyncCallback implements AsyncCallback<ArrayList<CourseId>> {
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
	}

	class FetchFacultiesAsyncCallback implements AsyncCallback<ArrayList<String>> {
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
	}
	
	class GetCourseDetailsCallback implements AsyncCallback<Course> {
		@Override
		public void onFailure(@SuppressWarnings("unused") Throwable caught) {
			Window.alert("Error while getting information about course.");
			Log.error("Error while getting information about selecting course.");
		}

		@Override
		public void onSuccess(Course result) {
			display.setHoveredRow(hoveredRow);
			display.setHoveredCourseDetail(result.toString());
		}
	}
	
	class GetSomeStringAsyncCallback implements AsyncCallback<String> {
		@Override
		public void onSuccess(String result) {
			Window.alert(result);
			Log.error(result);
		}

		@Override
		public void onFailure(@SuppressWarnings("unused") Throwable caught) {
			Window.alert("Cthulhu has awoken");
			Log.error("Cthulhu has awoken");
			
		}
	}

	@Deprecated
	class CourseDetailsTimer extends Timer {
		@Override
		public void run() {
			eventBus.fireEvent(new GetCourseDetailsEvent(hoveredCourse.get()));
		}
	}
	
}
