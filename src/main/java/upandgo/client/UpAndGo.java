package upandgo.client;

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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UpAndGo implements EntryPoint {

	LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Please sign in to your Google Account to access the UpAndGo application.");
	private Anchor signInLink = new Anchor("Sign In");

	@Override
	public void onModuleLoad() {
		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			@Override
			public void onFailure(@SuppressWarnings("unused") Throwable error) {
				//TODO: add some backup logic
			}

			@Override
			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if (loginInfo.isLoggedIn()) {
					loadUpAndGo();
				} else {
					loadLogin();
				}
			}
		});
	}

	@SuppressWarnings("static-method") void loadUpAndGo() {
		final Injector injector = Injector.INSTANCE;

		AppController appViewer = new AppController((CoursesServiceAsync) GWT.create(CoursesService.class),
				injector.getEventBus());

		appViewer.go(RootLayoutPanel.get());
	}

	void loadLogin() {
		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("stockList").add(loginPanel);
	}
}
