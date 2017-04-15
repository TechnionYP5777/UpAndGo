package upandgo.server.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.common.collect.HashMultimap;

import upandgo.server.model.loader.CourseLoader;
import upandgo.shared.entities.Faculty;
import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;
import upandgo.shared.property.CourseProperty;

/**
 * 
 * @author Nikita Dizhur
 * @since 30-11-16
 * 
 * Class that is in charge of loading courses and choosing the ones that we want to schedule.
 * 
 */
public class CourseModel implements Model {

	protected TreeMap<String, Course> coursesById;
	protected TreeMap<String, Course> coursesByName;
	protected List<Course> pickedCourseList;
	protected HashMultimap<String, PropertyChangeListener> listenersMap;
	protected CourseLoader loader;
	protected List<Faculty> facultyList;

	public CourseModel(final CourseLoader loader) {
		pickedCourseList = new ArrayList<>();
		listenersMap = HashMultimap.create();
		this.loader = loader;
		coursesById = loader.loadAllCoursesById();
		coursesByName = loader.loadAllCoursesByName();
		facultyList = loader.loadFaculties();
		for (final String id : this.loader.loadChosenCourseNames())
			pickedCourseList.add(coursesById.get(id));
	}

	public void pickCourse(final String name) {
		if (name == null)
			throw new NullPointerException();
		final Course pickedCourse = getCourseById(name);
		if (pickedCourseList.contains(pickedCourse))
			return;

		// save picking in DB
		final TreeSet<CourseId> pickedList = new TreeSet<>();
		final List<String> pickedIds = new ArrayList<>();
		pickedCourseList.add(pickedCourse);
		pickedCourseList.forEach(λ -> {
			pickedList.add(new CourseId(λ.getId(), λ.getName()));
			pickedIds.add(λ.getId());
		});

		// notify listeners
		listenersMap.get(CourseProperty.CHOSEN_LIST).forEach(λ -> λ.propertyChange(
				new PropertyChangeEvent(this, CourseProperty.CHOSEN_LIST, null, new ArrayList<>(pickedList))));
	}

	public void addCourse(final String name) {
		if (name == null)
			throw new NullPointerException();
		final List<String> prevCourseList = getCoursesNames();
		coursesById.put(name, loader.loadCourse(name));

		final List<String> curCourseList = new ArrayList<>(prevCourseList);
		curCourseList.remove(name);

		// notify listeners
		listenersMap.get(CourseProperty.CHOSEN_LIST).forEach(λ -> λ.propertyChange(
				new PropertyChangeEvent(this, CourseProperty.COURSE_LIST, prevCourseList, curCourseList)));
	}

	public List<String> getCoursesNames() {
		return new ArrayList<>(coursesById.keySet());
	}

	public void dropCourse(final String name) {
		if (name == null)
			throw new NullPointerException();
		final Course droppedCourse = getCourseById(name);
		if (!pickedCourseList.contains(droppedCourse))
			return;

		// save picking in DB
		final TreeSet<CourseId> pickedList = new TreeSet<>();
		pickedCourseList.remove(droppedCourse);
		pickedCourseList.forEach(λ -> pickedList.add(new CourseId(λ.getId(), λ.getName())));

		// notify listeners
		listenersMap.get(CourseProperty.CHOSEN_LIST).forEach(λ -> λ.propertyChange(
				new PropertyChangeEvent(this, CourseProperty.CHOSEN_LIST, null, new ArrayList<>(pickedList))));
	}

	/*
	 * expose course to listeners for "course_list" property
	 */
	public void exposeCourse(final String name) {
		if (name == null)
			throw new NullPointerException();
		listenersMap.get(CourseProperty.DETAILS).forEach(λ -> λ
				.propertyChange(new PropertyChangeEvent(this, CourseProperty.DETAILS, null, getCourseById(name))));
	}

	public Course getCourseByName(final String name) {
		if (name == null)
			throw new NullPointerException();
		for (final Entry<String, Course> $ : coursesById.entrySet())
			if (name.equals($.getValue().getName()))
				return $.getValue();
		return null;
	}

	public Course getCourseById(final String name) {
		if (name == null)
			throw new NullPointerException();
		for (final Entry<String, Course> $ : coursesById.entrySet())
			if (name.equals($.getValue().getId()))
				return $.getValue();
		return null;
	}

	public List<String> getChosenCourseNames() {
		final List<String> $ = new ArrayList<>();
		pickedCourseList.forEach(λ -> $.add(λ.getId()));
		return $;
	}

	public void loadChosenCourses() {
		// save picking in DB
		final HashSet<CourseId> pickedList = new HashSet<>();
		pickedCourseList.forEach(λ -> pickedList.add(new CourseId(λ.getId(), λ.getName())));

		// notify listeners
		listenersMap.get(CourseProperty.CHOSEN_LIST).forEach(λ -> λ.propertyChange(
				new PropertyChangeEvent(this, CourseProperty.CHOSEN_LIST, null, new ArrayList<>(pickedList))));
	}

	public void saveChosenCourses(final List<String> names) {
		loader.saveChosenCourseNames(names);
	}

	/*
	 * load needed courses (by name / subname) from DB if empty, load all of
	 * them
	 */
	public void loadQuery(final String query) {
		final TreeSet<CourseId> matchingIds = new TreeSet<>();
		if (query.isEmpty())
			coursesById.forEach((key, course) -> matchingIds.add(new CourseId(course.getId(), course.getName())));
		else {
			coursesById.forEach((key, course) -> {
				if (key.contains(query))
					matchingIds.add(new CourseId(course.getId(), course.getName()));
			});
			coursesByName.forEach((key, course) -> {
				if (key.toLowerCase().contains(query.toLowerCase()))
					matchingIds.add(new CourseId(course.getId(), course.getName()));
			});
		}

		listenersMap.get(CourseProperty.COURSE_LIST).forEach(λ -> λ.propertyChange(
				new PropertyChangeEvent(this, CourseProperty.COURSE_LIST, null, new ArrayList<>(matchingIds))));
	}

	/*
	 * load needed courses (by name / subname) from DB if empty, load all of
	 * them
	 */
	public void loadQueryByFaculty(final String query, final String faculty) {
		final TreeSet<CourseId> matchingIds = new TreeSet<>();
		coursesById.forEach(query.isEmpty() ? (key, course) -> {
			if (course.getFaculty().equals(faculty))
				matchingIds.add(new CourseId(course.getId(), course.getName()));
		} : (key, course) -> {
			if (key.toLowerCase().contains(query.toLowerCase()) && course.getFaculty().equals(faculty))
				matchingIds.add(new CourseId(course.getId(), course.getName()));
		});
		listenersMap.get(CourseProperty.COURSE_LIST).forEach(λ -> λ.propertyChange(
				new PropertyChangeEvent(this, CourseProperty.COURSE_LIST, null, new ArrayList<>(matchingIds))));
	}

	/*
	 * load faculty names
	 */
	public void loadFacultyNames() {
		final TreeSet<String> faculties = new TreeSet<>();
		facultyList.forEach(λ -> faculties.add(λ.getName()));
		listenersMap.get(CourseProperty.FACULTY_LIST).forEach(λ -> λ.propertyChange(
				new PropertyChangeEvent(this, CourseProperty.FACULTY_LIST, null, new ArrayList<>(faculties))));
	}

	public void loadChosenCoursesDetails() {
		listenersMap.get(CourseProperty.CHOSEN_LIST_DETAILS).forEach(λ -> λ.propertyChange(
				new PropertyChangeEvent(this, CourseProperty.CHOSEN_LIST_DETAILS, null, pickedCourseList)));
	}

	public void loadGilaionFrom(@SuppressWarnings("unused") final String path) {
		// TODO: implement it
	}

	public void loadCatalogFrom(@SuppressWarnings("unused") final String path) {
		// TODO: implement it
	}

	@Override
	public void addPropertyChangeListener(final String property, final PropertyChangeListener l) {
		if (property == null || l == null)
			throw new NullPointerException();
		listenersMap.put(property, l);
	}

	@Override
	public void removePropertyChangeListener(final String property, final PropertyChangeListener l) {
		if (property != null && l != null && listenersMap.containsKey(property))
			listenersMap.remove(property, l);

	}
}
