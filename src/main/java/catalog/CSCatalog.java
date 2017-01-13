package catalog;

import java.util.ArrayList;
import java.util.List;

import model.course.Course;

public abstract class CSCatalog extends Catalog {
	List<Course> listA, listB;
	int REQ_FACULTY_CHOICE;

	public CSCatalog(List<Course> obligatoryList, List<Course> freeChoiceList, List<Course> listAList,
			List<Course> listBList, int reqObligatory, int reqFreeChoice, int reqFacultyChoice) {
		super(obligatoryList, freeChoiceList, reqObligatory, reqFreeChoice);
		if (listAList == null || listBList == null)
			throw new NullPointerException();
		REQ_FACULTY_CHOICE = reqFacultyChoice;
		listA = new ArrayList<>(listAList);
		listB = new ArrayList<>(listBList);
	}

}
