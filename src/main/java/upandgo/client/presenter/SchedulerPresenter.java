package upandgo.client.presenter;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import upandgo.client.view.CourseListView;

/**
 * 
 * @author Omri Ben Shmuel
 * @since 18-04-17
 * 
 * A concrete presenter for {@link schedulerView}.
 * 
 */

public class SchedulerPresenter implements Presenter {
	
	private final Display view;
	
	public interface Display {
		public Widget asWidget();
	}
	
	public SchedulerPresenter(Display view) {
		this.view = view;
	}
	
	
	@Override
	public void bind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unbind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void go(Panel panel) {
		panel.add(view.asWidget());

	}

}
