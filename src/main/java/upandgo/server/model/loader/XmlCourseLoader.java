package upandgo.server.model.loader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import upandgo.server.CoursesServiceImpl;


import upandgo.shared.entities.Day;
import upandgo.shared.entities.Exam;
import upandgo.shared.entities.Faculty;
import upandgo.shared.entities.Lesson;
import upandgo.shared.entities.LocalTime;
import upandgo.shared.entities.StuffMember;
import upandgo.shared.entities.WeekTime;
import upandgo.shared.entities.Lesson.Type;
import upandgo.shared.entities.course.Course;

import com.allen_sauer.gwt.log.client.Log;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.SequenceInputStream;

/**
 * 
 * @author kobybs
 * @since 12-12-16
 * 
 *        Class for loading courses data from xml file.
 * 
 */
public class XmlCourseLoader extends CourseLoader {
	@SuppressWarnings("unused")
	private static final String CHOSEN_COURSES_PATH = "data/ChosenCourses.xml";
	@SuppressWarnings("unused")
	private static final String CHOSEN_LESSON_GROUPS = "data/ChosenLessonGroups.xml";
	@SuppressWarnings("unused")
	private static final String CHOSEN_LESSON_GROUPS_SER = "data/ChosenLessonGroups.ser";

	TreeMap<String, Course> coursesById;
	TreeMap<String, Course> coursesByName;

	private static InputStream coursesInfo;
	private static String projectId = "upandgo-168508";
	private static String bucketId = "upandgo-168508.appspot.com";
	private static String coursesInfoFilename = "test.XML";
	
	@SuppressWarnings("unused")
	private static String googleStorageCredentials = "upandgo-bf957bbe2318.json";
	  
	
	public XmlCourseLoader(final String REP_XML_PATH, boolean test_flag) {
	    super(REP_XML_PATH);
	    coursesInfoFilename = REP_XML_PATH;
	    coursesById = new TreeMap<>();
	    coursesByName = new TreeMap<>();
	    getCourses(test_flag);
	  }
	
	
	  public XmlCourseLoader(final String REP_XML_PATH) {
	    super(REP_XML_PATH);
	    coursesInfoFilename = REP_XML_PATH;
	    Log.debug(new File(".").getAbsolutePath() + "&&&&&&&&&");
	    StorageOptions.Builder optionsBuilder = StorageOptions.newBuilder();
	    
	    CoursesServiceImpl.someString += "We have got our credentials!";
	    
	    optionsBuilder.setProjectId(projectId);
	    loadCoursesInfo(optionsBuilder.build().getService(), BlobId.of(bucketId, coursesInfoFilename));
	    
	    coursesById = new TreeMap<>();
	    coursesByName = new TreeMap<>();
	    getCourses(false);
	  }

	@Override
	public HashMap<String, Course> loadCourses(@SuppressWarnings("unused") final List<String> names) {
		// Auto-generated method stub
		return null;
	}

	@Override
	public void updateCourse(@SuppressWarnings("unused") final Course __) {
		// Auto-generated method stub

	}

	@Override
	public List<String> loadAllCourseNames() {
		// Auto-generated method stub
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
	public void saveChosenCourses(final CoursesEntity e) {
		User user = UserServiceFactory.getUserService().getCurrentUser();
		
		if(user == null) {
			Log.warn("User was not signed in. selected courses could not be saved!");
			return;
		}
		
		e.setId(user.getUserId());
		CoursesServiceImpl.ofy().defer().save().entity(e);
	}

	@Override
	public CoursesEntity loadChosenCourses() {
		User user = UserServiceFactory.getUserService().getCurrentUser();

		if(user == null) {
			Log.warn("User was not signed in. selected courses could not be loaded!");
			return null;
		}
		
		final CoursesEntity $ = new CoursesEntity(user.getUserId());

		CoursesEntity ce = CoursesServiceImpl.ofy().load().type(CoursesEntity.class).id(user.getUserId()).now();
		return ce == null ? $ : ce;
	}

	@Override
	public void saveChosenLessonGroups(final ScheduleEntity e) {
		User user = UserServiceFactory.getUserService().getCurrentUser();
		
		if(user == null) {
			Log.warn("User was signed in. schedule could not be saved!");
			return;
		}
		
		e.setId(user.getUserId());
		CoursesServiceImpl.ofy().defer().save().entity(e);
	}

	@Override
	public ScheduleEntity loadChosenLessonGroups() {
		User user = UserServiceFactory.getUserService().getCurrentUser();

		if(user == null) {
			Log.warn("User was not signed in. selected courses could not be loaded!");
			return null;
		}
		
		ScheduleEntity scheduleEntity = CoursesServiceImpl.ofy().load().type(ScheduleEntity.class).id(user.getUserId()).now();
		return scheduleEntity != null ? scheduleEntity : new ScheduleEntity(user.getUserId());

	}

	public static void setStaffList(final CourseBuilder b, final Node p, final String s) {
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
				b.addStuffMember(new StuffMember(firstName, splited[splited.length - 1],
						((Element) n).getAttributes().getNamedItem("title").getNodeValue()));
			}
		}
	}

	@SuppressWarnings("resource")
	public void getCourses(boolean test_flag) {
		try {
			NodeList coursesList;
			coursesList = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(!test_flag ? coursesInfo
					: new FileInputStream(new File("resources/testXML/" + coursesInfoFilename)))
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
								: createExam(p, "moedA"));
						// get course exam's B date and time
						cb.setBTerm(((Element) p).getElementsByTagName("moedB").getLength() == 0 ? null
								: createExam(p, "moedB"));
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
								createLessonGroup(cb, n, p, "project");
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
												createLesson(n, m, 0, Day.fromLetter(((Element) m).getAttribute("day")), groupNum,
														place, Lesson.Type.LECTURE, "lecturer", ((Element) p).getAttribute("id"),
														((Element) p).getAttribute("name")));
									}
								}
							}
						}	
						final NodeList notesList = ((Element) p).getElementsByTagName("note");
						for (int n = 0 ; n < notesList.getLength() ; ++n){
							final Node note = notesList.item(n);
							if (note.getNodeType() == Node.ELEMENT_NODE)
								cb.addNote(note.getTextContent());
						}
						final Course c = cb.build();
						coursesById.put(c.getId(), c);
						coursesByName.put(c.getName(), c);
						cb.cleartutorialGroup();
					}

				cb.clearStaffMembers();
				cb.clearlecturesGroups();
				cb.clearNotes();
			}
		} catch (IOException | SAXException | ParserConfigurationException xxx) {
			xxx.printStackTrace();
		}
	}

	@SuppressWarnings("static-method")
	public Exam createExam(Node p, String examType) {
		String examTime = ((Element)p).getElementsByTagName(examType).item(0).getAttributes() .getNamedItem("time").getNodeValue();
		String examDayOfMonth = ((Element) p).getElementsByTagName(examType).item(0).getAttributes().getNamedItem("day").getNodeValue();
		String examMonth = ((Element) p).getElementsByTagName(examType).item(0).getAttributes().getNamedItem("month").getNodeValue();
		String examYear = ((Element) p).getElementsByTagName(examType).item(0).getAttributes().getNamedItem("year")	.getNodeValue();
		
		String format = examTime + " " + examDayOfMonth + "." + examMonth + "." + examYear;
		
		DateFormat df = new SimpleDateFormat("HH:mm dd.MM.yyyy");
		Calendar cal  = Calendar.getInstance();
		try {
			cal.setTime(df.parse(format));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return new Exam(examTime + " " + examDayOfMonth + " " + examMonth + " " + examYear + " "
				+ cal.get(Calendar.DAY_OF_WEEK));
	}

	public void sportParsing(final CourseBuilder b, final Node p) {
		b.setFaculty(((Element) p).getAttribute("faculty"));
		b.setPoints(Double.parseDouble(((Element) p).getAttribute("points")));
		String courseId = ((Element) p).getAttribute("id");
		setStaffList(b, p, "teacherInCharge");
		final NodeList notesList = ((Element) p).getElementsByTagName("note");
		for (int n = 0 ; n < notesList.getLength() ; ++n){
			final Node note = notesList.item(n);
			if (note.getNodeType() == Node.ELEMENT_NODE)
				b.addNote(note.getTextContent());
		}
		final NodeList sportsList = ((Element) p).getElementsByTagName("sport");
		for (int i = 0; i < sportsList.getLength(); ++i) {
			final Node n = sportsList.item(i);
			String sportCourseId = courseId;
			String sportCourseName = ((Element) n).getAttribute("name");
			b.setName(sportCourseName);
			int sportGroupNum = 99;  // should change later
			if (((Element) n).hasAttribute("group")){
				sportCourseId = courseId + "-" + ((Element) n).getAttribute("group");
				b.setId(sportCourseId);
				sportGroupNum = Integer.parseInt(((Element) n).getAttribute("group"));
			}
			final NodeList sportLessonsList = ((Element) n).getElementsByTagName("lesson");
			for (int f = 0; f < sportLessonsList.getLength() ; ++f){
				final Node h = sportLessonsList.item(f);
				if (h.getNodeType() == Node.ELEMENT_NODE
						&& Day.fromLetter(((Element) h).getAttribute("day")) != null)
					b.addTutorialGroup(sportGroupNum).addLessonToGroup(sportGroupNum,
							createLesson(n, h, i, Day.fromLetter(((Element) h).getAttribute("day")), sportGroupNum,
									((Element) h).getAttribute("building"), Type.SPORT, "assistant", sportCourseId,
									sportCourseName));
			}
			coursesById.put(((Element) p).getAttribute("id") + "-" + ((Element) n).getAttribute("group"), b.build());
			b.cleartutorialGroup();
		}
		b.clearNotes();
	}
	
	private static Type getTypeFromString(final String s){
		if ("tutorial".equals(s)){
			return Type.TUTORIAL;
		} else if ("lab".equals(s)){
			return Type.LABORATORY;
		} else if ("project".equals(s)){
			return Type.PROJECT;
		} else {
			return Type.LECTURE;
		}
	}

	public void createLessonGroup(final CourseBuilder b, final Node n, final Node p, final String s) {
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
					Lesson.Type t = getTypeFromString(s);
					final Node h = lessonList.item(f);
					final Element parentNode = (Element) ((Element) h).getParentNode();
					if (!s.equals(parentNode.getNodeName()))
						continue;
					int parentGroup = parentNode.hasAttribute("group") ? 0
							: !((Element) parentNode.getParentNode()).hasAttribute("group") ? -1
									: Integer.parseInt(((Element) parentNode.getParentNode()).getAttribute("group"));
					if (parentGroup != -1) {
						if (parentGroup == 0)
							parentGroup = Integer
									.parseInt(((Element) ((Element) h).getParentNode()).getAttribute("group"));
						if (h.getNodeType() == Node.ELEMENT_NODE
								&&  parentGroup == tutorialGroupNum) {
							String place = ((Element) h).getAttribute("building");
							if (!((Element) h).getAttribute("roomNumber").isEmpty())
								place += " " + ((Element) h).getAttribute("roomNumber");
							if (Day.fromLetter(((Element) h).getAttribute("day")) != null)
								b.addTutorialGroup(tutorialGroupNum).addLessonToGroup(tutorialGroupNum,
										createLesson(n, h, g, Day.fromLetter(((Element) h).getAttribute("day")), tutorialGroupNum,
												place, t, "assistant", ((Element) p).getAttribute("id"), ((Element) p).getAttribute("name")));
						}
					}
				}
				if ("sport".equals(s))
					return;
			}
		}

	}

	public static StuffMember findStaffByName(final CourseBuilder b, final String[] splited) {
		String firstName = "";
		for (int j = 0; j < splited.length - 1; ++j) {
			firstName += splited[j];
			if (j + 1 != splited.length - 1)
				firstName += " ";
		}
		for (final StuffMember $ : b.getStaffList())
			if ($.getFirstName().equals(firstName) && $.getLastName().equals(splited[splited.length - 1]))
				return $;
		return null;
	}

	public Lesson createLesson(final Node n, final Node h, @SuppressWarnings("unused") final int index, final Day lectureDay, final int groupNum,
			final String place, final Lesson.Type t, final String staff, final String courseId, final String courseName) {
		return new Lesson(
				((Element) n).getElementsByTagName(staff).getLength() == 0 ? null
						: findStaffByName(cb, findLGStaff(n, groupNum, staff).split(" ")),
				new WeekTime(lectureDay, LocalTime.parse("".equals(((Element) h).getAttribute("timeStart")) ? "00:00"
						: ((Element) h).getAttribute("timeStart"))),
				new WeekTime(lectureDay, LocalTime.parse("".equals(((Element) h).getAttribute("timeEnd")) ? "00:00"
						: ((Element) h).getAttribute("timeEnd"))),
				place, t, groupNum, courseId, courseName);
	}

	@SuppressWarnings("static-method")
	public String findLGStaff(final Node n, final int groupNum, final String staff) {
		for (int $ = 0; $ < ((Element) n).getElementsByTagName(staff).getLength(); ++$)
			if (((Element) ((Element) n).getElementsByTagName(staff).item($).getParentNode()).hasAttribute("group")
					&& Integer.parseInt(((Element) ((Element) n).getElementsByTagName(staff).item($).getParentNode())
							.getAttribute("group")) == groupNum)
				return ((Element) n).getElementsByTagName(staff).item($).getAttributes().getNamedItem("name")
						.getNodeValue();
		return "";
	}


	@Override
	public List<Faculty> loadFaculties() {
		final List<Faculty> $ = new ArrayList<>();
		$.add(new Faculty("00", "כל הפקולטות"));
		for (final Entry<String, Course> xxx : coursesById.entrySet()) {
			final Faculty faculty = new Faculty(xxx.getKey().substring(0, 2), xxx.getValue().getFaculty());
			if (!$.contains(faculty))
				$.add(faculty);
		}
		Collections.sort($.subList(1, $.size()), new Comparator<Faculty>() {

			@Override
			public int compare(Faculty o1, Faculty o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return $;
	}

	@Override
	public Course loadCourse(final String name) {
		return !coursesById.containsKey(name) ? null : coursesById.get(name);
	}
	
	public Course loadCourseByID(final String id) {
		return !coursesById.containsKey(id) ? null : coursesById.get(id);
	}


	private static void loadCoursesInfo(Storage s, BlobId i) {
		Blob blob = s.get(i);

		if (blob == null) {
			System.out.println("Can't download DB: No such object");
			return;
		}

		List<InputStream> streams = new ArrayList<>();

		if (blob.getSize().longValue() < 1_000_000)
			streams.add(new ByteArrayInputStream(blob.getContent()));
		else
			try (ReadChannel reader = blob.reader()) {
				for (ByteBuffer bytes = ByteBuffer.allocate(65536); reader.read(bytes) > 0;) {
					bytes.flip();
					streams.add(new ByteArrayInputStream(bytes.array()));
					bytes.clear();
				}
			} catch (@SuppressWarnings("unused") IOException e) {
				System.out.println("Can't download DB: read error");
				return;
			}

		coursesInfo = new SequenceInputStream(Collections.enumeration(streams));
	}
}