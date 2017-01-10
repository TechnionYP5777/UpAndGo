package catalog;

import java.util.ArrayList;
import java.util.List;

import model.course.Course;

public abstract class CSCatalog extends Catalog {
	List<Course> listA, listB;
	List<List<Course>> scientificChain;
	
	int listAPoints, listBPoints, scienChainPoints;
	
	static final int REQ_SCIEN_CHAIN_POINTS = 8;
	int REQ_LISTA_POINTS, REQ_LISTB_POINTS;
	
	public CSCatalog(List<Course> obligatoryList, List<Course> freeChoiceList, List<Course> listAList, List<Course> listBList, List<List<Course>> scientificChainList) {
		super(obligatoryList, freeChoiceList, 0, 0);
		if (listAList == null || listBList == null || scientificChainList == null)
			throw new NullPointerException();
		listA = new ArrayList<>(listAList);
		listB = new ArrayList<>(listBList);
		scientificChain = new ArrayList<>(scientificChainList);
	}

}
