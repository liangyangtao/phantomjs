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
 * 
 *         抓取用户微博时间调度类
 * */
public class UserQuartzJobBean extends QuartzJobBean {
	private static Log log = LogFactory.getLog(UserQuartzJobBean.class);

	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {

	}

	public void executeInternal() {
		log.info("开始抓取指定用户的微博！！！");
		new SinaLogin().console(1);

	}

}
