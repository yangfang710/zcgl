package com.cqupt.pub.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class CRMInterfaceDAO {
	
	private static Logger logger = Logger.getLogger(CRMInterfaceDAO.class);
	private String url = "jdbc:oracle:thin:@133.53.9.241:1521:zhyz";
	private String username = "lz_terminal";
	private String password = "123456";
	
	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			logger.info("加载驱动失败~");
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			logger.info("获取数据库连接失败~");
			e.printStackTrace();
		}
		return conn;
	}
	
	public PreparedStatement prepareStatement(Connection conn, String sql) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			logger.info("获取PreparedStatement失败！");
			e.printStackTrace();
		}
		return pstmt;
	}
	
	public void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void close(PreparedStatement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
