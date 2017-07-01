package upandgo.client.view;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Divider;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.ListDropDown;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.Navbar;
import org.gwtbootstrap3.client.ui.NavbarBrand;
import org.gwtbootstrap3.client.ui.NavbarCollapse;
import org.gwtbootstrap3.client.ui.NavbarHeader;
import org.gwtbootstrap3.client.ui.NavbarNav;
import org.gwtbootstrap3.client.ui.NavbarText;
import org.gwtbootstrap3.client.ui.constants.ButtonSize;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Pull;
import org.gwtbootstrap3.client.ui.constants.Toggle;
import org.gwtbootstrap3.client.ui.gwt.FlowPanel;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import upandgo.client.Resources;
import upandgo.client.Resources.NavBarStyle;

import upandgo.client.presenter.NavBarPresenter;
import upandgo.shared.entities.Semester;

public class NavBarView extends FlowPanel implements NavBarPresenter.Display {
	
	
	
	private NavBarStyle nvStyle = Resources.INSTANCE.navBarStyle();
	
	Navbar navbar = new Navbar();
	NavbarNav navbarNav = new NavbarNav();
	NavbarCollapse navbarCol = new NavbarCollapse();
	NavbarHeader header = new NavbarHeader();
	NavbarBrand brand = new NavbarBrand();
	NavbarText signInText = new NavbarText();
    ListDropDown semesterList = new ListDropDown();
    AnchorButton semesterButton = new AnchorButton();
    DropDownMenu semesterMenu = new DropDownMenu();
    List<AnchorListItem> semesterListItems = new ArrayList<>();
    Modal semesterModal = new Modal();
	Button semesterModalAcceptButton = new Button("<i class=\"fa fa-check\" aria-hidden=\"true\"></i>&nbsp;&nbsp;אשר");
	Button tempButton = new Button("כתוב סמסטר בלוג");
	Button tempButton2 = new Button("כתוב קורסים שנבחרו בלוג");
	org.gwtbootstrap3.client.ui.Button signInButton = new org.gwtbootstrap3.client.ui.Button("כניסה / הרשמה");
	
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
		signInText.setPull(Pull.RIGHT);
		signInText.setPaddingRight(15);
		signInText.add(signInButton);
		signInButton.setSize(ButtonSize.EXTRA_SMALL);
		signInButton.setType(ButtonType.LINK);
		signInButton.setIcon(IconType.USER_CIRCLE);
		header.add(brand);
		navbar.add(header);
		navbarCol.add(signInText);
		
		
		semesterButton.setDataToggle(Toggle.DROPDOWN);
		semesterButton.setText("בחר סמסטר");
		semesterList.add(semesterButton);
		for (Semester semester : Semester.values()){
			AnchorListItem semesterListItem = new AnchorListItem();
			semesterListItem.setId(semester.getId());
			semesterListItem.setText(semester.getName());
			semesterListItems.add(semesterListItem);
			semesterMenu.add(semesterListItem);
		}
		semesterList.add(semesterMenu);
	
		AnchorListItem helpLink = new AnchorListItem("מדריך למשתמש");
		helpLink.setHref("https://github.com/TechnionYP5777/UpAndGo/wiki/User-Manual-Version-2.0");
		helpLink.addStyleName(nvStyle.navBarLink());
		
		Node hrefNode = helpLink.getElement().getChild(0);
		if (hrefNode.getNodeType() == Node.ELEMENT_NODE){
			((Element)hrefNode).setAttribute("target", "_blank");
		}
		
		AnchorListItem reviewLink = new AnchorListItem("שליחת משוב");
		reviewLink.setHref("https://goo.gl/forms/xMXAzYbbYw1md5ls2");
		reviewLink.addStyleName(nvStyle.navBarLink());
		
		hrefNode = reviewLink.getElement().getChild(0);
		if (hrefNode.getNodeType() == Node.ELEMENT_NODE){
			((Element)hrefNode).setAttribute("target", "_blank");
		}

		navbarNav.add(reviewLink);
		navbarNav.add(helpLink);
		navbarNav.add(semesterList);
		navbarNav.setPull(Pull.NONE);

		navbarCol.add(navbarNav);
		navbar.add(navbarCol);
		
		this.add(navbar);
		//this.addStyleName(nvStyle.navBarPanel());
		
		

		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends HasClickHandlers & HasText> T getSignInOutButton() {
		return (T) signInButton;
	}

	@Override
	public Widget getAsWidget() {
		return this.asWidget();
	}
	
	@Override
	public List<AnchorListItem> getSemesterListItems(){
		return this.semesterListItems;
	}
	
	@Override
	public AnchorButton getSemesterButton(){
		return this.semesterButton;
	}
	
	@Override
	public HasClickHandlers getSemesterModalAcceptButton(){
		return this.semesterModalAcceptButton;
	}
	
	@Override
	public HasClickHandlers getTempButton(){
		return this.tempButton;
	}
	
	@Override
	public HasClickHandlers getTempButton2(){
		return this.tempButton2;
	}
	
	@Override
	public void semesterModalAcceptButtonSetSpin(boolean spin){
		if (spin)
			semesterModalAcceptButton.setHTML("<i class=\"fa fa-spinner fa-spin\" aria-hidden=\"true\"></i>&nbsp;&nbsp;נא המתן");
		else
			semesterModalAcceptButton.setHTML("<i class=\"fa fa-check\" aria-hidden=\"true\"></i>&nbsp;&nbsp;אשר");
	}
		
	@Override
	public Modal initializeSemesterModal(Semester semester){
		semesterModal = new Modal();
		semesterModal.setFade(true);
		semesterModal.setTitle("אזהרה");
		
		ModalBody semesterModalBody = new ModalBody();
		semesterModalBody.add(new Heading(HeadingSize.H4,"שינוי סמסטר יוביל למחיקת מערכת שלא נשמרה."));
		semesterModalBody.add(new Heading(HeadingSize.H4,"האם אתה בטוח שברצונך לעבור לסמסטר " + semester.getName() + "?"));
		semesterModal.add(semesterModalBody);
		
		ModalFooter semesterModalFooter = new ModalFooter();
		Button semesterModalCancelButton = new Button("<i class=\"fa fa-times\" aria-hidden=\"true\"></i>&nbsp;&nbsp;בטל");
		semesterModalCancelButton.setStyleName("btn btn-success");
		semesterModalCancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				semesterModal.hide();
			}
		});
		semesterModalAcceptButton.setStyleName("btn btn-danger");
		
		semesterModalFooter.add(semesterModalCancelButton);
		semesterModalFooter.add(semesterModalAcceptButton);
		semesterModal.add(semesterModalFooter);
		return semesterModal;
	}	
	
	@Override
	public void markChoosenSemesterEntry(Semester semester){
		for (final AnchorListItem semesterItem : semesterListItems){
			if (semesterItem.getId().equals(semester.getId())){
				semesterItem.setEnabled(false);
				semesterItem.setStyleName(nvStyle.choosenSemesterEntry());
			} else {
				semesterItem.setEnabled(true);
				semesterItem.removeStyleName(nvStyle.choosenSemesterEntry());
			}

		}
	}

}
