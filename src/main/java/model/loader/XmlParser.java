package model.loader;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
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

class xmlParser {
	public static List<Course> getCourses() {
		Course.CourseBuilder cb = new Course.CourseBuilder();
		List<Course> $ = new LinkedList<>();
		// resList.add
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("REPFILE/REP.XML");
			NodeList coursesList = doc.getElementsByTagName("Course");
			
			for (int i = 0; i < coursesList.getLength(); ++i) {
				Node p = coursesList.item(i);
				if (p.getNodeType() == Node.ELEMENT_NODE)
					$.add(cb.setId(((Element) p).getAttribute("id"))
							.setName(((Element) p).getElementsByTagName("name").item(0).getTextContent()).build());
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
		return $;
	}
	
	public static void setChosenCouseNames(List<String> names, String filepath){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element rootElement = doc.createElement("ChosenCourses");
			doc.appendChild(rootElement);
			
			names.forEach(name->{
				Element course = doc.createElement("Course");
				course.appendChild(doc.createTextNode(name));
				rootElement.appendChild(course);
			});
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			File dataDir = new File("data");
			if (!dataDir.exists() || !dataDir.isDirectory())
				dataDir.mkdir();
			transformer.transform((new DOMSource(doc)), (new StreamResult(new File(filepath))));

		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<String> getChosenCourseNames(String filepath) {
		List<String> $ = new LinkedList<>();
		// resList.add
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(filepath);
			NodeList chosenList = doc.getElementsByTagName("Chosen");
			for (int i = 0; i < chosenList.getLength(); ++i) {
				Node p = chosenList.item(i);
				if (p.getNodeType() == Node.ELEMENT_NODE)
					$.add(((Element) p).getNodeValue());
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
		return $;
	}
}