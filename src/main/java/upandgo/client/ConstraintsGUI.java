package upandgo.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HorizontalPanel;

import upandgo.client.Resources.ConstraintsStyle;
import upandgo.client.presenter.ConstraintsPresenter;


/**
 * @author Yaniv Levinsky
 *
 */

public class ConstraintsGUI extends HorizontalPanel implements ConstraintsPresenter.Display{

	
	private ConstraintsStyle cStyle = Resources.INSTANCE.constraintsStyle();

	
	@Override
	public <T extends HasClickHandlers> T getDaysOffValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isDayOffChecked(ClickEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T extends HasClickHandlers> T getMinWindowsValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isMinWindowsChecked(ClickEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T extends HasClickHandlers> T getStartTimeValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isStartTimeChecked(ClickEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getReqStartTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends HasClickHandlers> T getFinishTimeValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isFinishTimeChecked(ClickEvent event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getReqFinishTime() {
		// TODO Auto-generated method stub
		return null;
	}

}
