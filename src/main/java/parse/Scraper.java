package parse;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Scraper {
	
	public static Document getDocumentFromURL(URL ¢) throws IOException{
		return Jsoup.parse(¢, 15000);
	}

}
