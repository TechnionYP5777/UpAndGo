package upandgo.client.view;

/**
 * 
 * @author danabra
 * @since 15-06-17
 * 
 * A View that contains all the widgets on the left side, uses UiBinder
 * 
 */

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;

public class LeftSideView extends Composite {

	private static ExamsUiBinder uiBinder = GWT.create(ExamsUiBinder.class);

	interface ExamsUiBinder extends UiBinder<Widget, LeftSideView> {
	}
	
	@UiField(provided=true)
	ScrollPanel examsBar;
	
	@UiField(provided=true)
	SchedulerView sv;

	public LeftSideView(ScrollPanel examsBar, SchedulerView sv) {
		if(examsBar != null)
			this.examsBar = examsBar;
		else{
			this.examsBar = new ScrollPanel();
		}
		this.sv = sv;
		initWidget(uiBinder.createAndBindUi(this));
	}
}
