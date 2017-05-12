package upandgo.client.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.user.cellview.client.RowHoverEvent.HoveringScope;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import upandgo.client.CoursesServiceAsync;
import upandgo.client.event.HideCourseDetailsEvent;
import com.allen_sauer.gwt.log.client.Log;

import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;

/**
 * 
 * @author Nikita Dizhur
 * @since 25-04-17
 * 
 *        A concrete presenter for {@link CourseDetailsView}.
 * 
 */

// TODO: add History management

public class CourseDetailsPresenter implements Presenter {

	CoursesServiceAsync rpcService;
	Display display;
	EventBus eventBus;

	public interface Display {
		HasMouseOutHandlers getCourseDetails();

		void setCourseDetails(Course course);

		Widget asWidget();
	}

	Optional<Course> course = Optional.absent();

	public CourseDetailsPresenter(CoursesServiceAsync rpc, EventBus eventBus, Display display) {
		this.rpcService = rpc;
		this.display = display;
		this.eventBus = eventBus;
		bind();
	}

	@Override
	public void bind() {
		display.getCourseDetails().addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(@SuppressWarnings("unused") MouseOutEvent event) {
				eventBus.fireEvent(new HideCourseDetailsEvent());
			}
		});
	}

	@Override
	public void unbind() {
		// TODO
	}

	@Override
	public void go(LayoutPanel panel) {
		bind();

		panel.clear();
		panel.add(display.asWidget());

		fetchCourseDetails();
	}

	void fetchCourseDetails() {
		// TODO: give courseId to it!
		rpcService.getCourseDetails(new CourseId("", ""), new AsyncCallback<Course>() {
			@Override
			public void onSuccess(Course result) {
				course = Optional.fromNullable(result);
				if (course.isPresent())
					display.setCourseDetails(course.get());
				else {
					Window.alert("Error fetching course details");
					Log.error("Error fetching course details.");
				}
			}

			@Override
			public void onFailure(@SuppressWarnings("unused") Throwable caught) {
				Window.alert("Error fetching course details");
				Log.error("Error fetching course details.");
			}
		});
	}
}
