package upandgo.server.parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Yaniv Levinsky
 * @since 03-12-16
 * 
 * Class for hebrew reversing.
 * 
 */
public class HebReverse {

	public static String reverseLine(final String line) {

		return new StringBuffer(line).reverse() + "";
	}

	public static String reverseTextNotNumbers(final String line) {

		String $ = reverseLine(line);
		for (final Matcher p = Pattern.compile("\\d+[\\/\\.\\d]*\\d+").matcher($); p.find();)
			$ = $.substring(0, p.start()) + new StringBuilder(p.group()).reverse() + ""
					+ $.substring(p.end(), $.length());
		return $;
	}

}
