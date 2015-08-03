package com.cqupt.pub.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class Tools {
	static Logger logger = Logger.getLogger(Tools.class);
	
	
	public static String getTimeLimit(String deptId)  {
		String str = ServletActionContext.getServletContext().getAttribute(deptId + "TimeLimit") + "";
		System.out.println(deptId + " time limit:" + str);
		return str == null ? "" : str;
	}
	
	
	/**
	 * add serial number into application scope.
	 * @param userId user identifier
	 * @param serialNumber terminal serial number
	 */
	public static void addSerialNumber(String userId, String serialNumber){
		ServletActionContext.getServletContext().setAttribute(userId + "SerialNumber", serialNumber);
		logger.info("get:"+ServletActionContext.getServletContext().getAttribute(userId + "SerialNumber"));
	}
	
	/**
	 * return serial number from application scope if and only if the user upload serial number to the server,
	 * once return , clear the serial number from application scope, and set the value to "deprecated".
	 * @param userId user identifier
	 * @return 
	 */
	public static String getSerialNumber(String userId){
		String str = (String) ServletActionContext.getServletContext().getAttribute(userId + "SerialNumber");
		addSerialNumber(userId, "deprecated");
		return str == null ? "":str;
	}
	
	
	/**
	 * Tells whether or not the current user  belongs to management department
	 * @param request HttpServletRequest
	 * @return <tt>true</tt> if current user belongs to management department , <tt>false</tt> if not
	 */
	public static boolean isManagementDepartment(HttpServletRequest request){
		String deptId = request.getSession().getAttribute("deptId").toString();
		if(deptId.matches("1..1...")){
			return true;
		} else {
			return false;
		}
	}
	
	public static String getOrderID(){
		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
		Random r = new Random();
		int rannum = r.nextInt(1000) + 1000;   // 获取随机数
		String nowTimeStr = getNowTime("yyyyMMddHHmmss");// 时间格式化的格式
			return nowTimeStr + rannum;
	}
	
	public static String getAllocateID(){
		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
		Random r = new Random();
		int rannum = r.nextInt(10) + 10;   // 获取随机数
		String nowTimeStr = getNowTime("yyyyMMddHHmmss");// 时间格式化的格式
			return nowTimeStr + rannum;
	}
	
	public static String getNowTime(String format){
		SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
		return sDateFormat.format(new Date()); // 当前时间	
	}
	
	public static String getNowTime(){
		return getNowTime("yyyy-MM-dd HH:mm:ss");
	}
	
	public static String getFirstDayOfTheMonth(){
		Calendar curCal = Calendar.getInstance();
        SimpleDateFormat datef =  new SimpleDateFormat("yyyy-MM-dd");
        curCal.set(Calendar.DAY_OF_MONTH, 1);
        Date beginTime = curCal.getTime();
        String sTime = datef.format(beginTime);
        return sTime;
	}
	public static String getLastDayOfTheMonth(){
        
        Calendar cal = Calendar.getInstance();
		SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");
		             //当前月的最后一天   
		cal.set( Calendar.DATE, 1 );
		cal.roll(Calendar.DATE, - 1 );
		Date endTime=cal.getTime();
		String sTime = datef.format(endTime);
		return sTime;
	}
	public static String getFirstDayOfNextMonth(){
		Calendar curCal = Calendar.getInstance();
        SimpleDateFormat datef =  new SimpleDateFormat("yyyy-MM-dd");
        curCal.set(Calendar.MONTH, curCal.get(Calendar.MONTH)+1);
        Date beginTime = curCal.getTime();
        String sTime = datef.format(beginTime);
        return sTime;
	}

}
