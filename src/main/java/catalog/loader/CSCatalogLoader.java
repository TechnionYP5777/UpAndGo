/**
 * 
 */
package catalog.loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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
		try {
			NodeList coursesList = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new FileInputStream(catalogXmlPath)))
					.getElementsByTagName("CourseList");
			for (int i = 0; i < coursesList.getLength(); ++i) {
				Element elem = (Element) coursesList.item(i);
				String listName = elem.getAttribute("name");
				if ("רשימה א'".equals(listName))
					addCoursesToList(elem.getElementsByTagName("Course"), listA, m);
				if ("רשימה ב'".equals(listName))
					addCoursesToList(elem.getElementsByTagName("Course"), listB, m);
			}
		} catch (SAXException | IOException | ParserConfigurationException ¢) {
			¢.printStackTrace();
		}
		
	}
}
