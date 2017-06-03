package upandgo.server;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class CalendarImpl {
	
	private static Calendar stringToCalendar(String str) {
		DateFormat df = new SimpleDateFormat("HH:mm dd.MM.yyyy");
		Calendar cal  = Calendar.getInstance();
		try {
			cal.setTime(df.parse(str));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal;
	}
	
	public static int getDay(String calendarFormat) throws ParseException {
		Calendar cal  = stringToCalendar(calendarFormat);
		
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public static int getNumOfDaysBetween(String calendara, String calendarb) throws ParseException {
		Calendar cala  = stringToCalendar(calendara);
		Calendar calb  = stringToCalendar(calendarb);
		
		long end = calb.getTimeInMillis();
	    long start = cala.getTimeInMillis();
	    
	    return (int) TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
	}

}
