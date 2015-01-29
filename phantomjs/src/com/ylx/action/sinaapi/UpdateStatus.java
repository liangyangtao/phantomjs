package com.ylx.action.sinaapi;

import com.ylx.util.FinalWord;

import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.http.ImageItem;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

/**
 * @author 梁杨桃
 * 
 *         发表微博
 * */

public class UpdateStatus {
	/**
	 * 不带表情的
	 * 
	 * @param 发表的微博内容
	 * 
	 * @param token
	 * */
	public static void updateStatus(String statuses, String access_token) {
		Timeline tm = new Timeline();
		tm.client.setToken(FinalWord.MY_ACCESS_TOKEN);
		try {
			Status status = tm.UpdateStatus(statuses);
			Log.logInfo(status.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 带图片的
	 * 
	 * @param 发表的微博内容
	 * 
	 * 
	 * @param 图片
	 * 
	 * @param token
	 * */
	public static void updateStatus(String statuses, ImageItem item,
			String access_token) {
		Timeline tm = new Timeline();
		tm.client.setToken(FinalWord.MY_ACCESS_TOKEN);
		try {
			Status status = tm.UploadStatus(statuses, item);
			Log.logInfo(status.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

	// 测试成功
	public static void main(String[] args) {
		updateStatus("我就不摘掉撒飞洒的！！！", null);

	}

}
