package upandgo.shared.entities;



import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;


public class Exam implements IsSerializable{
	
	private Month month;
	private String dayOfMonth;
	private String year;
	
	private int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		
	public Exam() {
		month = Month.JANUARY;
		dayOfMonth = year = "";
	}
	
	public Exam(Month month, String dayOfMonth, String year) {
		this.dayOfMonth = dayOfMonth; 
		this.month = month;
		this.year = year;
	}
	
	
	public String getDay() {
		return dayOfMonth;
	}
	
	public Month getMonth() {
		return month;
	}
	
	public String getYear() {
		return year;
	}
	
	private String compareString() {
		return year + month.toString() + dayOfMonth;
	}
	
	private static Boolean isLeap(String year) {
		int yearnum = Integer.parseInt(year);
		if (((yearnum % 4 == 0) && (yearnum % 100 != 0)) || (yearnum % 400 == 0)) {
			return true;
		}
		return false;
	}
	public int compare(Exam exam) {
		return this.compareString().compareTo(exam.compareString());
	}
	
	//the exams should be in order, exama must be early then examb
	// both exams must be in the same year - for now
	public int daysBetweenExams (Exam exama, Exam examb) {
		int res = 0;
		if (isLeap(exama.getYear())) {
			monthDays[1] = 29;
		}
		for (int i = month.toInt(exama.getMonth()); i < (month.toInt(examb.getMonth()) -1 ); i++) {
			res += monthDays[i];
		}
		res += (monthDays[month.toInt(exama.getMonth()) -1 ] - Integer.parseInt(exama.getDay())) + Integer.parseInt(examb.getDay());
		monthDays[1] = 28;
		return res;
	}
	
}
