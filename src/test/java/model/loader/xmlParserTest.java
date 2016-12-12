package model.loader;

import java.util.List;

import org.junit.Test;

import model.course.Course;

@SuppressWarnings("static-method")
public class xmlParserTest {

	@Test
	public void test() {
		List<Course> res = xmlParser.getCourses();
		//assert(!res.isEmpty());
		for(Course ¢ : res)
			System.out.println(¢.getName());
	}

}
