package com.ylx.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ylx.util.ConnectionTools;
/**
 * @author 梁杨桃
 * 
 * */
public class UserDaoImpl {
	
	private static Log logger = LogFactory.getLog(UserDaoImpl.class);
	// 读取微博用户的链接
	public List<String> readUser() {
		List<String> results = new ArrayList<String>();
		try {
			PreparedStatement stmt = null;
			Connection conn = null;
			conn = ConnectionTools.getConn();
			conn.setAutoCommit(false);
			String sql = "select  distinct author_url from  weibo_author";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String keyword = rs.getString("author_url");
				if (keyword != null && keyword.trim().length() > 0) {
					results.add(keyword);
				}
			}
			conn.commit();
			stmt.close();

		} catch (Exception e) {
			logger.info("查询用户链接失败" + e);
		}
		return results;
	}
}
