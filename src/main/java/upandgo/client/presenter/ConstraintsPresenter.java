
/*
 * merged with scheduler presenter 
 */
/*
package upandgo.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

import upandgo.shared.entities.constraint.TimeConstraint;
*/
/**
 * 
 * @author Omri Ben- Shmuel
 * @since 29-04-17
 * 
 *        A concrete presenter for {@link constraintsView}.
 * 
 */
/*
public class ConstraintsPresenter implements Presenter {
	
	private final Display view;
	private final EventBus eventBus;
	private final List<TimeConstraint> constraintsList;
		
	public interface Display {
		<T extends HasClickHandlers> T getDaysOffValue();
		public int isDayOffChecked(ClickEvent event); // 1- if selected. 0- if not
		
		<T extends HasClickHandlers> T getMinWindowsValue();
		public int isMinWindowsChecked(ClickEvent event); // 1- if selected. 0- if not
		
		<T extends HasClickHandlers> T getStartTimeValue();
		public int isStartTimeChecked(ClickEvent event);
		public String getReqStartTime(); // result in format HH:MM
		
		<T extends HasClickHandlers> T getFinishTimeValue();
		public int isFinishTimeChecked(ClickEvent event);
		public String getReqFinishTime(); // result in format HH:MM
				
		public Widget asWidget();
	}
	
	public ConstraintsPresenter(Display view, EventBus eventBus) {
		this.eventBus = eventBus; 
		this.view = view;
		this.constraintsList = new ArrayList<>();
	}
	
	@Override
	public void bind() {
		
		view.getDaysOffValue().addClickHandler(new ClickHandler() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void onClick(ClickEvent event) {
				int $ = view.isDayOffChecked(event);
				if ($ == 1) {
					constraintsList.add(null); //change null to real constraints
				} else {
					constraintsList.remove(null); //change null to real constraints
				}
			}
		});	
		
		view.getMinWindowsValue().addClickHandler(new ClickHandler() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void onClick(ClickEvent event) {
				int $ = view.isMinWindowsChecked(event);
				if ($ == 1) {
					constraintsList.add(null); //change null to real constraints
				} else {
					constraintsList.remove(null); //change null to real constraints, override equals method in timeConstraints
				}
				
			}
		});
		
		view.getStartTimeValue().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int $ = view.isStartTimeChecked(event);
				if ($ == 1) {
					String startTime = view.getReqStartTime();
					//TimeConstraint con = new TimeConstraint(startTime); ** check how construct timeConstraints
					//constraintsList.add(con);
				} else {
					//constraintsList.remove(null); // need change
				}
			}
		});
		
		view.getFinishTimeValue().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int $ = view.isFinishTimeChecked(event);
				if ($ == 1) {
					String finishTime = view.getReqFinishTime();
					//TimeConstraint con = new TimeConstraint(finishTime); ** check how construct timeConstraints
					//constraintsList.add(con);
				} else {
					//constraintsList.remove(con);
				}
			}
		});
		
		}

	@Override
	public void unbind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void go(Panel panel) {
		bind();

		panel.clear();
		panel.add(view.asWidget());
	}

}*/
