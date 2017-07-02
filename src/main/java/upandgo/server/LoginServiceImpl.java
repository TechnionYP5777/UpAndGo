package upandgo.server;		
		
		
import com.google.appengine.api.users.User;		
import com.google.appengine.api.users.UserService;		
import com.google.appengine.api.users.UserServiceFactory;		
import com.google.gwt.user.server.rpc.RemoteServiceServlet;		
		
import upandgo.client.LoginInfo;		
import upandgo.client.LoginService;		
		
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {		
		
	private static final long serialVersionUID = -0x654969C2AC21B2FCL;		
		
	@Override		
	public LoginInfo login(String requestUri) {		
		UserService userService = UserServiceFactory.getUserService();		
		User user = userService.getCurrentUser();		
		LoginInfo loginInfo = new LoginInfo();		
		
		if (user == null) {
			loginInfo.setLoggedIn(false);
			loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
		} else {
			loginInfo.setLoggedIn(true);
			loginInfo.setEmailAddress(user.getEmail());
			loginInfo.setNickname(user.getNickname());
			loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
		}		
		return loginInfo;		
	}		
}