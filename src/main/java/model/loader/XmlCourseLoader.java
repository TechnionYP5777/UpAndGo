package model.loader;

import java.io.File;
import java.util.HashMap;
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

import model.course.Course;

public class XmlCourseLoader extends CourseLoader {
	List<Course> coursesList;
	
	public XmlCourseLoader(String path) {
		super(path);
		coursesList = xmlParser.getCourses();
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
	public HashMap<String, Course> loadAllCourses() {
		HashMap<String, Course> $ = new HashMap<>();
		for(Course ¢ : coursesList)
			$.put(¢.getId(), ¢);

		return $;
	}
	
	@Override
	public void saveChosenCourseNames(List<String> names){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element rootElement = doc.createElement("ChosenCourses");
			doc.appendChild(rootElement);
			
			names.forEach(name->{
				Element course = doc.createElement("Course");
				Element courseName = doc.createElement("name");
				courseName.appendChild(doc.createTextNode(name));
				course.appendChild(courseName);
				rootElement.appendChild(course);
			});
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			File dataDir = new File("data");
			if (!dataDir.exists() || !dataDir.isDirectory())
				dataDir.mkdir();
			transformer.transform((new DOMSource(doc)), (new StreamResult(new File("data/ChosenCourses.xml"))));

		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<String> loadChosenCourseNames(String filepath) {
		return xmlParser.getChosenCourseNames(filepath);		
	}
	

}