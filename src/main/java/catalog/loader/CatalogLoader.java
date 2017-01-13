/**
 * 
 */
package catalog.loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

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

	CatalogLoader(String catalogXmlPath, CourseModel m) {
		obligatory = new ArrayList<>();
		malags = new ArrayList<>();
		try {
			NodeList coursesList = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new FileInputStream(catalogXmlPath)))
					.getElementsByTagName("CourseList");
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

	protected static void addCoursesToList(NodeList coursesList, List<Course> cs, CourseModel m) {
		for (int i = 0; i < coursesList.getLength(); ++i) {
			Node p = coursesList.item(i);
			Course c = m.getCourseById(((Element) p).getAttribute("number"));
			if (c != null)
				cs.add(c);
		}
	}
}