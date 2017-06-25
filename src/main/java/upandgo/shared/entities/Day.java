package upandgo.shared.entities;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum Day implements IsSerializable{
	SUNDAY("ראשון","א"), MONDAY("שני","ב"), TUESDAY("שלישי","ג"), WEDNESDAY("רביעי","ד"),
	THURSDAY("חמישי","ה"), FRIDAY("שישי","ו"), SATURDAY("שבת","ש");
	
	private final String hebrewString;
	private final String hebrewLetter;
	
	private Day(final String hebrewString, final String hebrewLetter){
		this.hebrewString = hebrewString;
		this.hebrewLetter = hebrewLetter;
	}
	
	public static Day fromLetter(String text){
		for (Day day : Day.values())
			if (day.hebrewLetter.equals(text))
				return day;
		return null;
	}
	
	public String toLetter(){
		return hebrewLetter;
	}
	
	@Override
	public String toString(){
		return hebrewString;
	}
}
