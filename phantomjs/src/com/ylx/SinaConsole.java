package com.ylx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ylx.util.FinalWord;

/**
 * @author 梁杨桃
 * 
 * 
 *         主程序
 * */

public class SinaConsole {

	private static Log log = LogFactory.getLog(SinaConsole.class);

	static {
		// 启动日志
		try {
			PropertyConfigurator.configure(SinaConsole.class.getClassLoader()
					.getResource("").toURI().getPath()
					+ FinalWord.LOGFILE);
			log.info("---日志系统启动成功---");
		} catch (Exception e) {
			log.error("日志系统启动失败:" + e);
		}
	}

	public static void main(String[] args) {
		// ApplicationContext context =
		new ClassPathXmlApplicationContext("config/quartz-config.xml");

	}
}
