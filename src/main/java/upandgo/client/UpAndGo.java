package upandgo.client;

import com.allen_sauer.gwt.log.client.Log;
/**
 * 
 * @author danabra
 * @since 02-05-17
 * 
 * THis code is first to execute when the application is loaded
 * 
 */
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UpAndGo implements EntryPoint {

	LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Please sign in to your Google Account to access the UpAndGo application.");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");

	@Override
	public void onModuleLoad() {
		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		Log.warn("here2222");
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			@Override
			public void onFailure(Throwable error) {
				Log.warn("here333"+error.getLocalizedMessage());
				//TODO: add some backup logic
			}

			@Override
			public void onSuccess(LoginInfo result) {
				Log.warn("here1111");
				loginInfo = result;
				if (loginInfo.isLoggedIn()) {
					loadUpAndGo();
				} else {
					loadLogin();
				}
			}
		});
	}

	void loadUpAndGo() {
	    // Set up sign out hyperlink.
	    signOutLink.setHref(loginInfo.getLogoutUrl());
		RootLayoutPanel.get().add(signOutLink);
		
		final Injector injector = Injector.INSTANCE;

		AppController appViewer = new AppController((CoursesServiceAsync) GWT.create(CoursesService.class),
				injector.getEventBus());

		appViewer.go(RootLayoutPanel.get());
	}

	void loadLogin() {
		// Assemble login panel.
		Log.warn("here1111");
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootLayoutPanel.get().add(loginPanel);
	}
}
