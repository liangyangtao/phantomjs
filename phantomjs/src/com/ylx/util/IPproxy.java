package com.ylx.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author 梁杨桃
 * */
public class IPproxy {

	private static Log logger = LogFactory.getLog(IPproxy.class);

	public static void main(String[] args) {
		// getIP();
		getLocal();
	}

	// 要抓取代理的数量
	public static int num = 5;
	// 得到的原始ip
	public static List<String> ips = new ArrayList<String>();
	// 找的位置
	public static int i = 0;
	// 黑名单
	// public static List<String> filter = new ArrayList<String>();
	public static Set<String> filter = new HashSet<String>();

	// 得到可用的IP网页地址
	public static String getWebURL() {
		String url = "http://www.youdaili.cn/Daili/guonei/";
		Document page = JsoupUtil.readUrl(url);
		Elements elements = page.select("ul.newslist_line");
		Elements liElements = elements.get(0).select("li");
		Element element = liElements.get(0).select("a").get(0);
		String href = element.attr("href");
		return href;
	}

	// 得到该网页上所有的IP地址
	public static List<String> getIPs(String href) {
		ips = getDaiLiIP(href);
		return ips;
	}

	// 通过IP地址得到

	// 得到可用的代理ip
	public static List<String> getIP(int i) {
		if (i == 1) {
			getIPs(getWebURL());

		} else {
			getLocal();
		}
		List<String> proxyIps = getProxyIp(ips);
		return proxyIps;
	}

	// 寻找可以用的代理IP
	public static List<String> getProxyIp(List<String> ips) {
		List<String> proxyIPs = new ArrayList<String>();
		String myIpPage = JsoupUtil.getHtml(FinalWord.MYIPURL);
		String myip = StringHelper.mid(myIpPage, "[", "] 来自");
		logger.info("本地IP为：" + myip);
		for (String string : ips) {
			i++;
			logger.info("检测第" + i + "个  ：" + string);
			try {
				String ip = string.split(":")[0];
				if (filter.contains(ip)) {
					continue;
				}
				System.getProperties().setProperty("http.proxyHost", ip);

				System.getProperties().setProperty("http.proxyPort",
						string.split(":")[1]);
				String html = JsoupUtil.getHtml(FinalWord.MYIPURL);
				if (html.contains(myip)) {

				} else {
					filter.add(ip);
					proxyIPs.add(string);
					if (proxyIPs.size() >= num) {
						break;
					}
				}
			} catch (Exception e) {

				continue;
			}
		}

		return proxyIPs;
	}

	// 通过代理网址http://www.youdaili.cn/Daili 抓取代理IP
	public static List<String> getDaiLiIP(String url) {
		Document document = JsoupUtil.readUrl(url);
		Elements daiElements = document.select("div.cont_font");
		Elements spanElements = daiElements.get(0).select("p");
		String dailiText = spanElements.get(0).text();
		String[] ipArrays = dailiText.split(" ");
		List<String> ipList = new ArrayList<String>();
		for (String string : ipArrays) {
			if (string.contains("@")) {
				String iptemp = string.split("@")[0];
				ipList.add(iptemp);
			}
		}
		return ipList;
	}

	// 得到可用的代理IP
	public static String getNextIP() {

		String nextIP = null;
		while (true) {
			i++;
			String myIpPage = JsoupUtil.getHtml(FinalWord.MYIPURL);
			String myip = StringHelper.mid(myIpPage, "[", "] 来自");
			String string = ips.get(i);
			try {
				String ip = string.split(":")[0];
				if (filter.contains(ip)) {
					continue;
				}
				System.getProperties().setProperty("http.proxyHost", ip);
				System.getProperties().setProperty("http.proxyPort",
						string.split(":")[1]);
				String html = JsoupUtil.getHtml(FinalWord.MYIPURL);
				if (html.contains(myip)) {
					continue;
				} else {
					filter.add(ip);
					nextIP = string;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return nextIP;
	}

	public static void getLocal() {

		ips = new FileUitl().read();
		// List<String> proxyIps = getProxyIp(ips);
		// for (String string : proxyIps) {
		// logger.info(string);
		// }
		// return proxyIps;

	}
}
