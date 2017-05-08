package upandgo.client;


import org.gwtbootstrap3.client.ui.Navbar;
import org.gwtbootstrap3.client.ui.NavbarBrand;
import org.gwtbootstrap3.client.ui.NavbarCollapse;
import org.gwtbootstrap3.client.ui.NavbarHeader;
import org.gwtbootstrap3.client.ui.NavbarLink;
import org.gwtbootstrap3.client.ui.NavbarText;
import org.gwtbootstrap3.client.ui.constants.Pull;
import org.gwtbootstrap3.client.ui.gwt.FlowPanel;

import upandgo.client.Resources.NavBarStyle;

public class NavBarGUI extends FlowPanel{
	
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
	}

}
