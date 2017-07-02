package upandgo.server.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.allen_sauer.gwt.log.client.Log;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;

import upandgo.server.CoursesServiceImpl;
import upandgo.shared.entities.Day;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.model.scedule.Color;

public class CalendarModel {
	private static final String calendarName = "Technion's Lessons Schedule";

	private static final String tokenYear = "2017";
	private static final String tokenMonth = "04";
	private static final int tokenDay = 23;
	
	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT = new UrlFetchTransport();

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static AppEngineDataStoreFactory DATA_STORE_FACTORY = AppEngineDataStoreFactory.getDefaultInstance();

	private static String clientSecretJson = "/client_secret.json";
	
	private static GoogleClientSecrets clientSecrets;
	
	private Calendar calendarService;
	
	private String calendarId;
	
	public CalendarModel() {}

	public void createCalendar(List<LessonGroup> gs, Map<String, Color> colorMap) throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		if(user == null) {
			Log.warn("User was not signed in. schedule could not be exported!");
			return;
		}
		
	    Credential credential = newFlow().loadCredential(user.getUserId());
		calendarService = getCalendarService(credential);
		
		// Create a new calendar
		com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
		calendar.setSummary(calendarName);
		calendar.setTimeZone("Universal");
		
		// Insert the new calendar
		com.google.api.services.calendar.model.Calendar createdCalendar = calendarService.calendars().insert(calendar).execute();
		calendarId = createdCalendar.getId();

		user.getEmail();
		for(LessonGroup l: gs)
			if (l != null)
				for (Event ev : createEvents(l, colorMap.get(l.getCourseID())))
					CoursesServiceImpl.someString += "\nEvent created: "
							+ calendarService.events().insert(calendarId, ev).execute().getHtmlLink();
	}

	private static GoogleClientSecrets getClientCredential() throws IOException {
	    if (clientSecrets == null)
			clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
					new InputStreamReader(CalendarModel.class.getResourceAsStream(clientSecretJson)));
	  return clientSecrets;
	}
	
	public static String getRedirectUri(HttpServletRequest r) {
		GenericUrl url = new GenericUrl(r.getRequestURL().toString());
		url.setRawPath("/oauth2callback");
		return url.build();
	}
	
	public static GoogleAuthorizationCodeFlow newFlow() throws IOException {
	    return new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
	        getClientCredential(), Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(
	        DATA_STORE_FACTORY).setAccessType("offline").build();
	}
	
	private static Calendar getCalendarService(Credential c) {
		return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, c).build();
	}
	
	private static List<Event> createEvents(LessonGroup g, @SuppressWarnings("unused") Color c) {
		List<Event> events = new ArrayList<>();
		
		for(Lesson l: g.getLessons())
			if (l != null)
				events.add(new Event().setSummary(l.getCourseId() + "\n" + l.getCourseName())
						.setLocation((l.getPlace() == null) ? "" : l.getPlace() + ", " + l.getRoomNumber())
						.setDescription(String.valueOf(l.getGroup()) + "\n" + l.getType().name() + "\n"
								+ ((l.getRepresenter() == null) ? "" : l.getRepresenter().getFullName()))
						.setStart(new EventDateTime()
								.setDateTime(new DateTime(lessonTimeToRfc(l.getStartTime().getDay(),
										l.getStartTime().getTime().getHour(), l.getStartTime().getTime().getMinute())))
								.setTimeZone("Universal"))
						.setEnd(new EventDateTime()
								.setDateTime(new DateTime(lessonTimeToRfc(l.getEndTime().getDay(),
										l.getEndTime().getTime().getHour(), l.getEndTime().getTime().getMinute())))
								.setTimeZone("Universal"))
						.setRecurrence(Arrays.asList("RRULE:FREQ=WEEKLY")));
		
		return events;
	}
	
	private static String lessonTimeToRfc(Day d, int hour, int minute) {
		String dayStr = String.valueOf(tokenDay + d.ordinal()), hourStr = String.valueOf(hour);
		
		if(hour < 10)
			hourStr = "0"+hourStr;
		
		String minuteStr = String.valueOf(minute);
		if(minute < 10)
			minuteStr = "0"+minuteStr;
		
		return tokenYear+"-"+tokenMonth+"-"+dayStr+"T"+hourStr+":"+minuteStr+":00Z";	// e.g. "1985-04-12T23:20:50.52Z"
	}
	
  /**
   * Returns an {@link IOException} (but not a subclass) in order to work around restrictive GWT
   * serialization policy.
   */
  public static IOException wrappedIOException(IOException x) {
	return x.getClass() == IOException.class ? x : new IOException(x.getMessage());
}
}

















