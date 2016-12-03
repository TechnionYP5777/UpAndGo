package parse;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
 
/**
 * This utility extracts files and directories of a standard zip file to
 * a destination directory.
 * @author www.codejava.net
 *
 */
public class UnzipUtility {
    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;
    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists())
			destDir.mkdir();
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))){
            for (ZipEntry entry = zipIn.getNextEntry(); entry != null;) {
    			String filePath = destDirectory + File.separator + entry.getName();
    			if (!entry.isDirectory())
    				extractFile(zipIn, filePath);
    			else
    				(new File(filePath)).mkdir();
    			zipIn.closeEntry();
    			entry = zipIn.getNextEntry();
    		}
            zipIn.close();
        }
    }
    /**
     * Extracts a zip entry (file entry)
     * @param s
     * @param filePath
     * @throws IOException
     */
    private static void extractFile(ZipInputStream s, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))){
            byte[] bytesIn = new byte[BUFFER_SIZE];
            for (int read = s.read(bytesIn); read != -1; read = s.read(bytesIn))
    			bos.write(bytesIn, 0, read);
            bos.close();	
        }
    }
}