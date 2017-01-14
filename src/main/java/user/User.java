package user;

import java.util.ArrayList;
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
		courses = new ArrayList<>();
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
				String[] grade = ¢.split("\\t");
				if (grade[0].contains("םילשה אל") || Integer.parseInt(grade[0]) < 55) {
					continue;
				}	
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
