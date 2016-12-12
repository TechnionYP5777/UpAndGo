package model.loader;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.course.Course;
import model.course.Course.CourseBuilder;

class xmlParser {
	public static List<Course> getCourses() {
		Course.CourseBuilder cb = new Course.CourseBuilder();
		List<Course> resList = new LinkedList<Course>();
		// resList.add
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("REPFILE/REP.XML");
			NodeList coursesList = doc.getElementsByTagName("Course");
			//System.out.println(ridesList.getLength());
			for (int i = 0; i < coursesList.getLength(); i++) {
				Node p = coursesList.item(i);
				if (p.getNodeType() == Node.ELEMENT_NODE) {
					//System.out.println("ok");
					Element course = (Element) p;
					// String id = ride.getAttribute("id");
					Integer id = Integer.parseInt(course.getAttribute("id"));
					String name = course.getElementsByTagName("name").item(0)
							.getTextContent();

					
					
					Course c = cb.setId(id).setName(name).build();
					resList.add( c  );
				}
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resList;
	}
}