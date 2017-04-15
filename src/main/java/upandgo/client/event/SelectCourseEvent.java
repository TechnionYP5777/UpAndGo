package upandgo.client.event;

import com.google.gwt.event.shared.GwtEvent;

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
