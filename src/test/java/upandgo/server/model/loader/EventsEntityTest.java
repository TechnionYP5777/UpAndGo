package upandgo.server.model.loader;

import static org.junit.Assert.*;

import org.junit.Test;

import upandgo.shared.entities.Day;
import upandgo.shared.entities.LocalTime;
import upandgo.shared.entities.UserEvent;
import upandgo.shared.entities.WeekTime;

public class EventsEntityTest {

	@Test
	public void test_a() {
		EventsEntity eventEntity = new EventsEntity();
		assertEquals("", eventEntity.id);
		assertEquals(0, eventEntity.events.size());
		EventsEntity.Event event = new EventsEntity.Event();
		assertEquals("", event.getDescription());
		assertEquals("", event.getDay());
		assertEquals(0, event.getStartHour());
		assertEquals(0, event.getStartMinute());
		assertEquals(0, event.getDurationHour());
		assertEquals(0, event.getDurationMinute());
		
		EventsEntity.Event event2 = new EventsEntity.Event("test2", "א", 8, 30, 1, 30);
		eventEntity.setId("999999");
		assertEquals("999999", eventEntity.id);
		UserEvent userEvent2 = event2.getAsUserEvent();
		assertEquals("test2", userEvent2.getDescription());

		eventEntity = new EventsEntity("888888");
		assertEquals("888888", eventEntity.id);
		eventEntity.addEvent("201602", new UserEvent(new WeekTime(Day.SUNDAY,LocalTime.of(9, 0)),
				"test", LocalTime.of(1, 30)));
		eventEntity.addEvent("201602", new UserEvent(new WeekTime(Day.THURSDAY,LocalTime.of(9, 0)),
				"test", LocalTime.of(1, 30)));
		assertEquals(2, eventEntity.getEvents("201602").size());
		eventEntity.removeAllEvents("201602");
		assertEquals(0, eventEntity.getEvents("201602").size());


	}
	
}
