package upandgo.server.parse;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import upandgo.server.parse.UgParser;
import upandgo.shared.entities.Faculty;

public class UgParserTest {

	
	@Test
	@SuppressWarnings("static-method")
	public void testGetFaculties() {
		List<Faculty> faculties = UgParser.getFaculties();
		final List<String> ignoredIds = Arrays.asList("", "7", "99", "300", "450");
		for (String ignoredId : UgParser.ignoredFacutlyIds){
			for (Faculty faculty : faculties){
				assert(!faculty.getId().equals(ignoredId));
			}
		}
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testCoursesNamesAndID() {
		//List<String> faculties = UgParser.getCoursesNamesAndID();
		//assert(faculties.size() > 1000);
	}
	
	@Test
	@SuppressWarnings("static-method")
	public void testCreateCoursePrerequisitesElement() {
		UgParser.createCoursePrerequisitesElement("234107");
	}

	@Test
	public void testCreateCourseElement(){
		UgParser.createCourseElement("faculty","394800");
	}
	
	@Test
	public void testCreateCoursesXMLDocument(){
		UgParser.createCoursesXMLDocument();
	}
}
