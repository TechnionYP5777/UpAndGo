package upandgo.server.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import upandgo.shared.entities.Day;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.LessonGroup;

public class CalendarModel {
	private static final String calendarName = "Technion's Lessons Schedule";
	private static final String applicationName = "UpAndGo";

	private static final String tokenYear = "2017";
	private static final String tokenMonth = "04";
	private static final int tokenDay = 23;
	
	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/" + applicationName);

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	private static String clientSecretJson = "/client_secret_421422781135-a7id4ou240j7q491fb1eq8e1vm9i2vk6.apps.googleusercontent.com.json";
	
	private Credential userCredential = null;
	private Calendar calendarService = null;
	
	public CalendarModel() {}

	public void deleteCalendarIfExists() {
		if(userCredential == null) {
			userCredential = getCredentials();
			calendarService = getCalendarService(userCredential);
		}

		// Delete a calendar list entry
		try {
			calendarService.calendarList().delete(calendarName).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createCalendar(List<LessonGroup> lessons) {
		try {
			if(userCredential == null) {
				userCredential = getCredentials();
				calendarService = getCalendarService(userCredential);
			}
			
			// Create a new calendar list entry
			CalendarListEntry calendarListEntry = new CalendarListEntry();
			calendarListEntry.setId(calendarName);

			// Insert the new calendar list entry
			CalendarListEntry createdCalendarListEntry;
			createdCalendarListEntry = calendarService.calendarList().insert(calendarListEntry).execute();
			System.out.println(createdCalendarListEntry.getSummary());
			
			for(LessonGroup l: lessons) {
				for(Event ev: createEvents(l)) {
					String userEmail = UserServiceFactory.getUserService().getCurrentUser().getEmail();
					ev.setAttendees(Arrays.asList(new EventAttendee().setEmail(userEmail)));
					Event res = calendarService.events().insert(calendarName, ev).execute();
					System.out.printf("Event created: %s\n", res.getHtmlLink());
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Credential getCredentials() {
		// Load client secrets.
		InputStream in = CalendarModel.class.getResourceAsStream(clientSecretJson);
		try(InputStreamReader isr = new InputStreamReader(in)) {
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, isr);

			// Build flow and trigger user authorization request.
			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
					clientSecrets, Collections.singleton(CalendarScopes.CALENDAR))
							.setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
	
			// Get userId to get credentials.
			String userId = UserServiceFactory.getUserService().getCurrentUser().getUserId();
			return flow.loadCredential(userId);
		} catch (IOException e) {
			userCredential = null;
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Calendar getCalendarService(Credential cred) {
		// Initialize Calendar service with valid OAuth credentials
		// Calendar calendar = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, userCredential).setApplicationName(applicationName).build();
		return calendarService = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred).build();
	}
	
	private static List<Event> createEvents(LessonGroup lg) {
		List<Event> events = new ArrayList<>();
			
		for(Lesson l: lg.getLessons()) {
			String startTimeStr =
					lessonTimeToRfc(l.getStartTime().getDay(), l.getStartTime().getTime().getHour(), l.getStartTime().getTime().getMinute());
			EventDateTime startTime = new EventDateTime().setDateTime(new DateTime(startTimeStr)).setTimeZone("Israel");
			String endTimeStr =
					lessonTimeToRfc(l.getEndTime().getDay(), l.getEndTime().getTime().getHour(), l.getEndTime().getTime().getMinute());
			EventDateTime endTime = new EventDateTime().setDateTime(new DateTime(endTimeStr)).setTimeZone("Israel");
			
			//create event:
			Event event = new Event()
					.setSummary(l.getCourseId()+"\n"+l.getCourseName())
					.setLocation(l.getPlace()+", "+l.getRoomNumber())
				    .setDescription(String.valueOf(l.getGroup())+"\n"+l.getType().name()+"\n"+l.getRepresenter().getFullName())
				    .setStart(startTime).setEnd(endTime)
				    .setRecurrence(Arrays.asList("RRULE:FREQ=WEEKLY"));

			events.add(event);
		}
		
		return events;
	}
	
	private static String lessonTimeToRfc(Day day, int hour, int minute) {
		// TODO: it's just a workaround. need smth better:
		String dayStr = String.valueOf(tokenDay + day.ordinal());
		
		String hourStr = String.valueOf(hour);
		if(hour < 10)
			hourStr = "0"+hourStr;
		
		String minuteStr = String.valueOf(minute);
		if(hour < 10)
			minuteStr = "0"+minuteStr;
		
		return tokenYear+"-"+tokenMonth+"-"+dayStr+"T"+hourStr+":"+minuteStr+":00Z";	// e.g. "1985-04-12T23:20:50.52Z"
	}
	
}


















