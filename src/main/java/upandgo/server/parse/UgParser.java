package upandgo.server.parse;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import upandgo.shared.entities.Day;
import upandgo.shared.entities.Faculty;
import upandgo.shared.entities.Lesson.Type;

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
	private static final String GRADUATE_SEARCH_URL = "http://www.graduate.technion.ac.il/Heb/Subjects/?SUB=";
	static final List<String> ignoredFacutlyIds = Arrays.asList("", "7", "99", "300", "450");

	public static List<Faculty> getFaculties() {
		final List<Faculty> $ = new ArrayList<>();

		try {
			for (final Element faculty : Scraper.getDocumentFromURL(new URL(UG_SEARCH_URL))
					.select("select[name=\"FAC\"] option"))
				if (!ignoredFacutlyIds.contains(faculty.attr("value")))
					$.add(new Faculty(faculty.attr("value"), faculty.text()));

		} catch (final IOException xxx) {
			xxx.printStackTrace();
		}

		return $;

	}
	
	public static Map<String,String> getCoursesIDAndFaculty(boolean specificSemester, String semeserId) {
		Document courses = null;
		final Map<String,String> coursesMap = new TreeMap<>();

		final Map<String, String> dataMap = new HashMap<>();
		dataMap.put("CNM", "");
		dataMap.put("CNO", "");
		dataMap.put("PNT", "");
		dataMap.put("FAC", "");
		dataMap.put("LLN", "");
		dataMap.put("LFN", "");
		dataMap.put("SEM", semeserId);
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
		if (specificSemester){
			dataMap.put("OPTSTUD", "on");
		} else {
			dataMap.put("OPTSTUD", "");
		}
		dataMap.put("doSearch", "Y");

		int count = 0;

		for (final Faculty faculty : getFaculties()) {
			try {
				dataMap.put("FAC", faculty.getId());
				courses = Scraper.getSearchResults(UG_SEARCH_URL, dataMap);
			} catch (final IOException xxx) {
				xxx.printStackTrace();
			}
			if (courses != null){
				for (final Element course : courses.getElementsByClass("result-row")) {
					++count;
					coursesMap.put(course.childNode(1).childNode(1).childNode(0) + "", faculty.getName());
				}
			}

		}
		System.out.println("num of courses: " + count);
		return coursesMap;
	}
	

	public static List<String> getAllCoursesNamesAndID() {
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
		dataMap.put("OPTSTUD", "on");
		dataMap.put("doSearch", "Y");

		int count = 0;

		for (final Faculty faculty : getFaculties()) {
			try {
				dataMap.put("FAC", faculty.getId());
				courses = Scraper.getSearchResults(UG_SEARCH_URL, dataMap);
			} catch (final IOException xxx) {
				xxx.printStackTrace();
			}
			if (courses != null)
				for (final Element course : courses.getElementsByClass("result-row")) {
					++count;
					$.add(course.childNode(1).childNode(1).childNode(0) + "");
					System.out.println(course.childNode(1).childNode(1).childNode(0) + "");

				}
		}
		System.out.println("num of courses: " + count);
		java.util.Collections.sort($);
		return $;
	}
	
	public static void createCoursesXMLDocument(String semesterId) {
		
		try {
			org.w3c.dom.Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			org.w3c.dom.Element rootElement = doc.createElement("courses");
			doc.appendChild(rootElement);
			
			for (Entry<String,String> courses : getCoursesIDAndFaculty(true,semesterId).entrySet()){
				System.out.println("info for: " + courses.getKey() + " faculty " + courses.getValue());
				final org.w3c.dom.Element courseElement = createCourseElement(doc, semesterId, courses.getValue(), courses.getKey());
				if (courseElement.hasChildNodes()){
					rootElement.appendChild(courseElement);					
				}
			}
			
			final Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(doc), new StreamResult(new File("REPFILE/"+semesterId+".XML")));


		} catch (ParserConfigurationException | TransformerFactoryConfigurationError | TransformerException e) {
			e.printStackTrace();
		}
	
		
	}
	
	private static boolean notEmpty(Element e){
		return !"&nbsp;".equals(e.html());
	}
	
	public static org.w3c.dom.Element createCourseElement(org.w3c.dom.Document doc, String semesterId, final String faculty, final String courseID) {
				
		Element coursePage = null;
		try {
			coursePage = Scraper.getDocumentFromURL(new URL(GRADUATE_SEARCH_URL + courseID + "&SEM=" + semesterId));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (coursePage == null){
			return null;
		}
		
		final org.w3c.dom.Element courseElement = doc.createElement("course");
		
		courseElement.setAttribute("faculty",faculty);
		courseElement.setAttribute("id", courseID);
		
		courseElement.setAttribute("name",coursePage.select("span:containsOwn("+courseID+")").get(0).html().substring(9));
		
		final Element coursePointsTable = coursePage.getElementById("points");

		courseElement.setAttribute("points", coursePointsTable.select("th:contains(נקודות)").get(0).parent().nextElementSibling().child(0).html());
		
		final Element courseHoursTable = coursePointsTable.select("th:containsOwn(שעות)").get(0).parent();
		final Elements courseHours = courseHoursTable.children();
		
		if (notEmpty(courseHours.get(4))){
			courseElement.setAttribute("hoursLecture", courseHours.get(4).html());
		}
		if (notEmpty(courseHours.get(3))){
			courseElement.setAttribute("hoursTutorial", courseHours.get(3).html());
		}
		if (notEmpty(courseHours.get(2))){
			courseElement.setAttribute("hoursLab", courseHours.get(2).html());
		}
		if (notEmpty(courseHours.get(1))){
			courseElement.setAttribute("hoursProject", courseHours.get(1).html());
		}

		
		final org.w3c.dom.Element examsElement = createExamsElement(doc, coursePage);
		if (examsElement.hasChildNodes()){
			courseElement.appendChild(examsElement);			
		}
		
		final Element courseSchedulingTable = coursePage.getElementById("scheduling");
		if (courseSchedulingTable == null){
			return courseElement;
		}
		Elements lessonTableRows = courseSchedulingTable.getElementsByTag("tr");
	
		if (Integer.parseInt(courseID) >= 394800) {
			courseElement.setAttribute("faculty", "מקצועות ספורט");
			for (int i = 1; i < lessonTableRows.size(); ++i) {
				org.w3c.dom.Element sportElement = doc.createElement("sport");
				Element lessonTableRow = lessonTableRows.get(i);
				if (lessonTableRow.children().size() < 8) {
					continue;
				}
				@SuppressWarnings("unused")
				Type lessonType = Type.SPORT;
				sportElement.setAttribute("group", lessonTableRow.child(7).html());
				if (notEmpty(lessonTableRow.child(5))) {
					sportElement.setAttribute("name", lessonTableRow.child(5).html());
				}
				sportElement.appendChild(createLessonElement(doc, lessonTableRow));
				while (i + 1 < lessonTableRows.size() && lessonTableRows.get(i + 1).children().size() < 7
						&& lessonTableRows.get(i + 1).children().size() >= 4) {
					sportElement.appendChild(createLessonElement(doc, lessonTableRows.get(++i)));
				}
				courseElement.appendChild(sportElement);
			}
			final org.w3c.dom.Element notesElement = createNotesElement(doc, coursePage);
			if (notesElement.hasChildNodes()) {
				courseElement.appendChild(notesElement);
			}
		} else {
			int currentLectureGroup = 0;
			Type lessonType = null;
			org.w3c.dom.Element lectureElement = null;
			org.w3c.dom.Element lessonGroupElement = null;
			for (int i = 1; i < lessonTableRows.size(); ++i) {
				Element lessonTableRow = lessonTableRows.get(i);
				if (lessonTableRow.children().size() >= 7) {
					lessonType = Type.fromString(lessonTableRow.child(5).html());
					int group = Integer.parseInt(lessonTableRow.child(6).html());
					if (lessonType == Type.LECTURE) {
						if (currentLectureGroup == group)
							continue;
						lectureElement = doc.createElement("lecture");
						courseElement.appendChild(lectureElement);
						lessonGroupElement = lectureElement;
						currentLectureGroup = group;
					} else if (lessonType == Type.TUTORIAL) {
						lessonGroupElement = doc.createElement("tutorial");
					} else if (lessonType == Type.LABORATORY) {
						if (currentLectureGroup != group / 10 * 10) {
							currentLectureGroup = group / 10 * 10;
							lectureElement = doc.createElement("lecture");
							lectureElement.setAttribute("group", String.valueOf(currentLectureGroup));
							courseElement.appendChild(lectureElement);
						}
						lessonGroupElement = doc.createElement("lab");
					} else if (lessonType == Type.PROJECT) {
						if (currentLectureGroup != group / 10 * 10) {
							currentLectureGroup = group / 10 * 10;
							lectureElement = doc.createElement("lecture");
							lectureElement.setAttribute("group", String.valueOf(currentLectureGroup));
							courseElement.appendChild(lectureElement);
						}
						lessonGroupElement = doc.createElement("project");
					} else {
						continue;
					}
					lessonGroupElement.setAttribute("group", lessonTableRow.child(6).html());
					lessonGroupElement.appendChild(createLessonElement(doc, lessonTableRow));
					while (i + 1 < lessonTableRows.size() && lessonTableRows.get(i + 1).children().size() < 7
							&& lessonTableRows.get(i + 1).children().size() >= 4) {
						lessonGroupElement.appendChild(createLessonElement(doc, lessonTableRows.get(++i)));
					}
					if (notEmpty(lessonTableRow.child(4))) {
						String[] lecturers = lessonTableRow.child(4).html().split("<br>");
						for (String lecturer : lecturers) {
							if (!lecturer.trim().isEmpty()) {
								org.w3c.dom.Element lecturerElement = null;
								if (lessonType == Type.LECTURE) {
									lecturerElement = doc.createElement("lecturer");
								} else if (lessonType == Type.TUTORIAL) {
									lecturerElement = doc.createElement("assistant");
								} else if (lessonType == Type.LABORATORY) {
									lecturerElement = doc.createElement("guide");
								} else if (lessonType == Type.PROJECT) {
									lecturerElement = doc.createElement("moderator");
								} else {
									continue;
								}
								lecturerElement.setAttribute("title", lecturer.split(" ")[0]);
								lecturerElement.setAttribute("name",
										lecturer.substring(lessonTableRow.child(4).html().indexOf(" ") + 1));
								lessonGroupElement.appendChild(lecturerElement);
							}
						}
					}
					if (lessonType != Type.LECTURE && currentLectureGroup == group / 10 * 10
							&& lectureElement != null) {
						lectureElement.appendChild(lessonGroupElement);
					} else {
						courseElement.appendChild(lessonGroupElement);
					}
				}
			}
			final org.w3c.dom.Element notesElement = createNotesElement(doc, coursePage);
			if (notesElement.hasChildNodes()) {
				courseElement.appendChild(notesElement);
			}
		}
		
		
		return courseElement;
			
	}
	
	
	public static org.w3c.dom.Element createExamsElement(org.w3c.dom.Document doc, final Element coursePage){
		final org.w3c.dom.Element examsElement = doc.createElement("exams");
		final Element courseExamsTable = coursePage.getElementById("sylexam");
		if (courseExamsTable == null)
			return examsElement;
		final Elements courseExams = courseExamsTable.getElementsByTag("tr");
		for (int i = 1; i < courseExams.size(); ++i) {
			int rowSize = courseExams.get(i).children().size();
			if (notEmpty(courseExams.get(i).child(rowSize - 1))) {
				Element courseExam = courseExams.get(i);
				String moed = courseExam.child(rowSize - 1).html();
				final org.w3c.dom.Element examElement = doc.createElement("exam");
				if ("א".equals(moed))
					doc.renameNode(examElement, null, "moedA");
				else if ("ב".equals(moed))
					doc.renameNode(examElement, null, "moedB");
				examElement.setAttribute("time", !notEmpty(courseExam.child(rowSize - 4)) ? "00:00"
						: courseExam.child(rowSize - 4).html().substring(0, 5));
				if (notEmpty(courseExam.child(rowSize - 2))) {
					examElement.setAttribute("day", courseExam.child(rowSize - 2).html().substring(0, 2));
					examElement.setAttribute("month", courseExam.child(rowSize - 2).html().substring(3, 5));
					examElement.setAttribute("year", courseExam.child(rowSize - 2).html().substring(6, 10));
					examsElement.appendChild(examElement);
				}
			}
		}
		return examsElement;
	}
	
	public static org.w3c.dom.Element createNotesElement(org.w3c.dom.Document doc, final Element coursePage){
		final org.w3c.dom.Element notesElement = doc.createElement("notes");
		if (coursePage.select("th:contains(הערות)").isEmpty()
				|| !coursePage.select("th:contains(הערות)").get(0).hasAttr("align"))
			return notesElement;
		final Element courseNotesTable = coursePage.select("th:contains(הערות)").get(0).parent().parent();
		Elements notesTableRows = courseNotesTable.getElementsByTag("tr");
		for (int i = 1; i < notesTableRows.size(); ++i) {
			final org.w3c.dom.Element noteElement = doc.createElement("note");
			String noteHtml = notesTableRows.get(i).child(0).html().replace("<br>", "bbbbrrrr");
			noteElement.appendChild(doc.createTextNode(Jsoup.parse(noteHtml).text().replace("bbbbrrrr", "\n")));
			notesElement.appendChild(noteElement);
		}
		return notesElement;
	}
	
		
	public static org.w3c.dom.Element createLessonElement(org.w3c.dom.Document doc, final Element lessonTableRow){
		final org.w3c.dom.Element lessonElement = doc.createElement("lesson");

		if (notEmpty(lessonTableRow.child(3))){
			lessonElement.setAttribute("day", Day.fromLetter(lessonTableRow.child(3).html()).toLetter());
		}
		if (notEmpty(lessonTableRow.child(2))){
			lessonElement.setAttribute("timeStart", lessonTableRow.child(2).html().substring(0, 5));
			lessonElement.setAttribute("timeEnd", lessonTableRow.child(2).html().substring(6, 11));
		}
		if (notEmpty(lessonTableRow.child(1))){
			lessonElement.setAttribute("building", lessonTableRow.child(1).html());
		}
		if (notEmpty(lessonTableRow.child(0))){
			lessonElement.setAttribute("roomNumber", lessonTableRow.child(0).html());
		}
		
		return lessonElement;
	}


	public static void createCoursesPrerequisitesDocument() {
		//Do Nothing
	}

	public static void createCoursePrerequisitesElement(final String courseID) {
		try {
			final org.w3c.dom.Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			final org.w3c.dom.Element rootElement = doc.createElement("Prerequisites");
			final Element prerTableElement = Scraper.getDocumentFromURL(new URL(GRADUATE_SEARCH_URL + courseID))
					.getElementsByAttributeValue("class", "tab0").first();
			if (prerTableElement != null) {
				final Elements prerTableElementRows = prerTableElement.getElementsByTag("tr");

				int state = 0;

				for (Element prerTableElementRow : prerTableElementRows){
					if (prerTableElementRow.getElementsByTag("td").size() < 7){
						state = 0;
						continue;
					}
					if (prerTableElementRow.getElementsByTag("td").get(6).text().contains("מקצועות קדם"))
						state = 1;
										
					if (state == 1){
						if (prerTableElementRow.getElementsByTag("td").get(5).text().contains("או"))
							System.out.println("");
						if (prerTableElementRow.getElementsByTag("td").get(4).text().contains("("))
							System.out.print("( ");
						if (prerTableElementRow.getElementsByTag("td").get(3).text().contains("ו"))
							System.out.print(" & ");
						System.out.print(prerTableElementRow.getElementsByTag("a").text());
						if (prerTableElementRow.getElementsByTag("td").get(0).text().contains(")"))
							System.out.print(" )");
					}
					
				}


			}
			
			if (rootElement.hasChildNodes())
				doc.appendChild(rootElement);
								
			
		} catch (IOException | ParserConfigurationException xxx) {
			xxx.printStackTrace();
		}
	}

}
