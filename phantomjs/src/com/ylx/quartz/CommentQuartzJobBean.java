package com.ylx.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ylx.action.WeiboCommentAction;

/**
 * @author 梁杨桃
 * 
 * 
 *         评论调度类
 * */
public class CommentQuartzJobBean extends QuartzJobBean {
	private static Log logger = LogFactory.getLog(CommentQuartzJobBean.class);

	public void executeInternal() {
		logger.info("开始利用API对用户评论进行抓取！！！");
		new WeiboCommentAction().commentConsole();
	}

	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {

		// new WeiboCommentAction().commentConsole();
	}
}
