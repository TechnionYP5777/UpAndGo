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
import org.apache.commons.lang3.ArrayUtils;
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
	

	public static void getCoursesFromRepFile(){
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
		int count = 0;
		for (Matcher courseMatcher = Pattern
				.compile(SEPARATING_LINE+COURSE_SUMMERY+SEPARATING_LINE+COURSE_INFO_AND_LESSONS, Pattern.MULTILINE)
				.matcher(getRepFileAsString()); courseMatcher.find();){
			System.out.println(++count);
			//System.out.println(courseMatcher.group("CourseSummery") + courseMatcher.group("CourseInfo"));
			Matcher courseSummeryMatcher = Pattern
					.compile("^\\|\\s*(?<CourseID>\\d{6})\\s+(?<CourseName>.*?(?=\\s*\\|))", Pattern.MULTILINE)
					.matcher(courseMatcher.group("CourseSummery"));
			if (courseSummeryMatcher.find())
				System.out.println(
						courseSummeryMatcher.group("CourseID") + " " + courseSummeryMatcher.group("CourseName"));
			String[] courseInfoAndLessons = Pattern.compile("^\\|(רישום\\s+|\\s+)\\|\\n", Pattern.MULTILINE)
					.split(courseMatcher.group("CourseInfoAndLessons"));
			@SuppressWarnings("unused")
			String courseInfo = courseInfoAndLessons[0];
			String[] courseLessons = ArrayUtils.remove(courseInfoAndLessons, 0);
	        System.out.println("CourseLessons.length = " + courseLessons.length);
	        //System.out.println(CourseInfo);
	        //Check if the course isn't a sports (as they will require separate parsing)
	        if (Integer.parseInt(courseSummeryMatcher.group("CourseID")) < 394000)
				for (String courseLesson : courseLessons) {
					String lesson[] = courseLesson.split("[\\r\\n]+");
					System.out.println("lesson.length: " + lesson.length);
					for (String lessonLine : lesson)
						if (lessonLine.contains("הרצאה")) {
							System.out.println("Lacture");
							System.out.println("day: " + lessonLine.charAt(14));
							String lessonTimeText = lessonLine.substring(16, 27).replaceAll("\\s+", "");
							System.out.println(lessonTimeText);
							String lessonTime[] = lessonTimeText.replaceAll("\\.", ":").split("\\-");
							if (lessonTime.length == 2) {
								System.out.println("start at: " + lessonTime[1]);
								System.out.println("ends at: " + lessonTime[0]);
							}
							System.out.println("room number: " + lessonLine.substring(28, 32).replaceAll("\\s+", ""));
							System.out.println("building: " + lessonLine.substring(33, 43).replaceAll("\\s+$", ""));
						} else if (lessonLine.contains("מרצה")) {
							System.out.println("title: " + lessonLine.substring(14, 20).replaceAll("\\s+$", ""));
							System.out.println("name: " + lessonLine.substring(21, 40).replaceAll("\\s+$", ""));
						} else if (lessonLine.contains("תרגיל"))
							System.out.println("Tutorial");
						else if (lessonLine.contains("מתרגל"))
							System.out.println("Asistant");
						else if (lessonLine.contains("מעבדה"))
							System.out.println("Lab");
						else if (lessonLine.contains("מדריך"))
							System.out.println("Guide");
						else if (StringUtils.isBlank(lessonLine.substring(7, 14))) {
							System.out.println("day: " + lessonLine.charAt(14));
							String lessonTimeText = lessonLine.substring(16, 27).replaceAll("\\s+", "");
							System.out.println(lessonTimeText);
							String lessonTime[] = lessonTimeText.replaceAll("\\.", ":").split("\\-");
							if (lessonTime.length == 2) {
								System.out.println("start at: " + lessonTime[1]);
								System.out.println("ends at: " + lessonTime[0]);
							}
							System.out.println("room number: " + lessonLine.substring(28, 32).replaceAll("\\s+", ""));
							System.out.println("building: " + lessonLine.substring(33, 43).replaceAll("\\s+$", ""));
						}
				}        
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
	

}
