package parse;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Scraper {
	
	public static Document getDocumentFromURL(URL ¢) throws IOException{
		return Jsoup.parse(¢, 15000);
		//return Jsoup.connect((¢ + "")).timeout(15000).get();
	}
	
	public static Document getSearchResults(String ¢, Map<String,String> dataMap) throws IOException{


		return Jsoup.connect(¢).userAgent("Mozilla/5.0").timeout(15000).data(dataMap).post();
	}

}
