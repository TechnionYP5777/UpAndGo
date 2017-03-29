package model.course;

public enum Day {
	SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
	THURSDAY, FRIDAY, SATURDAY 
	
	public int compareTo(final WeekTime b) {
		return getDay().compareTo(b.getDay()) != 0 ? getDay().compareTo(b.getDay()) : getTime().compareTo(b.getTime());
	}

}
