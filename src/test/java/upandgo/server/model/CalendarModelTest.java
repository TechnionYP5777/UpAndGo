package upandgo.server.model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import upandgo.shared.model.scedule.Color;

public class CalendarModelTest {	
	@SuppressWarnings("static-method")
	@Test(expected = IllegalStateException.class)
	public void testCtor() throws IOException {
		CalendarModel model = new CalendarModel();
		model.createCalendar(null, null, null);
	}
	
	@SuppressWarnings("static-method")
	@Test
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
	
	@SuppressWarnings("static-method")
	@Test
	public void testWrappedIOException() {
		assertEquals("hello", CalendarModel.wrappedIOException(new IOException("hello")).getMessage());
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void testNewFlowNotThrowsExceptions() throws IOException {
		@SuppressWarnings("unused")
		GoogleAuthorizationCodeFlow flow = CalendarModel.newFlow();
	}
	
	@SuppressWarnings("static-method")
	@Test
	public void testGetRedirectUri() {
		String testLink = "https://test.com";
		HttpServletRequest httpServReq = Mockito.mock(HttpServletRequest.class);
		Mockito.when(httpServReq.getRequestURL()).thenReturn(new StringBuffer(testLink));
		assertEquals(testLink+"/oauth2callback", CalendarModel.getRedirectUri(httpServReq));
	}
}
