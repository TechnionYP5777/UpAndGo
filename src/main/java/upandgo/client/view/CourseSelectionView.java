package upandgo.client.view;


/**
 * 
 * @author danabra
 * @since 7-04-17
 * 
 * GUI class for list of courses and course selection 
 * 
 */
import static com.arcbees.gquery.tooltip.client.Tooltip.Tooltip;
import static com.google.gwt.query.client.GQuery.$;

import java.util.List;

import com.arcbees.gquery.tooltip.client.TooltipOptions;
import com.arcbees.gquery.tooltip.client.TooltipOptions.TooltipContentProvider;
import com.arcbees.gquery.tooltip.client.TooltipOptions.TooltipPlacement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.SingleSelectionModel;

import upandgo.client.Resources;
import upandgo.client.presenter.CourseListPresenter;
import upandgo.shared.entities.course.CourseId;


public class CourseSelectionView extends LayoutPanel implements CourseListPresenter.Display  {
    private CellTable<CourseId> ccl = new CellTable<>(); //chosen courses
    private Label cc = new Label("קורסים שנבחרו:");
    private CellTable<CourseId> scl = new CellTable<>(); //all courses list
    private Label sc = new Label("בחר קורסים:");
    private ListBox faculties = new ListBox(); //faculties
    private TextBox searchCourse = new TextBox();
    private ScrollPanel cclp = new ScrollPanel();
    private ScrollPanel sclp = new ScrollPanel();
    String hoveredCourseDetail = "Loading...";
    int rowNum = -1; //help verify that hoveredCourseDetail is relevant
    public CourseSelectionView(){
    	InitializePanel();
    	Resources.INSTANCE.courseListStyle().ensureInjected();

    }
    private void InitializePanel(){
    	// chosen course list initialization
    	ccl.setSelectionModel(new SingleSelectionModel<CourseId>());
    	ccl.addStyleName(Resources.INSTANCE.courseListStyle().ChosenCourses());
    	ccl.setWidth("100%");
    	TextColumn<CourseId> course = new TextColumn<CourseId>() {
			@Override
			public String getValue(CourseId object) {
				return object.getTitle();
			}
		}, course1 = new TextColumn<CourseId>() {
			@Override
			public String getValue(CourseId object) {
				return object.getTitle();
			}
		};
		ccl.addColumn(course);
	    DefaultSelectionEventManager<CourseId> selectionEventManager1 = DefaultSelectionEventManager.createCheckboxManager(0); // Limit selection to checkboxes in column 0.
	    ccl.setSelectionModel(ccl.getSelectionModel(), selectionEventManager1);
	    cclp.add(ccl);
	    cclp.setHeight("25em");
	    cclp.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
	    cclp.getElement().getStyle().setBorderWidth(1, Unit.PX);
	    cclp.getElement().getStyle().setBorderColor("LightGray");

    	//all courses list initialization
	    DefaultSelectionEventManager<CourseId> selectionEventManager2 = DefaultSelectionEventManager.createCheckboxManager(0); // Limit selection to checkboxes in column 0.
	    scl.setSelectionModel(ccl.getSelectionModel(), selectionEventManager2);
    	scl.setWidth("100%");
    	scl.addColumn(course1);
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
    	TooltipOptions options = new TooltipOptions().withDelayShow(300).withDelayHide(100).withAutoClose(true).withPlacement(TooltipPlacement.LEFT).withContent(new TooltipContentProvider() {
			
			@Override
			public String getContent(Element element) {
				@SuppressWarnings("boxing")
				int absoluteRowIndex = Integer.valueOf($(element).attr("__gwt_row"));
				return rowNum != absoluteRowIndex ? "Loading..." : hoveredCourseDetail;
			}
		});
    	//options.withContainer("element");
    	options.withSelector("tbody tr");
    	
    	
    	$(ccl).as(Tooltip).tooltip(options);
    	$(scl).as(Tooltip).tooltip(options);
    	
    	//adding widgets to panel
    	this.getElement().getStyle().setMargin(10, Unit.PX);
    	this.add(cc);
	    this.add(cclp);
	    this.add(sc);
	    this.add(faculties);
	    this.add(searchCourse);
	    this.add(sclp);
	    this.setWidgetTopBottom(cc, 0, Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(cclp, 2,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(sc, 29.3,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(faculties, 31.5,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(searchCourse, 34.5,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(sclp, 37.5,  Unit.EM, 0, Unit.EM);
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
	public void setSelectedCourses(List<CourseId> is) {
		
        ccl.setRowCount(is.size(), true);
        ccl.setVisibleRange(0, is.size());
	    ccl.setRowData(0,is);
		
	}
	@Override
	public void setNotSelectedCourses(List<CourseId> is) {
		scl.setRowCount(is.size(), true);
        scl.setVisibleRange(0, is.size());
	    scl.setRowData(0,is);
		
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
	public void setHoveredCourseDetail(String detail) {
		hoveredCourseDetail = detail;
		
	}
	@Override
	public Widget getAsWidget() {
		return this.asWidget();
	} 
    
}
