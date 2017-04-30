package upandgo.server.parse;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 
 * @author Yaniv Levinsky
 * @since 07-01-17
 * 
 * Class for loading files from server.
 * 
 */
public class Scraper {

	public static Document getDocumentFromURL(final URL xxx) throws IOException {
		return Jsoup.parse(xxx, 15000);
		// return Jsoup.connect((xxx + "")).timeout(15000).get();
	}

	public static Document getSearchResults(final String xxx, final Map<String, String> dataMap) throws IOException {

		return Jsoup.connect(xxx).userAgent("Mozilla/5.0").timeout(15000).data(dataMap).post();
	}

}
