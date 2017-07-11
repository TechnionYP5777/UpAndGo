package upandgo.server.model.datastore;

import com.allen_sauer.gwt.log.client.Log;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import upandgo.server.CoursesServiceImpl;

public class GoogleDatastore implements Datastore{

	public GoogleDatastore(){
		
	}
	
	@Override
	public void saveChosenCourses(CoursesEntity courseEntity) {
		User user = UserServiceFactory.getUserService().getCurrentUser();
		
		if(user == null) {
			Log.warn("GoogleDatastore: User is not signed in. selected courses could not be saved!");
			return;
		}
		
		courseEntity.setId(user.getUserId());
		CoursesServiceImpl.ofy().defer().save().entity(courseEntity);		
	}

	@Override
	public CoursesEntity loadChosenCourses() {
		User user = UserServiceFactory.getUserService().getCurrentUser();

		if(user == null) {
			Log.warn("GoogleDatastore: User is not signed in. selected courses could not be loaded!");
			return null;
		}

		CoursesEntity courseEntity = CoursesServiceImpl.ofy().load().type(CoursesEntity.class).id(user.getUserId()).now();
		return courseEntity != null ? courseEntity : new CoursesEntity(user.getUserId());
	}

	@Override
	public void saveChosenLessonGroups(ScheduleEntity scheduleEntity) {
		User user = UserServiceFactory.getUserService().getCurrentUser();
		
		if(user == null) {
			Log.warn("GoogleDatastore: User is not signed in. schedule could not be saved!");
			return;
		}
		
		scheduleEntity.setId(user.getUserId());
		CoursesServiceImpl.ofy().defer().save().entity(scheduleEntity);
	}

	@Override
	public ScheduleEntity loadChosenLessonGroups() {
		User user = UserServiceFactory.getUserService().getCurrentUser();

		if(user == null) {
			Log.warn("GoogleDatastore: User is not signed in. schedule could not be loaded!");
			return null;
		}
		
		ScheduleEntity scheduleEntity = CoursesServiceImpl.ofy().load().type(ScheduleEntity.class).id(user.getUserId()).now();
		return scheduleEntity != null ? scheduleEntity : new ScheduleEntity(user.getUserId());
	}

	@Override
	public void saveUserEvents(EventsEntity eventsEntity) {
		User user = UserServiceFactory.getUserService().getCurrentUser();
		
		if(user == null) {
			Log.warn("GoogleDatastore: User is not signed in. user events could not be saved!");
			return;
		}
		
		eventsEntity.setId(user.getUserId());
		CoursesServiceImpl.ofy().defer().save().entity(eventsEntity);		
	}

	@Override
	public EventsEntity loadUserEvents() {
		User user = UserServiceFactory.getUserService().getCurrentUser();

		if(user == null) {
			Log.warn("GoogleDatastore: User is not signed in. user events could not be loaded!");
			return null;
		}
		
		EventsEntity eventsEntity = CoursesServiceImpl.ofy().load().type(EventsEntity.class).id(user.getUserId()).now();
		return eventsEntity != null ? eventsEntity : new EventsEntity(user.getUserId());
	}

}
