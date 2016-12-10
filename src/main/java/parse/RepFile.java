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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RepFile {
	
	private static URL repFileUrl;
	private static File repFileLocal;
	
	public static void initialize(){
		try {
			repFileUrl = new URL("http://ug3.technion.ac.il/rep/REPFILE.zip");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		repFileLocal = new File("REPFILE.zip");
	}
	
	public static void downloadData(){
		downloadRepFile();
		unZipRepFile();
		//printRepFile();
		processRepFile();
	}
	
	public static void downloadRepFile(){
		try {
			FileUtils.copyURLToFile(repFileUrl, repFileLocal);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void unZipRepFile(){
		if(repFileLocal != null && repFileLocal.exists() && !repFileLocal.isDirectory())
			try {
				UnzipUtility.unzip((repFileLocal + ""), "REPFILE");
			} catch (IOException e) {
				e.printStackTrace();
			}


	}
	
	public static void printRepFile(){
		Charset ibm862charset = Charset.forName("IBM862");

		File repy = new File("REPFILE/REPY");
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

		File repyIn = new File("REPFILE/REPY");
		File repyOut = new File("REPFILE/REPHEB");
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
					.compile("^\\+\\-+\\+\\n\\|\\s*(?<CourseID>\\d{6})\\s+(?<CourseName>.*?(?=\\s{2}))", Pattern.MULTILINE)
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
			transformer.transform((new DOMSource(doc)), (new StreamResult(new File("REPFILE/REP.XML"))));

		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	private static String getRepFileAsString(){
		File repFile = new File("REPFILE/REPHEB");
		if(!repFile.exists())
			downloadData(); 

		String $ = null;
		try (
			BufferedReader repFileReader = new BufferedReader(new FileReader("REPFILE/REPHEB"));)
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
