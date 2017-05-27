package upandgo.shared.entities;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum Month implements IsSerializable{
	JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE,
	JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;
	
	Month(){}
	
	public static int toInt(Month month) {
		switch (month) {
		case JANUARY:
			return 1;
		case FEBRUARY:
			return 2;
		case MARCH:
			return 3;
		case APRIL:
			return 4;
		case MAY:
			return 5;
		case JUNE:
			return 6;
		case JULY:
			return 7; 
		case AUGUST:
			return 8;
		case SEPTEMBER:
			return 9;
		case OCTOBER:
			return 10;
		case NOVEMBER:
			return 11;
		default:
			return 12;
		}
	}
	
	public static String toString(Month month) {
		switch (month) {
		case JANUARY:
			return "01";
		case FEBRUARY:
			return "02";
		case MARCH:
			return "03";
		case APRIL:
			return "04";
		case MAY:
			return "05";
		case JUNE:
			return "06";
		case JULY:
			return "07"; 
		case AUGUST:
			return "08";
		case SEPTEMBER:
			return "09";
		case OCTOBER:
			return "10";
		case NOVEMBER:
			return "11";
		default:
			return "12";
		}
	}
}
