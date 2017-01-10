package catalog;

import java.util.ArrayList;
import java.util.List;

import model.course.Course;

public class SoftwareEngineering extends CSCatalog {
	public SoftwareEngineering(List<Course> obligatoryList, List<Course> freeChoiceList, List<Course> listAList,
			List<Course> listBList, List<List<Course>> scientificChainList) {
		super(obligatoryList, freeChoiceList, listAList, listBList, scientificChainList);
		// TODO Auto-generated constructor stub
	}

	List<Course> core;
	int corePoints;
	int projectOrSeminarsCount;
	
	static final int REQ_CORE_POINTS = 9;


	

}
