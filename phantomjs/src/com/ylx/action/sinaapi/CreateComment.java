package com.ylx.action.sinaapi;

import com.ylx.util.FinalWord;

import weibo4j.Comments;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Comment;
import weibo4j.model.WeiboException;

/**
 * @author 梁杨桃 给某个微博发表评论
 * */
public class CreateComment {

	// 给某个微博发表评论

	/**
	 * @param 评论的内容
	 * 
	 * @param 评论的id
	 * 
	 * @param token
	 * */
	public static void createComment(String comments, String id, String token) {
		Comments cm = new Comments();
		cm.client.setToken(FinalWord.MY_ACCESS_TOKEN);
		try {
			Comment comment = cm.createComment(comments, id);
			Log.logInfo(comment.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

	// 测试例子，成功
	public static void main(String[] args) {
		createComment("不容易啊！！！", "3675732963885956", null);
	}

}
