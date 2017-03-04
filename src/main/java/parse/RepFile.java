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
import java.util.LinkedList;
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

	private static final String SEPARATING_LINE = "^\\+\\-+\\+(\\n|\\Z)";
	private static final String SEPARATING_DOUBLE_LINE = "^\\+\\=+\\+\\n";
	private static final String FACULTY_NAME = "(?<FacultyName>.+?)";
	// private static final String FACULTY_COURSES =
	// "(?<FacultyCourses>.+?(?=^\\+\\=+\\+\\n))";
	private static final String FACULTY_COURSES = "(?<FacultyCourses>.+?(?=(^\\s*$)|\\Z))";
	private static final String COURSE_SUMMERY = "(?<CourseSummery>.+?\\n.+?\\n)";
	private static final String COURSE_INFO_AND_LESSONS = "(?<CourseInfoAndLessons>(.+?\\n)+?(?=" + SEPARATING_LINE
			+ "))";

	public static void downloadData() {
		try {
			final URL repFileUrl = new URL(REP_FILE_URL);
			final File repFileLocal = new File(REP_FILE_ZIP);
			FileUtils.copyURLToFile(repFileUrl, repFileLocal);
			if (repFileLocal.exists() && !repFileLocal.isDirectory())
				UnzipUtility.unzip(repFileLocal + "", REP_FILE_DIR);
		} catch (final MalformedURLException e1) {
			e1.printStackTrace();
		} catch (final IOException ¢) {
			¢.printStackTrace();
		}
		processRepFile();
	}

	public static void printRepFile() {
		final Charset ibm862charset = Charset.forName("IBM862");

		final File repy = new File(REP_FILE_DOS);
		if (repy.exists())
			try (FileInputStream fis = new FileInputStream(repy);
					InputStreamReader reader = new InputStreamReader(fis, ibm862charset);
					BufferedReader buffReader = new BufferedReader(reader)) {
				for (String line = buffReader.readLine(); line != null; line = buffReader.readLine())
					System.out.println(line);
				buffReader.close();
			} catch (final IOException ¢) {
				¢.printStackTrace();
			}
	}

	public static void processRepFile() {
		final Charset ibm862charset = Charset.forName("IBM862");

		final File repyIn = new File(REP_FILE_DOS), repyOut = new File(REP_FILE_HEBREW);
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
			} catch (final IOException ¢) {
				¢.printStackTrace();
			}
	}

	public static void getCoursesNamesAndIds() {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final Document doc = factory.newDocumentBuilder().newDocument();
			final Element rootElement = doc.createElement("Courses");
			doc.appendChild(rootElement);

			for (final Matcher regexMatcher = Pattern
					.compile("^\\+\\-+\\+\\n\\|\\s*(?<CourseID>\\d{6})\\s+(?<CourseName>)", Pattern.MULTILINE)
					.matcher(getRepFileAsString()); regexMatcher.find();) {
				// System.out.println(regexMatcher.group("CourseID") + " " +
				// regexMatcher.group("CourseName"));
				final Element course = doc.createElement("Course");
				course.setAttribute("id", regexMatcher.group("CourseID"));
				final Element courseName = doc.createElement("name");
				courseName.appendChild(doc.createTextNode(regexMatcher.group("CourseName")));
				course.appendChild(courseName);
				rootElement.appendChild(course);
			}

			final Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(doc), new StreamResult(new File(REP_FILE_XML)));

		} catch (ParserConfigurationException | TransformerException ¢) {
			¢.printStackTrace();
		}

	}

	public static void getCoursesInfoFromRepFile() {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final Document doc = factory.newDocumentBuilder().newDocument();
			final Element rootElement = doc.createElement("courses");
			doc.appendChild(rootElement);

			for (final Matcher facultyMatcher = Pattern
					.compile(SEPARATING_DOUBLE_LINE + FACULTY_NAME + SEPARATING_DOUBLE_LINE + FACULTY_COURSES,
							Pattern.MULTILINE + Pattern.DOTALL)
					.matcher(getRepFileAsString()); facultyMatcher.find();) {
				System.out.println(facultyMatcher.group("FacultyName"));
				for (final Matcher courseMatcher = Pattern
						.compile(SEPARATING_LINE + COURSE_SUMMERY + SEPARATING_LINE + COURSE_INFO_AND_LESSONS,
								Pattern.MULTILINE)
						.matcher(facultyMatcher.group("FacultyCourses")); courseMatcher.find();) {

					final Element course = createCourseElement(doc, courseMatcher.group("CourseSummery"));
					course.setAttribute("faculty",
							facultyMatcher.group("FacultyName").substring(15, 43).replaceAll("\\s+$", ""));

					final ArrayList<String> courseInfoAndLessons = new ArrayList<>(
							Arrays.asList(Pattern.compile("^\\|(רישום\\s+|מס.+|\\s+)\\|\\n", Pattern.MULTILINE)
									.split(courseMatcher.group("CourseInfoAndLessons"))));

					final String courseInfo = courseInfoAndLessons.get(0);
					courseInfoAndLessons.remove(0);
					courseInfoAndLessons.removeAll(Arrays.asList("", null));
					addInfoToCourse(doc, course, courseInfo);
					addLessonsToCourse(doc, course, courseInfoAndLessons);
					addNotesToCourse(doc, course, courseInfo);
					rootElement.appendChild(course);
				}
			}

			final Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(doc), new StreamResult(new File("REPFILE/test.XML")));

		} catch (ParserConfigurationException | TransformerException ¢) {
			¢.printStackTrace();
		}

	}

	private static boolean isSportCourse(final String id) {
		return "3948".equals(id.substring(0, 4)) || "3949".equals(id.substring(0, 4));
	}

	private static String getRepFileAsString() {
		if (!new File(REP_FILE_HEBREW).exists())
			downloadData();

		String $ = null;
		try (BufferedReader repFileReader = new BufferedReader(new FileReader(REP_FILE_HEBREW))) {
			final StringBuilder sb = new StringBuilder();
			for (String line = repFileReader.readLine(); line != null; line = repFileReader.readLine())
				sb.append(line).append("\n");
			$ = sb + "";
			repFileReader.close();
		} catch (final IOException ¢) {
			¢.printStackTrace();
		}
		return $;
	}

	private static Element createCourseElement(final Document d, final String courseSummery) {
		final Element $ = d.createElement("course");

		final Matcher courseSummeryMatcher = Pattern
				.compile("^\\|\\s*(?<CourseID>\\d{6})\\s+(?<CourseName>.*?(?=\\s*\\|))", Pattern.MULTILINE)
				.matcher(courseSummery);
		if (courseSummeryMatcher.find())
			$.setAttribute("id", courseSummeryMatcher.group("CourseID"));
		$.setAttribute("name", courseSummeryMatcher.group("CourseName"));

		final String[] courseSummeryLines = courseSummery.split("\\n");
		if (courseSummeryLines.length == 2)
			if (isSportCourse($.getAttribute("id"))) {
				$.setAttribute("points", courseSummeryLines[1].substring(46, 49));
				$.setAttribute("hours", courseSummeryLines[1].substring(31, 32));
			} else {
				$.setAttribute("points", courseSummeryLines[1].substring(40, 43));
				if (!" ".equals(courseSummeryLines[1].substring(21, 22)))
					$.setAttribute("hoursLecture", courseSummeryLines[1].substring(21, 22));
				if (!" ".equals(courseSummeryLines[1].substring(25, 26)))
					$.setAttribute("hoursTutorial", courseSummeryLines[1].substring(25, 26));
				if (!" ".equals(courseSummeryLines[1].substring(29, 30)))
					$.setAttribute("hoursLab", courseSummeryLines[1].substring(29, 30));
				if (!" ".equals(courseSummeryLines[1].substring(33, 34)))
					$.setAttribute("hoursProject", courseSummeryLines[1].substring(33, 34));
			}

		return $;
	}

	private static void addInfoToCourse(final Document d, final Element course, final String courseInfo) {

		if (isSportCourse(course.getAttribute("id"))) {
			System.out.println(course.getAttribute("id"));
			course.setAttribute("faculty", "מקצועות ספורט");
			for (final String infoLine : courseInfo.split("[\\r\\n]+"))
				if (infoLine.contains("מורה")) {
					final Element teacherInChargeElement = d.createElement("teacherInCharge");
					teacherInChargeElement.setAttribute("title", infoLine.substring(22, 26).replaceAll("\\s+$", ""));
					teacherInChargeElement.setAttribute("name", infoLine.substring(29, 45).replaceAll("\\s+$", ""));
					course.appendChild(teacherInChargeElement);
				}
		} else {
			final Element examsElement = d.createElement("exams");
			for (final String infoLine : courseInfo.split("[\\r\\n]+"))
				if (infoLine.contains("מורה  אחראי")) {
					final Element teacherInChargeElement = d.createElement("teacherInCharge");
					teacherInChargeElement.setAttribute("title", infoLine.substring(16, 22).replaceAll("\\s+$", ""));
					teacherInChargeElement.setAttribute("name", infoLine.substring(23, 40).replaceAll("\\s+$", ""));
					course.appendChild(teacherInChargeElement);
				} else if (infoLine.contains("מועד ראשון :")) {
					final Element examElement = d.createElement("moedA");
					addTimeToExam(examElement, infoLine);
					examsElement.appendChild(examElement);
				} else if (infoLine.contains("מועד שני   :")) {
					final Element examElement = d.createElement("moedB");
					addTimeToExam(examElement, infoLine);
					examsElement.appendChild(examElement);
				}
			if (examsElement.hasChildNodes())
				course.appendChild(examsElement);
		}

	}

	private static void addNotesToCourse(final Document d, final Element course, final String courseInfo) {

		final int notePoint = isSportCourse(course.getAttribute("id")) ? 9 : 3;
		final LinkedList<String> notes = new LinkedList<>();
		for (final String infoLine : courseInfo.split("[\\r\\n]+"))
			if (".".equals(StringUtils.substring(infoLine, notePoint, notePoint + 1)))
				notes.add(infoLine.substring(notePoint + 1).replaceAll("\\s+\\|$", ""));
			else if (!notes.isEmpty())
				notes.add(notes.removeLast() + " " + infoLine.substring(notePoint + 1).replaceAll("\\s+\\|$", ""));
		if (notes.isEmpty())
			return;
		final Element notesElement = d.createElement("notes");
		course.appendChild(notesElement);
		for (final String note : notes) {
			final Element noteElement = d.createElement("note");
			noteElement.appendChild(d.createTextNode(note));
			notesElement.appendChild(noteElement);
		}

	}

	private static void addTimeToExam(final Element exam, final String infoLine) {
		exam.setAttribute("day", infoLine.substring(22, 24));
		exam.setAttribute("month", infoLine.substring(25, 27));
		exam.setAttribute("year", "20" + infoLine.substring(28, 30));
		exam.setAttribute("time",
				StringUtils.isBlank(infoLine.substring(36, 42)) ? "00:00"
						: StringUtils.leftPad(infoLine.substring(36, 42).replaceAll("\\s+", "").replaceAll("\\.", ":"),
								5, '0'));
	}

	private enum InfoType {
		UNKNOWN, LECTURE, LECTURER, TUTORIAL, ASSISTANT, LAB, GUIDE, GROUP, MODERATOR
	}

	private static void addLessonsToCourse(final Document d, final Element course,
			final ArrayList<String> courseLessons) {

		if (isSportCourse(course.getAttribute("id"))) {

			for (final String courseLesson : courseLessons) {
				Element sportElement = null;
				for (final String lessonLine : courseLesson.split("[\\r\\n]+"))
					if (sportElement != null)
						sportElement.appendChild(getSportLessonElement(d, lessonLine));
					else {
						sportElement = d.createElement("sport");
						sportElement.setAttribute("group", lessonLine.substring(9, 11));
						sportElement.setAttribute("name", lessonLine.substring(12, 28).replaceAll("\\s+$", ""));
						sportElement.appendChild(getSportLessonElement(d, lessonLine));
						course.appendChild(sportElement);
					}

			}
			return;
		}

		Element lectureElement = null, tutorialsElement = null;
		for (final String courseLesson : courseLessons) {
			Element tutorialElement = null, labElement = null, groupElement = null;
			InfoType infoType = InfoType.UNKNOWN;
			for (final String lessonLine : courseLesson.split("[\\r\\n]+"))
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
					if (lectureElement == null) {
						lectureElement = d.createElement("lecture");
						course.appendChild(lectureElement);
					}
					if (tutorialsElement == null) {
						tutorialsElement = d.createElement("tutorials");
						lectureElement.appendChild(tutorialsElement);
					}
					tutorialElement = d.createElement("tutorial");
					if (!StringUtils.isBlank(lessonLine.substring(3, 5)))
						setGroupAttr(lectureElement, tutorialElement, lessonLine.substring(3, 5));
					tutorialsElement.appendChild(tutorialElement);
					tutorialElement.appendChild(getLessonElement(d, lessonLine));
				} else if (lessonLine.contains("מתרגל")) {
					infoType = InfoType.ASSISTANT;
					if (tutorialElement != null)
						tutorialElement.appendChild(getPersonElement(d, lessonLine, "assistant"));
				} else if (lessonLine.contains("מעבדה")) {
					infoType = InfoType.LAB;
					labElement = d.createElement("lab");
					labElement.appendChild(getLessonElement(d, lessonLine));
					if (lectureElement == null) {
						lectureElement = d.createElement("lecture");
						course.appendChild(lectureElement);
					}
					if (tutorialsElement == null) {
						tutorialsElement = d.createElement("labs");
						lectureElement.appendChild(tutorialsElement);
					}
					if (tutorialElement != null)
						tutorialElement.appendChild(labElement);
					else {
						if (!StringUtils.isBlank(lessonLine.substring(3, 5)))
							setGroupAttr(lectureElement, labElement, lessonLine.substring(3, 5));
						tutorialsElement.appendChild(labElement);
					}
				} else if (lessonLine.contains("מדריך")) {
					infoType = InfoType.GUIDE;
					if (labElement != null)
						labElement.appendChild(getPersonElement(d, lessonLine, "guide"));
				} else if (lessonLine.contains("קבוצה")) {
					infoType = InfoType.GROUP;
					groupElement = d.createElement("group");
					if (!StringUtils.isBlank(lessonLine.substring(3, 5))) {
						setGroupAttr(lectureElement, groupElement, lessonLine.substring(3, 5));
						(lectureElement == null ? course : lectureElement).appendChild(groupElement);
					} else {
						if (lectureElement == null) {
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
								setGroupAttr(lectureElement, tutorialElement, lessonLine.substring(3, 5));
							tutorialsElement.appendChild(tutorialElement);
						}
						tutorialElement.appendChild(groupElement);
					}
					groupElement.appendChild(getLessonElement(d, lessonLine));
				} else if (lessonLine.contains("מנחה")) {
					infoType = InfoType.MODERATOR;
					if (groupElement != null)
						groupElement.appendChild(getPersonElement(d, lessonLine, "moderator"));
				} else if (StringUtils.isBlank(lessonLine.substring(7, 14)) || ":".equals(lessonLine.substring(12, 13)))
					switch (infoType) {
					case ASSISTANT:
						if (tutorialElement != null)
							tutorialElement.appendChild(getPersonElement(d, lessonLine, "assistant"));
						break;
					case GROUP:
						if (groupElement != null)
							groupElement.appendChild(getLessonElement(d, lessonLine));
						break;
					case GUIDE:
						if (labElement != null)
							labElement.appendChild(getPersonElement(d, lessonLine, "guide"));
						break;
					case LAB:
						if (labElement != null)
							labElement.appendChild(getLessonElement(d, lessonLine));
						break;
					case LECTURE:
						if (lectureElement != null)
							lectureElement.appendChild(getLessonElement(d, lessonLine));
						break;
					case LECTURER:
						if (lectureElement != null)
							lectureElement.appendChild(getPersonElement(d, lessonLine, "lecturer"));
						break;
					case MODERATOR:
						if (groupElement != null)
							groupElement.appendChild(getPersonElement(d, lessonLine, "moderator"));
						break;
					case TUTORIAL:
						if (tutorialElement != null)
							tutorialElement.appendChild(getLessonElement(d, lessonLine));
						break;
					default:
						break;
					}
		}
	}

	private static Element getLessonElement(final Document d, final String lessonLine) {
		final Element $ = d.createElement("lesson");
		final String day = lessonLine.substring(14, 15);
		if (!StringUtils.isBlank(day))
			$.setAttribute("day", day);
		final String lessonTime[] = lessonLine.substring(16, 27).replaceAll("\\s+", "").replaceAll("\\.", ":")
				.split("\\-");
		if (lessonTime.length == 2) {
			$.setAttribute("timeStart", StringUtils.leftPad(lessonTime[1], 5, '0'));
			$.setAttribute("timeEnd", StringUtils.leftPad(lessonTime[0], 5, '0'));
		}
		final String roomNumber = lessonLine.substring(28, 32).replaceAll("\\s+", "");
		if (!StringUtils.isBlank(roomNumber))
			$.setAttribute("roomNumber", roomNumber);
		final String building = lessonLine.substring(33, 43).replaceAll("\\s+$", "");
		if (!StringUtils.isBlank(building))
			$.setAttribute("building", building);
		return $;
	}

	private static Element getSportLessonElement(final Document d, final String lessonLine) {
		final Element $ = d.createElement("lesson");
		$.setAttribute("day", lessonLine.substring(29, 30));
		final String lessonTime[] = lessonLine.substring(31, 42).replaceAll("\\s+", "").replaceAll("\\.", ":")
				.split("\\-");
		if (lessonTime.length == 2) {
			$.setAttribute("timeStart", StringUtils.leftPad(lessonTime[1], 5, '0'));
			$.setAttribute("timeEnd", StringUtils.leftPad(lessonTime[0], 5, '0'));
		}
		$.setAttribute("building", lessonLine.substring(43, 53).replaceAll("\\s+$", ""));
		return $;
	}

	private static Element getPersonElement(final Document d, final String lessonLine, final String role) {
		final Element $ = d.createElement(role);
		$.setAttribute("title", lessonLine.substring(14, 20).replaceAll("\\s+$", ""));
		$.setAttribute("name", lessonLine.substring(21, 40).replaceAll("\\s+$", ""));
		return $;
	}

	private static void setGroupAttr(final Element lectureElement, final Element hasGroupElement, final String group) {
		hasGroupElement.setAttribute("group", group);
		if (lectureElement != null && !lectureElement.hasAttribute("group"))
			lectureElement.setAttribute("group", String.valueOf(10 * (Integer.parseInt(group) % 10)));
	}

}
