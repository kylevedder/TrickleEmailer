package io.vedder.mailer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.StringJoiner;

public class FileUtils {

	public static boolean fileExists(String filePath) {
		return Files.exists(Paths.get(filePath), LinkOption.NOFOLLOW_LINKS);
	}

	public static Properties readPropertiesFile(String filePath) {
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(new File(filePath));
			prop.load(fis);
			return prop;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Reads all lines from a file and returns the lines as a list.
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<String> readFileLines(String filePath) {
		Path p = Paths.get(filePath);
		try {
			return Files.readAllLines(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Reads all lines from a file and returns the contents of the file as a
	 * string
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readFileString(String filePath) {
		StringJoiner message = new StringJoiner("\n");
		readFileLines(filePath).forEach(e -> message.add(e));
		return message.toString();
	}
}
