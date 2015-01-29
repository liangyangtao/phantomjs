package com.ylx.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ylx.entity.Author;
import com.ylx.util.ConnectionTools;

/**
 * @author 梁杨桃
 * 
 * */

public class SaveWeibodaoImpl {
	private static Log logger = LogFactory.getLog(SaveWeibodaoImpl.class);

	// 判断微博发布者是否存在
	public boolean isAuthor_idExit(String author_id) {
		boolean isExit = false;
		String sql = "select count(1) from weibo_author  where  author_id= ?";
		try {
			PreparedStatement stmt = null;
			Connection conn = null;
			conn = ConnectionTools.getConn();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, author_id);
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
			logger.error("查询author_id失败" + e);
		}
		return isExit;

	}

	// 判断微博是否存在
	public boolean isMidExit(String mid) {
		boolean isExit = false;
		String sql = "select count(1) from weibo_content  where  mid= ?";

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
			logger.error("查询mid失败" + e);
		}
		return isExit;

	}

	// 单个保存得到到的微博发表人和微博

	public void saveAuthor(Author author) {
		// 判断作者是否存在
		boolean isAuthorExit = isAuthor_idExit(author.getAuthor_id());
		if (isAuthorExit) {

		} else {
			String sql = "insert into weibo_author (author_id,author_nickName,author_url,author_img_url) values (?,?,?,?)";
			try {
				PreparedStatement stmt = null;
				Connection conn = null;
				conn = ConnectionTools.getConn();
				conn.setAutoCommit(false);
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, author.getAuthor_id());
				stmt.setString(2, author.getAuthor_nickName());
				stmt.setString(3, author.getAuthor_url());
				stmt.setString(4, author.getAuthor_img_url());
				stmt.execute();
				conn.commit();
				stmt.close();
			} catch (Exception e) {
				logger.error("保存发布人失败" + e);
			}

		}
		// 判断微博是否存在
		boolean isMidExit = isMidExit(author.getMid());
		if (isMidExit) {
			return;
		} else {
			String sql2 = "insert into weibo_content (mid,content,content_image,news_time,follow,crawl_time,author_id) values (?,?,?,?,?,NOW(),?)";
			try {
				PreparedStatement stmt = null;
				Connection conn = null;
				conn = ConnectionTools.getConn();
				conn.setAutoCommit(false);
				stmt = conn.prepareStatement(sql2);
				stmt.setString(1, author.getMid());
				stmt.setString(2, author.getContent());
				stmt.setString(3, author.getContent_image());
				stmt.setDate(4, new Date(author.getNews_time().getTime()));
				stmt.setString(5, author.getFollow());
				stmt.setString(6, author.getAuthor_id());
				stmt.execute();
				conn.commit();
				stmt.close();
			} catch (Exception e) {
				logger.error("保存发布人发表的微博失败" + e);
			}
		}
	}

	// 批量保存得到到的微博发表人和微博（不用批量插入）
	public void saveAuthor(List<Author> authors) {

//		String sql = "insert into weibo_author (author_id,author_nickName,author_url,author_img_url) values (?,?,?,?)";
//		try {
//			PreparedStatement stmt = null;
//			Connection conn = null;
//			conn = ConnectionTools.getConn();
//			conn.setAutoCommit(false);
//			stmt = conn.prepareStatement(sql);
//			for (Author author : authors) {
//				boolean isExit = isAuthor_idExit(author.getAuthor_id());
//				if (isExit) {
//					continue;
//				} else {
//					stmt.setString(1, author.getAuthor_id());
//					stmt.setString(2, author.getAuthor_nickName());
//					stmt.setString(3, author.getAuthor_url());
//					stmt.setString(4, author.getAuthor_img_url());
//					stmt.addBatch();
//
//				}
//			}
//			stmt.executeBatch();
//			conn.commit();
//			stmt.close();
//		} catch (Exception e) {
//			logger.error("保存发布人失败" + e);
//		}

		String sql2 = "insert into weibo_content (mid,content,content_image,news_time,follow,crawl_time,author_id) values (?,?,?,?,?,NOW(),?)";
		try {
			PreparedStatement stmt = null;
			Connection conn = null;
			conn = ConnectionTools.getConn();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql2);
			for (Author author : authors) {

				boolean isExit = isMidExit(author.getMid());
				if (isExit) {
					continue;
				}
				stmt.setString(1, author.getMid());
				stmt.setString(2, author.getContent());
				stmt.setString(3, author.getContent_image());
				stmt.setDate(4, new Date(author.getNews_time().getTime()));
				stmt.setString(5, author.getFollow());
				stmt.setString(6, author.getAuthor_id());
				stmt.addBatch();
			}
			stmt.executeBatch();
			conn.commit();
			stmt.close();
		} catch (Exception e) {
			logger.error("保存发布人发表的微博失败" + e);
		}

	}
}
