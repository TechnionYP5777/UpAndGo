package parse;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import model.Faculty;

public class UgParser {

	
	private static final String UG_SEARCH_URL = "https://ug3.technion.ac.il/rishum/search";
	//private static final String UG_COURSE_URL = "https://ug3.technion.ac.il/rishum/course/";
	private static final String GRADUATE_SEARCH_URL = "http://www.graduate.technion.ac.il/Heb/Subjects/?SUB=";


	
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
		Document courses = null;

		Map<String,String> dataMap = new HashMap<>();
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
		dataMap.put("OPTSTUD", "");
		dataMap.put("doSearch", "Y");
		
		int count = 0;
		
		for (Faculty faculty : getFaculties()){
			try {
				dataMap.put("FAC", faculty.getId());
				courses = Scraper.getSearchResults(UG_SEARCH_URL,dataMap);			
			} catch (IOException ¢) {
				// TODO Auto-generated catch block
				¢.printStackTrace();
			}
			if (courses!=null)
				for (Element course : courses.getElementsByClass("result-row")){
					++count;
					System.out.println(course.childNode(1).childNode(1).childNode(0));
					System.out.println((course.childNode(3).childNode(0) + "").replace("\n","").substring(1));


			}
		}
		System.out.println("num of courses: " + count);		
	}
	
	
	public static void getCoursePrerequisites(String courseID){
		try {
			for (Element prerequisitesElement : Scraper.getDocumentFromURL(new URL(GRADUATE_SEARCH_URL + courseID))
					.getElementsContainingOwnText("מקצועות קדם")) {
				for (Element prerequisite : prerequisitesElement.parent().parent().parent().children())
					System.out.print(((prerequisite + "").contains("<td>ו -</td>") ? " AND "
							: (!(prerequisite + "").contains("<td>או</td>") ? "" : "} OR ") + "{")
							+ prerequisite.getElementsByTag("a").text());
				System.out.println("}");
			}
		} catch (IOException ¢) {
			¢.printStackTrace();
		}
	}
	
}
