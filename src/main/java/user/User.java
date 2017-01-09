package user;

import java.util.LinkedList;
import java.util.List;
import catalog.Catalog;
import model.course.Course;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.junit.Test;

public class User {
	List<Course> courses;
	Catalog catalog;
	
	User(String grades, Catalog c) {
		catalog = c;
	    for (final Element ¢ : Parser.parse(grades, "http://techmvs.technion.ac.il").select(null))
			System.out.println("#### " + ¢);

	}
}
