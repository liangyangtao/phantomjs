package com.ylx.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConnectionTools {
	static Connection conn = null;
	public static Map<String, String> jdbcMap = XmlUtil.getJdbcMap();
	private static Log logger = LogFactory.getLog(ConnectionTools.class);

	public static Connection getConn() {

		if (conn == null) {

			try {
				logger.info("开始连接数据库......");
				Class.forName(jdbcMap.get(FinalWord.DRIVERCLASSNAME));
				conn = DriverManager.getConnection(jdbcMap
						.get(FinalWord.JDBCURL), jdbcMap
						.get(FinalWord.USERNAME), jdbcMap
						.get(FinalWord.PASSWORD));
				logger.info("数据库连接成功......");
				return conn;
			} catch (Exception e) {
				logger.error("数据库连接失败!!!!"+e);
			}
		} else {
			return conn;
		}

		return conn;
	}

	public static void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
