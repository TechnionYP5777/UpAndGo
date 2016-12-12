package parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HebReverse {
	
	public static String reverseLine(String line){
		
		return new StringBuffer(line).reverse() + "";
	}

	public static String reverseTextNotNumbers(String line){
		
	    String $ = reverseLine(line);
	    for (Matcher ¢ = Pattern.compile("\\d+[\\/\\.\\d]*\\d+").matcher($); ¢.find();)
			$ = $.substring(0,¢.start()) + (new StringBuilder(¢.group()).reverse() + "")
					+ $.substring(¢.end(), $.length());
	    return $;
	}

}
