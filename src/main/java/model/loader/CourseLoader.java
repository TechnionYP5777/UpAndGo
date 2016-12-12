package model.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.time.LocalDateTime;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.SAXException;

import model.course.Course;
import model.course.StuffMember;
import parse.RepFile;

/**
 * Abstract class for loading courses data from outside.
 * It can be implemented as e.g.: XmlCourseLoader, UrlCourseLoader, JsonLoader etc...
 * */
public abstract class CourseLoader {
	//TODO: I'm not sure, whether it should be a List or HashMap
	// apparently it's a hashMap with mapping (courseName -> course)
	protected final Course.CourseBuilder cb;
	protected final String path;
	
	public CourseLoader(String path) {
		this.cb = Course.giveCourseBuilderTo(this);
		this.path = path;
	}
	
	public abstract HashMap<String, Course> loadCourses(List<String> names);
	
	public abstract void updateCourse(Course c);
	
	/*the function gets course name and converting the course from the db to java
	 * return null if the course doesn't exists
	 */
	@SuppressWarnings({ "resource", "null" })
	public static Course loadCourse(String name) {
		if (name == null)
			throw new NullPointerException();
		File f = new File("REPFILE/REP.XML");
		//check if rep.xml already exists
		if ((!f.exists()) || (f.isDirectory()))
			RepFile.getCoursesFromRepFile();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
		    builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		    e.printStackTrace();  
		}
		org.w3c.dom.Document document = null;
		try {
		document = builder.parse(new FileInputStream("REPFILE/REP.XML"));
		} catch (SAXException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		XPath xPath =  XPathFactory.newInstance().newXPath();
		
		String exp1 = "//Course[name =\""+ name +"\"]/name";
		String exp2 = "//Course[name =\""+ name +"\"]/@id";
				
		String courseName = null;
		int courseNum = 0;
		try {
			courseName = xPath.compile(exp1).evaluate(document);
			if (courseName.isEmpty())
				return null;
			courseNum = Integer.parseInt(xPath.compile(exp2).evaluate(document));
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
		System.out.println(exp1);
		
		/*fictional!!! only for the prototype - remove after rep file contains more details*/
		List<StuffMember> st = new ArrayList<>();
		List<LocalDateTime> ot = new ArrayList<>();
		LocalDateTime temp = LocalDateTime.now();
		st.add(new StuffMember("aviv", "tsensor", "prof", "fake@fake", "taub 201",
				ot));
		return new Course(courseName, courseNum, "cs", st , 4, temp, temp);
	}
	
	public abstract HashMap<String, Course> loadAllCourses();
	
	public abstract List<String> loadAllCourseNames();
}
