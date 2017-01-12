package catalog;

import java.util.ArrayList;
import java.util.List;

import model.course.Course;

public abstract class Catalog {
	protected final List<Course> obligatory, freeChoice;
	int REQ_OBLIGATORY_POINTS;
	int REQ_FREE_CHOICE_POINTS;

	public Catalog(List<Course> obligatoryList, List<Course> freeChoiceList, int reqObligatory, int reqFreeChoice) {
		if (obligatoryList == null || freeChoiceList == null)
			throw new NullPointerException();
		obligatory = new ArrayList<>(obligatoryList);
		freeChoice = new ArrayList<>(freeChoiceList);
		REQ_OBLIGATORY_POINTS = reqObligatory;
		REQ_FREE_CHOICE_POINTS = reqFreeChoice;
	}
}
