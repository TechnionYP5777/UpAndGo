package parse;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class RepFile {
	
	private static URL repFileUrl;
	private static File repFileLocal;
	
	public static void initialize(){
		try {
			repFileUrl = new URL("http://ug3.technion.ac.il/rep/REPFILE.zip");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		repFileLocal = new File("REPFILE.zip");
	}
	
	public static void getData(){
		downloadRepFile();
		unZipRepFile();
	}
	
	public static void downloadRepFile(){
		try {
			FileUtils.copyURLToFile(repFileUrl, repFileLocal);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void unZipRepFile(){
		if(repFileLocal != null && repFileLocal.exists() && !repFileLocal.isDirectory())
			try {
				UnzipUtility.unzip((repFileLocal + ""), "REPFILE");
			} catch (IOException e) {
				e.printStackTrace();
			}


	}
	

}
