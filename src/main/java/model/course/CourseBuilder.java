package model.course;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CourseBuilder {
	
	protected String name;
	protected String id;
	protected String faculty;
	protected int points;
	protected LocalDateTime aTerm;
	protected LocalDateTime bTerm;
	
	protected final List<StuffMember> stuff = new ArrayList<>();
	protected final List<Lesson> lessons = new ArrayList<>();

	public CourseBuilder setName(String ¢) {
		this.name = ¢;
		return this;
	}
	
	public CourseBuilder setId(String ¢) {
		this.id = ¢;
		return this;
	}
	
	public CourseBuilder setFaculty(String ¢) {
		this.faculty = ¢;
		return this;
	}
	
	public CourseBuilder setPoints(int ¢) {
		this.points = ¢;
		return this;
	}
	
	public CourseBuilder setATerm(LocalDateTime ¢) {
		this.aTerm = ¢;
		return this;
	}
	
	public CourseBuilder setBTerm(LocalDateTime ¢) {
		this.bTerm = ¢;
		return this;
	}
	
	public CourseBuilder addStuffMember(StuffMember ¢) {
		if(!this.stuff.contains(¢))
			this.stuff.add(¢);
		return this;
	}
	public CourseBuilder addLesson(Lesson ¢) {
		this.lessons.add(¢);
		if(!this.stuff.contains(¢.representer))
			this.stuff.add(¢.representer);
		return this;
	}
	
	
	
	public Course build() {
		Course $ = new Course(this.name, this.id, this.faculty, this.stuff, this.points, this.aTerm, this.bTerm);
		for(Lesson ¢: this.lessons)
			$.addLesson(¢);
		return $;
	}
}
