package catalog;

import java.util.ArrayList;
import java.util.List;

import model.course.Course;

public class SoftwareEngineering extends CSCatalog {
	List<Course> core;
	List<List<Course>> scientificChain;

	static final int CORE_POINTS = 9;
	static final int SCIENT_POINTS = 8;
	static final int OBLIGATORY_POINTS = 108;
	static final int MIN_LISTA_POINTS = 15;
	static final double TOTAL_FACULTY_CHOICE_POINTS = 25.5;
	static final int FREE_CHOICE_POINTS = 10;

	public SoftwareEngineering(List<Course> obligatoryList, List<Course> freeChoiceList,
			List<List<Course>> facultyChoiceList, List<Course> listAList, List<Course> listBList) {
		super(obligatoryList, freeChoiceList, facultyChoiceList, listAList, listBList, OBLIGATORY_POINTS,
				FREE_CHOICE_POINTS, MIN_LISTA_POINTS);
		// TODO Auto-generated constructor stub
	}

}
