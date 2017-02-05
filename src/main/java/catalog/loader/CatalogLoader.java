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
	CatalogLoader(final String catalogXmlPath, final CourseModel m) {
		model = m;
		obligatory = new ArrayList<>();
		malags = new ArrayList<>();
		try {
			coursesList = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(new FileInputStream(catalogXmlPath))).getElementsByTagName("CourseList");
			for (int i = 0; i < coursesList.getLength(); ++i) {
				final Element elem = (Element) coursesList.item(i);
				final String listName = elem.getAttribute("name");
				if ("מקצועות חובה".equals(listName))
					addCoursesToList(elem.getElementsByTagName("Course"), obligatory, m);
				if ("בחירה חופשית".equals(listName)) {
					final NodeList freeChoiceCourses = elem.getElementsByTagName("CourseList");
					for (int j = 0; j < freeChoiceCourses.getLength(); ++j) {
						final Element elem2 = (Element) freeChoiceCourses.item(j);
						if ("מלגים".equals(elem2.getAttribute("name")))
							addCoursesToList(elem2.getElementsByTagName("Course"), malags, m);
					}
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException ¢) {
			¢.printStackTrace();
		}
	}

	protected static void addCoursesToList(final NodeList coursesList, final List<Course> cl, final CourseModel m) {
		for (int i = 0; i < coursesList.getLength(); ++i) {
			final Node p = coursesList.item(i);
			final Course c = m.getCourseById(((Element) p).getAttribute("number"));
			if (c != null)
				cl.add(c);
			else {
				final NodeList name = ((Element) p).getElementsByTagName("name");
				final Course c2 = new Course(((Element) name.item(0)).getAttribute("courseName"),
						((Element) p).getAttribute("number"), "faculty", new ArrayList<>(), 0, null, null,
						new ArrayList<>(), new ArrayList<>());
				c2.markAsNotPass();
				cl.add(c2);
			}
		}
	}

	protected static void markDoneCoursesForOneList(final Set<String> userCourses, final List<Course> coursesList) {
		for (final Course ¢ : coursesList)
			if (userCourses.contains(¢.getId()))
				¢.MarkDone();
	}

	protected void markDoneCourses(final Set<String> userCourses) {
		markDoneCoursesForOneList(userCourses, obligatory);
		markDoneCoursesForOneList(userCourses, malags);

	}
	protected static List<Course> getDoneCoursesForOneList(final List<Course> l) {
		final List<Course> $ = new ArrayList<>();
		for (final Course c: l)
			if (c.getDone() && c.isPassThisSemester())
				$.add(c);
		return $;
	}
	protected List<Course> getDoneCourse() {
		final List<Course> $ = getDoneCoursesForOneList(obligatory);
		$.addAll(getDoneCoursesForOneList(malags));
		return $;
	}
	protected static List<Course> getCoursesTheUserCanTakeForOneList(final List<Course> cs) {
		final List<Course> $ = new ArrayList<>();
		for (final Course c: cs) {
			for (final Course c1 : c.getPrerequisites())
				if (!c1.getDone())
					break;
			if (!c.getDone())
				$.add(c);
		}
		return $;
	} //* TODO: think how to do it for co-requisites courses *//
	protected List<Course> getCoursesTheUserCanTake() {
		final List<Course> $ = getCoursesTheUserCanTakeForOneList(obligatory);
		$.addAll(getCoursesTheUserCanTakeForOneList(malags));
		return $;
	}

}