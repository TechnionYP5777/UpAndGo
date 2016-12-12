package model.loader;


import org.junit.Test;

import model.course.Course;

@SuppressWarnings("static-method")
public class xmlParserTest {

	@Test
	public void test() {
		for (Course ¢ : xmlParser.getCourses())
			System.out.println(¢.getName() + " " + ¢.getId());
	}

}
