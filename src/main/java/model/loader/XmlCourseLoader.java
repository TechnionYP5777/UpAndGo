package model.loader;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.course.Course;
import parse.RepFile;

public class XmlCourseLoader extends CourseLoader {
	private static final String REP_XML_PATH = "REPFILE/REP.XML";
	private static final String DATA_DIR_PATH = "data";
	private static final String CHOSEN_COURSES_PATH = "data/ChosenCourses.xml";

	
	//List<Course> coursesList;
	TreeMap<String, Course> courses;
	
	public XmlCourseLoader() {
		super(REP_XML_PATH);
		
		if (!new File(path).exists())
			RepFile.getCoursesNamesAndIds();
		
		//Create a data dir for saving changes if it does not exists
		File dataDir = new File(DATA_DIR_PATH);
		if (!dataDir.exists() || !dataDir.isDirectory())
			dataDir.mkdir();
		
		//coursesList = xmlParser.getCourses(path);
		//Get data from REP XML file.
		courses = new TreeMap<>();
		getCourses();
	}

	@Override
	public HashMap<String, Course> loadCourses(@SuppressWarnings("unused") List<String> names) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCourse(@SuppressWarnings("unused") Course __) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Course loadCourse(@SuppressWarnings("unused") String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> loadAllCourseNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeMap<String, Course> loadAllCourses() {
		return courses;
	}
	
	@Override
	public void saveChosenCourseNames(List<String> names){
        try {
    		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element rootElement = doc.createElement("ChosenCourses");
			doc.appendChild(rootElement);
			
			names.forEach(name->{
				Element course = doc.createElement("Course");
				course.appendChild(doc.createTextNode(name));
				rootElement.appendChild(course);
			});
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			transformer.transform((new DOMSource(doc)), (new StreamResult(new File(CHOSEN_COURSES_PATH))));

		} catch (ParserConfigurationException | TransformerException ¢) {
			¢.printStackTrace();
		}

	}

	@Override
	public List<String> loadChosenCourseNames() {
		if (!(new File(CHOSEN_COURSES_PATH).exists()))
			return Collections.emptyList();
		List<String> $ = new LinkedList<>();
		try {
			NodeList chosenList = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(CHOSEN_COURSES_PATH).getElementsByTagName("Course");
			for (int i = 0; i < chosenList.getLength(); ++i) {
				Node p = chosenList.item(i);
				if (p.getNodeType() == Node.ELEMENT_NODE)
					$.add(((Element) p).getTextContent());
			}
		} catch (IOException | SAXException | ParserConfigurationException ¢) {
			¢.printStackTrace();
		}
		return $;
	}
	
	private void getCourses() {
		try {
			NodeList coursesList = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(REP_XML_PATH)
					.getElementsByTagName("course");
			for (int i = 0; i < coursesList.getLength(); ++i) {
				Node p = coursesList.item(i);
				if (p.getNodeType() == Node.ELEMENT_NODE)
					courses.put(((Element) p).getAttribute("id"), cb
							.setId(((Element) p).getAttribute("id"))
							.setName(((Element) p).getAttribute("name"))
							.setPoints(Double.parseDouble(((Element) p).getAttribute("points")))
							.build());
			}
		} catch (IOException | SAXException | ParserConfigurationException ¢) {
			¢.printStackTrace();
		}
	}
	

}