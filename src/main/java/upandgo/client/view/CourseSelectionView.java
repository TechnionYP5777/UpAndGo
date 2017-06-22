package upandgo.client.view;


/**
 * 
 * @author danabra
 * @since 7-04-17
 * 
 * GUI class for list of courses and course selection 
 * 
 */

import java.util.List;

import com.arcbees.gquery.tooltip.client.TooltipOptions.TooltipContentProvider;
import com.google.gwt.dom.client.Element;
import com.allen_sauer.gwt.log.client.Log;
import com.arcbees.gquery.tooltip.client.Tooltip;
import com.arcbees.gquery.tooltip.client.TooltipImpl;
import com.arcbees.gquery.tooltip.client.TooltipOptions;
import com.arcbees.gquery.tooltip.client.TooltipOptions.TooltipPlacement;
import com.arcbees.gquery.tooltip.client.TooltipOptions.TooltipTrigger;
import com.arcbees.gquery.tooltip.client.event.BeforeShowTooltipEvent;
import com.arcbees.gquery.tooltip.client.event.BeforeShowTooltipEventHandler;
import com.arcbees.gquery.tooltip.client.event.HideTooltipEvent;
import com.arcbees.gquery.tooltip.client.event.HideTooltipEventHandler;
import com.arcbees.gquery.tooltip.client.event.ShowTooltipEvent;
import com.arcbees.gquery.tooltip.client.event.ShowTooltipEventHandler;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;

import upandgo.client.Resources;
import upandgo.client.presenter.CourseListPresenter;
import upandgo.shared.entities.StuffMember;
import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;

import static com.google.gwt.query.client.GQuery.$;
import static com.arcbees.gquery.tooltip.client.Tooltip.Tooltip;


public class CourseSelectionView extends LayoutPanel implements CourseListPresenter.Display  {
    private CellTable<CourseId> ccl = new CellTable<>(); //chosen courses
    private ListDataProvider<CourseId> selectedModel;
    private Label cc = new Label("קורסים שנבחרו:");
    private CellTable<CourseId> scl = new CellTable<>(); //all courses list
    private ListDataProvider<CourseId> deselectedModel;
    private Label sc = new Label("בחר קורסים:");
    private ListBox faculties = new ListBox(); //faculties
    private TextBox searchCourse = new TextBox();
    private ScrollPanel cclp = new ScrollPanel();
    private ScrollPanel sclp = new ScrollPanel();
    private Button clearCourses = new Button("<i class=\"fa fa-trash\" aria-hidden=\"true\"></i>&nbsp;&nbsp;מחק הכל");
    Course hoveredCourse = null;
    int rowNum = -1; //helps verify that hoveredCourseDetail is relevant
    public CourseSelectionView(){
    	InitializePanel();
    	Resources.INSTANCE.courseListStyle().ensureInjected();


    }
    private void InitializePanel(){
    	// chosen course list initialization
    	selectedModel = new ListDataProvider<>();
    	selectedModel.addDataDisplay(ccl);
    	ccl.setSelectionModel(new SingleSelectionModel<CourseId>());
    	ccl.addStyleName(Resources.INSTANCE.courseListStyle().ChosenCourses());
    	ccl.setWidth("100%"); 	
    	Column<CourseId, String> selectedCourseColumn = new Column<CourseId, String>(new SelectedCourseCell()) {
			
    		@Override
			public String getValue(CourseId object) {
				return object.getTitle();
			}
		};
		
		ccl.addColumn(selectedCourseColumn);
	    DefaultSelectionEventManager<CourseId> selectionEventManager1 = DefaultSelectionEventManager.createCheckboxManager(0); // Limit selection to checkboxes in column 0.
	    ccl.setSelectionModel(ccl.getSelectionModel(), selectionEventManager1);
	    ccl.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
	    ccl.setRowStyles(new RowStyles<CourseId>() {
			@Override
			public String getStyleNames(CourseId row, int rowIndex) {
				return "courseSelectionRow";
			}
		});
	    cclp.add(ccl);
	    cclp.setHeight("15em");
	    cclp.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
	    cclp.getElement().getStyle().setBorderWidth(1, Unit.PX);
	    cclp.getElement().getStyle().setBorderColor("LightGray");
	    
	    Column<CourseId, String> notSelectedCOurseColumn = new Column<CourseId, String>(new NotSelectedCourseCell()) {
			
    		@Override
			public String getValue(CourseId object) {
				return object.getTitle();
			}
		};
    	//all courses list initialization
		deselectedModel = new ListDataProvider<>();
		deselectedModel.addDataDisplay(scl);
	    DefaultSelectionEventManager<CourseId> selectionEventManager2 = DefaultSelectionEventManager.createCheckboxManager(0); // Limit selection to checkboxes in column 0.
	    scl.setSelectionModel(ccl.getSelectionModel(), selectionEventManager2);
	    scl.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
    	scl.setWidth("100%");
    	scl.addColumn(notSelectedCOurseColumn);
    	scl.setRowStyles(new RowStyles<CourseId>() {
			@Override
			public String getStyleNames(CourseId row, int rowIndex) {
				return "courseSelectionRow";
			}
		});
	    sclp.add(scl);
	    sclp.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
	    sclp.getElement().getStyle().setBorderWidth(1, Unit.PX);
	    sclp.getElement().getStyle().setBorderColor("LightGray");
    	
    	//initializing faculty selection
    	faculties.setWidth("100%");
    	faculties.setHeight("2em");
 
    	//search course text field initialization
    	searchCourse.setHeight("2em");
    	searchCourse.setWidth("100%");
    	searchCourse.setTitle("חפש קורסים");
    	searchCourse.getElement().setPropertyString("placeholder", "חפש קורסים...");

    	cc.getElement().getStyle().setFontStyle(FontStyle.OBLIQUE);
    	cc.getElement().getStyle().setFontSize(1.2, Unit.EM);
    	cc.getElement().getStyle().setColor("Red");
    	sc.getElement().getStyle().setFontStyle(FontStyle.OBLIQUE);
    	sc.getElement().getStyle().setFontSize(1.2, Unit.EM);
    	sc.getElement().getStyle().setColor("Red");
    	
		//Course Tooltip functionality
    	TooltipOptions options = new TooltipOptions().withDelay(500).withAutoClose(true).withPlacement(TooltipPlacement.LEFT).withContent(new TooltipOptions.TooltipWidgetContentProvider() {
			
			@Override
			public IsWidget getContent(Element element) {
				@SuppressWarnings("boxing")
				int absoluteRowIndex = Integer.valueOf($(element).attr("__gwt_row"));

				String html = "<div>";
				if(rowNum == absoluteRowIndex && hoveredCourse!=null){
					html+= "<b>" + hoveredCourse.getName() + "</b><br/><br/>";
					html+="<div align=right>";
					html+="<u>" + "מספר הקורס:" + "</u>" + " " +hoveredCourse.getId() + "<br/>";
					html+="<u>" + "נקודות:" +"</u>" + " " + hoveredCourse.getPoints() + "<br/>";
					html+="<u>" + "סגל הקורס:" + "</u>" + " " ;
					if(!hoveredCourse.getStuff().isEmpty()){
						for(StuffMember sm : hoveredCourse.getStuff()){
							html+= sm.getTitle()+ " " + sm.getFirstName()+ " " + sm.getLastName() + ", ";
						}
						html = html.substring(0, html.length()-3);
						html+=".";
					}
						
					html+="<br/><br/>";
					html+="<a href=\"https://ug3.technion.ac.il/rishum/course?MK=" + hoveredCourse.getId() + "&CATINFO=&SEM=201602\">קישור לאתר הקורס</a>";
				}
				html += "</div></div>";
				
				return new HTML(html);
			//	? "Loading..." : hoveredCourseDetail;
			}
		});
    	options.withSelector("tbody tr");
    	  	
    	
    	$(ccl).as(Tooltip).tooltip(options);
    	$(scl).as(Tooltip).tooltip(options);
    	
    	
    	
    	
    	//initializing clear course button
    	clearCourses.setStyleName("btn btn-primary clear-button");
   
    	
    	this.getElement().getStyle().setMargin(10, Unit.PX);
    	this.add(cc);
	    this.add(cclp);
	    this.add(sc);
	    this.add(clearCourses);
	    this.add(faculties);
	    this.add(searchCourse);
	    this.add(sclp);
	 
	    this.setWidgetTopBottom(cc, 0, Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(cclp, 2,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(clearCourses, 17.3,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(sc, 18,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(faculties, 20,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(searchCourse, 23,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(sclp, 26,  Unit.EM, 0, Unit.EM);
    }
    
    // Implementation of Display
	@Override
	public CellTable<CourseId> getSelectedCoursesList() {
		return ccl;
	}
	@Override
	public CellTable<CourseId> getNotSelectedCoursesList() {
		return scl;
	}
	@Override
	public HasChangeHandlers getFacultyDropList() {
		return faculties;
	}
	@Override
	public HasKeyUpHandlers getCourseSearch() {
		return searchCourse;
	}
	@Override
	public void setSelectedCourses(List<CourseId> courses) {
		selectedModel.setList(courses);
		ccl.setRowCount(selectedModel.getList().size(), true);
		ccl.setVisibleRange(0, courses.size());
		
	}
	@Override
	public void setNotSelectedCourses(List<CourseId> is) {
		deselectedModel.setList(is);
		scl.setRowCount(is.size(), true);
		scl.setVisibleRange(0, is.size());
		
	}
	@Override
	public void setFaculties(List<String> faculties) {
		for(String s : faculties)
			this.faculties.addItem(s);
		
	}
	@Override
	public CourseId getSelectedCourse(int row) {
		return ccl.getVisibleItem(row);
	}
	@Override
	public CourseId getUnselectedCourse(int row) {
		return scl.getVisibleItem(row);
	}
	@Override
	public int getHoveredSelectedCourseRow(CellPreviewEvent<CourseId> i) {
		return i.getIndex();
	}
	@Override
	public int getHoveredNotSelectedCourseRow(CellPreviewEvent<CourseId> i) {
		
		return i.getIndex();
	}
	@Override
	public int getSelectedFacultyRow(@SuppressWarnings("unused") ChangeEvent e) {
		return this.faculties.getSelectedIndex();
	}
	@Override
	public String getCourseQuery(@SuppressWarnings("unused") KeyUpEvent e) {
		return this.searchCourse.getValue();
	}
	@Override
	public void setHoveredRow(int row) {
		rowNum = row;
		
	}
	@Override
	public void setHoveredCourseDetail(Course detail) {
		hoveredCourse = detail;
		
	}
	@Override
	public Widget getAsWidget() {
		return this.asWidget();
	}
	@Override
	public HasClickHandlers getClearCoursesButton() {
		return clearCourses;
	}
	@Override
	public void updateLists() {

		deselectedModel.refresh();
		selectedModel.refresh();
		scl.setVisibleRange(0, deselectedModel.getList().size());
		scl.setRowCount(deselectedModel.getList().size(), true);
		
		ccl.setVisibleRange(0, selectedModel.getList().size());
		ccl.setRowCount(selectedModel.getList().size(), true);
		
		
	}
    
}