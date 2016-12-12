package parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HebReverse {
	
	public static String reverseLine(String line){
		
		return new StringBuffer(line).reverse() + "";
	}

	public static String reverseTextNotNumbers(String line){
		
	    String $ = reverseLine(line);
	    for (Matcher p = Pattern.compile("\\d+[\\/\\.\\d]*\\d+").matcher($); p.find();)
			$ = $.substring(0,p.start()) + (new StringBuilder(p.group()).reverse() + "")
					+ $.substring(p.end(), $.length());
	    return $;
	}

}
