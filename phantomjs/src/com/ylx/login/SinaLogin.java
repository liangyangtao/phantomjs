package com.ylx.login;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.ylx.action.KeywordAction;
import com.ylx.action.UserAction;

/**
 * @author 梁杨桃
 * 
 *         登陆新浪微博
 * */

public class SinaLogin {

	private static Log logger = LogFactory.getLog(SinaLogin.class);

	// 搜索用户的浏览器
	// private static WebDriver driver = SinaLogin.getInstence();
	// private static WebDriver driver2 = SinaLogin.getInstence2();

	// 初始化浏览器
	// private static WebDriver getInstence() {
	// if (driver == null) {
	// System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
	// driver = new ChromeDriver();
	// driver.manage().window().maximize();
	// }
	// return driver;
	// }
	//
	// private static WebDriver getInstence2() {
	// if (driver2 == null) {
	// System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
	// driver2 = new ChromeDriver();
	// driver2.manage().window().maximize();
	// }
	// return driver;
	// } System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

	public void console(int i) {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		// 登陆成功以后 ，去取博主发表的微博
		if (i == 1) {
			boolean isLogin = false;
			// 如果没有登陆成功，一直登陆
			while (isLogin == false) {
				打开，填上你的用户名和密码
//				isLogin = loginSina("", "weibocom", driver);
			}
			userConsole(driver);
		} else {
			boolean isLogin = false;
			// 如果没有登陆成功，一直登陆
			while (isLogin == false) {
				打开，填上你的用户名和密码
//				isLogin = loginSina("", "weibocom", driver);
			}
			keywordConsole(driver);
		}

	}

	private void userConsole(WebDriver driver) {
		new UserAction().userConsole(driver);
	}

	public void keywordConsole(WebDriver driver) {
		new KeywordAction().keywordConsole(driver, 1);
	}

	// 登录新浪微博
	public boolean loginSina(String username, String password, WebDriver driver) {
		driver.get("http://weibo.com/login.php");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		WebElement userElement = driver.findElement(By.name("username"));
		WebElement passwordElement = driver.findElement(By.name("password"));
		WebElement buttonElement = driver.findElement(By.className("W_btn_g"));
		userElement.sendKeys(username);
		passwordElement.sendKeys(password);
		buttonElement.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (driver.getCurrentUrl().contains("weibo.com/signup/signup.php")
				|| driver.getPageSource().contains("下次自动登录")) {
			logger.info("登陆失败");
			return false;
		}
		return true;
	}

}
