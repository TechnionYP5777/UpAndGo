package upandgo.shared.entities;



import com.google.gwt.user.client.rpc.IsSerializable;


public class Exam implements IsSerializable{
	
	private Month month;
	private String dayOfMonth;
	private String year;
	
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
	
}
