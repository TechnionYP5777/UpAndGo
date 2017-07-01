package upandgo.client.presenter;

import static com.arcbees.gquery.tooltip.client.Tooltip.Tooltip;
import static com.google.gwt.query.client.GQuery.$;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.arcbees.gquery.tooltip.client.Tooltip;
import com.arcbees.gquery.tooltip.client.TooltipOptions;
import com.arcbees.gquery.tooltip.client.TooltipOptions.TooltipPlacement;
import com.arcbees.gquery.tooltip.client.event.BeforeShowTooltipEvent;
import com.arcbees.gquery.tooltip.client.event.BeforeShowTooltipEventHandler;
import com.arcbees.gquery.tooltip.client.event.ShowTooltipEvent;
import com.arcbees.gquery.tooltip.client.event.ShowTooltipEventHandler;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.inject.Inject;

import upandgo.client.CoursesServiceAsync;
import upandgo.client.event.AuthenticationEvent;
import upandgo.client.event.AuthenticationEventHandler;
import upandgo.client.event.ChangeSemesterEvent;
import upandgo.client.event.ChangeSemesterEventHandler;
import upandgo.client.event.SelectCourseEvent;
import upandgo.client.event.UnselectCourseEvent;
import upandgo.client.event.ClearAllCoursesEvent;
import upandgo.client.event.CollidingCourseDeselectedEvent;
import upandgo.client.event.CollidingCourseDeselectedEventHandler;
import upandgo.shared.entities.Semester;
import upandgo.shared.entities.StuffMember;
import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;
import upandgo.shared.utils.FuzzySearch;

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

		void setHoveredCourseDetail(Course detail);
		
		void updateLists();
		
		HasClickHandlers getClearCoursesButton();

		CourseId getSelectedCourse(int row);

		CourseId getUnselectedCourse(int row);

		int getHoveredSelectedCourseRow(CellPreviewEvent<CourseId> event); // pass
																			// -1
																			// if
																			// none

		int getHoveredNotSelectedCourseRow(CellPreviewEvent<CourseId> event); // pass
																				// -1
																				// if
																				// none

		int getSelectedFacultyRow(ChangeEvent event); // pass -1 if there is no
														// selectedFaculty
														// chosen

		String getCourseQuery(KeyUpEvent event);

		Widget getAsWidget();
		
		void setSelectedLoadingAnimation(boolean animate);
		
		void setNotSelectedLoadingAnimation(boolean animate);
	}

	@Inject
	public CourseListPresenter(CoursesServiceAsync rpc, EventBus eventBus, Display display, Semester defaultSemester) {
		this.rpcService = rpc;
		this.display = display;
		this.eventBus = eventBus;
		this.currentSemester = defaultSemester;
		
		
		this.eventBus.addHandler(AuthenticationEvent.TYPE, new AuthenticationEventHandler() {
			
			@Override
			public void onAuthenticationChanged(AuthenticationEvent event) {
				isSignedIn = event.isSignedIn();
				if (isSignedIn)
					rpcService.getSelectedCourses(currentSemester, new FetchSelectedCoursesAsyncCallback());
			}
		});
		
		this.eventBus.addHandler(CollidingCourseDeselectedEvent.TYPE, new CollidingCourseDeselectedEventHandler() {
			
			@Override
			public void onCollidedCourseDeselected(CourseId c) {
				deselectCourse(c);
				
			}
		});
		
		this.eventBus.addHandler(ChangeSemesterEvent.TYPE, new ChangeSemesterEventHandler(){

			@Override
			public void onSemesterChange(ChangeSemesterEvent event) {
				Log.info("CourseListPresenter: Got semester change event: " + event.getSemester().getId());
				currentSemester = event.getSemester();
				getFacultiesAndCourses();
			}
			
		});
	}

	CoursesServiceAsync rpcService;
	Display display;
	EventBus eventBus;
	Semester currentSemester;

	List<CourseId> selectedCourses;
	List<CourseId> notSelectedCourses;
	List<CourseId> allCourses;
	List<String> faculties;
	String courseQuery = "";

	String selectedFaculty = "";
	CourseId hoveredCourseId = null;
	Course hoveredCourse = null;
	int hoveredRow = -1;
	
	
	
	int selectedClickedRow = -1;
	int unselectedClickedRow = -1;

	boolean isSignedIn = false;
	
	
	@Override
	public void bind() {

		// define faculty list functionality
		display.getFacultyDropList().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				int $ = display.getSelectedFacultyRow(event);
				if ($ < 0)
					return;
				if ($ == 0) // if it's "all faculties" option
					selectedFaculty = "";
				else
					selectedFaculty = faculties.get($);
				display.setNotSelectedLoadingAnimation(true);
				rpcService.getNotSelectedCourses(currentSemester, courseQuery, selectedFaculty,
						new FetchNotSelectedCoursesAsyncCallback());

			}
		});

		// define selected courses list functionality
		display.getSelectedCoursesList().addDomHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(@SuppressWarnings("unused") final DoubleClickEvent event) {
				deselectCourse(null);
			}
		}, DoubleClickEvent.getType());

		display.getSelectedCoursesList().addCellPreviewHandler(new CellPreviewEvent.Handler<CourseId>() {

			@Override
			public void onCellPreview(CellPreviewEvent<CourseId> event) {

				boolean isMouseOver = BrowserEvents.MOUSEOVER.equals(event.getNativeEvent().getType());
				boolean isMouseOut = BrowserEvents.MOUSEOUT.equals(event.getNativeEvent().getType());
				boolean isClick = BrowserEvents.CLICK.equals(event.getNativeEvent().getType());

				if (isMouseOver) {
					hoveredRow = event.getIndex();
					if (hoveredRow < 0) {
						return;
					}
					
					CourseId newCourseId = selectedCourses.get(hoveredRow);
					if(!newCourseId.equals(hoveredCourseId)) {
						hoveredCourseId = newCourseId;
						rpcService.getCourseDetails(currentSemester, hoveredCourseId, new GetCourseDetailsCallback());
					}
					//display.getSelectedCoursesList().getRowElement(event.getIndex()).getCells().getItem(event.getColumn()).setTitle(newCourseId.getTitle());
				}

				if (isMouseOut) {
						hoveredCourseId = null;
						hoveredRow = -1;
						display.setHoveredRow(-1);
						display.setHoveredCourseDetail(null);
				}
				if (isClick) {

					selectedClickedRow = event.getIndex();
					EventTarget eventTarget = event.getNativeEvent().getEventTarget();
					if (eventTarget.toString().equals("[object HTMLButtonElement]")
							|| eventTarget.toString().equals("[object HTMLElement]")) {
						deselectCourse(null);
					}
				}
			}
		});

		// define not selected courses list functionality
		display.getNotSelectedCoursesList().addDomHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(@SuppressWarnings("unused") final DoubleClickEvent event) {
				selectCourse();
			}
		}, DoubleClickEvent.getType());

		display.getNotSelectedCoursesList().addCellPreviewHandler(new CellPreviewEvent.Handler<CourseId>() {

			@Override
			public void onCellPreview(CellPreviewEvent<CourseId> event) {
				boolean isMouseOver = BrowserEvents.MOUSEOVER.equals(event.getNativeEvent().getType());
				boolean isMouseOut = BrowserEvents.MOUSEOUT.equals(event.getNativeEvent().getType());
				boolean isClick = BrowserEvents.CLICK.equals(event.getNativeEvent().getType());

				if (isMouseOver) {
					
					hoveredRow = display.getHoveredNotSelectedCourseRow(event);
					if (hoveredRow < 0) {
						return;
					}
					CourseId newCourseId = notSelectedCourses.get(hoveredRow);
					if (!newCourseId.equals(hoveredCourseId)) {
						hoveredCourseId = newCourseId;
						rpcService.getCourseDetails(currentSemester, hoveredCourseId, new
								GetCourseDetailsCallback());
					}
//					display.getNotSelectedCoursesList().getRowElement(event.getIndex()).getCells()
//							.getItem(event.getColumn()).setTitle(newCourseId.getTitle());
				}

				if (isMouseOut) {
					hoveredCourseId = null;
					hoveredRow = -1;
					display.setHoveredRow(-1);
					display.setHoveredCourseDetail(null);
				}
				if (isClick) {
					unselectedClickedRow = event.getIndex();
					EventTarget eventTarget = event.getNativeEvent().getEventTarget();
					if (eventTarget.toString().equals("[object HTMLButtonElement]")
							|| eventTarget.toString().equals("[object HTMLElement]")) {
						selectCourse();
					}
				}

			}
		});

		display.getCourseSearch().addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				courseQuery = display.getCourseQuery(event);
				if(courseQuery.isEmpty()){
					notSelectedCourses.clear();
					notSelectedCourses.addAll(allCourses);
					display.updateLists();
					return;
				}
				
				notSelectedCourses.clear();
				for(CourseId c : allCourses){
					if(FuzzySearch.similarity(courseQuery, c.getTitle()) > 50)
						notSelectedCourses.add(c);
				}
				
				Collections.sort(notSelectedCourses, new Comparator<CourseId>() {
					@Override
					public int compare(CourseId o1, CourseId o2) {
						return (FuzzySearch.similarity(courseQuery, o2.getTitle()) - FuzzySearch.similarity(courseQuery, o1.getTitle()));
					}
					
				});
				display.updateLists();
			}
			
		});
		
		display.getClearCoursesButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				selectedCourses.clear();
				notSelectedCourses.clear();
				notSelectedCourses.addAll(allCourses);
				Collections.sort(notSelectedCourses);
				display.updateLists();
				if(isSignedIn){
					rpcService.unselectAllCourses(currentSemester, new AsyncCallback<Void>() {
						@Override
						public void onFailure(@SuppressWarnings("unused") Throwable caught) {
							//Window.alert("Error while unselecting all course.");
							Log.error("CourseListPresenter: Error while unselecting all course.");
						}
	
						@Override
						public void onSuccess(@SuppressWarnings("unused") Void result) {
							Log.info("CourseListPresenter: got onSuccuess from server");
							eventBus.fireEvent(new ClearAllCoursesEvent());
						}
					});
				} else {
					eventBus.fireEvent(new ClearAllCoursesEvent());
				}
			}
		});
		
 
    	$(display.getNotSelectedCoursesList()).as(Tooltip).tooltip(getOptions(false));
    	$(display.getSelectedCoursesList()).as(Tooltip).tooltip(getOptions(true));

		

	}

	@Override
	public void unbind() {
		// TODO
	}

	@Override
	public void go(LayoutPanel panel) {
		bind();

		// panel.clear();
		panel.add(display.getAsWidget());
		panel.setWidgetRightWidth(display.getAsWidget(), 1, Unit.EM, 20, Unit.PCT);
		panel.setWidgetTopBottom(display.getAsWidget(), 4.5, Unit.EM, 1, Unit.EM);

		// rpcService.getSomeString(new GetSomeStringAsyncCallback());
		getFacultiesAndCourses();
		
	}
	
	void getFacultiesAndCourses(){
		Log.info("CourseListPresenter: setLoadingAnimation true");
		display.setNotSelectedLoadingAnimation(true);
		rpcService.getFaculties(currentSemester, new FetchFacultiesAsyncCallback());
		if (isSignedIn){
			display.setSelectedLoadingAnimation(true);
			rpcService.getSelectedCourses(currentSemester, new FetchSelectedCoursesAsyncCallback());
		} else {
			selectedCourses = new ArrayList<>();
			display.setSelectedCourses(selectedCourses);
			rpcService.getNotSelectedCourses(currentSemester, courseQuery, selectedFaculty, new FetchNotSelectedCoursesAsyncCallback());
		}

	}

	class FetchSelectedCoursesAsyncCallback implements AsyncCallback<ArrayList<CourseId>> {
		@Override
		public void onSuccess(ArrayList<CourseId> result) {
			selectedCourses = result;
			display.setSelectedCourses(selectedCourses);
			display.setSelectedLoadingAnimation(false);
			rpcService.getNotSelectedCourses(currentSemester, courseQuery, selectedFaculty, new FetchNotSelectedCoursesAsyncCallback());
		}

		@Override
		public void onFailure(@SuppressWarnings("unused") Throwable caught) {
			//Window.alert("Error fetching selected courses.");
			Log.error("CourseListPresenter: Error fetching selected courses.");
		}
	}

	class FetchNotSelectedCoursesAsyncCallback implements AsyncCallback<ArrayList<CourseId>> {
		@Override
		public void onSuccess(ArrayList<CourseId> result) {
			result.remove(new CourseId());
			notSelectedCourses =result;
			allCourses = new ArrayList<>(result);
			allCourses.addAll(selectedCourses);
			Log.info("CourseListPresenter: allCourses size " + allCourses.size() + " notSelectedCourses size " + notSelectedCourses.size());
			display.setNotSelectedCourses(notSelectedCourses);
			Log.info("CourseListPresenter: setLoadingAnimation false");
			display.setNotSelectedLoadingAnimation(false);

		}

		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
			Window.alert("FetchNotSelectedCoursesAsyncCallback got: " + caught.getLocalizedMessage() + "*"
					+ caught.getMessage() + "&&"
					+ (caught.getCause() != null ? caught.getCause().getLocalizedMessage() : "") + "*"
					+ (caught.getCause() != null ? caught.getCause().getMessage() : "*") + "end");
			Window.alert("CourseListPresenter: Error fetching not selected courses.");
			Log.error("CourseListPresenter: Error fetching not selected courses.");
		}
	}

	class FetchFacultiesAsyncCallback implements AsyncCallback<ArrayList<String>> {
		@Override
		public void onSuccess(ArrayList<String> result) {
			faculties = new ArrayList<>(result);
			display.setFaculties(faculties);
		}

		@Override
		public void onFailure(@SuppressWarnings("unused") Throwable caught) {
			//Window.alert("Error fetching faculties.");
			Log.error("CourseListPresenter: Error fetching faculties.");

		}
	}

	class GetCourseDetailsCallback implements AsyncCallback<Course> {
		@Override
		public void onFailure(@SuppressWarnings("unused") Throwable caught) {
			//Window.alert("CourseListPresenter: Error while getting information about course.");
			Log.error("CourseListPresenter: Error while getting information about selecting course.");
		}

		@Override
		public void onSuccess(Course result) {
			//display.setHoveredRow(hoveredRow);
			//display.setHoveredCourseDetail(result);
			hoveredCourse = result;
		}
	}

	class GetSomeStringAsyncCallback implements AsyncCallback<String> {
		@Override
		public void onSuccess(String result) {
			// Window.alert(result);
			Log.info(result);
		}

		@Override
		public void onFailure(@SuppressWarnings("unused") Throwable caught) {
			Window.alert("Cthulhu has awoken!!!!!!!!!");
			Log.error("Cthulhu has awoken!!!!!!!!");
			Log.error("**+++++++++++" + caught.getLocalizedMessage() + "**+++++++++++" + caught.getMessage());

		}
	}
	void deselectCourse(CourseId deselectedCourse) {

		final CourseId $;
		$ = deselectedCourse != null ? deselectedCourse : display.getSelectedCourse(selectedClickedRow);
		if ($ != null) {
			selectedCourses.remove($);
			notSelectedCourses.clear();
			for(CourseId c : allCourses){
				if(!selectedCourses.contains(c)){
					notSelectedCourses.add(c);
				}
			}
			display.updateLists();
			if (isSignedIn) {
				rpcService.unselectCourse(currentSemester, $, new AsyncCallback<Void>() {
					@Override
					public void onFailure(@SuppressWarnings("unused") Throwable caught) {
						//Window.alert("CourseListPresenter: Error while unselecting course.");
						Log.error("CourseListPresenter: Error while unselecting course.");
					}

					@Override
					public void onSuccess(@SuppressWarnings("unused") Void result) {
						Log.info("CourseListPresenter: Success while unselecting course.");
						eventBus.fireEvent(new UnselectCourseEvent($));
						
					}
				});
			} else {
				eventBus.fireEvent(new UnselectCourseEvent($));
			}
		}
	}

	void selectCourse() {
		final CourseId $ = display.getUnselectedCourse(unselectedClickedRow);
		if ($ != null) {
			selectedCourses.add($);
			notSelectedCourses.remove($);
			display.updateLists();
			if (isSignedIn) {
				rpcService.selectCourse(currentSemester, $, new AsyncCallback<Void>() {
					@Override
					public void onFailure(@SuppressWarnings("unused") Throwable caught) {
						Window.alert("CourseListPresenter: Error while selecting course.");
						Log.error("CourseListPresenter: Error while selecting course.");
					}

					@Override
					public void onSuccess(@SuppressWarnings("unused") Void result) {
						Log.info("CourseListPresenter: got onSuccuess from server");
						eventBus.fireEvent(new SelectCourseEvent($));
					}
				});
			} else {
				eventBus.fireEvent(new SelectCourseEvent($));
			}
		}
	}
	
	boolean isCourseNumSelected(String courseNum){
		for(CourseId c : selectedCourses){
			if(c.number().equals(courseNum))
				return true;
		}
		return false;
	}
	
	//Course Tooltip functionality
	private TooltipOptions getOptions(boolean selectedCourses){
		
    	TooltipOptions options = new TooltipOptions().withDelay(100).withAutoClose(true).withPlacement(TooltipPlacement.LEFT).withContent(new TooltipOptions.TooltipWidgetContentProvider() {
			
			@Override
			public IsWidget getContent(Element element) {
				@SuppressWarnings("boxing")
				int absoluteRowIndex = Integer.valueOf($(element).attr("__gwt_row"));

				String html = "<div>";
				if(hoveredRow == absoluteRowIndex && hoveredCourse!=null){
					html+= "<b>" + hoveredCourse.getName() + "</b><br/>";
					html+="<div align=right>";
					html+="<u>" + "מספר הקורס:" + "</u>" + " " + hoveredCourse.getId() + "<br/>";
					html+="<u>" + "נקודות:" +"</u>" + " " + hoveredCourse.getPoints() + "<br/>";
					html+="<u>" + "סגל הקורס:" + "</u>" + " " ;
					if(!hoveredCourse.getStuff().isEmpty()){
						for(StuffMember sm : hoveredCourse.getStuff()){
							html+= sm.getTitle()+ " " + sm.getFirstName()+ " "  + sm.getLastName() + ", ";
						}
						html = html.substring(0, html.length()-3);
						html+=".";
					}
					html+="<br/>";
					html+="<u>" + "הערות:" + "</u><ul>";
					for(String s : hoveredCourse.getNotes()){
						html+="<li>" + s + "</li>";
					}
					html+="</ul>";
				
				}
				html += "</div></div>";
				
				return new HTML(html);
			//	? "Loading..." : hoveredCourseDetail;
			}
		});
    	options.withSelector("tbody tr");
    	if(!selectedCourses){
	    	options.addShowTooltipEventHandler(new ShowTooltipEventHandler() {
				@Override
				public void onShow(final ShowTooltipEvent event) {
					
			
					Element e = event.getTooltip().elements()[0];
					
				RegExp pattern = RegExp.compile("[0-9][0-9][0-9][0-9][0-9][0-9]<br");
					MatchResult m = pattern.exec(e.getInnerHTML());
					final String courseId = m.getGroup(0).substring(0, 6);
					Log.error("$$$course num = " + courseId);
						Timer maybeRemoveTooltip = new Timer() {
							GQuery tt = event.getTooltip();
							@Override
							public void run() {
								if(isCourseNumSelected(courseId)){
									
									tt.remove();
								}
								
							}
						};
					maybeRemoveTooltip.schedule(70);
					
				}
			});
    	}
    	else{
    		options.addShowTooltipEventHandler(new ShowTooltipEventHandler() {
				@Override
				public void onShow(final ShowTooltipEvent event) {
					
			
					Element e = event.getTooltip().elements()[0];
					
				RegExp pattern = RegExp.compile("[0-9][0-9][0-9][0-9][0-9][0-9]<br");
					MatchResult m = pattern.exec(e.getInnerHTML());
					final String courseId = m.getGroup(0).substring(5, 11);
						Timer maybeRemoveTooltip = new Timer() {
							GQuery tt = event.getTooltip();
							@Override
							public void run() {
								if(!isCourseNumSelected(courseId)){
									
									tt.remove();
								}
								
							}
						};
					maybeRemoveTooltip.schedule(70);
					
				}
			});
    	}
    	return options;
	}
	

}
