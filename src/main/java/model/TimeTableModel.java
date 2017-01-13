package model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import model.course.Course;

public class TimeTableModel implements Model {

	List<Course> courses = new ArrayList<>();
	
	public TimeTableModel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addPropertyChangeListener(@SuppressWarnings("unused") String property, @SuppressWarnings("unused") PropertyChangeListener __) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removePropertyChangeListener(@SuppressWarnings("unused") String property, @SuppressWarnings("unused") PropertyChangeListener __) {
		// TODO Auto-generated method stub

	}
	
	public void setCourses(List<Course> ¢) {
		courses = new ArrayList<>(¢);
	}

}
