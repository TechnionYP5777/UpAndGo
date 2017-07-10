package upandgo.client.resources;

import com.google.gwt.i18n.client.Constants;

public interface AppConstants extends Constants {
	@Key("version")
	String version();
	@Key("artifactId")
	String artifactId();
	@Key("appengineAppId")
	String appengineAppId();
}