package upandgo.server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.base.Predicate;
import com.google.gwt.resources.gss.CollectAndRemoveConstantDefinitions;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import upandgo.server.model.loader.CourseLoader;
import upandgo.shared.entities.Faculty;
import upandgo.shared.entities.course.Course;
import upandgo.shared.entities.course.CourseId;

/**
 * 
 * @author Nikita Dizhur
 * @since 30-11-16
 * 
 * Class that is in charge of loading courses and choosing the ones that we want to schedule.
 * 
 */
public class CourseModel { // implements Model {

	protected TreeMap<String, Course> coursesById;
	protected TreeMap<String, Course> coursesByName;
	protected List<Course> pickedCourseList;
	protected CourseLoader loader;
	protected List<Faculty> facultyList;

	public CourseModel(final CourseLoader loader) {
		pickedCourseList = new ArrayList<>();
		this.loader = loader;
		coursesById = loader.loadAllCoursesById();
		coursesByName = loader.loadAllCoursesByName();
		facultyList = loader.loadFaculties();
		for (final String id : this.loader.loadChosenCourseNames())
			pickedCourseList.add(coursesById.get(id));
	}

	public void pickCourse(final String id) {
		if (id == null)
			throw new NullPointerException();
		final Course pickedCourse = getCourseById(id);
		if (pickedCourseList.contains(pickedCourse))
			return;

		// save picking in DB
		final TreeSet<CourseId> pickedList = new TreeSet<>();
		final List<String> pickedIds = new ArrayList<>();
		pickedCourseList.add(pickedCourse);
		pickedCourseList.forEach(new Consumer<Course>() {
			@Override
			public void accept(Course λ) {
				pickedList.add(new CourseId(λ.getId(), λ.getName()));
				pickedIds.add(λ.getId());
			}
		});
	}

	public void addCourse(final String name) {
		if (name == null)
			throw new NullPointerException();
		final List<String> prevCourseList = getCoursesNames();
		coursesById.put(name, loader.loadCourse(name));

		final List<String> curCourseList = new ArrayList<>(prevCourseList);
		curCourseList.remove(name);
	}

	public List<String> getCoursesNames() {
		return new ArrayList<>(coursesById.keySet());
	}

	public void dropCourse(final String id) {
		if (id == null)
			throw new NullPointerException();
		final Course droppedCourse = getCourseById(id);
		if (!pickedCourseList.contains(droppedCourse))
			return;

		// save picking in DB
		final TreeSet<CourseId> pickedList = new TreeSet<>();
		pickedCourseList.remove(droppedCourse);
		pickedCourseList.forEach(new Consumer<Course>() {
			@Override
			public void accept(Course λ) {
				pickedList.add(new CourseId(λ.getId(), λ.getName()));
			}
		});
	}

	public Course getCourseByName(final String name) {
		if (name == null)
			throw new NullPointerException();
		for (final Entry<String, Course> $ : coursesByName.entrySet())
			if (name.equals($.getValue().getName()))
				return $.getValue();
		return null;
	}

	public Course getCourseById(final String id) {
		if (id == null)
			throw new NullPointerException();
		for (final Entry<String, Course> $ : coursesById.entrySet())
			if (id.equals($.getValue().getId()))
				return $.getValue();
		return null;
	}

	public List<String> getChosenCourseNames() {
		final List<String> $ = new ArrayList<>();
		pickedCourseList.forEach(new Consumer<Course>() {
			@Override
			public void accept(Course λ) {
				$.add(λ.getId());
			}
		});
		return $;
	}

	public List<CourseId> loadChosenCourses() {
		// save picking in DB
		final HashSet<CourseId> pickedList = new HashSet<>();
		pickedCourseList.forEach(new Consumer<Course>() {
			@Override
			public void accept(Course λ) {
				pickedList.add(new CourseId(λ.getId(), λ.getName()));
			}
		});
		
		return new ArrayList<>(pickedList);
	}

	public void saveChosenCourses(final List<String> names) {
		loader.saveChosenCourseNames(names);
	}
	
	public List<Course> getPickedCoursesList () {
		return pickedCourseList;
	}
	
	private List<CourseId> getNotSelectedCoursesByFaculty(final String faculty){
		List<CourseId> res = new ArrayList<>();
		for(Map.Entry<String, Course> entry : coursesById.entrySet()){
			Course c = entry.getValue();
			if((faculty.isEmpty() && !pickedCourseList.contains(c)) || (c.getFaculty().equals(faculty)) && !pickedCourseList.contains(c))
				res.add(new CourseId(c.getId(), c.getName()));
			
		}
		return res;
		
	}

	/*
	 * load needed courses from DB if empty, except those that are already chosen.
	 * the returned course are sorted by their fuzzy search score
	 */
	public List<CourseId> loadQueryByFaculty(final String query, final String faculty) {
		if(query.isEmpty())
			return getNotSelectedCoursesByFaculty(faculty);
		
		List<CourseId> relevantCourses = Lists.newArrayList(Collections2.filter(getNotSelectedCoursesByFaculty(faculty), new Predicate<CourseId>() {
			@Override
			public boolean apply(CourseId c) {
				return FuzzySearch.tokenSortPartialRatio(query, c.getTitle()) > 70; 	// we remove courses below a certain score
			}
		}));
		
		Collections.sort(relevantCourses, new Comparator<CourseId>() {
			@Override
			public int compare(CourseId o1, CourseId o2) {
				return FuzzySearch.tokenSortPartialRatio(query, o2.getTitle()) - FuzzySearch.tokenSortPartialRatio(query, o1.getTitle());
			}
			
		});

		return relevantCourses;
	}

	/*
	 * load faculty names
	 */
	public List<String> loadFacultyNames() {
		final TreeSet<String> faculties = new TreeSet<>();
		facultyList.forEach(new Consumer<Faculty>() {
			@Override
			public void accept(Faculty λ) {
				faculties.add(λ.getName());
			}
		});
		
		return new ArrayList<>(faculties);
	}

	public List<Course> loadChosenCoursesDetails() {
		return pickedCourseList;
	}

	public void loadGilaionFrom(@SuppressWarnings("unused") final String path) {
		// TODO: implement it
	}

	public void loadCatalogFrom(@SuppressWarnings("unused") final String path) {
		// TODO: implement it
	}
}
