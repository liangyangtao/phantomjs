package com.ylx.util;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupUtil {
	private final static Logger logger = Logger.getLogger(JsoupUtil.class);

	public static Document readUrl(String url) {
		if (url == null || url.trim().isEmpty()) {
			return null;
		}

		Document doc = null;
		try {
			Connection conn = Jsoup.connect(url);
			conn.header(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2 Googlebot/2.1");

			doc = conn.timeout(200 * 1000).get();
		} catch (Exception e) {
			logger.error("读取Url" + url + "失败" + e);
			return null;
		}
		return doc;
	}

	public static boolean checkURL(String url) {
		if (url == null || url.trim().isEmpty()) {
			return false;
		}
		Document doc = null;
		Connection conn = Jsoup.connect(url);
		conn.header(
				"User-Agent",
				"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2 Googlebot/2.1");
		try {
			doc = conn.timeout(200 * 1000).get();
		} catch (Exception e) {
			logger.error("读取Url" + url + "失败" + e);
			return false;
		}
		if (doc == null) {
			return false;
		} else {
			return true;
		}
	}

	public static String getHtml(String address) {
		StringBuffer html = new StringBuffer();
		String result = null;
		try {
			URL url = new URL(address);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 7.0; NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)");
			BufferedInputStream in = new BufferedInputStream(
					conn.getInputStream());

			try {
				String inputLine;
				byte[] buf = new byte[4096];
				int bytesRead = 0;
				while (bytesRead >= 0) {
					inputLine = new String(buf, 0, bytesRead, "ISO-8859-1");
					html.append(inputLine);
					bytesRead = in.read(buf);
					inputLine = null;
				}
				buf = null;
			} finally {
				in.close();
				conn = null;
				url = null;
			}
			result = new String(html.toString().trim().getBytes("ISO-8859-1"),
					"gb2312").toLowerCase();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			html = null;
		}
		return result;
	}
}
