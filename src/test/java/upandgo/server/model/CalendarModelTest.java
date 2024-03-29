package upandgo.server.model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import upandgo.shared.entities.Day;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.LocalTime;
import upandgo.shared.entities.Semester;
import upandgo.shared.entities.StuffMember;
import upandgo.shared.entities.WeekTime;
import upandgo.shared.entities.Lesson.Type;
import upandgo.shared.model.scedule.Color;

public class CalendarModelTest {	
	@Test(expected = IllegalStateException.class)
	@SuppressWarnings("static-method")
	public void testCtor() throws IOException {
		CalendarModel model = new CalendarModel();
		model.createCalendar(null, null, null);
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testColorToColorId() {
		assertEquals("5", CalendarModel.colorToColorId(Color.GOLD));
		assertEquals("7", CalendarModel.colorToColorId(Color.PALETURQUOISE));
		assertEquals("9", CalendarModel.colorToColorId(Color.SLATEBLUE));
		assertEquals("3", CalendarModel.colorToColorId(Color.BLUEVIOLET));
		assertEquals("11", CalendarModel.colorToColorId(Color.BROWN));
		assertEquals("4", CalendarModel.colorToColorId(Color.CORAL));
		assertEquals("10", CalendarModel.colorToColorId(Color.CHARTREUSE));
		assertEquals("1", CalendarModel.colorToColorId(Color.CORNFLOWERBLUE));
		assertEquals("6", CalendarModel.colorToColorId(Color.DARKORANGE));
		assertEquals("2", CalendarModel.colorToColorId(Color.SEAGREEN));
		assertEquals("8", CalendarModel.colorToColorId(Color.DARKSLATEGRAY));
		assertEquals("5", CalendarModel.colorToColorId(Color.GOLDENROD));
		assertEquals("7", CalendarModel.colorToColorId(Color.TURQUOISE));
		assertEquals("9", CalendarModel.colorToColorId(Color.NAVY));
		assertEquals("3", CalendarModel.colorToColorId(Color.FUCHSIA));
		assertEquals("11", CalendarModel.colorToColorId(Color.CRIMSON));
		assertEquals("10", CalendarModel.colorToColorId(Color.LIMEGREEN));
		assertEquals("11", CalendarModel.colorToColorId(Color.ORANGERED));
		assertEquals("10", CalendarModel.colorToColorId(Color.OLIVEDRAB));
	}
	
	static class IOException2 extends IOException {
		private static final long serialVersionUID = 1L;

		IOException2(String s) {
			super(s);
		}
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testWrappedIOException() {
		assertEquals("hello", CalendarModel.wrappedIOException(new IOException("hello")).getMessage());
		assertEquals("hello", CalendarModel.wrappedIOException(new IOException2("hello")).getMessage());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testNewFlowNotThrowsExceptions() throws IOException {
		@SuppressWarnings("unused")
		GoogleAuthorizationCodeFlow flow = CalendarModel.newFlow();
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testGetRedirectUri() {
		String testLink = "https://test.com";
		HttpServletRequest httpServReq = Mockito.mock(HttpServletRequest.class);
		Mockito.when(httpServReq.getRequestURL()).thenReturn(new StringBuffer(testLink));
		assertEquals(testLink + "/oauth2callback", CalendarModel.getRedirectUri(httpServReq));
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testCreateEvents() {
		LessonGroup lg = new LessonGroup(10);
		lg.addLesson(new Lesson(new StuffMember(), new WeekTime(Day.FRIDAY, LocalTime.of(1, 1)),
				new WeekTime(Day.FRIDAY, LocalTime.of(2, 1)), "place 1", Type.LECTURE, 1, "1", "1"));
		lg.addLesson(new Lesson(null, new WeekTime(Day.FRIDAY, LocalTime.of(1, 1)),
				new WeekTime(Day.FRIDAY, LocalTime.of(2, 1)), null, Type.LECTURE, 1, "1", "1"));
		lg.addLesson(null);
		List<Event> events = CalendarModel.createEvents(lg, Color.BLUEVIOLET, Semester.WINTER17);
		assertEquals(2, events.size());
		assertEquals(CalendarModel.colorToColorId(Color.BLUEVIOLET), events.get(0).getColorId());
		assertEquals(CalendarModel.colorToColorId(Color.BLUEVIOLET), events.get(1).getColorId());
		assertEquals("place 1", events.get(0).getLocation());
		assertEquals("", events.get(1).getLocation());
		System.out.println(events.get(0).getStart().getDateTime().toStringRfc3339());
		System.out.println(DateTime.parseRfc3339("2017-10-25T01:01:00.000Z"));
		assertEquals(DateTime.parseRfc3339("2017-10-26T01:01:00.000Z"), events.get(0).getStart().getDateTime());
		assertEquals(DateTime.parseRfc3339("2017-10-26T01:01:00.000Z"), events.get(1).getStart().getDateTime());
		assertEquals(DateTime.parseRfc3339("2017-10-26T02:01:00.000Z"), events.get(0).getEnd().getDateTime());
		assertEquals(DateTime.parseRfc3339("2017-10-26T02:01:00.000Z"), events.get(1).getEnd().getDateTime());
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testLessonStartToRfc() {
		String res = CalendarModel.lessonStartToRfc(Semester.WINTER17.getStartDate(), Day.FRIDAY, 2, 1);
		assertEquals("2017-10-26T02:01:00Z", res);
		res = CalendarModel.lessonStartToRfc(Semester.WINTER17.getStartDate(), Day.FRIDAY, 20, 1);
		assertEquals("2017-10-26T20:01:00Z", res);
		res = CalendarModel.lessonStartToRfc(Semester.WINTER17.getStartDate(), Day.FRIDAY, 2, 10);
		assertEquals("2017-10-26T02:10:00Z", res);
		res = CalendarModel.lessonStartToRfc(Semester.WINTER17.getStartDate(), Day.FRIDAY, 20, 10);
		assertEquals("2017-10-26T20:10:00Z", res);
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testGetRecurrenceRule() {
		String res = CalendarModel.getRecurrenceRule("01/01/2000");
		assertEquals("RRULE:FREQ=WEEKLY;UNTIL=20000101T235959Z", res);
	}
}
