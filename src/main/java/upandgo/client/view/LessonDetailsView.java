package upandgo.client.view;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.ListItem;
import org.gwtbootstrap3.client.ui.ModalComponent;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.html.UnorderedList;

import com.google.gwt.user.client.ui.VerticalPanel;

import upandgo.client.Resources;
import upandgo.client.Resources.LessonDetailsStyle;
import upandgo.shared.entities.Lesson;

public class LessonDetailsView extends VerticalPanel implements ModalComponent{
	
	private Lesson lesson;
	
	private UnorderedList notesList = new UnorderedList();
	
	private LessonDetailsStyle ldStyle = Resources.INSTANCE.lessonDetailsStyle();


	public LessonDetailsView(Lesson lesson){
		this.lesson = lesson;
		InitializePanel();
		ldStyle.ensureInjected();
	}
	
	private void InitializePanel(){
		this.addStyleName(ldStyle.lessonDetailsPanel());
		this.setHorizontalAlignment(ALIGN_RIGHT);
		this.add(new Heading(HeadingSize.H4, "שם הקורס: " + lesson.getCourseName()));
		this.add(new Heading(HeadingSize.H4, "זמן התחלה: " + lesson.getStartTime().toHebrewString()));
		this.add(new Heading(HeadingSize.H4, "זמן סיום: " + lesson.getEndTime().toHebrewString()));
		this.add(new Heading(HeadingSize.H4, "מיקום: " + lesson.getPlace()));
		this.add(new Heading(HeadingSize.H4, "קבוצת רישום: " + lesson.getGroup()));
		if (lesson.getRepresenter()!=null){
			this.add(new Heading(HeadingSize.H4, "מרצה: " + lesson.getRepresenter().getTitle() + " " + lesson.getRepresenter().getLastName() + " " + lesson.getRepresenter().getFirstName()));
		}
		
		Anchor ugLink = new Anchor("קישור ל-UG", "https://ug3.technion.ac.il/rishum/course?MK=" + lesson.getCourseId());
		ugLink.setTarget("_blank");
				
		this.add(new Heading(HeadingSize.H4, "הערות על הקורס: "));

		this.add(notesList);
		
		this.add(ugLink);



	}
	
	public String getCourseId(){
		return lesson.getCourseId();
	}
	
	public void addNote(String note){
		notesList.add(new ListItem(note));
	}
}
