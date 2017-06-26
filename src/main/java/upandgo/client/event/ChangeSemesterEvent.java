package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;

import upandgo.shared.entities.Semester;
import upandgo.shared.entities.course.CourseId;

/**
 * 
 * @author Yaniv Levinsky
 * 
 * Event that happens when user click on a different semester {@link ChangeSemesterEventHandler}.
 * 
 */

public class ChangeSemesterEvent extends GwtEvent<ChangeSemesterEventHandler> {
	public static Type<ChangeSemesterEventHandler> TYPE = new Type<>();
	private final Semester semester;

	public ChangeSemesterEvent(Semester semester) {
	    this.semester = semester;
	  }

	public Semester getSemester() {
		return semester;
	}

	@Override
	public Type<ChangeSemesterEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChangeSemesterEventHandler handler) {
		handler.onSemesterChange(this);

	}
}
