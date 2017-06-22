package upandgo.shared.entities;

import upandgo.shared.entities.Lesson.Type;

public class UserEvent {

	private WeekTime time;
	private String description;
	private LocalTime duration;
	
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
		return new Lesson(null,time,time.addDuration(duration),description,Type.LECTURE,10,"999999","user events");
	}
	
	
	
}
