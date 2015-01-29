package com.ylx.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weibo4j.model.Comment;
import weibo4j.model.CommentWapper;
import weibo4j.model.User;

import com.ylx.util.ConnectionTools;
import com.ylx.util.StringHelper;

/**
 * @author 梁杨桃
 * 
 * */
public class WeiboCommentDaoImpl {
	private static Log logger = LogFactory.getLog(WeiboCommentDaoImpl.class);
	public SaveWeibodaoImpl saveWeibodaoImpl = new SaveWeibodaoImpl();

	// 读取要抓取评论的的微博mid
	public List<String> readCommentId() {
		String sql = "select mid from weibo_content";
		List<String> mids = new ArrayList<String>();
		try {
			PreparedStatement stmt = null;
			Connection conn = null;
			conn = ConnectionTools.getConn();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				mids.add(rs.getString("mid"));
			}
			conn.commit();
			stmt.close();
		} catch (Exception e) {
			logger.error("查询微博mid 失败" + e);
		}
		return mids;
	}

	// 保存微博评论
	public void saveCommmentWapper(CommentWapper commentWapper) {

		for (Comment comment : commentWapper.getComments()) {
			logger.info(comment);
			try {
				saveComment(comment);
			} catch (Exception e) {
				logger.error("保存微博评论失败" + e);
				continue;

			}
		}

	}

	// 保存评论
	public void saveComment(Comment comment) {
		boolean isCommentExit = isCommentExit(comment.getMid());
		if (isCommentExit) {

		} else {
			String sql = "insert into weibo_comment (comment_id,comment_text,comment_time,comment_user_id,content_mid,comment_source,crawl_time) values(?,?,?,?,?,?,NOW())";
			try {
				PreparedStatement stmt = null;
				Connection conn = null;
				conn = ConnectionTools.getConn();
				conn.setAutoCommit(false);
				stmt = conn.prepareStatement(sql);
				logger.info(sql);
				logger.info(comment.getMid());
				stmt.setString(1, comment.getMid());

				stmt.setString(2, comment.getText());
				stmt.setDate(3, new java.sql.Date(comment.getCreatedAt()
						.getTime()));
				stmt.setString(4, comment.getUser().getId());
				stmt.setString(5, comment.getStatus().getMid());
				stmt.setString(6,
						StringHelper.mid(comment.getSource(), ">", "</a>"));
				stmt.execute();
				conn.commit();
				stmt.close();
			} catch (Exception e) {
				logger.error("保存评论失败" + e);
			}
			saveUser(comment.getUser());
		}

	}

	public boolean isCommentExit(String mid) {
		boolean isExit = false;
		String sql = "select count(1) from weibo_comment  where  comment_id= ?";
		try {
			PreparedStatement stmt = null;
			Connection conn = null;
			conn = ConnectionTools.getConn();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, mid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int num = rs.getInt(1);
				if (num > 0) {
					isExit = true;
				}
			}
			conn.commit();
			stmt.close();
		} catch (Exception e) {
			logger.error("查询评论是否存在失败" + e);
		}
		return isExit;

	}

	// 保存评论人
	private void saveUser(User user) {
		// 判断作者是否存在
		boolean isAuthorExit = saveWeibodaoImpl.isAuthor_idExit(user.getId());
		if (isAuthorExit) {

		} else {
			String sql = "insert into weibo_author (author_id,author_nickName,author_url,author_img_url) values (?,?,?,?)";
			try {
				PreparedStatement stmt = null;
				Connection conn = null;
				conn = ConnectionTools.getConn();
				conn.setAutoCommit(false);
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, user.getId());
				stmt.setString(2, user.getScreenName());
				stmt.setString(3, "http://weibo.com/u/" + user.getId());
				stmt.setString(4, user.getProfileImageUrl());
				stmt.execute();
				conn.commit();
				stmt.close();
			} catch (Exception e) {
				logger.error("保存微博评论人失败" + e);
			}

		}
	}

}
