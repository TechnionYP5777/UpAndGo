package catalog;

import java.util.ArrayList;
import java.util.List;

import model.course.Course;

/**
 * 
 * @author Sapir Bismot
 * @since 10-01-2017
 * 
 * Class to represent study catalog for Software Engineering.
 */
public class SoftwareEngineering extends CSCatalog {
	List<Course> core;
	List<List<Course>> scientificChain;

	static final int CORE_POINTS = 9;
	static final int SCIENT_POINTS = 8;
	static final int OBLIGATORY_POINTS = 108;
	static final int MIN_LISTA_POINTS = 15;
	static final double TOTAL_FACULTY_CHOICE_POINTS = 25.5;
	static final int FREE_CHOICE_POINTS = 10;

	public SoftwareEngineering(final List<Course> obligatoryList, final List<Course> malagsList,
			final List<Course> listAList, final List<Course> listBList, final List<Course> coreList,
			final List<List<Course>> scientChainLists) {
		super(obligatoryList, malagsList, listAList, listBList, OBLIGATORY_POINTS, FREE_CHOICE_POINTS,
				MIN_LISTA_POINTS);
		if (coreList == null || scientChainLists == null)
			throw new NullPointerException();
		core = new ArrayList<>(coreList);
		scientificChain = new ArrayList<>(scientChainLists);
	}
}
