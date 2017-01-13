/**
 * 
 */
package catalog.loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.CourseModel;
import model.course.Course;
import catalog.*;

/**
 * 
 * @author sapir
 * @since Jan 13, 2017
 */
public class SECatalogLoader extends CSCatalogLoader {
	List<Course> core;
	List<List<Course>> scientificChain;
	SoftwareEngineering theCatalog;

	SECatalogLoader(String catalogXmlPath, CourseModel m) {
		super(catalogXmlPath, m);
		core = new ArrayList<>();
		scientificChain = new ArrayList<>();
		List<Course> physicsChain = new ArrayList<>();
		List<Course> chemistryChain1 = new ArrayList<>();
		List<Course> biologyChain = new ArrayList<>();
		List<Course> chemistryChain2 = new ArrayList<>();
		List<Course> otherScientCourses = new ArrayList<>();
		try {
			NodeList coursesList = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(catalogXmlPath)
					.getElementsByTagName("CourseList");
			for (int i = 0; i < coursesList.getLength(); ++i) {
				Element elem = (Element) coursesList.item(i);
				String listName = elem.getAttribute("name");
				if ("מקצועות ליבה".equals(listName))
					addCoursesToList(elem.getElementsByTagName("Course"), core, m);
				if ("שרשרת מדעית".equals(listName)) {
					NodeList scientChainsList = elem.getElementsByTagName("CourseList");
					for (int j = 0; j < scientChainsList.getLength(); ++j) {
						Element elem2 = (Element) scientChainsList.item(j);
						String chainName = elem2.getAttribute("nama");
						if ("שרשרת פיסיקה".equals(chainName))
							addCoursesToList(elem.getElementsByTagName("Course"), physicsChain, m);
						if ("שרשרת ביולוגיה".equals(chainName))
							addCoursesToList(elem.getElementsByTagName("Course"), biologyChain, m);
						if ("שרשרת כימיה 1".equals(chainName))
							addCoursesToList(elem.getElementsByTagName("Course"), chemistryChain1, m);
						if ("שרשרת כימיה 2".equals(chainName))
							addCoursesToList(elem.getElementsByTagName("Course"), chemistryChain2, m);
						if ("קורסים מדעיים נוספים".equals(chainName))
							addCoursesToList(elem.getElementsByTagName("Course"), otherScientCourses, m);
					}
				}
				addCoursesToList(elem.getElementsByTagName("Course"), listB, m);
			}
		} catch (SAXException | IOException | ParserConfigurationException ¢) {
			¢.printStackTrace();
		}
		scientificChain.add(physicsChain);
		scientificChain.add(biologyChain);
		scientificChain.add(chemistryChain1);
		scientificChain.add(chemistryChain2);
		scientificChain.add(otherScientCourses);
		theCatalog = new SoftwareEngineering(obligatory, malags, listA, listB, core, scientificChain);
	}

	public Catalog getCatalog() {
		return theCatalog;
	}

}
