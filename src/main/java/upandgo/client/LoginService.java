package upandgo.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import upandgo.shared.entities.LoginInfo;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
	
  public LoginInfo login(String requestUri);
  
}
