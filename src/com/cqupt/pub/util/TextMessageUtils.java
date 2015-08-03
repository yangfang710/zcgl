package com.cqupt.pub.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;

/**
 * The <code>TextMessageUtils</code> class wrapped some useful methods of sending text messages.
 * @author Kevin Su
 * @since JDK1.0
 */
public class TextMessageUtils {
	
	private static Logger logger = Logger.getLogger(TextMessageUtils.class);
	
	/**
	 * When a case accepted, send a text message to user.
	 * @param cellphoneNumber cellphone number
	 * @param content message content
	 * @return true if text message sent successfully, if not ,return false.
	 */
	public static boolean caseAcceptedAlertUser(String cellphoneNumber, String content) {
		return sendMessage(cellphoneNumber, content);
	}
	
	
	/**
	 * When the terminal has been repaired, send a text message to source department.
	 * @param caseNum case number
	 * @return true if text message sent successfully, if not ,return false.
	 */
	public static boolean alertSourceDepartment(String caseNum) {
		String deptTelNum = "";
		String content = "";
		DataStormSession session = null;
		List<Map<String, Object>> list = null;
		Map<String, Object> map = null;
		String sql = "";
		boolean success = false;
		try {
			session = DataStormSession.getInstance();
			sql = "select a.dept_name, a.dept_id, b.current_dept_name from zcgl.case_accept as a inner join zcgl.case_status as b on a.case_num=b.case_num where a.case_num='" + caseNum + "'";
			list = session.findSql(sql);
			if(list.size() == 0) {
				logger.info("短信通知工单来源部门：未查到该工单，短信发送失败！工单号：" + caseNum);
			} else {
				map = list.get(0);
				deptTelNum = getDeptTelNumber(map.get("deptId").toString());
				content = map.get("deptName").toString() + "，您好！工单号为" + caseNum + "的终端已经在" + map.get("currentDeptName").toString() + "维修完成，您可以在售后系统“业务管理”模块下的“工单查询”模块查看。";
				success = sendMessage(deptTelNum, content);
			}
			session.closeSession();
		} catch (CquptException e) {
			if(session != null) {
				try {
					session.exceptionCloseSession();
				} catch (CquptException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		
		return success;
	}
	
	/**
	 * When a case has been launched and the destination department is one of "地包商",
	 * send a text message to destination department.
	 * @param caseNum case number
	 * @return true if text message sent successfully, if not ,return false.
	 */
	public static boolean alertRepairDepartment(String caseNum) {
		String deptTelNum = "";
		String content = "";
		DataStormSession session = null;
		List<Map<String, Object>> list = null;
		Map<String, Object> map = null;
		String sql = "";
		boolean success = false;
		try {
			session = DataStormSession.getInstance();
			sql = "select a.launch_dept_name, a.receive_dept_id, a.receive_dept_name from zcgl.case_launch as a where a.launch_status='待接收' and a.case_num='" + caseNum + "'";
			list = session.findSql(sql);
			/*System.out.println(sql);
			System.out.println(list);*/
			if(list.size() == 0) {
				logger.info("~~~~~~~短信通知地包商接收工单：未查到该工单，短信发送失败！工单号：" + caseNum);
			} else {
				map = list.get(0);
				deptTelNum = getDeptTelNumber(map.get("receiveDeptId").toString());
				content = map.get("receiveDeptName").toString() + "，您好！" + map.get("launchDeptName").toString() + "向你部门发起一条维修工单，工单号为" + caseNum + "，您可以在售后系统“工单派发”模块下的“工单接收”模块查看。";
				success = sendMessage(deptTelNum, content);
			}
			session.closeSession();
		} catch (CquptException e) {
			if(session != null) {
				try {
					session.exceptionCloseSession();
				} catch (CquptException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		
		return success;
	}
	
	/**
	 * When the terminal has been repaired and the terminal is at receive department,
	 * send a text message to terminal holder in order to remind him/her of getting their terminal.
	 * @param caseNum case number
	 * @return true if text message sent successfully, if not ,return false.
	 */
	public static boolean alertUser(String caseNum) {
		String userTel = "";
		String content = "";
		DataStormSession session = null;
		List<Map<String, Object>> list = null;
		Map<String, Object> map = null;
		String sql = "";
		boolean success = false;
		try {
			session = DataStormSession.getInstance();
			sql = "select a.client_name, a.contact_number, a.dept_name, b.case_status, date_format(a.accept_time,'%Y年%m月%d日') as accept_time from zcgl.case_accept as a inner join zcgl.case_status as b on a.case_num=b.case_num where a.case_num='" + caseNum + "'";
			list = session.findSql(sql);
			if(list.size() == 0) {
				logger.info("短信通知用户取机：未查到该工单，短信发送失败！工单号：" + caseNum);
			} else {
				map = list.get(0);
				if(map.get("caseStatus").toString().equals("维修")) {
					//repair done
					userTel = map.get("contactNumber").toString();
					content = map.get("clientName").toString() + "，您好！您于" + map.get("acceptTime").toString() + "在" + map.get("deptName").toString() + "维修的终端已经维修完成，请带上有效身份证件到该部门取机。";
					success = sendMessage(userTel, content);
				} else {
					//repair undone
					logger.info("维修未完成，暂不短信通知用户取机！");
				}
			}
			session.closeSession();
		} catch (CquptException e) {
			if(session != null) {
				try {
					session.exceptionCloseSession();
				} catch (CquptException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * get department contact number through a department identifier
	 * @param deptId
	 * @return department contact number
	 */
	private static String getDeptTelNumber(String deptId) {
		//every department must has a telephone number for receiving text messages.
		//return "";
		String number = "";
		Object o = ServletActionContext.getServletContext().getAttribute(deptId + "TelNumber");
		if(o != null) {
			number = o.toString();
		} else {
			logger.info("查询部门电话号码失败！！！！！deptId:" + deptId);
		}
		return number;
	}
	
	/**
	 * sending text message by inserting several pieces of data into CRM database.
	 * @param cellphoneNumber client's telephone number
	 * @param content text message to be sent
	 * @return
	 */
	private static boolean sendMessage(String cellphoneNumber, String content) {
		logger.info("sending message=>" + cellphoneNumber + ":" + content);
		//TODO 测试的时候请讲下面注释去掉，否则程序会卡死（未连接泸州VPN）或者发送真实发送出短信（连接了泸州VPN）
		//if("a".equals("a")) return true;
		CRMInterfaceDAO session = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		boolean result = false;
		try {
			session = new CRMInterfaceDAO();
			conn = session.getConnection();
			conn.setAutoCommit(false);
			
			sql = "select dept_no from sa.mail_push_dept_new where dept_no=?";
			pstmt = session.prepareStatement(conn, sql);
			pstmt.setString(1, cellphoneNumber);
			rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				sql = "insert into sa.mail_push_dept_new(dept_no, dept_phone, state) values(?,?,'tass')";
				System.out.println(sql);
				pstmt = session.prepareStatement(conn, sql);
				pstmt.setLong(1, new Long(cellphoneNumber));
				pstmt.setString(2, cellphoneNumber);
				pstmt.executeUpdate();
			}
			
			sql = "insert into sa.mail_push_new (mail_content, mail_date, state, mail_no, send_dept)" + 
					" values(?,sysdate,'0',SA.CHUNCU_SEQ_new.nextval,?) ";
			System.out.println(sql);
			pstmt = session.prepareStatement(conn, sql);
			pstmt.setString(1, content);
			pstmt.setLong(2, new Long(cellphoneNumber));
			pstmt.executeUpdate();
			
			
			pstmt = session.prepareStatement(conn, "update sa.mail_push_new_flag set new_flag='Y' ");
			System.out.println("update sa.mail_push_new_flag set new_flag='Y' ");
			pstmt.executeUpdate();
			
			logger.info("sending completed ...");
			conn.commit();
			result = true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			session.close(rs);
			session.close(pstmt);
			session.close(conn);
		}
		
		return result;
	}
	
	
	public static void main(String[] args ) {
		sendMessage("13193196401", "网络短信测试  from kevin ...");
		//System.out.println(getDeptTelNumber("1"));
		//System.out.println("尊敬的用户您好，您的终端售后业务于 " + Tools.getNowTime() + " 在" + "重邮" + "受理成功。");
	}
}




