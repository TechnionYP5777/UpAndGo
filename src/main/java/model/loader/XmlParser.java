package model.loader;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.course.Course;

/**
 * 
 * @author kobybs
 * @since 12-12-16
 * 
 * Class for parsing courses data from xml file.
 * 
 */
@Deprecated
class xmlParser {
	public static List<Course> getCourses() {
		final Course.CourseBuilder cb = new Course.CourseBuilder();
		final List<Course> $ = new LinkedList<>();
		// resList.add
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final NodeList coursesList = factory.newDocumentBuilder().parse("REPFILE/REP.XML")
					.getElementsByTagName("Course");
			for (int i = 0; i < coursesList.getLength(); ++i) {
				final Node p = coursesList.item(i);
				if (p.getNodeType() == Node.ELEMENT_NODE)
					$.add(cb.setId(((Element) p).getAttribute("id"))
							.setName(((Element) p).getElementsByTagName("name").item(0).getTextContent()).build());
			}
		} catch (final IOException | SAXException | ParserConfigurationException ¢) {
			¢.printStackTrace();
		}
		return $;
	}
}
