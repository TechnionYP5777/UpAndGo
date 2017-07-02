package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;

import upandgo.shared.entities.course.CourseId;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-04-16
 * 
 * Event that happens when user selects some course in list. Is handled by {@link SelectCourseEventHandler}.
 * 
 */

public class SelectCourseEvent extends GwtEvent<SelectCourseEventHandler>{
	public static Type<SelectCourseEventHandler> TYPE = new Type<>();
	private final CourseId id;

	public SelectCourseEvent(CourseId id) {
	    this.id = id;
	  }

	public CourseId getId() {
		return id;
	}

	@Override
	public Type<SelectCourseEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SelectCourseEventHandler h) {
		h.onSelectCourse(this);
		
	}

}
