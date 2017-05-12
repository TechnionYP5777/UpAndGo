package upandgo.client;



import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Navbar;
import org.gwtbootstrap3.client.ui.NavbarBrand;
import org.gwtbootstrap3.client.ui.NavbarCollapse;
import org.gwtbootstrap3.client.ui.NavbarHeader;
import org.gwtbootstrap3.client.ui.NavbarText;
import org.gwtbootstrap3.client.ui.constants.ButtonSize;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Pull;
import org.gwtbootstrap3.client.ui.gwt.FlowPanel;

import com.github.gwtbootstrap.client.ui.Modal;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import upandgo.client.Resources.NavBarStyle;

public class NavBarView extends FlowPanel{
	
	
	
	private NavBarStyle nvStyle = Resources.INSTANCE.navBarStyle();
	
	Navbar navbar = new Navbar();
	NavbarCollapse navbarCol = new NavbarCollapse();
	NavbarHeader header = new NavbarHeader();
	NavbarBrand brand = new NavbarBrand();
	NavbarText text = new NavbarText();
	Button signInButton = new Button("כניסה / הרשמה");
	
	public NavBarView(){
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
		text.setPull(Pull.RIGHT);
		text.setPaddingRight(15);
		text.add(signInButton);
		signInButton.setSize(ButtonSize.EXTRA_SMALL);
		signInButton.setType(ButtonType.LINK);
		signInButton.setIcon(IconType.USER_CIRCLE);
		header.add(brand);
		navbar.add(header);
		navbarCol.add(text);
		navbar.add(navbarCol);
		this.add(navbar);
		this.addStyleName(nvStyle.NavBarPanel());
		
		
		signInButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {

				Modal loginBox = new Modal(true);
				loginBox.setTitle("כניסה / הרשמה");
				loginBox.setId("login");
				loginBox.add(new LoginDialog());
				loginBox.show();

/*				// TODO Auto-generated method stub
				LoginDialog myDialog = new LoginDialog();

	            int left = Window.getClientWidth()/ 3;
	            int top = Window.getClientHeight()/ 3;
	            myDialog.setPopupPosition(left, top);
	            myDialog.show();*/
			}
		});
		
	}

}
