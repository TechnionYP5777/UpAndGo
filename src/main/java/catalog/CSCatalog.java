package catalog;

import java.util.ArrayList;
import java.util.List;

import model.course.Course;

/**
 * 
 * @author Sapir Bismot
 * @since 19-12-2016
 * 
 * Abstract class to represent study catalog for Computer Science.
 * 
 */
public abstract class CSCatalog extends Catalog {
	List<Course> listA, listB;
	int REQ_FACULTY_CHOICE;

	public CSCatalog(final List<Course> obligatoryList, final List<Course> malagsList, final List<Course> listAList,
			final List<Course> listBList, final int reqObligatory, final int reqFreeChoice,
			final int reqFacultyChoice) {
		super(obligatoryList, malagsList, reqObligatory, reqFreeChoice);
		if (listAList == null || listBList == null)
			throw new NullPointerException();
		REQ_FACULTY_CHOICE = reqFacultyChoice;
		listA = new ArrayList<>(listAList);
		listB = new ArrayList<>(listBList);
	}

}
