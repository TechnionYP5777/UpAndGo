package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;

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
	private final String idOrName;

	public SelectCourseEvent(String idOrName) {
	    this.idOrName = idOrName;
	  }

	public String getIdOrName() {
		return idOrName;
	}

	@Override
	public Type<SelectCourseEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SelectCourseEventHandler handler) {
		handler.onSelectCourse(this);
		
	}

}
