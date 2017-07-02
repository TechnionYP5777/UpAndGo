/*		
 * Copyright (c) 2010 Google Inc.		
 * 		
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except		
 * in compliance with the License. You may obtain a copy of the License at		
 * 		
 * http://www.apache.org/licenses/LICENSE-2.0		
 * 		
 * Unless required by applicable law or agreed to in writing, software distributed under the License		
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express		
 * or implied. See the License for the specific language governing permissions and limitations under		
 * the License.		
 */		
		
package upandgo.server;		
		
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;		
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;		
import com.google.api.client.auth.oauth2.Credential;		
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeCallbackServlet;		
import com.google.appengine.api.users.UserServiceFactory;		
		
import java.io.IOException;		
		
import javax.servlet.ServletException;		
import javax.servlet.http.HttpServletRequest;		
import javax.servlet.http.HttpServletResponse;		
		
/**		
 * HTTP servlet to process access granted from user.		
 * 		
 * @author Yaniv Inbar		
 */		
		
public class OAuth2Callback extends AbstractAppEngineAuthorizationCodeCallbackServlet {		
		
	  private static final long serialVersionUID = 1;		
		
	  @Override
	@SuppressWarnings("unused")
	protected void onSuccess(HttpServletRequest r, HttpServletResponse resp, Credential c)
			throws ServletException, IOException {
		  // Do nothing
	}		
		
	  @Override
	@SuppressWarnings("unused")
	protected void onError(HttpServletRequest r, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
			throws ServletException, IOException {
		String nickname = UserServiceFactory.getUserService().getCurrentUser().getNickname();
		resp.getWriter().print("<h3>" + nickname + ", why don't you want to play with me?</h1>");
		resp.setStatus(200);
		resp.addHeader("Content-Type", "text/html");
	}		
		
	  @Override
	@SuppressWarnings("unused")
	protected String getRedirectUri(HttpServletRequest r) throws ServletException, IOException {
		return upandgo.server.model.CalendarModel.getRedirectUri(r);
	}		
		
	  @Override		
	  protected AuthorizationCodeFlow initializeFlow() throws IOException {		
	    return upandgo.server.model.CalendarModel.newFlow();		
	  }		
	}