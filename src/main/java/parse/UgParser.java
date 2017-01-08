package parse;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
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
	
	public static void getCoursesNamesAndID(){
		List<Faculty> faculties = getFaculties();
		Document courses = null;

		Map<String,String> dataMap = new HashMap();
		dataMap.put("CNM", "");
		dataMap.put("CNO", "");
		dataMap.put("PNT", "");
		dataMap.put("FAC", "");
		dataMap.put("LLN", "");
		dataMap.put("LFN", "");
		dataMap.put("SEM", "201602");
		dataMap.put("RECALL", "Y");
		dataMap.put("D1", "on");
		dataMap.put("D2", "on");
		dataMap.put("D3", "on");
		dataMap.put("D4", "on");
		dataMap.put("D5", "on");
		dataMap.put("D6", "on");
		dataMap.put("FTM", "");
		dataMap.put("TTM", "");
		dataMap.put("SIL", "");
		dataMap.put("OPTCAT", "on");
		dataMap.put("OPTSEM", "on");
		dataMap.put("OPTSTUD", "on");
		dataMap.put("doSearch", "Y");
		
		for (Faculty faculty : getFaculties()){
			try {
				dataMap.put("FAC", faculty.getId());
				courses = Scraper.getSearchResults(UG_SEARCH_URL,dataMap);			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Element course : courses.getElementsByClass("result-row")){
				System.out.println(course.childNode(1).childNode(1).childNode(0));
				System.out.println(course.childNode(3).childNode(0).toString().replace("\n","").substring(1));


			}
		}
		

		
		
	}
}
