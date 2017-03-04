package catalog;

import java.util.ArrayList;
import java.util.List;

import model.course.Course;

public abstract class Catalog {
	List<Course> obligatory, malags;
	int REQ_OBLIGATORY_POINTS;
	int REQ_FREE_CHOICE_POINTS;

	public Catalog(final List<Course> obligatoryList, final List<Course> malagsList, final int reqObligatory,
			final int reqFreeChoice) {
		if (obligatoryList == null || malagsList == null)
			throw new NullPointerException();
		obligatory = new ArrayList<>(obligatoryList);
		malags = new ArrayList<>(malagsList);
		REQ_OBLIGATORY_POINTS = reqObligatory;
		REQ_FREE_CHOICE_POINTS = reqFreeChoice;
	}
}
