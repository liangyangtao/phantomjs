package com.ylx.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weibo4j.Comments;
import weibo4j.model.CommentWapper;
import weibo4j.model.WeiboException;

import com.ylx.dao.impl.WeiboCommentDaoImpl;
import com.ylx.util.FinalWord;

/**
 * @author 梁杨桃
 * 
 *         抓取微博的评论
 * */
public class WeiboCommentAction {
	WeiboCommentDaoImpl weiboCommentDaoImpl = new WeiboCommentDaoImpl();
	private static Log logger = LogFactory.getLog(WeiboCommentAction.class);

	public static void main(String[] args) {
		new WeiboCommentAction().commentConsole();
	}

	// 操作流程方法
	public void commentConsole() {
		// 读取要抓取评论的微博
		List<String> weiboIds = weiboCommentDaoImpl.readCommentId();

		for (String string : weiboIds) {
			// 调用API得到要评论列表
			// String string = "3670696091860498";
			CommentWapper commentWapper = getComment(string);
			// 保存评论
			weiboCommentDaoImpl.saveCommmentWapper(commentWapper);
		}
	}

	// 新浪微博 API 根据mid 得到评论的方法
	public CommentWapper getComment(String id) {
		Comments cm = new Comments();
		// 这是要填写的开发者token值， 可能会过期，及时调整
		cm.client.setToken(FinalWord.ACCESS_TOKEN);
		CommentWapper comment = null;
		try {
			comment = cm.getCommentById(id);
		} catch (WeiboException e) {
			logger.info("读取评论失败" + e);
		}
		return comment;
	}
}
