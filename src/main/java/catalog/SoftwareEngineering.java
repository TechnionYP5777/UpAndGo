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

	public SoftwareEngineering(List<Course> obligatoryList, List<Course> malagsList, List<Course> listAList,
			List<Course> listBList, List<Course> coreList, List<List<Course>> scientChainLists) {
		super(obligatoryList, malagsList, listAList, listBList, OBLIGATORY_POINTS,
				FREE_CHOICE_POINTS, MIN_LISTA_POINTS);
		if (coreList == null || scientChainLists == null)
			throw new NullPointerException();
		core = new ArrayList<>(coreList);
		scientificChain = new ArrayList<>(scientChainLists);
	}
}
