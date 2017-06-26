package upandgo.server.parse;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Ignore;
import org.junit.Test;

import upandgo.server.parse.UgParser;
import upandgo.shared.entities.Faculty;

@Ignore
public class UgParserTest {

	
	@Test
	@SuppressWarnings("static-method")
	public void testGetFaculties() {
		List<Faculty> faculties = UgParser.getFaculties();
		final List<String> ignoredIds = Arrays.asList("", "7", "99", "300", "450");
		for (String ignoredId : UgParser.ignoredFacutlyIds){
			for (Faculty faculty : faculties){
				assert(!faculty.getId().equals(ignoredId));
			}
		}
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testCoursesNamesAndID() {
		//List<String> faculties = UgParser.getCoursesNamesAndID();
		//assert(faculties.size() > 1000);
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testCreateCoursePrerequisitesElement() {
		UgParser.createCoursePrerequisitesElement("234107");
	}

	@Test
	public void testCreateCourseElement(){
		org.w3c.dom.Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		org.w3c.dom.Element rootElement = doc.createElement("courses");
		doc.appendChild(rootElement);
		rootElement.appendChild(UgParser.createCourseElement(doc,"faculty","046041"));
		
		try {
			final Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(System.out, "UTF-8")));
		} catch (TransformerFactoryConfigurationError | UnsupportedEncodingException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	
	@Test
	public void testCreateCoursesXMLDocument(){
		UgParser.createCoursesXMLDocument();
	}
}
