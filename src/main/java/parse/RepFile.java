package parse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		processRepFile();
	}

	
	public static void printRepFile(){
		Charset ibm862charset = Charset.forName("IBM862");

		File repy = new File(REP_FILE_DOS);
		if (repy.exists())
			try(
				FileInputStream fis = new FileInputStream(repy);
				InputStreamReader reader = new InputStreamReader(fis, ibm862charset);
				BufferedReader buffReader = new BufferedReader(reader))
			{
				for (String line = buffReader.readLine(); line != null; line = buffReader.readLine())
					System.out.println(line);
				buffReader.close();					
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
	}
	
	public static void processRepFile(){
		Charset ibm862charset = Charset.forName("IBM862");

		File repyIn = new File(REP_FILE_DOS);
		File repyOut = new File(REP_FILE_HEBREW);
		if (repyIn.exists())
			try(
				FileInputStream fis = new FileInputStream(repyIn);
				InputStreamReader reader = new InputStreamReader(fis, ibm862charset);
				BufferedReader buffReader = new BufferedReader(reader);
				FileWriter fr = new FileWriter(repyOut);
				BufferedWriter buffWriter = new BufferedWriter(fr))
			{
				if (!repyOut.exists())
					repyOut.createNewFile();
				for (String line = buffReader.readLine(); line != null; line = buffReader.readLine())
					buffWriter.write(HebReverse.reverseTextNotNumbers(line) + "\n");
				buffReader.close();
				buffWriter.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
	}
	

	public static void getCoursesFromRepFile(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
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

		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			String[] CourseInfoAndLessons = Pattern.compile("^\\|(רישום\\s+|\\s+)\\|\\n", Pattern.MULTILINE)
					.split(courseMatcher.group("CourseInfoAndLessons"));
			String CourseInfo = CourseInfoAndLessons[0];
			String[] CourseLessons = ArrayUtils.remove(CourseInfoAndLessons, 0);
/*			if (CourseInfoMatcher.find()){
				System.out.println(CourseInfoMatcher.group("CourseLesson"));
			}*/
	        System.out.println("CourseLessons.length = " + CourseLessons.length);
		    for(String a:CourseLessons)
				System.out.println(a);
	        
	        //System.out.println(CourseInfo);
		}
			
	}
	
	private static String getRepFileAsString(){
		File repFile = new File(REP_FILE_HEBREW);
		if(!repFile.exists())
			downloadData(); 

		String $ = null;
		try (
			BufferedReader repFileReader = new BufferedReader(new FileReader(REP_FILE_HEBREW));)
		{	
			StringBuilder sb = new StringBuilder();
			for (String line = repFileReader.readLine(); line != null; line = repFileReader.readLine())
				sb.append(line).append("\n");

			$ = sb + "";
			
			repFileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return $;
	}
	

}
