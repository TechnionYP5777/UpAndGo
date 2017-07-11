package upandgo.server.model.datastore;

public interface Datastore {
	public void saveChosenCourses(CoursesEntity courseEntity);

	public CoursesEntity loadChosenCourses();

	public void saveChosenLessonGroups(ScheduleEntity scheduleEntity);

	public ScheduleEntity loadChosenLessonGroups();
	
	public void saveUserEvents(final EventsEntity eventsEntity);
	
	public EventsEntity loadUserEvents();
}
