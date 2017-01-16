/**
 * 
 */
package catalog.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Element;

import model.CourseModel;
import model.course.Course;

/**
* 
* @author sapir
* @since Jan 13, 2017
*/
public abstract class CSCatalogLoader extends CatalogLoader {
	protected List<Course> listA, listB;
	CSCatalogLoader(String catalogXmlPath, CourseModel m) {
		super(catalogXmlPath, m);
		listA = new ArrayList<>();
		listB = new ArrayList<>();
		for (int i = 0; i < coursesList.getLength(); ++i) {
			Element elem = (Element) coursesList.item(i);
			String listName = elem.getAttribute("name");
			if ("רשימה א'".equals(listName))
				addCoursesToList(elem.getElementsByTagName("Course"), listA, m);
			if ("רשימה ב'".equals(listName))
				addCoursesToList(elem.getElementsByTagName("Course"), listB, m);
		}
	}
	@Override
	protected void markDoneCourses(Set<String> userCourses) {
		super.markDoneCourses(userCourses);
		markDoneCoursesForOneList(userCourses, listA);
		markDoneCoursesForOneList(userCourses, listB);
	}
	@Override
	protected List<Course> getDoneCourse() {
		List<Course> $ = super.getDoneCourse();
		$.addAll(getDoneCoursesForOneList(listA));
		$.addAll(getDoneCoursesForOneList(listB));
		return $;
	}
	@Override
	protected List<Course> getCoursesTheUserCanTake() {
		List<Course> $ = super.getCoursesTheUserCanTake();
		$.addAll(getCoursesTheUserCanTakeForOneList(listA));
		$.addAll(getCoursesTheUserCanTakeForOneList(listB));
		return $;
	}
}
