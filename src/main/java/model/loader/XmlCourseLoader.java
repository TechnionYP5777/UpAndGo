package model.loader;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.course.Course;
import model.course.Lesson;
import model.course.StuffMember;
import model.course.WeekTime;
import parse.RepFile;

public class XmlCourseLoader extends CourseLoader {
	private static final String REP_XML_PATH = "REPFILE/REP.XML";
	private static final String DATA_DIR_PATH = "data";
	private static final String CHOSEN_COURSES_PATH = "data/ChosenCourses.xml";
	
	
	//List<Course> coursesList;
	TreeMap<String, Course> courses;
	
	public XmlCourseLoader() {
		super(REP_XML_PATH);
		
		if (!new File(path).exists())
			RepFile.getCoursesNamesAndIds();
		
		//Create a data dir for saving changes if it does not exists
		File dataDir = new File(DATA_DIR_PATH);
		if (!dataDir.exists() || !dataDir.isDirectory())
			dataDir.mkdir();
		
		//coursesList = xmlParser.getCourses(path);
		//Get data from REP XML file.
		courses = new TreeMap<>();
		getCourses();
	}

	@Override
	public HashMap<String, Course> loadCourses(@SuppressWarnings("unused") List<String> names) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCourse(@SuppressWarnings("unused") Course __) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Course loadCourse(@SuppressWarnings("unused") String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> loadAllCourseNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeMap<String, Course> loadAllCourses() {
		return courses;
	}
	
	@Override
	public void saveChosenCourseNames(List<String> names){
        try {
    		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element rootElement = doc.createElement("ChosenCourses");
			doc.appendChild(rootElement);
			
			names.forEach(name->{
				Element course = doc.createElement("Course");
				course.appendChild(doc.createTextNode(name));
				rootElement.appendChild(course);
			});
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			transformer.transform((new DOMSource(doc)), (new StreamResult(new File(CHOSEN_COURSES_PATH))));

		} catch (ParserConfigurationException | TransformerException ¢) {
			¢.printStackTrace();
		}

	}

	@Override
	public List<String> loadChosenCourseNames() {
		if (!(new File(CHOSEN_COURSES_PATH).exists()))
			return Collections.emptyList();
		List<String> $ = new LinkedList<>();
		try {
			NodeList chosenList = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(CHOSEN_COURSES_PATH).getElementsByTagName("Course");
			for (int i = 0; i < chosenList.getLength(); ++i) {
				Node p = chosenList.item(i);
				if (p.getNodeType() == Node.ELEMENT_NODE)
					$.add(((Element) p).getTextContent());
			}
		} catch (IOException | SAXException | ParserConfigurationException ¢) {
			¢.printStackTrace();
		}
		return $;
	}
	
	private static void setStaffList (Course.CourseBuilder cb ,Node p, String s) {
		NodeList TicList = ((Element) p).getElementsByTagName(s);
		for (int k = 0; k < TicList.getLength(); ++k) {
			Node n = TicList.item(k);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				String firstName = "";
				String[] splited = (((Element) n).getAttributes()
						.getNamedItem("name").getNodeValue()).split(" ");
				for (int j=0; j < splited.length -1 ; ++j) {
					firstName += splited[j];
					if (j+1 != splited.length)
						firstName += " ";
				}
				cb.addStuffMember(new StuffMember(firstName, splited[splited.length - 1],
						((Element) n).getAttributes().getNamedItem("title").getNodeValue()));
			}	
		}
	}
	
	private void getCourses() {
		try {
			NodeList coursesList = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(REP_XML_PATH)
					.getElementsByTagName("course");
			for (int i = 0; i < coursesList.getLength(); ++i) {
				Node p = coursesList.item(i);
				if (p.getNodeType() == Node.ELEMENT_NODE)
					//get course id
					cb.setId(((Element) p).getAttribute("id"));
					//get course name
					cb.setName(((Element) p).getAttribute("name"));
					//get course points
					cb.setPoints(Double.parseDouble(((Element) p).getAttribute("points")));
					//get course exam's A date and time
					cb.setATerm(LocalDateTime.parse(
							((Element) p).getElementsByTagName("moedA").item(0).getAttributes()
							.getNamedItem("year").getNodeValue()
							+ "-"
							+ ((Element) p).getElementsByTagName("moedA").item(0).getAttributes()
									.getNamedItem("month").getNodeValue()
							+ "-"
							+ ((Element) p).getElementsByTagName("moedA").item(0).getAttributes()
									.getNamedItem("day").getNodeValue()
							+ " " + ((Element) p).getElementsByTagName("moedA").item(0).getAttributes()
									.getNamedItem("time").getNodeValue(), 
							DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
					//get course exam's B date and time
					cb.setBTerm(LocalDateTime.parse(
							((Element) p).getElementsByTagName("moedB").item(0).getAttributes()
							.getNamedItem("year").getNodeValue()
							+ "-"
							+ ((Element) p).getElementsByTagName("moedB").item(0).getAttributes()
									.getNamedItem("month").getNodeValue()
							+ "-"
							+ ((Element) p).getElementsByTagName("moedB").item(0).getAttributes()
									.getNamedItem("day").getNodeValue()
							+ " " + ((Element) p).getElementsByTagName("moedB").item(0).getAttributes()
									.getNamedItem("time").getNodeValue(), 
							DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
					//get course staff 
					setStaffList (cb, p,"teacherInCharge");
					setStaffList (cb, p,"lecturer");
					setStaffList (cb, p,"assistant");
					//get lectures group Lessons
					NodeList lectureList = ((Element) p).getElementsByTagName("lecture");
					for (int groupNum = 0, k = 0; k < lectureList.getLength(); ++k) {
						++groupNum;
						Node n = lectureList.item(k);
						if (n.getNodeType() == Node.ELEMENT_NODE) {
							NodeList lessonList = ((Element) n).getElementsByTagName("lesson");
							for (int g = 0; g < lessonList.getLength(); ++g) {
								Node m = lessonList.item(g);
								if ((m.getNodeType() == Node.ELEMENT_NODE) && ("lecture".equals(((Element) m).getParentNode().getNodeName()))) {
									String place = ((Element) m).getAttribute("building");
									if (!((Element) m).getAttribute("roomNumber").isEmpty())
										place += " " + ((Element) m).getAttribute("roomNumber");
									DayOfWeek lectureDay = convertStrToDay(((Element) m).getAttribute("day"));
									cb.addLectureGroup(groupNum).addLessonToGroup(groupNum,
											(new Lesson(
													findStaffByName(cb,
															(((Element) n).getElementsByTagName("lecturer").item(0).getAttributes().getNamedItem("name")
																	.getNodeValue()).split(" ")),
													new WeekTime(lectureDay,
															LocalTime.parse(((Element) m).getAttribute("timeStart"))),
													new WeekTime(lectureDay,
															LocalTime.parse(((Element) m).getAttribute("timeEnd"))),
													place, Lesson.Type.LECTURE, groupNum,
													((Element) p).getAttribute("id"))));
								}
							}
						}
					}
					
					courses.put(((Element) p).getAttribute("id"), cb.build());
					cb.clearStaffMembers();
					cb.clearlecturesGroups();
			}
		} catch (IOException | SAXException | ParserConfigurationException ¢) {
			¢.printStackTrace();
		}
	}

	private static StuffMember findStaffByName(Course.CourseBuilder cb, String[] splited) {
		String firstName = "";
		for (int j=0; j < splited.length -1 ; ++j) {
			firstName += splited[j];
			if (j+1 != splited.length)
				firstName += " ";
		}
		for (StuffMember $ : cb.getStaffList())
			if ($.getFirstName().equals(firstName) && $.getLastName().equals(splited[splited.length - 1]))
				return $;
		return null;
	}

	private static DayOfWeek convertStrToDay(String ¢) {
		switch (¢) {
		  case "א":  return DayOfWeek.valueOf("SUNDAY");
		  case "ב":  return DayOfWeek.valueOf("MONDAY");
		  case "ג":  return DayOfWeek.valueOf("TUESDAY");
		  case "ד":  return DayOfWeek.valueOf("WEDNESDAY");
		  case "ה":  return DayOfWeek.valueOf("THURSDAY");
		  case "ו":  return DayOfWeek.valueOf("FRIDAY");
		  default: return DayOfWeek.valueOf("SATURDAY");
		}
	}
	

}