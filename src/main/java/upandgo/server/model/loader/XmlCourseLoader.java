package upandgo.server.model.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.Consumer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import upandgo.server.parse.RepFile;
import upandgo.shared.entities.Day;
import upandgo.shared.entities.Faculty;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.StuffMember;
import upandgo.shared.entities.WeekTime;
import upandgo.shared.entities.Lesson.Type;
import upandgo.shared.entities.course.Course;

/**
 * 
 * @author kobybs
 * @since 12-12-16
 * 
 * Class for loading courses data from xml file.
 * 
 */
public class XmlCourseLoader extends CourseLoader {
	private static String REP_XML_PATH; // = "REPFILE/REP.XML"
	private static final String DATA_DIR_PATH = "data";
	private static final String CHOSEN_COURSES_PATH = "data/ChosenCourses.xml";
	private static final String CHOSEN_LESSON_GROUPS = "data/ChosenLessonGroups.xml";
	private static final String CHOSEN_LESSON_GROUPS_SER = "data/ChosenLessonGroups.ser";

	// List<Course> coursesList;
	TreeMap<String, Course> coursesById;
	TreeMap<String, Course> coursesByName;

	public XmlCourseLoader(final String REP_XML_PATH) {
		super(REP_XML_PATH);
		XmlCourseLoader.REP_XML_PATH = REP_XML_PATH;

		if (!new File(path).exists())
			RepFile.getCoursesNamesAndIds();

		// Create a data dir for saving changes if it does not exists
		final File dataDir = new File(DATA_DIR_PATH);
		if (!dataDir.exists() || !dataDir.isDirectory())
			dataDir.mkdir();

		// coursesList = xmlParser.getCourses(path);
		// Get data from REP XML file.
		coursesById = new TreeMap<>();
		coursesByName = new TreeMap<>();
		getCourses();
	}

	@Override
	public HashMap<String, Course> loadCourses(@SuppressWarnings("unused") final List<String> names) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCourse(@SuppressWarnings("unused") final Course __) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> loadAllCourseNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeMap<String, Course> loadAllCoursesById() {
		return coursesById;
	}

	@Override
	public TreeMap<String, Course> loadAllCoursesByName() {
		return coursesByName;
	}

	@Override
	public void saveChosenCourseNames(final List<String> names) {
		try {
			final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			final Element rootElement = doc.createElement("ChosenCourses");
			doc.appendChild(rootElement);

			names.forEach(new Consumer<String>() {
				@Override
				public void accept(String name) {
					final Element course = doc.createElement("Course");
					course.appendChild(doc.createTextNode(name));
					rootElement.appendChild(course);
				}
			});

			final Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			transformer.transform(new DOMSource(doc), new StreamResult(new File(CHOSEN_COURSES_PATH)));

		} catch (ParserConfigurationException | TransformerException xxx) {
			xxx.printStackTrace();
		}

	}

	@Override
	public List<String> loadChosenCourseNames() {
		if (!new File(CHOSEN_COURSES_PATH).exists())
			return Collections.emptyList();
		final List<String> $ = new LinkedList<>();
		// TODO use try-with-resource
		try {
			final NodeList chosenList = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(CHOSEN_COURSES_PATH).getElementsByTagName("Course");
			for (int i = 0; i < chosenList.getLength(); ++i) {
				final Node p = chosenList.item(i);
				if (p.getNodeType() == Node.ELEMENT_NODE)
					$.add(((Element) p).getTextContent());
			}
		} catch (IOException | SAXException | ParserConfigurationException xxx) {
			xxx.printStackTrace();
		}
		return $;
	}

	@Override
	public void saveChosenLessonGroups(final List<LessonGroup> gs) {
		try {
			final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			final Element rootElement = doc.createElement("ChosenLessonGroups");
			doc.appendChild(rootElement);

			gs.forEach(new Consumer<LessonGroup>() {
				@Override
				public void accept(LessonGroup group) {
					final Element groupElement = doc.createElement("lessonGroup");
					groupElement.setAttribute("courseID", group.getCourseID());
					groupElement.setAttribute("groupNum", String.valueOf(group.getGroupNum()));
					rootElement.appendChild(groupElement);
				}
			});

			final Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			transformer.transform(new DOMSource(doc), new StreamResult(new File(CHOSEN_LESSON_GROUPS)));

		} catch (ParserConfigurationException | TransformerException xxx) {
			xxx.printStackTrace();
		}
		// TODO Use try with resource
		try {
			final FileOutputStream fileOut = new FileOutputStream(CHOSEN_LESSON_GROUPS_SER);
			final ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(gs);
			out.close();
			fileOut.close();
		} catch (final IOException xxx) {
			xxx.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LessonGroup> loadChosenLessonGroups() {
		if (!new File(CHOSEN_LESSON_GROUPS).exists())
			return Collections.emptyList();
		List<LessonGroup> $ = new LinkedList<>();
		try {
			final NodeList chosenList = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(CHOSEN_LESSON_GROUPS).getElementsByTagName("lessonGroup");
			for (int i = 0; i < chosenList.getLength(); ++i) {
				final Node p = chosenList.item(i);
				if (p.getNodeType() == Node.ELEMENT_NODE)
					addLessonsToLessonGroup(new LessonGroup(Integer.parseInt(((Element) p).getAttribute("groupNum"))),
							((Element) p).getAttribute("courseID"), ((Element) p).getAttribute("groupNum"));
			}
		} catch (IOException | SAXException | ParserConfigurationException xxx) {
			xxx.printStackTrace();
		}
		// TODO: Use try-with-resources, and enable this warning --yg
		try {
			final FileInputStream fileIn = new FileInputStream(CHOSEN_LESSON_GROUPS_SER);
			final ObjectInputStream in = new ObjectInputStream(fileIn);
			$ = (List<LessonGroup>) in.readObject();
			in.close();
			fileIn.close();
		} catch (final IOException xxx) {
			xxx.printStackTrace();
			return $;
		} catch (final ClassNotFoundException xxx) {
			System.out.println("LessonGroup class not found");
			xxx.printStackTrace();
			return $;
		}

		return $;

	}

	private static void addLessonsToLessonGroup(final LessonGroup g, final String courseID, final String groupNum) {
		try {
			final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(REP_XML_PATH);
			final XPathExpression lessonExpr = XPathFactory.newInstance().newXPath().compile("lesson");
			final Element courseElement = (Element) ((NodeList) XPathFactory.newInstance().newXPath()
					.compile("//course[@id=\"" + courseID + "\"]").evaluate(doc, XPathConstants.NODESET)).item(0);
			System.out.println(courseElement.getAttribute("name"));
			System.out.println(courseElement.getAttribute("id"));
			System.out.println(groupNum);
			final List<Node> lessonGroupList = new ArrayList<>();
			final NodeList lectureList = courseElement.getElementsByTagName("lecture");
			for (int xxx = 0; xxx < lectureList.getLength(); ++xxx)
				lessonGroupList.add(lectureList.item(xxx));
			final NodeList tutorialList = courseElement.getElementsByTagName("tutorial");
			for (int xxx = 0; xxx < tutorialList.getLength(); ++xxx)
				lessonGroupList.add(tutorialList.item(xxx));
			final NodeList labList = courseElement.getElementsByTagName("lab");
			for (int xxx = 0; xxx < labList.getLength(); ++xxx)
				lessonGroupList.add(labList.item(xxx));
			lessonGroupList.forEach(new Consumer<Node>() {
				@Override
				public void accept(Node groupNode) {
					if (((Element) groupNode).getAttribute("group").equals(groupNum))
						try {
							final NodeList lessonList = (NodeList) lessonExpr.evaluate(groupNode, XPathConstants.NODESET);
							for (int f = 0; f < lessonList.getLength(); ++f) {
								final Node h = lessonList.item(f);
								if (h.getNodeType() == Node.ELEMENT_NODE)
									g.addLesson(new Lesson(new StuffMember("temp", "temp"), new WeekTime(
											convertStrToDay(((Element) h).getAttribute("day")),
											LocalTime.parse("".equals(((Element) h).getAttribute("timeStart")) ? "00:00"
													: ((Element) h).getAttribute("timeStart"))),
											new WeekTime(convertStrToDay(((Element) h).getAttribute("day")), LocalTime
													.parse("".equals(((Element) h).getAttribute("timeEnd")) ? "00:00"
															: ((Element) h).getAttribute("timeEnd"))),
											"nowhere", Lesson.Type.LECTURE, Integer.parseInt(groupNum),
											courseElement.getAttribute("id"), courseElement.getAttribute("name")));
							}
						} catch (final XPathExpressionException xxx) {
							xxx.printStackTrace();
						}
				}
			});
			System.out.println(g.getLessons().size());
		} catch (XPathExpressionException | SAXException | ParserConfigurationException | IOException xxx) {
			xxx.printStackTrace();
		}

	}

	private static void setStaffList(final CourseBuilder cb, final Node p, final String s) {
		final NodeList TicList = ((Element) p).getElementsByTagName(s);
		for (int k = 0; k < TicList.getLength(); ++k) {
			final Node n = TicList.item(k);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				String firstName = "";
				final String[] splited = ((Element) n).getAttributes().getNamedItem("name").getNodeValue().split(" ");
				for (int j = 0; j < splited.length - 1; ++j) {
					firstName += splited[j];
					if (j + 1 != splited.length - 1)
						firstName += " ";
				}
				cb.addStuffMember(new StuffMember(firstName, splited[splited.length - 1],
						((Element) n).getAttributes().getNamedItem("title").getNodeValue()));
			}
		}
	}

	private void getCourses() {
		try {
			final NodeList coursesList = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(REP_XML_PATH)
					.getElementsByTagName("course");
			for (int i = 0; i < coursesList.getLength(); ++i) {
				final Node p = coursesList.item(i);
				if (p.getNodeType() == Node.ELEMENT_NODE)
					if ("מקצועות ספורט".equals(((Element) p).getAttribute("faculty")))
						sportParsing(cb, p);
					else {
						// get course id
						cb.setId(((Element) p).getAttribute("id"));
						// get course name
						cb.setName(((Element) p).getAttribute("name"));
						// get course faculty
						cb.setFaculty(((Element) p).getAttribute("faculty"));
						// get course points
						cb.setPoints(Double.parseDouble(((Element) p).getAttribute("points")));
						// get course exam's A date and time
						cb.setATerm(((Element) p).getElementsByTagName("moedA").getLength() == 0 ? null
								: LocalDateTime.parse(
										((Element) p).getElementsByTagName("moedA").item(0).getAttributes()
												.getNamedItem("year").getNodeValue()
												+ "-"
												+ ((Element) p).getElementsByTagName("moedA").item(0).getAttributes()
														.getNamedItem("month").getNodeValue()
												+ "-"
												+ ((Element) p).getElementsByTagName("moedA").item(0).getAttributes()
														.getNamedItem("day").getNodeValue()
												+ " "
												+ ((Element) p).getElementsByTagName("moedA").item(0).getAttributes()
														.getNamedItem("time").getNodeValue(),
										DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
						// get course exam's B date and time
						cb.setBTerm(((Element) p).getElementsByTagName("moedB").getLength() == 0 ? null
								: LocalDateTime.parse(
										((Element) p).getElementsByTagName("moedB").item(0).getAttributes()
												.getNamedItem("year").getNodeValue()
												+ "-"
												+ ((Element) p).getElementsByTagName("moedB").item(0).getAttributes()
														.getNamedItem("month").getNodeValue()
												+ "-"
												+ ((Element) p).getElementsByTagName("moedB").item(0).getAttributes()
														.getNamedItem("day").getNodeValue()
												+ " "
												+ ((Element) p).getElementsByTagName("moedB").item(0).getAttributes()
														.getNamedItem("time").getNodeValue(),
										DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
						// get course staff
						setStaffList(cb, p, "teacherInCharge");
						setStaffList(cb, p, "lecturer");
						setStaffList(cb, p, "assistant");
						setStaffList(cb, p, "moderator");
						setStaffList(cb, p, "guide");
						// get lectures and tutorial group Lessons
						final NodeList lectureList = ((Element) p).getElementsByTagName("lecture");
						for (int groupNum = 0, k = 0; k < lectureList.getLength(); ++k) {
							final Node n = lectureList.item(k);
							groupNum = !((Element) n).hasAttribute("group") ? groupNum + 10
									: Integer.parseInt(((Element) n).getAttribute("group"));
							if (n.getNodeType() == Node.ELEMENT_NODE) {
								createLessonGroup(cb, n, p, "tutorial");
								createLessonGroup(cb, n, p, "lab");
								final NodeList lessonList = ((Element) n).getElementsByTagName("lesson");
								for (int g = 0; g < lessonList.getLength(); ++g) {
									final Node m = lessonList.item(g);
									if (m.getNodeType() == Node.ELEMENT_NODE
											&& "lecture".equals(((Element) m).getParentNode().getNodeName())
											&& ((Element) m).hasAttributes()) {
										String place = ((Element) m).getAttribute("building");
										if (!((Element) m).getAttribute("roomNumber").isEmpty())
											place += " " + ((Element) m).getAttribute("roomNumber");
										cb.addLectureGroup(groupNum).addLessonToGroup(groupNum,
												createLesson(n, m, p, 0,
														convertStrToDay(((Element) m).getAttribute("day")), groupNum,
														place, Lesson.Type.LECTURE, "lecturer"));
									}
								}
							}
						}
						final Course c = cb.build();
						coursesById.put(c.getId(), c);
						coursesByName.put(c.getName(), c);
						cb.cleartutorialGroup();
					}

				cb.clearStaffMembers();
				cb.clearlecturesGroups();

			}
		} catch (IOException | SAXException | ParserConfigurationException xxx) {
			xxx.printStackTrace();
		}
	}

	private void sportParsing(final CourseBuilder b, final Node p) {
		b.setFaculty(((Element) p).getAttribute("faculty"));
		b.setPoints(Double.parseDouble(((Element) p).getAttribute("points")));
		final String courseNum = ((Element) p).getAttribute("id");
		setStaffList(b, p, "teacherInCharge");
		final NodeList sportsList = ((Element) p).getElementsByTagName("sport");
		for (int i = 0; i < sportsList.getLength(); ++i) {
			final Node n = sportsList.item(i);
			b.setName(((Element) n).getAttribute("name"));
			b.setId(courseNum + "-" + ((Element) n).getAttribute("group"));
			createLessonGroup(b, p, p, "sport");
			coursesById.put(((Element) p).getAttribute("id") + "-" + ((Element) n).getAttribute("group"), cb.build());
			cb.cleartutorialGroup();
		}
	}

	private void createLessonGroup(final CourseBuilder b, final Node n, final Node p, final String s) {
		final NodeList tutorialList = ((Element) n).getElementsByTagName(s);
		for (int g = 0; g < tutorialList.getLength(); ++g) {
			final Node m = tutorialList.item(g);
			if (m.getNodeType() == Node.ELEMENT_NODE) {
				int tutorialGroupNum = 30;
				if ("".equals(((Element) m).getAttribute("group")))
					++tutorialGroupNum;
				else
					tutorialGroupNum = Integer.parseInt(((Element) m).getAttribute("group"));
				//
				final NodeList lessonList = ((Element) n).getElementsByTagName("lesson");
				for (int f = 0; f < lessonList.getLength(); ++f) {
					Lesson.Type t = "lab".equals(s) ? Type.LABORATORY : "sport".equals(s) ? Type.SPORT : Type.TUTORIAL;
					final Node h = lessonList.item(f);
					final Element parentNode = (Element) ((Element) h).getParentNode();
					if ("lab".equals(parentNode.getNodeName()))
						t = Type.LABORATORY;
					int parentGroup = parentNode.hasAttribute("group") ? 0
							: !((Element) parentNode.getParentNode()).hasAttribute("group") ? -1
									: Integer.parseInt(((Element) parentNode.getParentNode()).getAttribute("group"));
					if (parentGroup != -1) {
						if (parentGroup == 0)
							parentGroup = Integer
									.parseInt(((Element) ((Element) h).getParentNode()).getAttribute("group"));
						if (h.getNodeType() == Node.ELEMENT_NODE
								&& /*
									 * (s.equals(((Element)
									 * h).getParentNode().getNodeName())) &&
									 */ parentGroup == tutorialGroupNum) {
							String place = ((Element) h).getAttribute("building");
							if (!((Element) h).getAttribute("roomNumber").isEmpty())
								place += " " + ((Element) h).getAttribute("roomNumber");
							if (convertStrToDay(((Element) h).getAttribute("day")) != Day.SATURDAY)
								b.addTutorialGroup(tutorialGroupNum).addLessonToGroup(tutorialGroupNum,
										createLesson(n, h, p, g, convertStrToDay(((Element) h).getAttribute("day")),
												tutorialGroupNum, place, t, "assistant"));
						}
					}
				}
				if ("sport".equals(s))
					return;
			}
		}

	}

	private static StuffMember findStaffByName(final CourseBuilder cb, final String[] splited) {
		String firstName = "";
		for (int j = 0; j < splited.length - 1; ++j) {
			firstName += splited[j];
			if (j + 1 != splited.length - 1)
				firstName += " ";
		}
		for (final StuffMember $ : cb.getStaffList())
			if ($.getFirstName().equals(firstName) && $.getLastName().equals(splited[splited.length - 1]))
				return $;
		return null;
	}

	private Lesson createLesson(final Node n, final Node h, final Node p, final int index, final Day lectureDay,
			final int groupNum, final String place, final Lesson.Type t, final String staff) {
		return new Lesson(
				((Element) n).getElementsByTagName(staff).getLength() == 0 ? null
						: findStaffByName(cb, findLGStaff(n, groupNum, staff).split(" ")),
				new WeekTime(lectureDay, LocalTime.parse("".equals(((Element) h).getAttribute("timeStart")) ? "00:00"
						: ((Element) h).getAttribute("timeStart"))),
				new WeekTime(lectureDay, LocalTime.parse("".equals(((Element) h).getAttribute("timeEnd")) ? "00:00"
						: ((Element) h).getAttribute("timeEnd"))),
				place, t, groupNum, ((Element) p).getAttribute("id"), ((Element) p).getAttribute("name"));
	}

	private String findLGStaff(final Node n, final int groupNum, final String staff) {
		for (int $ = 0; $ < ((Element) n).getElementsByTagName(staff).getLength(); ++$)
			if (((Element) ((Element) n).getElementsByTagName(staff).item($).getParentNode()).hasAttribute("group")
					&& Integer.parseInt(((Element) ((Element) n).getElementsByTagName(staff).item($).getParentNode())
							.getAttribute("group")) == groupNum)
				return ((Element) n).getElementsByTagName(staff).item($).getAttributes().getNamedItem("name")
						.getNodeValue();
		return "";
	}

	private static Day convertStrToDay(final String xxx) {
		switch (xxx) {
		case "א":
			return Day.SUNDAY;
		case "ב":
			return Day.MONDAY;
		case "ג":
			return Day.TUESDAY;
		case "ד":
			return Day.WEDNESDAY;
		case "ה":
			return Day.THURSDAY;
		case "ו":
			return Day.FRIDAY;
		default:
			return Day.SATURDAY;
		}
	}

	@Override
	public List<Faculty> loadFaculties() {
		final List<Faculty> $ = new ArrayList<>();
		for (final Entry<String, Course> xxx : coursesById.entrySet()) {
			final Faculty faculty = new Faculty(xxx.getKey().substring(0, 2), xxx.getValue().getFaculty());
			if (!$.contains(faculty))
				$.add(faculty);
		}
		return $;
	}

	@Override
	public Course loadCourse(final String name) {
		return !coursesById.containsKey(name) ? null : coursesById.get(name);
	}
}