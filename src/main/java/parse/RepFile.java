package parse;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class RepFile {
	
	private static URL RepFileUrl;
	private static File RepFileLocal;
	
	public static void downloadRepFile(){
		try {
			RepFileUrl = new URL("http://ug3.technion.ac.il/rep/REPFILE.zip");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RepFileLocal = new File("REPFILE.zip");
		try {
			FileUtils.copyURLToFile(RepFileUrl, RepFileLocal);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}
