package com.cqupt.pub.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The <code>TerminalSourceUtils</code> class wrapped some methods of getting terminal source 
 * by offering a phone number or a serial number of terminal.
 * @author Kevin Su
 * @since JDK1.0
 */
public class TerminalSourceUtils {

	/**getSourceBySerialNumber-backup
	 * Getting a source corresponding to the serial number by offering a serialNumber.
	 * @param serialNumber terminal serial number
	 * @return source corresponding to the serial number, if serial number is not exist, return empty string.
	 */
	public static String getSourceBySerialNumber(String serialNumber) {
		//TODO 测试的时候请讲下面注释去掉，否则程序会卡死（未连接泸州VPN）
		//if("a".equals("a")) return "notExist";
		String source = "notExist";
		CRMInterfaceDAO session = null;//System.out.println(TextMessageUtils.getDeptTelNumber("1"));
		
		session = new CRMInterfaceDAO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = session.getConnection();
			sql = "select TERMINAL_TYPE, MODEL_TYPE,START_DT from SA.TERMINAL_DEVICE where BCD_CODE=? ";
			pstmt = session.prepareStatement(conn, sql);
			pstmt.setString(1, serialNumber);//System.out.println("~~~~" + serialNumber);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				source = "exist;" + rs.getString(1) + ";" + rs.getString(2)+";"+rs.getString(3);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			session.close(rs);
			session.close(pstmt);
			session.close(conn);
		}
		System.out.println("source++++++++++: "+source);
		return source;
	}

	/**
	 * Getting a source corresponding to the phone number by offering a phone number.
	 * @param serialNumber terminal serial number
	 * @return source corresponding to the serial number, if serial number is not exist, return empty string.
	 *  
	 */
	public static String getSourceByTelephoneNumber(String telephoneNumber) {
		throw new RuntimeException("This method is not supported now ...");
		//return source; 
	}
	
}
