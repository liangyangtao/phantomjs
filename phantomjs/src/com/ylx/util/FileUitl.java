package com.ylx.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/**
 * @author 梁杨桃
 * 
 * */
public class FileUitl {
	private final static Logger logger = Logger.getLogger(FileUitl.class);
	static String path = "";
	static {
		try {
			path = FileUitl.class.getClassLoader().getResource("").toURI()
					.getPath();
			path = path.substring(1, path.length()) + FinalWord.RESAULT;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public List<String> read() {
		return loadUserWords((InputStream) this.getClass().getResourceAsStream(
				"ip.txt"));

	}

	public List<String> loadUserWords(InputStream input) {
		String line;
		List<String> myWords = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(input,
					"UTF-8"), 1024);
			while ((line = br.readLine()) != null) {
				line = line.trim().toLowerCase();
				myWords.add(line);
			}
			br.close();
		} catch (IOException e) {
			logger.error("WARNING: cannot open user words list!");
		}
		return myWords;
	}

	public static void writeList(List<Entry<String, Integer>> allList,
			String path1, String fileName) {
		if (path1 == null || path1.isEmpty()) {
			path1 = path;
		}
		File file = new File(path1);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {

			FileWriter fw = new FileWriter(path + fileName, true);

			fw.append(allList.get(0).toString() + "\n\r");

			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public static void writeString(String words, String path1, String fileName) {
		if (path1 == null || path1.isEmpty()) {
			path1 = path;
		}
		File file = new File(path1);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {

			FileWriter fw = new FileWriter(path + fileName, true);
			fw.append(words + "\n\r");
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
