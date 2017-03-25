package parse;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 
 * @author www.codejava.net
 * 
 * This utility extracts files and directories of a standard zip file to a
 * destination directory.
 *
 */
public class UnzipUtility {
	/**
	 * Size of the buffer to read/write data
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Extracts a zip file specified by the zipFilePath to a directory specified
	 * by destDirectory (will be created if does not exists)
	 * 
	 * @param zipFilePath
	 * @param destDirectory
	 * @throws IOException
	 */
	public static void unzip(final String zipFilePath, final String destDirectory) throws IOException {
		final File destDir = new File(destDirectory);
		if (!destDir.exists())
			destDir.mkdir();
		try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
			for (ZipEntry entry = zipIn.getNextEntry(); entry != null;) {
				final String filePath = destDirectory + File.separator + entry.getName();
				if (entry.isDirectory())
					new File(filePath).mkdir();
				else
					extractFile(zipIn, filePath);
				zipIn.closeEntry();
				entry = zipIn.getNextEntry();
			}
			zipIn.close();
		}
	}

	/**
	 * Extracts a zip entry (file entry)
	 * 
	 * @param s
	 * @param filePath
	 * @throws IOException
	 */
	private static void extractFile(final ZipInputStream s, final String filePath) throws IOException {
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
			final byte[] bytesIn = new byte[BUFFER_SIZE];
			for (int read = s.read(bytesIn); read != -1; read = s.read(bytesIn))
				bos.write(bytesIn, 0, read);
			bos.close();
		}
	}
}