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
					.compile("^\\+\\-+\\+\\n\\|\\s*(?<CourseID>\\d{6})\\s+(?<CourseName>.*?(?=\\s*\\|))", Pattern.MULTILINE)
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
			
			for (Matcher courseMatcher = Pattern
					.compile(SEPARATING_LINE+COURSE_SUMMERY+SEPARATING_LINE+COURSE_INFO_AND_LESSONS, Pattern.MULTILINE)
					.matcher(getRepFileAsString()); courseMatcher.find();){
				
				Element course = createCourseElement(doc, courseMatcher.group("CourseSummery"));
				
				ArrayList<String> courseInfoAndLessons = new ArrayList<>(Arrays.asList(
						Pattern.compile("^\\|(רישום\\s+|מס.+|\\s+)\\|\\n", Pattern.MULTILINE)
						.split(courseMatcher.group("CourseInfoAndLessons"))));
				
				@SuppressWarnings("unused")
				String courseInfo = courseInfoAndLessons.get(0);
				courseInfoAndLessons.remove(0);
				courseInfoAndLessons.removeAll(Arrays.asList("", null));
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
	
	private enum InfoType {
		UNKNOWN, LECTURE, LECTURER, TUTORIAL
	}
	
	private static void addLessonsToCourse(Document d, Element course, ArrayList<String> courseLessons){
		
		if (Integer.parseInt(course.getAttribute("id")) < 394000)
			for (String courseLesson : courseLessons) {
				String lesson[] = courseLesson.split("[\\r\\n]+");
				//System.out.println("lesson.length: " + lesson.length);
				Element lectureGroupElement = null;
				InfoType infoType = InfoType.UNKNOWN;
				for (String lessonLine : lesson){
					System.out.println(lessonLine);
					if (lessonLine.contains("הרצאה")) {
						infoType = InfoType.LECTURE;
						lectureGroupElement = d.createElement("lecture");
						course.appendChild(lectureGroupElement);
						lectureGroupElement.appendChild(getLessonElement(d, lessonLine));
					} else if (lessonLine.contains("מרצה")) {
						infoType = InfoType.LECTURER;
						if (lectureGroupElement!=null)
							lectureGroupElement.appendChild(getLecturerElement(d, lessonLine));
					} else if (lessonLine.contains("תרגיל"))
						System.out.println("Tutorial");
					else if (lessonLine.contains("מתרגל"))
						System.out.println("Asistant");
					else if (lessonLine.contains("מעבדה"))
						System.out.println("Lab");
					else if (lessonLine.contains("מדריך"))
						System.out.println("Guide");
					else if ((StringUtils.isBlank(lessonLine.substring(7, 14)) || ":".equals(lessonLine.substring(12, 13)))
							&& lectureGroupElement != null)
						switch (infoType) {
						case LECTURE:
							lectureGroupElement.appendChild(getLessonElement(d, lessonLine));
							break;
						case LECTURER:
							lectureGroupElement.appendChild(getLecturerElement(d, lessonLine));
							break;
						default:
						}
				}

			}        
	}
	
	private static Element getLessonElement(Document d, String lessonLine){
		Element $ = d.createElement("lesson");
		$.setAttribute("day", lessonLine.substring(14,15));
		String lessonTime[] = lessonLine.substring(16, 27).replaceAll("\\s+", "").replaceAll("\\.", ":").split("\\-");
		if (lessonTime.length == 2) {
			$.setAttribute("timeStart", lessonTime[1]);
			$.setAttribute("timeEnd", lessonTime[0]);
		}
		$.setAttribute("roomNumber", lessonLine.substring(28, 32).replaceAll("\\s+", ""));
		$.setAttribute("building", lessonLine.substring(33, 43).replaceAll("\\s+$", ""));
		return $;
	}
	
	private static Element getLecturerElement(Document d, String lessonLine){
		Element $ = d.createElement("lecturer");
		$.setAttribute("title", lessonLine.substring(14, 20).replaceAll("\\s+$", ""));
		$.setAttribute("name", lessonLine.substring(21, 40).replaceAll("\\s+$", ""));
		return $;
	}
		
	
}
