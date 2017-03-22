package parse;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class UgParserTest {

	
	@Test
	@SuppressWarnings("static-method")
	public void test() {
		// for(Faculty ¢ : UgParser.getFaculties())
		// System.out.println(¢.getId() + " " + ¢.getName());
		// UgParser.createCoursePrerequisitesElement("236522");
		UgParser.createCoursesPrerequisitesDocument();
	}

}
