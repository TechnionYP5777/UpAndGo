package upandgo.client.presenter;

import java.util.List;

import org.gwtbootstrap3.client.shared.event.ModalHiddenEvent;
import org.gwtbootstrap3.client.shared.event.ModalHiddenHandler;
import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Modal;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import upandgo.client.CoursesServiceAsync;
import upandgo.client.LoginInfo;
import upandgo.client.LoginServiceAsync;
import upandgo.client.event.AuthenticationEvent;
import upandgo.client.event.ChangeSemesterEvent;
import upandgo.shared.entities.Semester;

public class NavBarPresenter implements Presenter {

	public interface Display {
		<T extends HasClickHandlers & HasText> T getSignInOutButton();

		Widget getAsWidget();
		
		public List<AnchorListItem> getSemesterListItems();
		
		public AnchorButton getSemesterButton();
		
		public Modal initializeSemesterModal(Semester s);
		
		public HasClickHandlers getSemesterModalAcceptButton();
		
		public void semesterModalAcceptButtonSetSpin(boolean spin);
				
		public void markChoosenSemesterEntry(Semester s);
		
		public HasClickHandlers getTempButton();
		
		public HasClickHandlers getTempButton2();
	}

	static String signInMessage = "Sign In";
	static String signOutMessage = "Sign Out";
	
	protected LoginServiceAsync rpcService;
	protected CoursesServiceAsync coursesService;
	protected Display display;
	protected EventBus eventBus;

	protected LoginInfo loginInfo;
	String signInHref = "";
	String signOutHref = "";
	
	protected HandlerRegistration signInOutHandler;

	Semester currentSemester;
	Semester requestedSemester;
	Modal semesterModal;
	
	@Inject
	public NavBarPresenter(LoginServiceAsync rpc, CoursesServiceAsync coursesService, EventBus eventBus, Display display, Semester defaultSemester) {
		this.rpcService = rpc;
		this.coursesService = coursesService;
		this.display = display;
		this.eventBus = eventBus;
		this.currentSemester = defaultSemester;
	}

	@Override
	public void bind() {
		refreshUser();
				
		for (final AnchorListItem semesterItem : display.getSemesterListItems())
			semesterItem.addClickHandler(new ClickHandler() {
				@Override
				@SuppressWarnings("unused")
				public void onClick(ClickEvent e) {
					Log.info("NavBarPresenter: user clicked on semester " + semesterItem.getId());
					requestedSemester = Semester.fromId(semesterItem.getId());
					semesterModal = display.initializeSemesterModal(requestedSemester);
					semesterModal.show();
					semesterModal.addHiddenHandler(new ModalHiddenHandler() {
						@Override
						public void onHidden(ModalHiddenEvent evt) {
							display.semesterModalAcceptButtonSetSpin(false);
						}
					});
				}
			});
		
		display.getSemesterModalAcceptButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(@SuppressWarnings("unused") ClickEvent arg0) {
				Log.info("NavBarPresenter: user accepted semester change " + requestedSemester.getId());
				display.semesterModalAcceptButtonSetSpin(true);
				eventBus.fireEvent(new ChangeSemesterEvent(Semester.fromId(requestedSemester.getId())));
				currentSemester = requestedSemester;
				display.getSemesterButton().setText(currentSemester.getName());
				semesterModal.hide();
				display.markChoosenSemesterEntry(currentSemester);
				
			}
		});
		
		display.getSemesterButton().setText(currentSemester.getName());
		
		display.markChoosenSemesterEntry(currentSemester);
		
		display.getTempButton2().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(@SuppressWarnings("unused") ClickEvent e) {
				coursesService.getSelectedCoursesString(currentSemester, new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String arg0) {
						Log.info("NavBarPresenter: onSuccess " + arg0);
						
					}
					
					@Override
					public void onFailure(Throwable arg0) {
						Log.info("NavBarPresenter: onFailure " + arg0);
						
					}
				});				
			}
		});
	}

	@Override
	public void unbind() {
		// Auto-generated method stub

	}

	@Override
	public void go(LayoutPanel p) {
		bind();
		
		p.add(display.getAsWidget());

		p.setWidgetLeftRight(display.getAsWidget(), 0, Unit.EM, 0, Unit.EM);
		p.setWidgetTopHeight(display.getAsWidget(), 0, Unit.EM, 4, Unit.EM);
		
		
		display.getAsWidget().getElement().getParentElement().getStyle().setPosition(Position.STATIC);

	}

	class LogInClickHandler implements ClickHandler {

		@Override
		public void onClick(@SuppressWarnings("unused") ClickEvent e) {
			Log.warn("entered LogInClickHandler onClick");
			if(signInOutHandler != null)
				signInOutHandler.removeHandler();
			Log.warn("user's info: " + loginInfo.isLoggedIn() + " " + loginInfo.getNickname());
			display.getSignInOutButton().setText("Welcome, "+loginInfo.getNickname()+".\n"+signOutMessage);
			signInOutHandler = display.getSignInOutButton().addClickHandler(new LogOutClickHandler());
			Window.Location.assign(signInHref);
			eventBus.fireEvent(new AuthenticationEvent(true));
		}
	}

	class LogOutClickHandler implements ClickHandler {

		@Override
		public void onClick(@SuppressWarnings("unused") ClickEvent e) {
			Log.warn("entered LogOutClickHandler onClick");
			if(signInOutHandler != null)
				signInOutHandler.removeHandler();
			Log.warn("user's info: " + loginInfo.isLoggedIn() + " " + loginInfo.getNickname());
			display.getSignInOutButton().setText(signInMessage);
			signInOutHandler = display.getSignInOutButton().addClickHandler(new LogInClickHandler());
			eventBus.fireEvent(new AuthenticationEvent(false));
			Window.Location.assign(signOutHref);
		}
	}

	void refreshUser() {
		rpcService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			@Override
			public void onFailure(Throwable error) {
				Log.warn("here333" + error.getLocalizedMessage());
			}

			@Override
			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if (result.isLoggedIn()) {
					if(signInOutHandler != null)
						signInOutHandler.removeHandler();
					Log.warn("user have signed in");
					signOutHref = loginInfo.getLogoutUrl();
					display.getSignInOutButton().setText("Welcome, "+loginInfo.getNickname()+".\n"+signOutMessage);
					signInOutHandler = display.getSignInOutButton().addClickHandler(new LogOutClickHandler());
					eventBus.fireEvent(new AuthenticationEvent(true));
				} else {
					if(signInOutHandler != null)
						signInOutHandler.removeHandler();
					Log.warn("user haven't signed in");
					signInHref = loginInfo.getLoginUrl();
					display.getSignInOutButton().setText(signInMessage);
					signInOutHandler = display.getSignInOutButton().addClickHandler(new LogInClickHandler());
					eventBus.fireEvent(new AuthenticationEvent(false));
				}
			}
		});
	}

}
