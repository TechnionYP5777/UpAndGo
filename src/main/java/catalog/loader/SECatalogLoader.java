package catalog.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import catalog.Catalog;
import catalog.SoftwareEngineering;
import model.CourseModel;
import model.course.Course;

/**
 * 
 * @author Sapir Bismot
 * @since 13-01-2017
 * 
 * Class for loading Software Engineering study catalog.
 * 
 */
public class SECatalogLoader extends CSCatalogLoader {
	List<Course> core;
	List<List<Course>> scientificChain;
	SoftwareEngineering theCatalog;

	public SECatalogLoader(final String catalogXmlPath, final CourseModel m) {
		super(catalogXmlPath, m);
		System.out.println(m.getCoursesNames());
		core = new ArrayList<>();
		scientificChain = new ArrayList<>();
		final List<Course> physicsChain = new ArrayList<>(), chemistryChain1 = new ArrayList<>(),
				biologyChain = new ArrayList<>(), chemistryChain2 = new ArrayList<>(),
				otherScientCourses = new ArrayList<>();
		for (int i = 0; i < coursesList.getLength(); ++i) {
			final Element elem = (Element) coursesList.item(i);
			final String listName = elem.getAttribute("name");
			if ("מקצועות ליבה".equals(listName))
				addCoursesToList(elem.getElementsByTagName("Course"), core, m);
			if ("שרשרת מדעית".equals(listName)) {
				final NodeList scientChainsList = elem.getElementsByTagName("CourseList");
				for (int j = 0; j < scientChainsList.getLength(); ++j) {
					final Element elem2 = (Element) scientChainsList.item(j);
					final String chainName = elem2.getAttribute("name");
					if ("שרשרת פיסיקה".equals(chainName))
						addCoursesToList(elem2.getElementsByTagName("Course"), physicsChain, m);
					if ("שרשרת ביולוגיה".equals(chainName))
						addCoursesToList(elem2.getElementsByTagName("Course"), biologyChain, m);
					if ("שרשרת כימיה 1".equals(chainName))
						addCoursesToList(elem2.getElementsByTagName("Course"), chemistryChain1, m);
					if ("שרשרת כימיה 2".equals(chainName))
						addCoursesToList(elem2.getElementsByTagName("Course"), chemistryChain2, m);
					if ("קורסים מדעיים נוספים".equals(chainName))
						addCoursesToList(elem2.getElementsByTagName("Course"), otherScientCourses, m);
				}
			}
		}
		scientificChain.add(physicsChain);
		scientificChain.add(biologyChain);
		scientificChain.add(chemistryChain1);
		scientificChain.add(chemistryChain2);
		scientificChain.add(otherScientCourses);
		// * maybe we done need the Catalog class and the catalogLoader is
		// enough *//

		theCatalog = new SoftwareEngineering(obligatory, malags, listA, listB, core, scientificChain);
		System.out.println("malags: " + malags);
		System.out.println("listA: " + listA);
		System.out.println("listB: " + listB);
		System.out.println("core: " + core);
		System.out.println("scientificChain: " + scientificChain);
		System.out.println("obligatory: " + obligatory);
	}

	public Catalog getCatalog() {
		return theCatalog;
	}

	@Override
	public void markDoneCourses(final Set<String> userCourses) {
		super.markDoneCourses(userCourses);
		markDoneCoursesForOneList(userCourses, core);
		for (final List<Course> ¢ : scientificChain)
			markDoneCoursesForOneList(userCourses, ¢);
	}

	@Override
	public List<Course> getDoneCourse() {
		final List<Course> $ = super.getDoneCourse();
		$.addAll(getDoneCoursesForOneList(core));
		for (final List<Course> ¢ : scientificChain)
			$.addAll(getDoneCoursesForOneList(¢));
		return $;
	}

	@Override
	public List<Course> getCoursesTheUserCanTake() {
		final List<Course> $ = super.getCoursesTheUserCanTake();
		$.addAll(getCoursesTheUserCanTakeForOneList(core));
		for (final List<Course> ¢ : scientificChain)
			$.addAll(getCoursesTheUserCanTakeForOneList(¢));
		return $;
	}
}
