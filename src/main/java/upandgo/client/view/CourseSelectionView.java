package upandgo.client.view;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
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
import upandgo.client.Resources.examsBarStyle;
import upandgo.client.presenter.CourseListPresenter;
import upandgo.shared.entities.course.CourseId;


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
    private HTML examsBar;
    private HTML examsBarB;
    private Button clearCourses = new Button("<i class=\"fa-trash\" aria-hidden=\"true\"></i>&nbsp;&nbsp;מחק הכל");
    private com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel examsScrollPanel;
    String hoveredCourseDetail = "Loading...";
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
    	
//		//Course Tooltip functionality
//    	TooltipOptions options = new TooltipOptions().withDelayShow(300).withDelayHide(100).withAutoClose(true).withPlacement(TooltipPlacement.LEFT).withContent(new TooltipContentProvider() {
//			
//			@Override
//			public String getContent(Element element) {
//				@SuppressWarnings("boxing")
//				int absoluteRowIndex = Integer.valueOf($(element).attr("__gwt_row"));
//				return rowNum != absoluteRowIndex ? "Loading..." : hoveredCourseDetail;
//			}
//		});
//    	//options.withContainer("element");
//    	options.withSelector("tbody tr");
//    	
//    	
//    	$(ccl).as(Tooltip).tooltip(options);
//    	$(scl).as(Tooltip).tooltip(options);
    	
    	//initializing exams bar inside a mgwt scroll panel
		examsBarStyle ebStyle = Resources.INSTANCE.examsBarStyle();
		ebStyle.ensureInjected();
		HTML examsBar = new HTML("");
    	examsScrollPanel = new com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel();
    	examsScrollPanel.setScrollingEnabledX(true);
    	examsScrollPanel.setScrollingEnabledY(false);
    	examsScrollPanel.setShowVerticalScrollBar(false);
    	examsScrollPanel.setWidget(examsBar);
    	examsScrollPanel.addStyleName(ebStyle.examBarPanel());
    	examsBar.addStyleName("horizontal-scroll-wrapper");
    	
    	
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
		Log.info("dsfsfsfsfsfsfsdfsfsfsfsdfsdfsfsd");
		selectedModel.setList(courses);
		ccl.setRowCount(selectedModel.getList().size(), true);
		ccl.setVisibleRange(0, courses.size());
		
	   

		
		
	}
	@Override
	public void setNotSelectedCourses(List<CourseId> is) {
		deselectedModel.setList(is);
		scl.setRowCount(is.size(), true);
		scl.setVisibleRange(0, is.size());
		Log.info("csfscscsd  " + is.size());
		
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
	@Override
	public Widget getExamsBar() {
		return examsScrollPanel;
	}
	@Override
	public HasClickHandlers getClearCoursesButton() {
		return clearCourses;
	}
	@Override
	public void updateLists() {

		Log.info("begin of updateLists");

		deselectedModel.refresh();
		selectedModel.refresh();
		scl.setVisibleRange(0, deselectedModel.getList().size());
		scl.setRowCount(deselectedModel.getList().size(), true);
		
		ccl.setVisibleRange(0, selectedModel.getList().size());
		ccl.setRowCount(selectedModel.getList().size(), true);

		Log.info("begin of updateLists/2");

		List<CourseId> is = new ArrayList<>(), isB = new ArrayList<>(), courses = selectedModel.getList();
	    for(CourseId c : courses){
	    	if(c.aTerm()!=null)
	    		is.add(c);
	    	if(c.bTerm()!=null)
	    		isB.add(c);
	    }
	    Log.info("begin of updateLists/3");
	    Collections.sort(is,(new Comparator<CourseId>() { //sort courses by their final exam date

			@Override
			public int compare(CourseId o1, CourseId o2) {
				return o1.aTerm().compare(o2.aTerm());
			}
		}));
	    Log.info("begin of updateLists/4");
	    Collections.sort(isB,(new Comparator<CourseId>() { //sort courses by their final exam date

			@Override
			public int compare(CourseId o1, CourseId o2) {
				return o1.bTerm().compare(o2.bTerm());
			}
		}));
	    Log.info("begin of updateLists/5");
	    long width=0;
		String examsAlephBarHTML = "";
		for(int i=0; i < is.size(); i++){
			if(i == is.size()-1){
				examsAlephBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ffff80;\"> <b><u>" + is.get(i).aTerm().toString() + "</u></b><br>" + is.get(i).aTerm().getTimeToDisplay() + is.get(i).name() + "</div> ";
				width+=275;
				break;
			}
			int daysBetween = is.get(i+1).aTerm().daysBetweenExams(is.get(i).aTerm());
			if(daysBetween == 0 ){			
				examsAlephBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ff4d4d;\"> <b><u>" + is.get(i).aTerm().toString() + "</u></b><br>" + is.get(i).aTerm().getTimeToDisplay() + is.get(i).name();
				width+=275;
				while(daysBetween == 0 &&  i < is.size()-1){
					i++;
					examsAlephBarHTML+="<br>" + is.get(i).aTerm().getTimeToDisplay() + is.get(i).name();
					daysBetween = is.get(i+1).aTerm().daysBetweenExams(is.get(i).aTerm());
				}
				examsAlephBarHTML+="</div>";
				if(daysBetween > 0 ){
					for(int k = 0 ; k < daysBetween-1; k++){
						examsAlephBarHTML+="<div  class=\"child\" style=\"background-color:#85e085; \"></div>";
						width+=85;
					}
				}
				
			}
			else{
				examsAlephBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ffff80;\"> <b><u>" + is.get(i).aTerm().toString() + "</u></b><br>" + is.get(i).aTerm().getTimeToDisplay() + is.get(i).name() + "</div> ";
				width+=275;
				for(int k = 0 ; k < daysBetween-1; k++){
					examsAlephBarHTML+="<div class=\"child\" style=\"background-color:#85e085;\"></div>";
					width+=85;
				}
			}
		}
		Log.info("begin of updateLists/6");
		long widthb=0;
		String examsBetBarHTML = "";
		for(int i=0; i < isB.size(); i++){
			if(i == isB.size()-1){
				examsBetBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ffff80;\"> <b><u>" + isB.get(i).bTerm().toString() + "</u></b><br>" + isB.get(i).bTerm().getTimeToDisplay() + isB.get(i).name() + "</div> ";
				widthb+=275;
				break;
			}
			int daysBetween = isB.get(i+1).bTerm().daysBetweenExams(isB.get(i).bTerm());
			Log.info("$$#$#$#" + daysBetween);
			if(daysBetween == 0 ){			
				examsBetBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ff4d4d;\"> <b><u>" + isB.get(i).bTerm().toString() + "</u></b><br>" + isB.get(i).bTerm().getTimeToDisplay() + isB.get(i).name();
				widthb+=275;
				while(daysBetween == 0 &&  i < isB.size()-1){
					i++;
					examsBetBarHTML+="<br>" + isB.get(i).bTerm().getTimeToDisplay() + isB.get(i).name();
					daysBetween = isB.get(i+1).bTerm().daysBetweenExams(isB.get(i).bTerm());
				}
				examsBetBarHTML+="</div>";
				if(daysBetween > 0 ){
					for(int k = 0 ; k < daysBetween-1; k++){
						examsBetBarHTML+="<div  class=\"child\" style=\"background-color:#85e085; \"></div>";
						widthb+=85;
					}
				}
				
			}
			else{
				examsBetBarHTML+="<div align=\"center\" class=\"big-child\" style=\"background-color:#ffff80;\"> <b><u>" + isB.get(i).bTerm().toString() + "</u></b><br>" + isB.get(i).bTerm().getTimeToDisplay() + isB.get(i).name() + "</div> ";
				widthb+=275;
				for(int k = 0 ; k < daysBetween-1; k++){
					examsBetBarHTML+="<div class=\"child\" style=\"background-color:#85e085;\"></div>";
					widthb+=85;
				}
			}
		}
		Log.info("begin of updateLists/7");
		examsBar = new HTML(examsAlephBarHTML);
		examsBar.addStyleName("horizontal-scroll-wrapper");
		examsBar.getElement().getStyle().setWidth(width, Unit.PX);
		examsScrollPanel.setWidget(examsBar);
//			examsBarB = new HTML(examsBetBarHTML);
//			examsBarB.addStyleName("horizontal-scroll-wrapper");
//			examsBarB.getElement().getStyle().setWidth(widthb, Unit.PX);
//			examsScrollPanel.setWidget(examsBarB);

		
	} 
    
}