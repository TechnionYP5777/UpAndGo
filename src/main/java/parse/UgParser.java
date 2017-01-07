package parse;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Element;

import model.Faculty;

public class UgParser {

	
	private static final String UG_SEARCH_URL = "https://ug3.technion.ac.il/rishum/search";
	
	public static List<Faculty> getFaculties(){
		List<Faculty> $ = new ArrayList<>();
		List<String> ignoredIds = Arrays.asList("", "7", "99", "300", "450");

		try {
			for (Element faculty : Scraper.getDocumentFromURL(new URL(UG_SEARCH_URL))
					.select("select[name=\"FAC\"] option"))
				if (!ignoredIds.contains(faculty.attr("value")))
					$.add(new Faculty(faculty.attr("value"), faculty.text()));
				
		} catch (IOException ¢) {
			¢.printStackTrace();
		}
		
		return $;
		
	}
}
