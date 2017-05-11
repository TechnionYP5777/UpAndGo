package upandgo.client;


import org.gwtbootstrap3.client.ui.Navbar;
import org.gwtbootstrap3.client.ui.NavbarBrand;
import org.gwtbootstrap3.client.ui.NavbarCollapse;
import org.gwtbootstrap3.client.ui.NavbarHeader;
import org.gwtbootstrap3.client.ui.NavbarLink;
import org.gwtbootstrap3.client.ui.NavbarText;
import org.gwtbootstrap3.client.ui.SuggestBox;
import org.gwtbootstrap3.client.ui.constants.Pull;
import org.gwtbootstrap3.client.ui.gwt.FlowPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.VerticalPanel;

import upandgo.client.Resources.NavBarStyle;

public class NavBarGUI extends FlowPanel{
	
	
	 private static class MyDialog extends DialogBox {

	      public MyDialog() {
	         // Set the dialog box's caption.
	         setText("Login\Signup");

	         // Enable animation.
	         setAnimationEnabled(true);

	         // Enable glass background.
	         setGlassEnabled(true);

	         // DialogBox is a SimplePanel, so you have to set its widget 
	         // property to whatever you want its contents to be.
	         Button ok = new Button("OK");
	         ok.addClickHandler(new ClickHandler() {
	            @Override
				public void onClick(ClickEvent event) {
	               MyDialog.this.hide();
	            }
	         });

	         Label label = new Label("Please enter username and password");

	         VerticalPanel panel = new VerticalPanel();
	         panel.setHeight("20em");
	         panel.setWidth("300");
	         panel.setSpacing(10);
	         panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	         panel.add(label);
	         panel.add(ok);

	         setWidget(panel);
	      }
	   }
	
	
	private NavBarStyle nvStyle = Resources.INSTANCE.navBarStyle();
	
	Navbar navbar = new Navbar();
	NavbarCollapse navbarCol = new NavbarCollapse();
	NavbarHeader header = new NavbarHeader();
	NavbarBrand brand = new NavbarBrand();
	NavbarText text = new NavbarText();
	NavbarLink signIn = new NavbarLink();
	
	public NavBarGUI(){
    	InitializePanel();
    	nvStyle.ensureInjected();
    }

	private void InitializePanel() {
		//this.addStyleName(nvStyle.NavBarPanel());
		//this.addStyleName("navbar navbar-toggleable-md navbar-light bg-faded");
		//appTitle.addStyleName("navbar-brand");
		//this.add(appTitle);
		
		//navbar.setPosition(NavbarPosition.FIXED_TOP);
		brand.setText("Up&Go");
		signIn.setText("כניסה / הרשמה");
		text.setPull(Pull.RIGHT);
		text.setPaddingRight(15);
		text.add(signIn);
		header.add(brand);
		navbar.add(header);
		navbarCol.add(text);
		navbar.add(navbarCol);
		this.add(navbar);
		this.addStyleName(nvStyle.NavBarPanel());
		
		
		signIn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				// TODO Auto-generated method stub
				MyDialog myDialog = new MyDialog();

	            int left = Window.getClientWidth()/ 2;
	            int top = Window.getClientHeight()/ 2;
	            myDialog.setPopupPosition(left, top);
	            myDialog.show();
			}
		});
		
	}

}
