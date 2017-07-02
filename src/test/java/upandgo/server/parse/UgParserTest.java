package upandgo.server.parse;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;

import upandgo.server.parse.UgParser;
import upandgo.shared.entities.Faculty;

public class UgParserTest {

	
	@Test
	@SuppressWarnings("static-method")
	public void testGetFaculties() {
		List<Faculty> faculties = UgParser.getFaculties();
		@SuppressWarnings("unused")
		final List<String> ignoredIds = Arrays.asList("", "7", "99", "300", "450");
		for (String ignoredId : UgParser.ignoredFacutlyIds){
			for (Faculty faculty : faculties){
				assert(!faculty.getId().equals(ignoredId));
			}
		}
	}
	
	@Test
	public void testCoursesNamesAndID() {
//		Do nothing
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testCreateCoursePrerequisitesElement() {
		UgParser.createCoursePrerequisitesElement("234107");
	}

	@Test
	@SuppressWarnings("static-method")
	public void testCreateCourseElement() {
		org.w3c.dom.Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("null")
		org.w3c.dom.Element rootElement = doc.createElement("courses");
		doc.appendChild(rootElement);
		rootElement.appendChild(UgParser.createCourseElement(doc, "201602", "faculty", "046041"));
		try {
			final Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(System.out, "UTF-8")));
		} catch (TransformerFactoryConfigurationError | UnsupportedEncodingException | TransformerException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void test_a() throws MalformedURLException, IOException {
		assertEquals(3137, UgParser.getCoursesIDAndFaculty(false, "201602").size());
		assertEquals(1237, UgParser.getCoursesIDAndFaculty(true, "201602").size());
		assertEquals(1237, UgParser.getAllCoursesNamesAndID().size());
		org.w3c.dom.Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		org.w3c.dom.Element elem = UgParser.createCourseElement(doc, "201602", "מקצועות ספורט", "394800");
		UgParser.createExamsElement(doc, Scraper.getDocumentFromURL(
				new URL("http://www.graduate.technion.ac.il/Heb/Subjects/?SUB=394800&SEM=201602")));
	}
	

	@Test
	@SuppressWarnings("static-method")
	public void testCreateCoursesXMLDocument() {
		UgParser.createCoursesXMLDocument("201603");
	}
}
