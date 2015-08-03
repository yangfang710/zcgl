package com.cqupt.pub.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

import com.cqupt.pub.exception.CquptException;

public final class DbConnection {

	private String driver = "com.mysql.jdbc.Driver";

	private String url = "jdbc:mysql://localhost:3306/zcgl?characterEncoding=gb2312";
	private String user = "zcgl";
	private String password = "zcgl6126";

	// private String url =
	// "jdbc:mysql://172.23.8.200:3306/zcgl?characterEncoding=gb2312";
	// private String user = "zcgl";
	// private String password = "zcgl";

	private Logger logger = Logger.getLogger(this.getClass());

	public DbConnection() {
	}

	// connect datebase mysql
	public Connection getConnection() throws CquptException {
		logger.debug("into getConnection() ");
		Connection conn = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (Exception ex) {
			throw new CquptException(ex);
		}
	}

	// releace datebase mysql
	public boolean releaseConnection(Connection conn) {
		logger.debug("into releaseConnection(Connection conn)  ");
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

}
