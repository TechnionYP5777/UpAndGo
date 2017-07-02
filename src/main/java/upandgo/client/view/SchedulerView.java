package upandgo.client.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

import upandgo.client.Resources;
import upandgo.client.Resources.MainStyle;
import upandgo.client.Resources.ExamsBarStyle;
import upandgo.client.presenter.SchedulerPresenter;
import upandgo.shared.entities.Exam;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.UserEvent;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.Color;
import upandgo.shared.model.scedule.ConstraintsPool;
import upandgo.shared.model.scedule.CourseTuple;

public class SchedulerView extends LayoutPanel implements SchedulerPresenter.Display{

	private MainStyle style = Resources.INSTANCE.mainStyle();
    private HTML examsBar;
    @SuppressWarnings("unused")
	private HTML examsBarB;
    private com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel examsScrollPanel;
	TimeTableView timeTableView = new TimeTableView();
	ScrollPanel scrollableTimeTable = new ScrollPanel(timeTableView);
	SchedulerConstraintsView schedualerConstraintsView = new SchedulerConstraintsView();
	SchedulerCollisionsView schedulerCollisionsView = new SchedulerCollisionsView();
	
	SchedulerControlsView schedualerControlsView = new SchedulerControlsView(schedualerConstraintsView);
	ExamsControlsView examsControlsView = new ExamsControlsView();
	Modal collisionBox = new Modal();
	Button collisionBoxButton = new Button("הסר קורס");
	List<RadioButton> radios;
	List<CourseTuple> solversTuples;
	
	ExportScheduleModal exportScheduleModal = new ExportScheduleModal();
	
	public SchedulerView(){
		InitializePanel();
		InitializeCollisionBox();
		style.ensureInjected();
	}
	
	
	private void InitializePanel(){
		
		
		//initializing exams bar inside a mgwt scroll panel
		ExamsBarStyle ebStyle = Resources.INSTANCE.examsBarStyle();
		ebStyle.ensureInjected();
		examsBar = new HTML("");
    	examsScrollPanel = new com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel();
    	examsScrollPanel.setScrollingEnabledX(true);
    	examsScrollPanel.setScrollingEnabledY(false);
    	examsScrollPanel.setShowVerticalScrollBar(false);
    	examsScrollPanel.setShowHorizontalScrollBar(false);
    	examsScrollPanel.setWidget(examsBar);
    	examsScrollPanel.addStyleName(ebStyle.examBarPanel());
    	examsBar.addStyleName("horizontal-scroll-wrapper");
		
		
		// needs to be injected
		
		scrollableTimeTable.addStyleName(style.scrollableTimeTable());

		this.setHeight("100%");
		
		this.add(scrollableTimeTable);
		this.setWidgetLeftRight(scrollableTimeTable, 11, Unit.EM, 0, Unit.EM);
		this.setWidgetTopBottom(scrollableTimeTable, 1, Unit.EM, 1, Unit.EM);
		this.getWidgetContainerElement(scrollableTimeTable).getStyle().setProperty("transition", "bottom 0.3s linear 0s");
		
		this.add(examsScrollPanel);
		this.setWidgetLeftRight(examsScrollPanel, 11, Unit.EM, 0, Unit.EM);
		this.setWidgetBottomHeight(examsScrollPanel, 0, Unit.EM, 0, Unit.EM);
		this.getWidgetContainerElement(examsScrollPanel).getStyle().setProperty("transition", "height 0.3s linear 0s");	
		
		this.add(schedualerControlsView);
		this.setWidgetLeftWidth(schedualerControlsView, 0, Unit.EM, 10, Unit.EM);
		this.setWidgetTopBottom(schedualerControlsView, 1, Unit.EM, 1, Unit.EM);
		
		this.add(examsControlsView);
		this.setWidgetLeftWidth(examsControlsView, 0, Unit.EM, 10, Unit.EM);
		this.setWidgetBottomHeight(examsControlsView, 1, Unit.EM, 7, Unit.EM);

		schedualerControlsView.exportSchedule.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(@SuppressWarnings("unused") ClickEvent event) {
				exportScheduleModal.setText("הייצוא של מערכת שעות שלך ל-Google Calendar בתהליך, אנא המתן");
				exportScheduleModal.setAsLoading();
				exportScheduleModal.show();
			}
		});
		}
	
	

	private void InitializeCollisionBox(){
		ModalFooter collisionBoxFooter = new ModalFooter();
				
		collisionBoxFooter.add(collisionBoxButton);
		collisionBox.setFade(true);
		collisionBox.setTitle("פתרון התנגשויות");
				
		ModalBody collisionBoxBody = new ModalBody();
		collisionBoxBody.add(schedulerCollisionsView);
		collisionBox.add(collisionBoxBody);
		
		collisionBox.add(collisionBoxFooter);
		
		
		
	}
	
	@Override
	public void drawCollisionView(List<CourseTuple> solvers){
		schedulerCollisionsView.updateList(solvers);
		radios = schedulerCollisionsView.getRadios();
		solversTuples = schedulerCollisionsView.getSolversTuples();
		collisionBox.show();
	}
	
	
	@Override
	public void drawEmptyListView(){
		
		
		final Modal semesterModal = new Modal();
		semesterModal.setFade(true);
		semesterModal.setTitle("לא נבחרו קורסים");
		
		ModalBody semesterModalBody = new ModalBody();
		semesterModalBody.add(new Heading(HeadingSize.H4,"לא ניתן לבנות מערכת ללא קורסים, אנא בחר קורסים מתוך הרשימה בצד ימין."));
		semesterModal.add(semesterModalBody);
		
		ModalFooter semesterModalFooter = new ModalFooter();
		Button semesterModalAcceptButton = new Button("<i class=\"fa fa-check\" aria-hidden=\"true\"></i>&nbsp;&nbsp;הבנתי");
		semesterModalAcceptButton.setStyleName("btn btn-success");
		semesterModalAcceptButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(@SuppressWarnings("unused") ClickEvent arg0) {
				semesterModal.hide();
			}
		});

		semesterModalFooter.add(semesterModalAcceptButton);
		semesterModal.add(semesterModalFooter);
		semesterModal.show();
	}
	
	
	@Override
	public void drawManyCollisionView(){
		
		
		final Modal semesterModal = new Modal();
		semesterModal.setFade(true);
		semesterModal.setTitle("יותר מדי התנגשויות");
		
		ModalBody semesterModalBody = new ModalBody();
		semesterModalBody.add(new Heading(HeadingSize.H4,"לא ניתן להשלים את בניית המערכת בשל עודף התנגשויות,"));
		semesterModalBody.add(new Heading(HeadingSize.H4,"אנא וותר על חלק מהקורסים ונסה שוב."));
		semesterModal.add(semesterModalBody);
		
		ModalFooter semesterModalFooter = new ModalFooter();
		Button semesterModalAcceptButton = new Button("<i class=\"fa fa-check\" aria-hidden=\"true\"></i>&nbsp;&nbsp;הבנתי");
		semesterModalAcceptButton.setStyleName("btn btn-success");
		semesterModalAcceptButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(@SuppressWarnings("unused") ClickEvent arg0) {
				semesterModal.hide();
			}
		});
		
		semesterModalFooter.add(semesterModalAcceptButton);
		semesterModal.add(semesterModalFooter);
		semesterModal.show();

	}
	
	
	@Override
	public HasClickHandlers clearSchedule() {
		return schedualerControlsView.clearSchedule;
	}

	@Override
	public HasClickHandlers buildSchedule() {
		return schedualerControlsView.buildSchedule;
	}

	@Override
	public HasClickHandlers nextSchedule() {
		return schedualerControlsView.nextSchedule;
	}

	@Override
	public HasClickHandlers prevSchedule() {
		return schedualerControlsView.prevSchedule;
	}

	@Override
	public HasClickHandlers saveSchedule() {
		return schedualerControlsView.saveSchedule;
	}
 	
	@Override
	public Widget getAsWidget() {
		return this.asWidget();
	}

	@Override
	public void displaySchedule(List<LessonGroup> lessons, Map<String, Color> map, List<UserEvent> events) {
		timeTableView.displaySchedule(lessons, map, events);
		
	}
	
	@Override
	public void setConstraintsPool(List<Course> selectedCourses, ConstraintsPool constraintsPool){
		schedualerConstraintsView.setConstraintsPool(selectedCourses, constraintsPool);
	}
	
	@Override
	public ConstraintsPool getConstraintsPool(){
		return schedualerConstraintsView.getConstraintsPool();
	}
	
	@Override
	public Modal getConstraintsModal(){
		return schedualerControlsView.constraintsBox;
	}
	
	@Override
	public HasClickHandlers getConstraintsBoxSaveButton() {
		return schedualerControlsView.constraintsBoxSaveButton;
	}
	
	@Override
	public Modal getCollisionModal(){
		return collisionBox;
	}
	
	@Override
	public Button getCollisionModalButton(){
		return collisionBoxButton;
	}
	
	@Override
	public List<RadioButton> getCollisionRadios(){
		return radios;
	}
	
	@Override
	public List<CourseTuple> getCollisionSolversTuples(){
		return solversTuples;
	}
	
	@Override
	public void setPrevEnable(boolean enable){
		schedualerControlsView.setPrevEnable(enable);
	}
	
	@Override
	public void setNextEnable(boolean enable){
		schedualerControlsView.setNextEnable(enable);
	}
	
	@Override
	public void setCurrentScheduleIndex(int index, int max){
		schedualerControlsView.setCurrentScheduleIndex(index,max);
	}
	
	@Override
	public void scheduleBuilt(){
		schedualerControlsView.scheduleBuilt();
	}
	
	@Override
	public HasClickHandlers getExamButton(){
		return examsControlsView.examsButton;
	}
	
	@Override
	public HasClickHandlers getMoedAButton(){
		return examsControlsView.moedA;
	}
	@Override
	public HasClickHandlers getMoedBButton(){
		return examsControlsView.moedB;
	}

	@Override
	public void updateExamsBar(List<Course> courses, boolean isMoedA) {
		Log.info("courses: " + courses);
		List<Course> is = new ArrayList<>();
		Map<Course, String> courseColors = new HashMap<>();
		long width=0;
		String examsBarHTML = "";
		Exam exami = null, examiPlusOne = null;
		if(isMoedA){
			for (int i = 0; i < courses.size(); ++i) {
				
				Course c = courses.get(i);
				if (c.getaTerm() != null) {
					is.add(c);
					courseColors.put(c, Color.valueOf(i).toString());
				}
			}
		 	Collections.sort(is,(new Comparator<Course>() { //sort courses by their final exam date

				@Override
				public int compare(Course o1, Course o2) {
					return o1.getaTerm().compare(o2.getaTerm());
				}
			}));
		}
		else{
			for (int i = 0; i < courses.size(); ++i) {
				
				Course c = courses.get(i);
				if (c.getbTerm() != null) {
					is.add(c);
					courseColors.put(c, Color.valueOf(i).toString());
				}
			}
			Collections.sort(is,(new Comparator<Course>() { //sort courses by their final exam date

				@Override
				public int compare(Course o1, Course o2) {
					return o1.getbTerm().compare(o2.getbTerm());
				}
			}));
		}
	   	   
		for(int i=0; i < is.size(); ++i){
			exami = isMoedA ? is.get(i).getaTerm() : is.get(i).getbTerm();
			if(i == is.size()-1){
				examsBarHTML+="<div align=\"center\" class=\"big-child\" style=\"border-color:" + courseColors.get(is.get(i)) +";\"> <b><u>" + exami.toString() + "</u></b><br>" + exami.getTimeToDisplay() + is.get(i).getName() + "</div> ";
				width+=275;
				break;
			}
			examiPlusOne = isMoedA ? is.get(i+1).getaTerm() : is.get(i+1).getbTerm();
			int daysBetween = examiPlusOne.daysBetweenExams(exami);
			if(daysBetween == 0 ){			
				
				int p = i;
				int examsNum = 1;
				while(daysBetween == 0){
					examsNum++;
					++p;			
					exami = isMoedA ? is.get(p).getaTerm() : is.get(p).getbTerm();
					if(p == is.size()-1){
						break;
					}
					examiPlusOne = isMoedA ? is.get(p+1).getaTerm() : is.get(p+1).getbTerm();
					daysBetween = examiPlusOne.daysBetweenExams(exami);
				}
				examsBarHTML+="<div align=\"center\" class=\"big-child\" style=\"color: #D8000C;background-color: #FFBABA;\"> <b><u>" + exami.toString() + "</u></b>";
				width+=275;
				if(examsNum<3){
					for(; i <= p; ++i){
						examsBarHTML+="<br>" + exami.getTimeToDisplay() + is.get(i).getName();
					}
				} else {
					examsBarHTML += "<div style=\"line-height: 1.2em;\">";
					if (examsNum == 3)
						for (; i <= p; ++i) {
							examsBarHTML += exami.getTimeToDisplay() + is.get(i).getName() + "<br>";
						}
					else {
						for (int j = 0; j < 2; ++j) {
							examsBarHTML += exami.getTimeToDisplay() + is.get(i + j).getName() + "<br>";
						}
						examsBarHTML += "<div style=\"float:buttom; font-size: 11px;\">*יש עוד " + (examsNum - 2)
								+ " מבחנים ביום זה שאינם מוצגים. </div>";
					}
					examsBarHTML += "</div>";
				}
				i=p;
				examsBarHTML+="</div>";
				if(daysBetween > 0 ){
					for(String k : examiPlusOne.datesBetweenExams(exami)){
						examsBarHTML+="<div align=\"left\" class=\"child\">" + k + "</div>";
						width+=85;
					}
				}
				
			}
			else{
				examsBarHTML+="<div align=\"center\" class=\"big-child\" style=\"border-color:" + courseColors.get(is.get(i)) +"\"> <b><u>" + exami.toString() + "</u></b><br>" + exami.getTimeToDisplay() + is.get(i).getName() + "</div> ";
				width+=275;
				for(String k : examiPlusOne.datesBetweenExams(exami)){
					examsBarHTML+="<div align=\"left\" class=\"child\">" + k + "</div>";
					width+=85;
				}
			}
		}
		examsBar = new HTML(examsBarHTML);
		examsBar.addStyleName("horizontal-scroll-wrapper");
		examsBar.getElement().getStyle().setWidth(width, Unit.PX);
		examsScrollPanel.setWidget(examsBar);
	}

	@Override
	public void collapseExamsBar() {
		this.setWidgetTopBottom(scrollableTimeTable, 1, Unit.EM, 1, Unit.EM);
		this.setWidgetBottomHeight(examsScrollPanel, 1, Unit.EM, 0, Unit.EM);
		examsControlsView.moedA.getElement().getStyle().setVisibility(Visibility.HIDDEN);
		examsControlsView.moedA.getElement().getStyle().setOpacity(0);
		examsControlsView.moedA.getElement().getStyle().setProperty("transition", "visibility 0s linear 0.3s,opacity 0.3s linear");
		
		examsControlsView.moedB.getElement().getStyle().setVisibility(Visibility.HIDDEN);
		examsControlsView.moedB.getElement().getStyle().setOpacity(0);
		examsControlsView.moedB.getElement().getStyle().setProperty("transition", "visibility 0s linear 0.3s,opacity 0.3s linear");
		
	}

	@Override
	public void openExamsBar() {
		this.setWidgetTopBottom(scrollableTimeTable, 1, Unit.EM, 102, Unit.PX);
		this.setWidgetBottomHeight(examsScrollPanel, 1, Unit.EM, 84, Unit.PX);
		examsControlsView.moedA.getElement().getStyle().setVisibility(Visibility.VISIBLE);
		examsControlsView.moedA.getElement().getStyle().setOpacity(1);
		examsControlsView.moedA.getElement().getStyle().setProperty("transitionDelay","0s");
		
		examsControlsView.moedB.getElement().getStyle().setVisibility(Visibility.VISIBLE);
		examsControlsView.moedB.getElement().getStyle().setOpacity(1);
		examsControlsView.moedB.getElement().getStyle().setProperty("transitionDelay","0s");
		
	}


	@Override
	public void setNotesOnLessonModal(String courseId, List<String> courseNotes) {
		timeTableView.setNotesOnLessonModal(courseId, courseNotes);
	}
	
	@Override
	public Button getLessonModalCreateConstraintButton() {
		return timeTableView.lessonDetailsBoxCreateConstraintButton;
	}
	
	@Override
	public Button getLessonModalRemoveConstraintButton() {
		return timeTableView.lessonDetailsBoxRemoveConstraintButton;
	}

	@Override
	public Modal getLessonModal(){
		return timeTableView.lessonDetailsBox;
	}

	@Override
	public Lesson getLessonModelCurrentLesson() {
		return timeTableView.lessonDetailsBoxCurrentLesson;
	}
	
	@Override
	public Modal getUserEventBox(){
		return timeTableView.userEventBox;
	}
	
	@Override
	public UserEvent getUserEvent(){
		return timeTableView.getUserEvent();
	}
	
	@Override
	public HasClickHandlers getUserEventBoxSaveButton() {
		return timeTableView.userEventBoxSaveButton;
	}
	
	@Override
	public HasClickHandlers getUserEventBoxDeleteButton() {
		return timeTableView.userEventBoxDeleteButton;
	}


	@Override
	public HasClickHandlers getExportScheduleButton() {
		return schedualerControlsView.exportSchedule;
	}
	
	@Override
	public void setExportScheduleText(String text) {
		exportScheduleModal.setText(text);
	}
	
	@Override
	public void setExportScheduleAsWarning() {
		exportScheduleModal.setAsWarning();
	}
	
	@Override
	public void setExportScheduleAsSuccess() {
		exportScheduleModal.setAsSuccess();
	}

}