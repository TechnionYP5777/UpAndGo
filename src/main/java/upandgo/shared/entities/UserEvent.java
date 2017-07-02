package upandgo.shared.entities;

import java.util.Objects;

import com.google.gwt.user.client.rpc.IsSerializable;

import upandgo.shared.entities.Lesson.Type;

public class UserEvent implements IsSerializable{

	private WeekTime time;
	private String description;
	private LocalTime duration;
	
	public UserEvent() {
		this.time = null;
		this.description = "";
		this.duration = null;
	}
	
	public UserEvent(WeekTime time, String description, LocalTime duration) {
		this.time = time;
		this.description = description;
		this.duration = duration;
	}

	public WeekTime getWeekTime() {
		return time;
	}

	public void setTime(WeekTime time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalTime getDuration() {
		return duration;
	}

	public void setDuration(LocalTime duration) {
		this.duration = duration;
	}


	public Lesson getAsLesson(){
		return new Lesson(null,time,time.addDuration(duration),description,Type.LECTURE,999,"999999","user events");
	}
	
	@Override
	public boolean equals(final Object d) {
		return d instanceof UserEvent && time.equals(((UserEvent) d).getWeekTime())
				&& description.equals(((UserEvent) d).getDescription())
				&& duration.equals(((UserEvent) d).getDuration());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(time);
	}
	
}
