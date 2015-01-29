package com.ylx.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * @author 梁杨桃
 * */
public class XmlUtil {
	public static Map<String, String> jdbcMap = new HashMap<String, String>();

	public static Map<String, String> getJdbcMap() {
		readXML();
		return jdbcMap;
	}

	public static void readXML() {
		try {
			String path = XmlUtil.class.getClassLoader().getResource("")
					.toURI().getPath()
					+ FinalWord.XMLURL;
			File file = new File(path);
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(file);
			Element root = document.getRootElement();
			Element jdbc = root.element(FinalWord.JDBC);
			Iterator<Element> jdbcIterator = jdbc.elementIterator();
			while (jdbcIterator.hasNext()) {
				Element recordEless = (Element) jdbcIterator.next();
				jdbcMap.put(recordEless.getName(), recordEless.getTextTrim());
			}

		} catch (DocumentException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
}
