package user;

import java.util.List;

import model.course.Course;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import catalog.Catalog;

public class User {
	List<Course> courses;
	Catalog catalog;
	String name;
	
	public User(String grades, Catalog c) {
		catalog = c;
		int count = 0;
		Boolean flag = false;
		for (String ¢ : grades.split("\\r?\\n")) {
			if(count++ == 7) {
				¢ = new StringBuffer(¢).reverse().toString();
				name = ¢.substring(3, ¢.length());
			}
			if (flag) {
				if(¢.contains("עצוממ") || ¢.equals("") || ¢.contains("רוטפ") || ¢.contains("-")) {
					flag = false;
					continue;
				}
				System.out.println(¢);
				

			}
			if (¢.contains("ןויצ")) {
				flag = true;
			}
		}
		

	}
	
	public String getName() {
		return name;
	}
}
