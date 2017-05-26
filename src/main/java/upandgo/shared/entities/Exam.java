package upandgo.shared.entities;



import com.google.gwt.user.client.rpc.IsSerializable;


public class Exam implements IsSerializable{
	
	private LocalTime time;
	private Month month;
	private String dayOfMonth;
	private String year;
	
	private Exam() {
		time = null;
		month = Month.JANUARY;
		dayOfMonth = year = "";
	}
	
	public Exam(LocalTime time, Month month, String dayOfMonth, String year) {
		this.time = time;
		this.dayOfMonth = dayOfMonth; 
		this.month = month;
		this.year = year;
	}
	
	
	public String getDay() {
		return dayOfMonth;
	}
	
	public LocalTime getTime() {
		return time;
	}
	
	public Month getMonth() {
		return month;
	}
	
	public String getYear() {
		return year;
	}
	
}
