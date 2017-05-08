package upandgo.client;

import com.github.gwtbootstrap.client.ui.Brand;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.Navbar;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

import upandgo.client.Resources.NavBarStyle;

public class NavBarGUI extends FlowPanel{
	
	private NavBarStyle nvStyle = Resources.INSTANCE.navBarStyle();
	
	Navbar navbar = new Navbar();
	Brand brand = new Brand("Up&Go");
	NavLink signIn = new NavLink("Sign In");
	Label appTitle = new Label("Up&Go");
	
	public NavBarGUI(){
    	InitializePanel();
    	nvStyle.ensureInjected();
    }

	private void InitializePanel() {
		//this.addStyleName(nvStyle.NavBarPanel());
		//this.addStyleName("navbar navbar-toggleable-md navbar-light bg-faded");
		//appTitle.addStyleName("navbar-brand");
		//this.add(appTitle);
		
		navbar.add(brand);
		navbar.add(signIn);
		this.add(navbar);
	}

}
