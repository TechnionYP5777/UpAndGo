package upandgo.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import upandgo.shared.entities.LoginInfo;

public interface LoginServiceAsync {
	
  public void login(String requestUri, AsyncCallback<LoginInfo> async);
  
}
