package com.ylx.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.ylx.dao.impl.KeywordDaoImpl;
import com.ylx.dao.impl.SaveWeibodaoImpl;
import com.ylx.entity.Author;
import com.ylx.login.SinaLogin;
import com.ylx.util.FinalWord;
import com.ylx.util.IPproxy;
import com.ylx.util.StringHelper;

/**
 * @author 梁杨桃
 * 
 * 
 *         通过关键词抓取微博
 * */
public class KeywordAction {
	public SaveWeibodaoImpl saveWeibodaoImpl = new SaveWeibodaoImpl();
	private static Log logger = LogFactory.getLog(KeywordAction.class);
	public KeywordDaoImpl keywordDaoImpl = new KeywordDaoImpl();

	static {
		// 启动日志
		try {
			PropertyConfigurator.configure(KeywordAction.class.getClassLoader()
					.getResource("").toURI().getPath()
					+ FinalWord.LOGFILE);
			logger.info("---日志系统启动成功---");
		} catch (Exception e) {
			logger.error("日志系统启动失败:" + e);
		}
	}

	public static void main(String[] args) {
		KeywordAction keywordAction = new KeywordAction();
		keywordAction.keywordConsole(null, 0);
	}

	// 设置不同代理IP浏览器
	public WebDriver getDriver(String PROXY) {
		org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.PROXY, proxy);
		System.setProperty("webdriver.firefox.bin",
				"C:\\Program Files\\Mozilla Firefox\\firefox.exe");
		WebDriver driver = new FirefoxDriver(cap);
		return driver;
	}

	// 操作方法
	public void keywordConsole(WebDriver webdriver, int index) {

		List<String> keywords = keywordDaoImpl.readKeyWord();
		if (index == 0) {
			// 得到代理IP列表
			List<String> ips = IPproxy.getIP(1);
			List<WebDriver> drivers = new ArrayList<WebDriver>();
			// 设置代理IP
			for (String PROXY : ips) {
				logger.info("使用的IP地址为" + PROXY);
				WebDriver driver = getDriver(PROXY);
				driver.manage().window().maximize();
				drivers.add(driver);
			}
			// 得到关键词

			int i = drivers.size();
			for (String string : keywords) {
				if (i <= 0) {
					i = drivers.size();
				}
				i--;
				try {
					// 根据关键词搜索微博
					logger.info("使用的编号为" + i);
					WebDriver driver = drivers.get(i);
					boolean isDriver = searchWeibo(string, driver);
					if (!isDriver) {
						logger.info("IP地址异常" + i);
						String PROXY = IPproxy.getNextIP();
						logger.info("使用的IP地址为" + PROXY);
						WebDriver driver1 = getDriver(PROXY);
						driver1.manage().window().maximize();
						// drivers.remove(i);
						driver = null;
						drivers.set(i, driver1);
					}

				} catch (Exception e) {
					logger.info(e);
					continue;
				}
			}
			// 保存
		} else {
			for (String string : keywords) {
				searchWeibo(string, webdriver);
			}
		}

	}

	// 根据关键字搜索微博
	public boolean searchWeibo(String keyword, WebDriver driver) {

		String sousuoUrl = "http://s.weibo.com/";
		driver.get(sousuoUrl);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		WebElement weiboElement = driver.findElement(By
				.xpath("//*[@id=\"pl_searchHead\"]/div/ul/li/a[2]"));
		weiboElement.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		WebElement keywordElement = driver.findElement(By
				.className("searchInp_form"));
		keywordElement.sendKeys(keyword);

		WebElement buttonElement = driver
				.findElement(By.className("searchBtn"));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		buttonElement.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String sourcePage = driver.getPageSource();

		if (sourcePage.contains("你的行为有些异常")) {
			driver.quit();
			return false;
		}
		saveWebo(sourcePage);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// List<String> urls = getNext(sousuoUrl, 10);
		//
		// for (String string : urls) {
		// driver.get(string);
		// try {
		// Thread.sleep(3000);
		// } catch (InterruptedException e1) {
		// e1.printStackTrace();
		// }
		// String temp = driver.getPageSource();
		// boolean isExit = saveWebo(temp);
		// if (isExit) {
		// break;
		// }
		// }
		// 得到下一页
		// WebElement userElement = null;
		// while (true) {
		// try {
		// userElement = driver.findElement(By.linkText("下一页"));
		// break;
		// } catch (Exception e) {
		// try {
		// Thread.sleep(3000);
		// } catch (InterruptedException e1) {
		// e1.printStackTrace();
		// }
		// userElement = driver.findElement(By.linkText("下一页"));
		// }
		//
		// }

		// while (userElement != null) {
		// userElement.click();
		// try {
		// Thread.sleep(5000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// String temp = driver.getPageSource();
		// saveWebo(temp);
		// try {
		// userElement = driver.findElement(By.linkText("下一页"));
		// } catch (Exception e) {
		// logger.info("获取下一页失败" + e);
		// return;
		// }
		// }
		return true;
	}

	// 得到微博页码URL
	public List<String> getNext(String sourcurl, int num) {
		List<String> urls = new ArrayList<String>();
		for (int i = 2; i < num; i++) {
			urls.add(sourcurl + "&page=" + i);
		}
		return urls;
	}

	// 通过关键词搜索微博
	public boolean saveWebo(String sourcePage) {
		boolean isExit = false;
		// List<Author> authors = new ArrayList<Author>();
		Document doc = Jsoup.parse(sourcePage);
		Elements feed_listsElements = doc.select("div.feed_lists");
		if (feed_listsElements.size() == 0) {
			return isExit = true;

		}
		Element feed_lists = feed_listsElements.get(0);
		Elements feed_listsElement = feed_lists.select("dl.feed_list");
		for (Element element : feed_listsElement) {
			String mid = element.attr("mid");
			isExit = saveWeibodaoImpl.isMidExit(mid);
			if (isExit) {
				break;
			}
			logger.info(mid);
			// 得到发微博者信息
			Elements faceElements = element.select("dt.face");
			Element aElement = faceElements.get(0).select("a").get(0);
			String author_url = aElement.attr("href");
			logger.info(author_url);
			String suda_date = aElement.attr("suda-data");
			String author_id = suda_date.split(":")[1];
			logger.info(author_id);
			String author_nickName = aElement.attr("title");
			logger.info(author_nickName);
			Element author_ImgElement = aElement.select("img").get(0);
			String author_img_url = author_ImgElement.attr("src");
			logger.info(author_img_url);
			// 得到包含关键词的微博内容
			Element contentElement = element.select("dd.content").get(0);
			Element emElement = contentElement.select("em").get(0);
			String content = emElement.text();
			logger.info(content);
			// 得到微博中的图片

			Elements imgsElement = element.select("ul.piclist");
			StringBuffer image = new StringBuffer();
			if (imgsElement.size() != 0) {
				Elements liElement = imgsElement.get(0).select("li");
				for (int i = 0; i < liElement.size(); i++) {
					Element content_ImgElement = liElement.get(i);
					Element img = content_ImgElement.select("img").get(0);
					if (i == liElement.size() - 1) {
						image.append(img.attr("src"));
					} else {
						image.append(img.attr("src") + "@@@");
					}
				}
			}

			String content_image = image.toString();
			logger.info(content_image);
			// 得到发布的时间
			Element pubElement = element.select("p.info").get(0);
			Elements pubAElement = pubElement.select("a");
			Element timeElement = pubAElement.get(pubAElement.size() - 2);
			String news_time = timeElement.attr("title");
			logger.info(news_time);
			Element followElement = pubAElement.get(pubAElement.size() - 1);
			String follow = followElement.text();
			logger.info(follow);
			Author author = new Author();
			author.setAuthor_id(author_id);
			author.setAuthor_img_url(author_img_url);
			author.setAuthor_nickName(author_nickName);
			author.setAuthor_url(author_url);
			// author.setAuto_id(auto_id);
			author.setFollow(follow);
			author.setMid(mid);
			author.setNews_time(StringHelper.stringToDate(news_time));
			author.setContent(content);
			author.setContent_image(content_image);
			// 保存微博
			saveWeibodaoImpl.saveAuthor(author);
			// authors.add(author);
		}
		// 保存微博
		// saveWeibodaoImpl.saveAuthor(authors);
		return isExit;
	}
}
