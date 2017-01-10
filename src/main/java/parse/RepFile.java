package parse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RepFile {
	
	private static final String REP_FILE_URL = "http://ug3.technion.ac.il/rep/REPFILE.zip";
	private static final String REP_FILE_ZIP = "REPFILE.zip";
	private static final String REP_FILE_DIR = "REPFILE";
	private static final String REP_FILE_DOS = "REPFILE/REPY";
	private static final String REP_FILE_HEBREW = "REPFILE/REPHEB";
	private static final String REP_FILE_XML = "REPFILE/REP.XML";

	
	private static final String SEPARATING_LINE = "^\\+\\-+\\+\\n";
	private static final String SEPARATING_DOUBLE_LINE = "^\\+\\=+\\+\\n";
	private static final String FACULTY_NAME = "(?<FacultyName>.+?)";
	private static final String FACULTY_COURSES = "(?<FacultyCourses>.+?(?=^\\+\\=+\\+\\n))";
	private static final String COURSE_SUMMERY = "(?<CourseSummery>.+?\\n.+?\\n)";
	private static final String COURSE_INFO_AND_LESSONS = "(?<CourseInfoAndLessons>(.+?\\n)+?(?="+SEPARATING_LINE+"))";


	public static void downloadData(){
		try {
			URL repFileUrl = new URL(REP_FILE_URL);
			File repFileLocal = new File(REP_FILE_ZIP);
			FileUtils.copyURLToFile(repFileUrl, repFileLocal);
			if(repFileLocal.exists() && !repFileLocal.isDirectory())
				UnzipUtility.unzip((repFileLocal + ""), REP_FILE_DIR);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException ¢) {
			¢.printStackTrace();
		}
		processRepFile();
	}

	
	public static void printRepFile(){
		Charset ibm862charset = Charset.forName("IBM862");

		File repy = new File(REP_FILE_DOS);
		if (repy.exists())
			try (FileInputStream fis = new FileInputStream(repy);
					InputStreamReader reader = new InputStreamReader(fis, ibm862charset);
					BufferedReader buffReader = new BufferedReader(reader)) {
				for (String line = buffReader.readLine(); line != null; line = buffReader.readLine())
					System.out.println(line);
				buffReader.close();
			} catch (IOException ¢) {
				¢.printStackTrace();
			} 
	}
	
	public static void processRepFile(){
		Charset ibm862charset = Charset.forName("IBM862");

		File repyIn = new File(REP_FILE_DOS);
		File repyOut = new File(REP_FILE_HEBREW);
		if (repyIn.exists())
			try (FileInputStream fis = new FileInputStream(repyIn);
					InputStreamReader reader = new InputStreamReader(fis, ibm862charset);
					BufferedReader buffReader = new BufferedReader(reader);
					FileWriter fr = new FileWriter(repyOut);
					BufferedWriter buffWriter = new BufferedWriter(fr)) {
				if (!repyOut.exists())
					repyOut.createNewFile();
				for (String line = buffReader.readLine(); line != null; line = buffReader.readLine())
					buffWriter.write(HebReverse.reverseTextNotNumbers(line) + "\n");
				buffReader.close();
				buffWriter.close();
			} catch (IOException ¢) {
				¢.printStackTrace();
			} 
	}
	

	public static void getCoursesNamesAndIds(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
			Document doc = factory.newDocumentBuilder().newDocument();
			Element rootElement = doc.createElement("Courses");
			doc.appendChild(rootElement);
			
			for (Matcher regexMatcher = Pattern
					.compile("^\\+\\-+\\+\\n\\|\\s*(?<CourseID>\\d{6})\\s+(?<CourseName>", Pattern.MULTILINE)
					.matcher(getRepFileAsString()); regexMatcher.find();){
				//System.out.println(regexMatcher.group("CourseID") + " " + regexMatcher.group("CourseName")); 
				Element course = doc.createElement("Course");
				course.setAttribute("id", regexMatcher.group("CourseID"));
				Element courseName = doc.createElement("name");
				courseName.appendChild(doc.createTextNode(regexMatcher.group("CourseName")));
				course.appendChild(courseName);
				rootElement.appendChild(course);
			}
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			transformer.transform((new DOMSource(doc)), (new StreamResult(new File(REP_FILE_XML))));

		} catch (ParserConfigurationException | TransformerException ¢) {
			¢.printStackTrace();
		}


	}
	

		
	public static void getCoursesInfoFromRepFile(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			Document doc = factory.newDocumentBuilder().newDocument();
			Element rootElement = doc.createElement("courses");
			doc.appendChild(rootElement);
			
			for (Matcher facultyMatcher = Pattern
					.compile(SEPARATING_DOUBLE_LINE+FACULTY_NAME+SEPARATING_DOUBLE_LINE+FACULTY_COURSES, Pattern.MULTILINE+Pattern.DOTALL)
					.matcher(getRepFileAsString()); facultyMatcher.find();)
				for (Matcher courseMatcher = Pattern
						.compile(SEPARATING_LINE+COURSE_SUMMERY+SEPARATING_LINE+COURSE_INFO_AND_LESSONS, Pattern.MULTILINE)
						.matcher(facultyMatcher.group("FacultyCourses")); courseMatcher.find();){
					
					Element course = createCourseElement(doc, courseMatcher.group("CourseSummery"));
					course.setAttribute("faculty", facultyMatcher.group("FacultyName").substring(15, 43).replaceAll("\\s+$", ""));
					
					ArrayList<String> courseInfoAndLessons = new ArrayList<>(Arrays.asList(
							Pattern.compile("^\\|(רישום\\s+|מס.+|\\s+)\\|\\n", Pattern.MULTILINE)
							.split(courseMatcher.group("CourseInfoAndLessons"))));
					
					String courseInfo = courseInfoAndLessons.get(0);
					courseInfoAndLessons.remove(0);
					courseInfoAndLessons.removeAll(Arrays.asList("", null));
					addInfoToCourse(doc, course, courseInfo);
					addLessonsToCourse(doc, course, courseInfoAndLessons);
					
					rootElement.appendChild(course);	
				}
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			transformer.transform((new DOMSource(doc)), (new StreamResult(new File("REPFILE/test.XML"))));


		} catch (ParserConfigurationException | TransformerException ¢) {
			¢.printStackTrace();
		}
			
	}
	
	
	private static String getRepFileAsString(){
		if(!new File(REP_FILE_HEBREW).exists())
			downloadData(); 

		String $ = null;
		try (BufferedReader repFileReader = new BufferedReader(new FileReader(REP_FILE_HEBREW))) {
			StringBuilder sb = new StringBuilder();
			for (String line = repFileReader.readLine(); line != null; line = repFileReader.readLine())
				sb.append(line).append("\n");
			$ = sb + "";
			repFileReader.close();
		} catch (IOException ¢) {
			¢.printStackTrace();
		}
		return $;
	}
	
	private static Element createCourseElement(Document d, String courseSummery){
		Element $ = d.createElement("course");
		
		Matcher courseSummeryMatcher = Pattern
				.compile("^\\|\\s*(?<CourseID>\\d{6})\\s+(?<CourseName>.*?(?=\\s*\\|))", Pattern.MULTILINE)
				.matcher(courseSummery);
		if (courseSummeryMatcher.find())
			$.setAttribute("id", courseSummeryMatcher.group("CourseID"));
			$.setAttribute("name", courseSummeryMatcher.group("CourseName"));

		return $;
	}
	
	private static void addInfoToCourse(Document d, Element course, String courseInfo){
		if (Integer.parseInt(course.getAttribute("id").substring(0, 2)) == 39)
			return;
		System.out.println(course.getAttribute("id"));
		Element examsElement = d.createElement("exams");
		course.appendChild(examsElement);
		for (String infoLine : courseInfo.split("[\\r\\n]+"))
			if (infoLine.contains("מורה")) {
				Element teacherInChargeElement = d.createElement("teacherInCharge");
				teacherInChargeElement.setAttribute("title", infoLine.substring(16, 22).replaceAll("\\s+$", ""));
				teacherInChargeElement.setAttribute("name", infoLine.substring(23, 40).replaceAll("\\s+$", ""));
				course.appendChild(teacherInChargeElement);
			} else if (infoLine.contains("ראשון")) {
				Element examElement = d.createElement("moedA");
				addTimeToExam(examElement, infoLine);
				examsElement.appendChild(examElement);
			} else if (infoLine.contains("שני")) {
				Element examElement = d.createElement("moedB");
				addTimeToExam(examElement, infoLine);
				examsElement.appendChild(examElement);
			}
	}
	
	private static void addTimeToExam(Element exam, String infoLine){
		exam.setAttribute("day", infoLine.substring(22, 24));
		exam.setAttribute("month", infoLine.substring(25, 27));
		exam.setAttribute("year", "20" + infoLine.substring(28, 30));
		exam.setAttribute("time", StringUtils.leftPad(infoLine.substring(36, 42).replaceAll("\\s+", "").replaceAll("\\.", ":"),5,'0'));
	}
	
	private enum InfoType {
		UNKNOWN, LECTURE, LECTURER, TUTORIAL, ASSISTANT, LAB, GUIDE, GROUP, MODERATOR
	}
	
	private static void addLessonsToCourse(Document d, Element course, ArrayList<String> courseLessons){
		
		if (Integer.parseInt(course.getAttribute("id").substring(0, 2)) == 39)
			return;
		System.out.println(course.getAttribute("id"));
		Element lectureElement = null;
		Element tutorialsElement = null;
		for (String courseLesson : courseLessons) {
			Element tutorialElement = null;
			Element labElement = null;
			Element groupElement = null;
			InfoType infoType = InfoType.UNKNOWN;
			for (String lessonLine : courseLesson.split("[\\r\\n]+"))
				if (lessonLine.contains("הרצאה")) {
					infoType = InfoType.LECTURE;
					lectureElement = d.createElement("lecture");
					if (!StringUtils.isBlank(lessonLine.substring(3, 5)))
						lectureElement.setAttribute("group", lessonLine.substring(3, 5));
					course.appendChild(lectureElement);
					lectureElement.appendChild(getLessonElement(d, lessonLine));
					tutorialsElement = null;
				} else if (lessonLine.contains("מרצה")) {
					infoType = InfoType.LECTURER;
					if (lectureElement != null)
						lectureElement.appendChild(getPersonElement(d, lessonLine, "lecturer"));
				} else if (lessonLine.contains("תרגיל")) {
					infoType = InfoType.TUTORIAL;
					if (lectureElement == null){
						lectureElement = d.createElement("lecture");
						course.appendChild(lectureElement);
					}
					if (tutorialsElement == null) {
						tutorialsElement = d.createElement("tutorials");
						lectureElement.appendChild(tutorialsElement);
					}
					tutorialElement = d.createElement("tutorial");
					if (!StringUtils.isBlank(lessonLine.substring(3, 5)))
						tutorialElement.setAttribute("group", lessonLine.substring(3, 5));
					tutorialsElement.appendChild(tutorialElement);
					tutorialElement.appendChild(getLessonElement(d, lessonLine));
				} else if (lessonLine.contains("מתרגל")) {
					infoType = InfoType.ASSISTANT;
					if (tutorialElement != null)
						tutorialElement.appendChild(getPersonElement(d, lessonLine, "assistant"));
				} else if (lessonLine.contains("מעבדה")) {
					infoType = InfoType.LAB;
					if (lectureElement == null){
						lectureElement = d.createElement("lecture");
						course.appendChild(lectureElement);
					}
					if (tutorialsElement == null) {
						tutorialsElement = d.createElement("tutorials");
						lectureElement.appendChild(tutorialsElement);
					}
					if (tutorialElement == null) {
						tutorialElement = d.createElement("tutorial");
						if (!StringUtils.isBlank(lessonLine.substring(3, 5)))
							tutorialElement.setAttribute("group", lessonLine.substring(3, 5));
						tutorialsElement.appendChild(tutorialElement);
					}
					labElement = d.createElement("lab");
					tutorialElement.appendChild(labElement);
					labElement.appendChild(getLessonElement(d, lessonLine));
				} else if (lessonLine.contains("מדריך")){
					infoType = InfoType.GUIDE;
					if (labElement != null)
						labElement.appendChild(getPersonElement(d, lessonLine, "guide"));
				}
				else if (lessonLine.contains("קבוצה")){
					infoType = InfoType.GROUP;
					groupElement = d.createElement("group");
					if (!StringUtils.isBlank(lessonLine.substring(3, 5))){
						groupElement.setAttribute("group", lessonLine.substring(3, 5));
						course.appendChild(groupElement);
					} else {
						if (lectureElement == null){
							lectureElement = d.createElement("lecture");
							course.appendChild(lectureElement);
						}
						if (tutorialsElement == null) {
							tutorialsElement = d.createElement("tutorials");
							lectureElement.appendChild(tutorialsElement);
						}
						if (tutorialElement == null) {
							tutorialElement = d.createElement("tutorial");
							if (!StringUtils.isBlank(lessonLine.substring(3, 5)))
								tutorialElement.setAttribute("group", lessonLine.substring(3, 5));
							tutorialsElement.appendChild(tutorialElement);
						}
						tutorialElement.appendChild(groupElement);
					}
					groupElement.appendChild(getLessonElement(d, lessonLine));
				}
				else if (lessonLine.contains("מנחה"))
					infoType = InfoType.MODERATOR;
				else if ((StringUtils.isBlank(lessonLine.substring(7, 14)) || ":".equals(lessonLine.substring(12, 13))))
					switch (infoType) {
					case LECTURE:
						if (lectureElement != null)
							lectureElement.appendChild(getLessonElement(d, lessonLine));
						break;
					case LECTURER:
						if (lectureElement != null)
							lectureElement.appendChild(getPersonElement(d, lessonLine, "lecturer"));
						break;
					case TUTORIAL:
						if (tutorialElement != null)
							tutorialElement.appendChild(getLessonElement(d, lessonLine));
						break;
					case ASSISTANT:
						if (tutorialElement != null)
							tutorialElement.appendChild(getPersonElement(d, lessonLine, "assistant"));
						break;
					case LAB:
						if (labElement != null)
							labElement.appendChild(getLessonElement(d, lessonLine));
						break;
					case GUIDE:
						if (labElement != null)
							labElement.appendChild(getPersonElement(d, lessonLine, "guide"));
						break;
					case GROUP:
						if (groupElement != null)
							groupElement.appendChild(getLessonElement(d, lessonLine));
						break;
					default:
					}
		}
	}
	
	private static Element getLessonElement(Document d, String lessonLine){
		Element $ = d.createElement("lesson");
		String day = lessonLine.substring(14,15);
		if (!StringUtils.isBlank(day))
			$.setAttribute("day", day);
		String lessonTime[] = lessonLine.substring(16, 27).replaceAll("\\s+", "").replaceAll("\\.", ":").split("\\-");
		if (lessonTime.length == 2) {
			$.setAttribute("timeStart", StringUtils.leftPad(lessonTime[1],5,'0'));
			$.setAttribute("timeEnd", StringUtils.leftPad(lessonTime[0],5,'0'));
		}
		String roomNumber = lessonLine.substring(28, 32).replaceAll("\\s+", "");
		if (!StringUtils.isBlank(roomNumber))
			$.setAttribute("roomNumber", roomNumber);
		String building = lessonLine.substring(33, 43).replaceAll("\\s+$", "");
		if (!StringUtils.isBlank(building))
			$.setAttribute("building", building);
		return $;
	}
	
	
	private static Element getPersonElement(Document d, String lessonLine, String role){
		Element $ = d.createElement(role);
		$.setAttribute("title", lessonLine.substring(14, 20).replaceAll("\\s+$", ""));
		$.setAttribute("name", lessonLine.substring(21, 40).replaceAll("\\s+$", ""));
		return $;
	}
		
	
}
