package catalog;

import java.util.ArrayList;
import java.util.List;

import model.course.Course;

public class CSCatalog extends Catalog {
	List<Course> listA, listB, scientificChain1, scientificChain2;

	public CSCatalog(List<Course> obligatoryList, List<Course> freeChoiceList, List<Course> malagsList,
			List<Course> sportsList, List<Course> listAList, List<Course> listBList, List<Course> scientificChain1List,
			List<Course> scientificChain2List) {
		super(obligatoryList, freeChoiceList, malagsList, sportsList);
		if (listAList == null || listBList == null || scientificChain1List == null || scientificChain2List == null)
			throw new NullPointerException();
		listA = new ArrayList<>(listAList);
		listB = new ArrayList<>(listBList);
		scientificChain1 = new ArrayList<>(scientificChain1List);
		scientificChain2 = new ArrayList<>(scientificChain2List);
	}

}
