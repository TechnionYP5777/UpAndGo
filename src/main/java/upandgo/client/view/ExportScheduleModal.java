package upandgo.client.view;

import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.i18n.client.HasDirection;

public class ExportScheduleModal extends Modal implements HasText {

	Button okButton = new Button("<i class=\"fa fa-check\" aria-hidden=\"true\"></i>&nbsp;&nbsp;המשך");
	Label text = new Label();
	
	
	ExportScheduleModal() {
		setAsWarning();
		setText("");
		text.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		 
		okButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(@SuppressWarnings("unused") ClickEvent event) {
				ExportScheduleModal.this.hide();
			}
		});

		ModalFooter footer = new ModalFooter();
		footer.add(okButton);
		
		
		
		ModalBody body = new ModalBody();
		body.add(text);
		
		
		this.add(body);
		this.add(footer);

		this.setFade(true);
		this.setTitle("יצוא מערכת");
	}
	
	void setAsWarning() {
		okButton.setHTML("<i class=\"fa fa-check\" aria-hidden=\"true\"></i>&nbsp;&nbsp;המשך");
		okButton.setStyleName("btn btn-warning");
		okButton.setEnabled(true);
	}
	
	void setAsSuccess() {
		okButton.setHTML("<i class=\"fa fa-check\" aria-hidden=\"true\"></i>&nbsp;&nbsp;המשך");
		okButton.setStyleName("btn btn-success");
		okButton.setEnabled(true);
	}
	
	void setAsLoading() {
		okButton.setHTML("<i class=\"fa fa-spinner fa-spin\" aria-hidden=\"true\"></i>&nbsp;&nbsp;בתהליך");
		okButton.setStyleName("btn btn-info");
		okButton.setEnabled(false);
	}

	@Override
	public String getText() {
		return text.getText();
	}

	@Override
	public void setText(String newText) {
		text.setText(newText, HasDirection.Direction.RTL);	
	}
	
	
	
}
