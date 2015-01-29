package com.ylx.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ylx.dao.impl.SaveWeibodaoImpl;
import com.ylx.dao.impl.UserDaoImpl;
import com.ylx.entity.Author;
import com.ylx.util.StringHelper;

/**
 * @author 梁杨桃
 * 
 *         抓取用户的微博
 * */
public class UserAction {

	private static Log logger = LogFactory.getLog(UserAction.class);
	public UserDaoImpl userDaoImpl = new UserDaoImpl();
	public SaveWeibodaoImpl saveWeibodaoImpl = new SaveWeibodaoImpl();

	// 主要的操作的流程方法
	public void userConsole(WebDriver driver) {
		// 读取要抓取的人的微博url
		List<String> userUrls = userDaoImpl.readUser();

		for (String string : userUrls) {
			try {
				// 各自分析
				analyseWeibo(string, driver);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		driver.quit();

	}

	// 分析微博
	public void analyseWeibo(String url, WebDriver driver) {
		driver.get(url);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// PRF_feed_list_more SW_fun_bg S_line2
		List<WebElement> webElement = driver.findElements(By
				.className("SW_fun_bg"));

		// driver.findElement(By.linkText(""));

		for (WebElement webElement2 : webElement) {
			if (webElement2.getText().contains("更多")) {
				logger.info("点击查看更多微博");
				webElement2.click();
				break;
			}
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// <div class="W_loading" node-type="lazyload"></div>
		// 鼠标滚动到最下边 ，第一次刷新
		// driver.manage().window().getSize().height;
		((JavascriptExecutor) driver)
				.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((JavascriptExecutor) driver)
				.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((JavascriptExecutor) driver)
				.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	
		/*
		 * WebElement webElement2 =
		 * driver.findElement(By.className("W_loading"));
		 * logger.info("鼠标滚动到最下边 ，第一次刷新"); webElement2.click(); try {
		 * Thread.sleep(5000); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 */
		// 鼠标滚动到最下边，第二次刷新
		/*
		 * WebElement webElement1 =
		 * driver.findElement(By.className("W_loading")); webElement1.click();
		 * try { Thread.sleep(5000); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 */
		String page = driver.getPageSource();
		boolean isExit = false;
		// 解析保存页面
		isExit = saveWeibo(page);
		if (isExit) {
			// 如果mid已经存在，就不调到下一页了
			return;
		} else {

			// 下一页
			// W_pages
			List<WebElement> nextPageElement = driver.findElements(By
					.className("W_btn_c"));
			if (nextPageElement.size() != 0) {
				boolean isNextExit = false;
				for (WebElement webElement3 : nextPageElement) {
					if (webElement3.getText().contains("下一页")) {
						webElement3.click();
						isNextExit = true;
						break;
					}
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (isNextExit) {
					String nextURL = driver.getCurrentUrl();
					logger.info(nextURL);
					analyseWeibo(nextURL, driver);
				}
			}

		}

	}

	// 解析并保存微博
	public boolean saveWeibo(String html) {
		boolean isExit = false;
		Document document = Jsoup.parse(html);
		// WB_feed WB_feed_self
		Elements prfDocument = document.select("div.WB_feed");
		logger.info(prfDocument.size());
		if (prfDocument.size() == 0) {
			return isExit = true;
		}
		List<Author> authors = new ArrayList<Author>();
		Elements feedElements = prfDocument.get(0).select("div.WB_feed_type");
		logger.info(feedElements.size());
		for (Element element : feedElements) {
			String mid = element.attr("mid");
			isExit = saveWeibodaoImpl.isMidExit(mid);
			// 如果存在就直接忽略以后的
			if (isExit) {
				break;
			}
			logger.info(mid);

			// ouid=3576802365&rouid=3817447416
			String author_id = StringHelper.getStringByReg(
					element.attr("tbinfo"), "\\d{1,20}");
			logger.info(author_id);
			Element textElement = element.select("div.WB_text").get(0);
			String content = textElement.text();

			// WB_media_expand
			Elements expandElements = element.select("div.WB_media_expand");
			if (expandElements.size() != 0) {
				Elements expandElement = expandElements.get(0).select(
						"div.WB_text");
				if (expandElement.size() != 0) {
					content = expandElement.get(0).text() + content;
				}
			}
			logger.info(content);
			// WB_media_list
			Elements media_listElements = element.select("div.WB_media_list");
			StringBuffer image = new StringBuffer();
			if (media_listElements.size() != 0) {
				Elements imgeElements = media_listElements.get(0).select(
						"img.bigcursor");
				for (int i = 0; i < imgeElements.size(); i++) {
					if (i != imgeElements.size() - 1) {
						image.append(imgeElements.get(i).attr("src") + "@@@");
					} else {
						image.append(imgeElements.get(i).attr("src"));
					}
				}
			}
			String content_image = image.toString();
			logger.info(content_image);
			Element funcElement = element.select("div.WB_from").get(0);
			String news_time = funcElement.select("a").get(0).attr("title");
			String follow = funcElement.select("a").get(1).text();
			logger.info(news_time);
			logger.info(follow);
			Author author = new Author();
			author.setAuthor_id(author_id);
			// author.setAuthor_img_url(author_img_url);
			// author.setAuthor_nickName(author_nickName);
			// author.setAuthor_url(author_url);
			// author.setAuto_id(auto_id);
			author.setFollow(follow);
			author.setMid(mid);
			author.setNews_time(StringHelper.stringToDate(news_time));
			author.setContent(content);
			author.setContent_image(content_image);
			authors.add(author);
		}
		saveWeibodaoImpl.saveAuthor(authors);
		return isExit;

	}

}
