package parse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

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
		//printRepFile();
		processRepFile();
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
	
	public static void printRepFile(){
		Charset ibm862charset = Charset.forName("IBM862");

		File repy = new File("REPFILE/REPY");
		if (repy.exists())
			try(
				FileInputStream fis = new FileInputStream(repy);
				InputStreamReader reader = new InputStreamReader(fis, ibm862charset);
				BufferedReader buffReader = new BufferedReader(reader))
			{
				for (String line = buffReader.readLine(); line != null; line = buffReader.readLine())
					System.out.println(line);
				buffReader.close();					
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
	}
	
	public static void processRepFile(){
		Charset ibm862charset = Charset.forName("IBM862");

		File repyIn = new File("REPFILE/REPY");
		File repyOut = new File("REPFILE/REPHEB");
		if (repyIn.exists())
			try(
				FileInputStream fis = new FileInputStream(repyIn);
				InputStreamReader reader = new InputStreamReader(fis, ibm862charset);
				BufferedReader buffReader = new BufferedReader(reader);
				FileWriter fr = new FileWriter(repyOut);
				BufferedWriter buffWriter = new BufferedWriter(fr))
			{
				if (!repyOut.exists())
					repyOut.createNewFile();
				for (String line = buffReader.readLine(); line != null; line = buffReader.readLine())
					buffWriter.write(HebReverse.reverseTextNotNumbers(line) + "\n");
				buffReader.close();
				buffWriter.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
	}

}
