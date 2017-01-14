package user;

import java.util.HashSet;
import java.util.Set;

import catalog.Catalog;

public class User {
	Set<String> courses;
	Catalog catalog;
	String name;

	public User(String grades, Catalog c) {
		catalog = c;
		courses = new HashSet<>();
		int count = 0;
		boolean flag = false;
		for (String ¢ : grades.split("\\r?\\n")) {
			if (count++ == 7) {
				¢ = new StringBuffer(¢).reverse().toString();
				name = ¢.substring(3, ¢.length());
			}
			if (flag) {
				if (¢.contains("עצוממ") || ¢.equals("") || ¢.contains("רוטפ") || ¢.contains("-")) {
					flag = false;
					continue;
				}
				String[] grade = ¢.split("\\t");
				if (grade[0].contains("םילשה אל") || Integer.parseInt(grade[0]) < 55) {
					if (!grade[0].contains("םילשה אל") && courses.contains(grade[2]))
						courses.remove(grade[2]);
					continue;
				}
				courses.add(grade[2]);
			}
			if (¢.contains("ןויצ"))
				flag = true;
		}
	}

	public String getName() {
		return name;
	}
}
