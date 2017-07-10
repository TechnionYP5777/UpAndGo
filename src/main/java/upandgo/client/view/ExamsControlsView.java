package upandgo.client.view;


import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.RadioButton;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

import upandgo.client.resources.Resources;
import upandgo.client.resources.Resources.ExamsControlsStyle;

public class ExamsControlsView extends VerticalPanel{

	private ExamsControlsStyle ecStyle = Resources.INSTANCE.examsControlsStyle();

	Button examsButton = new Button("<i class=\"fa fa-pencil-square-o\" aria-hidden=\"true\"></i>&nbsp;&nbsp;לוח מבחנים");
	RadioButton moedA = new RadioButton("group1", new SafeHtml() {
		
		@Override
		public String asString() {
			return "מועד א'";
		}
	});
	RadioButton moedB = new RadioButton("group1", new SafeHtml() {
		
		@Override
		public String asString() {
			return "מועד ב'";
		}
	});
	ButtonGroup moadim = new ButtonGroup();
	
	
	public ExamsControlsView(){
    	InitializePanel();
    	ecStyle.ensureInjected();
    }

	private void InitializePanel() {
		
		this.setStyleName(ecStyle.examsControlsPanel());

		this.setVerticalAlignment(ALIGN_BOTTOM);
		this.setHorizontalAlignment(ALIGN_CENTER);
		
		examsButton.setStyleName("btn btn-primary");
		moedA.setStyleName("btn btn-default");
		moedB.setStyleName("btn btn-default");
		
		moedA.getElement().getStyle().setWidth(5, Unit.EM);
		moedA.getElement().getStyle().setVisibility(Visibility.HIDDEN);
		moedA.getElement().getStyle().setOpacity(0);
		moedA.getElement().getStyle().setProperty("transition", "visibility 0s linear 0.3s,opacity 0.3s linear");
		moedA.setActive(true);
		
		moedB.getElement().getStyle().setWidth(5, Unit.EM);
		moedB.getElement().getStyle().setVisibility(Visibility.HIDDEN);
		moedB.getElement().getStyle().setOpacity(0);
		moedB.getElement().getStyle().setProperty("transition", "visibility 0s linear 0.3s,opacity 0.3s linear");
		
		moadim.setDataToggle(Toggle.BUTTONS);
		
		moadim.add(moedA);
		moadim.add(moedB);
		
		this.add(moadim);

		this.add(examsButton);
		


	}
	
}
