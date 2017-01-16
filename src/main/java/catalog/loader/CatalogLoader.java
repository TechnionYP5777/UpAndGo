/**
 * 
 */
package catalog.loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import model.CourseModel;
import model.course.Course;

/**
 * @author sapir
 * @since 2017-01-12
 */
public abstract class CatalogLoader {
	protected List<Course> obligatory, malags;
	NodeList coursesList;
	protected CourseModel model;

	@SuppressWarnings("resource")
	CatalogLoader(String catalogXmlPath, CourseModel m) {
		model = m;
		obligatory = new ArrayList<>();
		malags = new ArrayList<>();
		try {
			coursesList = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(new FileInputStream(catalogXmlPath))).getElementsByTagName("CourseList");
			for (int i = 0; i < coursesList.getLength(); ++i) {
				Element elem = (Element) coursesList.item(i);
				String listName = elem.getAttribute("name");
				if ("מקצועות חובה".equals(listName))
					addCoursesToList(elem.getElementsByTagName("Course"), obligatory, m);
				if ("בחירה חופשית".equals(listName)) {
					NodeList freeChoiceCourses = elem.getElementsByTagName("CourseList");
					for (int j = 0; j < freeChoiceCourses.getLength(); ++j) {
						Element elem2 = (Element) freeChoiceCourses.item(j);
						if ("מלגים".equals(elem2.getAttribute("name")))
							addCoursesToList(elem2.getElementsByTagName("Course"), malags, m);
					}
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException ¢) {
			¢.printStackTrace();
		}
	}

	protected static void addCoursesToList(NodeList coursesList, List<Course> cl, CourseModel m) {
		for (int i = 0; i < coursesList.getLength(); ++i) {
			Node p = coursesList.item(i);
			Course c = m.getCourseById(((Element) p).getAttribute("number"));
			if (c != null)
				cl.add(c);
			else {
				NodeList name = ((Element) p).getElementsByTagName("name");
				Course c2 = new Course(((Element) name.item(0)).getAttribute("courseName"),
						((Element) p).getAttribute("number"), "faculty", new ArrayList<>(), 0, null, null,
						new ArrayList<>(), new ArrayList<>());
				c2.markAsNotPass();
				cl.add(c2);
			}
		}
	}

	protected static void markDoneCoursesForOneList(Set<String> userCourses, List<Course> coursesList) {
		for (Course ¢ : coursesList)
			if (userCourses.contains(¢.getId()))
				¢.MarkDone();
	}

	protected void markDoneCourses(Set<String> userCourses) {
		markDoneCoursesForOneList(userCourses, obligatory);
		markDoneCoursesForOneList(userCourses, malags);

	}
	protected static List<Course> getDoneCoursesForOneList(List<Course> l) {
		List<Course> $ = new ArrayList<>();
		for (Course c: l)
			if (c.getDone() && c.isPassThisSemester())
				$.add(c);
		return $;
	}
	protected List<Course> getDoneCourse() {
		List<Course> $ = getDoneCoursesForOneList(obligatory);
		$.addAll(getDoneCoursesForOneList(malags));
		return $;
	}
	protected static List<Course> getCoursesTheUserCanTakeForOneList(List<Course> cs) {
		List<Course> $ = new ArrayList<>();
		for (Course c: cs) {
			for (Course c1 : c.getPrerequisites())
				if (!c1.getDone())
					break;
			if (!c.getDone())
				$.add(c);
		}
		return $;
	} //* TODO: think how to do it for co-requisites courses *//
	protected List<Course> getCoursesTheUserCanTake() {
		List<Course> $ = getCoursesTheUserCanTakeForOneList(obligatory);
		$.addAll(getCoursesTheUserCanTakeForOneList(malags));
		return $;
	}

}