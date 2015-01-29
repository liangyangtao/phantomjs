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
public class KeywordDaoImpl {
	private static Log logger = LogFactory.getLog(KeywordDaoImpl.class);
    // 读取要查询关键词，表示公司的关键词很烂的说，只提取了小于13个长度的词
	public List<String> readKeyWord() {
		List<String> results = new ArrayList<String>();
		try {
			PreparedStatement stmt = null;
			Connection conn = null;
			conn = ConnectionTools.getConn();
			conn.setAutoCommit(false);
			String sql = "select keyname from  ir_keyname";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String keyword = rs.getString("keyname");
				if (keyword != null && keyword.trim().length() > 0
						&& keyword.trim().length() <= 12) {
					results.add(keyword);
				}
			}
			conn.commit();
			stmt.close();

		} catch (Exception e) {
			logger.info("查询关键词失败" + e);
		}
		return results;
	}
}
