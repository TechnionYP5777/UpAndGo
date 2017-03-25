package parse;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import model.Faculty;

/**
 * 
 * @author Yaniv Levinsky
 * @since 07-01-17
 * 
 * Class for parsing information posted in UG.
 * 
 */
public class UgParser {

	private static final String UG_SEARCH_URL = "https://ug3.technion.ac.il/rishum/search";
	// private static final String UG_COURSE_URL =
	// "https://ug3.technion.ac.il/rishum/course/";
	private static final String GRADUATE_SEARCH_URL = "http://www.graduate.technion.ac.il/Heb/Subjects/?SUB=";

	public static List<Faculty> getFaculties() {
		final List<Faculty> $ = new ArrayList<>();
		final List<String> ignoredIds = Arrays.asList("", "7", "99", "300", "450");

		try {
			for (final Element faculty : Scraper.getDocumentFromURL(new URL(UG_SEARCH_URL))
					.select("select[name=\"FAC\"] option"))
				if (!ignoredIds.contains(faculty.attr("value")))
					$.add(new Faculty(faculty.attr("value"), faculty.text()));

		} catch (final IOException ¢) {
			¢.printStackTrace();
		}

		return $;

	}

	public static List<String> getCoursesNamesAndID() {
		Document courses = null;
		final List<String> $ = new ArrayList<>();

		final Map<String, String> dataMap = new HashMap<>();
		dataMap.put("CNM", "");
		dataMap.put("CNO", "");
		dataMap.put("PNT", "");
		dataMap.put("FAC", "");
		dataMap.put("LLN", "");
		dataMap.put("LFN", "");
		dataMap.put("SEM", "201602");
		dataMap.put("RECALL", "Y");
		dataMap.put("D1", "on");
		dataMap.put("D2", "on");
		dataMap.put("D3", "on");
		dataMap.put("D4", "on");
		dataMap.put("D5", "on");
		dataMap.put("D6", "on");
		dataMap.put("FTM", "");
		dataMap.put("TTM", "");
		dataMap.put("SIL", "");
		dataMap.put("OPTCAT", "on");
		dataMap.put("OPTSEM", "on");
		dataMap.put("OPTSTUD", "");
		dataMap.put("doSearch", "Y");

		int count = 0;

		for (final Faculty faculty : getFaculties()) {
			try {
				dataMap.put("FAC", faculty.getId());
				courses = Scraper.getSearchResults(UG_SEARCH_URL, dataMap);
			} catch (final IOException ¢) {
				// TODO Auto-generated catch block
				¢.printStackTrace();
			}
			if (courses != null)
				for (final Element course : courses.getElementsByClass("result-row")) {
					++count;
					$.add(course.childNode(1).childNode(1).childNode(0) + "");
					// System.out.println(course.childNode(1).childNode(1).childNode(0));
					// System.out.println((course.childNode(3).childNode(0) +
					// "").replace("\n","").substring(1));

				}
		}
		System.out.println("num of courses: " + count);
		java.util.Collections.sort($);
		return $;
	}

	public static void createCoursesPrerequisitesDocument() {
		for (final String courseID : getCoursesNamesAndID()) {
			System.out.println(courseID);
			createCoursePrerequisitesElement(courseID);
		}
	}

	public static void createCoursePrerequisitesElement(final String courseID) {
		try {
			final org.w3c.dom.Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			final org.w3c.dom.Element rootElement = doc.createElement("Prerequisites");
			org.w3c.dom.Element prepOptionElement = doc.createElement("PrerOption");
			final Element prerTabElement = Scraper.getDocumentFromURL(new URL(GRADUATE_SEARCH_URL + courseID))
					.getElementsByAttributeValue("class", "tab0").first();
			if (prerTabElement != null)
				for (final Element prerequisitesElement : prerTabElement.getElementsContainingOwnText("מקצועות קדם")) {
					System.out.println(prerequisitesElement);
					for (final Element prerequisite : prerequisitesElement.parent().parent().parent().children()) {
						if ((prerequisite + "").contains("<td>או</td>")) {
							rootElement.appendChild(prepOptionElement);
							prepOptionElement = doc.createElement("PrerOption");
						}
						final org.w3c.dom.Element courseElement = doc.createElement("PrepCourse");
						courseElement.appendChild(doc.createTextNode(prerequisite.getElementsByTag("a").text()));
						prepOptionElement.appendChild(courseElement);
					}
					if (prepOptionElement.hasChildNodes())
						rootElement.appendChild(prepOptionElement);
				}
			if (rootElement.hasChildNodes())
				doc.appendChild(rootElement);
			final Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(System.out, "UTF-8")));
		} catch (TransformerException | IOException | ParserConfigurationException ¢) {
			¢.printStackTrace();
		}
	}

}
