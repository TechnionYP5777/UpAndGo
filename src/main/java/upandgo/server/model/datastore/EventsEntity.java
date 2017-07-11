package upandgo.server.model.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import upandgo.shared.entities.Day;
import upandgo.shared.entities.LocalTime;
import upandgo.shared.entities.UserEvent;
import upandgo.shared.entities.WeekTime;

@Entity
public class EventsEntity {
	public static class Event {

		public String description;
		public String day;
		public int startHour;
		public int startMinute;
		public int durationHour;
		public int durationMinute;
		
		public Event() {
			this.description = "";
			this.day = "";
			this.startHour = 0;
			this.startMinute = 0;
			this.durationHour = 0;
			this.durationMinute = 0;
		}
		
		public Event(String description, String day, int startHour, int startMinute, int durationHour,
				int dueationMinute) {
			this.description = description;
			this.day = day;
			this.startHour = startHour;
			this.startMinute = startMinute;
			this.durationHour = durationHour;
			this.durationMinute = dueationMinute;
		}
		
		public Event(UserEvent userEvent) {
			this.description = userEvent.getDescription();
			this.day = userEvent.getWeekTime().getDay().toLetter();
			this.startHour = userEvent.getWeekTime().getTime().getHour();
			this.startMinute = userEvent.getWeekTime().getTime().getMinute();
			this.durationHour = userEvent.getDuration().getHour();
			this.durationMinute = userEvent.getDuration().getMinute();
		}
		
		public UserEvent getAsUserEvent(){
			return new UserEvent(new WeekTime(Day.fromLetter(day),LocalTime.of(startHour, startMinute)),
					description, LocalTime.of(durationHour, durationMinute));
		}

		public String getDescription() {
			return description;
		}

		public String getDay() {
			return day;
		}

		public int getStartHour() {
			return startHour;
		}

		public int getStartMinute() {
			return startMinute;
		}

		public int getDurationHour() {
			return durationHour;
		}

		public int getDurationMinute() {
			return durationMinute;
		}
		
	}
	
	@Id public String id;	// user's userId
	public Map<String,List<Event>> events;
	
	public EventsEntity() {
		id = "";
		events = new TreeMap<>();
	}
	
	public EventsEntity(String id) {
		this.id = id;
		this.events = new TreeMap<>();
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public List<Event> getEvents(String semesterId){
		if (events.containsKey(semesterId)){
			return events.get(semesterId);
		}
		return new ArrayList<>();
	}
	
	public void addEvent(String semesterId, UserEvent userEvent){
		Event newEvent = new Event(userEvent);
		if (events.containsKey(semesterId)){
			events.get(semesterId).add(newEvent);
		}
		else{
			List<Event> eventsList = new ArrayList<>();
			eventsList.add(newEvent);
			events.put(semesterId, eventsList);
		}
	}
	
	public void removeAllEvents(String semesterId){
		if (events.containsKey(semesterId)){
			events.put(semesterId,new ArrayList<Event>());
		}
	}
	
}
