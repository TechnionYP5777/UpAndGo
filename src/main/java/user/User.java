package user;

import java.util.List;
import catalog.Catalog;
import model.course.Course;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

public class User {
	List<Course> courses;
	Catalog catalog;
	
	User(String grades, Catalog c) {
		catalog = c;
	    for (final Element ¢ : Parser.parse(grades, "http://techmvs.technion.ac.il").select(null))
			System.out.println("#### " + ¢);

	}
}
