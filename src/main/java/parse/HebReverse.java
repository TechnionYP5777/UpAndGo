package parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HebReverse {
	
	public static String reverseLine(String line){
		
		String reversed = new StringBuffer(line).reverse().toString();
		return reversed;
	}
	
	
	public static String reverseTextNotNumbers(String line){
		
	    String res = reverseLine(line);
	    Matcher matcher = Pattern.compile("\\d+[\\/\\.\\d]*\\d+").matcher(res);
	    while (matcher.find()) {
	        String number = matcher.group();
	        String reveresedNumber = new StringBuilder(number).reverse().toString();
		    System.out.println(reveresedNumber);
	        res = res.substring(0,matcher.start()) + reveresedNumber + res.substring(matcher.end(),res.length());
	        
	    }
	    System.out.println(res);
	    return res;
	}
/*	
	public static String reverseOnlyHebrew(String line){
		
	    String res = line;
	    Matcher matcher = Pattern.compile("[\\u0590-\\u05FF]+( [\\u0590-\\u05FF[\\-]]+)+[\\u0590-\\u05FF]").matcher(res);
	    while (matcher.find()) {
	        String number = matcher.group();
	        String reveresedNumber = new StringBuilder(number).reverse().toString();
		    System.out.println(reveresedNumber);
	        res = res.substring(0,matcher.start()) + reveresedNumber + res.substring(matcher.end(),res.length());
	        
	    }
	    System.out.println(res);
	    return res;
	}*/
}
