package catalog;

import java.util.ArrayList;
import java.util.List;

import model.course.Course;

public abstract class Catalog {
	protected final List<Course> obligatory, freeChoice, malags, sports;

	public Catalog(List<Course> obligatoryList, List<Course> freeChoiceList, List<Course> malagsList,
			List<Course> sportsList) {
		if (obligatoryList == null || freeChoiceList == null || malagsList == null || sportsList == null)
			throw new NullPointerException();
		obligatory = new ArrayList<>(obligatoryList);
		freeChoice = new ArrayList<>(freeChoiceList);
		malags = new ArrayList<>(malagsList);
		sports = new ArrayList<>(sportsList);
	}
}
