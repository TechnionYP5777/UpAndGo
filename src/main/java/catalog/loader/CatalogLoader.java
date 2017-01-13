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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.CourseModel;
import model.course.Course;

/**
 * @author sapir
 * @since 2017-01-day
 */
public class CatalogLoader {
	CatalogLoader(String catalogXmlPath, CourseModel m) {
		List<Course> obligatory = new ArrayList<>();
		
		try {
			NodeList coursesList = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(catalogXmlPath).getElementsByTagName("CourseList");
			for (int i = 0; i < coursesList.getLength(); ++i) {
				Node n = coursesList.item(i);
				if (!"מקצועות חובה".equals(((Element) n).getAttribute("name"))) continue;
				NodeList courses = ((Element) n).getElementsByTagName("Course");
				addCoursesToList(courses, obligatory, m);
				System.out.println(obligatory.toString());
			}
		
		} catch (SAXException | IOException | ParserConfigurationException ¢) {
			¢.printStackTrace();
		}
	}

	
	private void addCoursesToList(NodeList coursesList, List<Course> l, CourseModel m) {
		for (int i = 0; i < coursesList.getLength(); ++i) {
			Node p = coursesList.item(i);
			Course c = m.getCourseById(((Element) p).getAttribute("number"));
			if (c != null)
				l.add(c);
		}
	}
}
