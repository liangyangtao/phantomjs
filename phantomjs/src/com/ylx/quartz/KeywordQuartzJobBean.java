package com.ylx.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ylx.login.SinaLogin;

/**
 * @author 梁杨桃
 * 
 *         关键词搜索调度类
 * 
 * */
public class KeywordQuartzJobBean extends QuartzJobBean {
	private static Log logger = LogFactory.getLog(KeywordQuartzJobBean.class);

	public void executeInternal() {

		logger.info("开始根据关键词抓取！！！");

		new SinaLogin().console(2);
	}

	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {

	}

}
