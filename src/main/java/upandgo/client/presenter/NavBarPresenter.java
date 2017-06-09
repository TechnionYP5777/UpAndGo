package upandgo.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import upandgo.client.LoginInfo;
import upandgo.client.LoginServiceAsync;

public class NavBarPresenter implements Presenter {

	public interface Display {
		<T extends HasClickHandlers & HasText> T getSignInOutButton();

		Widget getAsWidget();
	}

	static String signInMessage = "Sign In";
	static String signOutMessage = "Sign Out";
	
	protected LoginServiceAsync rpcService;
	protected Display display;
	protected EventBus eventBus;

	protected LoginInfo loginInfo = null;
	String signInHref = "";
	String signOutHref = "";
	
	protected HandlerRegistration signInOutHandler = null;

	@Inject
	public NavBarPresenter(LoginServiceAsync rpc, EventBus eventBus, Display display) {
		this.rpcService = rpc;
		this.display = display;
		this.eventBus = eventBus;
	}

	@Override
	public void bind() {
		refreshUser();
	}

	@Override
	public void unbind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void go(LayoutPanel panel) {
		bind();
		
		panel.add(display.getAsWidget());

		panel.setWidgetLeftRight(display.getAsWidget(), 0, Unit.EM, 0, Unit.EM);
		panel.setWidgetTopHeight(display.getAsWidget(), 0, Unit.EM, 4, Unit.EM);

	}

	class LogInClickHandler implements ClickHandler {

		@Override
		public void onClick(@SuppressWarnings("unused") ClickEvent event) {
			Log.warn("entered LogInClickHandler onClick");
			if(signInOutHandler != null)
				signInOutHandler.removeHandler();
			Log.warn("user's info: " + loginInfo.isLoggedIn() + " " + loginInfo.getNickname());
			display.getSignInOutButton().setText(signOutMessage);
			signInOutHandler = display.getSignInOutButton().addClickHandler(new LogOutClickHandler());
			Window.Location.assign(signInHref);
//			refreshUser();
		}
	}

	class LogOutClickHandler implements ClickHandler {

		@Override
		public void onClick(@SuppressWarnings("unused") ClickEvent event) {
			Log.warn("entered LogOutClickHandler onClick");
			if(signInOutHandler != null)
				signInOutHandler.removeHandler();
			Log.warn("user's info: " + loginInfo.isLoggedIn() + " " + loginInfo.getNickname());
//			Log.warn("user have logged-out");
//			signInLink.setHref(loginInfo.getLoginUrl());
			display.getSignInOutButton().setText(signInMessage);
			signInOutHandler = display.getSignInOutButton().addClickHandler(new LogInClickHandler());
			Window.Location.assign(signOutHref);
//			refreshUser();
		}
	}

	void refreshUser() {
		rpcService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			@Override
			public void onFailure(Throwable error) {
				Log.warn("here333" + error.getLocalizedMessage());
				// TODO: add some backup logic
			}

			@Override
			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if (result.isLoggedIn()) {
					if(signInOutHandler != null)
						signInOutHandler.removeHandler();
					Log.warn("user have logged-in");
					signOutHref = loginInfo.getLogoutUrl();
					display.getSignInOutButton().setText(signOutMessage);
					signInOutHandler = display.getSignInOutButton().addClickHandler(new LogOutClickHandler());
				} else {
					if(signInOutHandler != null)
						signInOutHandler.removeHandler();
					Log.warn("user haven't logged-in");
					signInHref = loginInfo.getLoginUrl();
					display.getSignInOutButton().setText(signInMessage);
					signInOutHandler = display.getSignInOutButton().addClickHandler(new LogInClickHandler());
				}
			}
		});
	}

}
