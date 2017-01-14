package user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.course.Course;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import catalog.Catalog;

public class User {
	Set<String> courses;
	Catalog catalog;
	String name;
	
	public User(String grades, Catalog c) {
		catalog = c;
		courses = new HashSet<>();
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
				if (grade[0].contains("םילשה אל") || Integer.parseInt(grade[0]) < 55) { //* TODO: check about not completed courses *//
					if (!grade[0].contains("םילשה אל") && courses.contains(grade[2]))
						courses.remove(grade[2]);
					continue;
				}
				courses.add(grade[2]);
			}
			if (¢.contains("ןויצ"))
				flag = true;
		}
		System.out.print(courses);
	}
	
	public String getName() {
		return name;
	}
}
