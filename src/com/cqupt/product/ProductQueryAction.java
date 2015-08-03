package com.cqupt.product;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.DecodeUtils;
import com.cqupt.pub.util.JsonUtil;
import com.cqupt.pub.util.Tools;
import com.opensymphony.xwork2.ActionSupport;

public class ProductQueryAction extends ActionSupport {
	Logger logger = Logger.getLogger(getClass());
	private static final long serialVersionUID = 1794195924380050122L;
	HttpServletRequest request = null;

	public String execute() {
		logger.info("ProductQueryAction:");
		this.request = ServletActionContext.getRequest();
		String dataAuthId = (String) request.getSession().getAttribute(
				"dataAuthId");
		String pageSize = request.getParameter("pagesize");
		String page = request.getParameter("page");
		HttpServletResponse response = ServletActionContext.getResponse();
		// 设置字符集
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(getProductList(pageSize, page, dataAuthId));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 直接输入响应的内容
		return null;// 不需要跳转某个视图 因为上面已经有了直接输出的响应结果

	}

	private String getProductList(String pageSize, String page,
			String dataAuthId) {
		this.request = ServletActionContext.getRequest();
		String groupId = (String) request.getSession().getAttribute(
				"groupId");
	
		String userName = (String) request.getSession().getAttribute(
				"userName");
		
		DataStormSession session = null;
		String resultStr = "";
		String sortName = request.getParameter("sortname");
		String sortOrder = request.getParameter("sortorder");
		String productCode = DecodeUtils.decodeRequestString(
				request.getParameter("productCode"), "");
		String productName = DecodeUtils.decodeRequestString(
				request.getParameter("productName"), "");
		String productVersion = DecodeUtils.decodeRequestString(
				request.getParameter("productVersion"), "");
		String manufacturers = DecodeUtils.decodeRequestString(
				request.getParameter("manufacturers"), "");
		String departmentId = DecodeUtils.decodeRequestString(
				request.getParameter("departmentId"), "");
		System.out.println("departmentId："+departmentId);
		String receiveUser = DecodeUtils.decodeRequestString(
				request.getParameter("receiveUser"), "");
		String inTimeBegin = request.getParameter("inTimeBegin");
		String inTimeEnd = request.getParameter("inTimeEnd");
		try {
			if (inTimeBegin != null) {
				inTimeBegin = java.net.URLDecoder.decode(inTimeBegin, "UTF-8");
				if (inTimeBegin.equals("全部")) {
					inTimeBegin = "";
				}
			} else {
				inTimeBegin = "";//Tools.getFirstDayOfTheMonth();
			}

			if (inTimeEnd != null) {
				inTimeEnd = java.net.URLDecoder.decode(inTimeEnd, "UTF-8");
				if (inTimeEnd.equals("全部")) {
					inTimeEnd = "";
				}
			} else {
				inTimeEnd = "";//Tools.getLastDayOfTheMonth();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String productStatus = DecodeUtils.decodeRequestString(
				request.getParameter("productStatus"), "");
		if (productStatus.equals("全部")) {
			productStatus = "";
		}
		String manageUser = DecodeUtils.decodeRequestString(
				request.getParameter("manageUser"), "");
		String useUser = DecodeUtils.decodeRequestString(
				request.getParameter("useUser"), "");
		String address = DecodeUtils.decodeRequestString(
				request.getParameter("address"), "");

		try {
			session = DataStormSession.getInstance();
			String sql = "";
			// 每个字段取个别名，不含下划，为了实现排序功能
			sql = "SELECT\n" + "	@rownum :=@rownum + 1 AS rownum,\n" + "	t.*\n"
					+ "FROM\n" + "	(SELECT @rownum := 0) r,\n" + "	(select a.*,b.dept_name useuserdept,b.team_name useuserteam from (\n"
					+ "		SELECT\n" + "			a.id,\n" + "			a.address,\n"
					+ "			a.class_code classcode,\n"
					+ "			a.department_id departmentid,\n"
					+ "			a.department_name departmentname,\n"
					+ "			a.manage_user manageuser,\n"
					+ "			a.manufacturers,\n" + "			a.price,\n"
					+ "			a.specification,\n"
					+ "			a.receive_user receiveuser,\n"
					+ "			a.use_user useuser,\n"
					+ "			a.product_code productcode,\n"
					+ "			a.product_name productname,\n"
					+ "			a.product_status productstatus,\n"
					+ "			a.product_use productuse,\n"
					+ "			a.product_version productversion,\n"
					+ "			date_format(\n" + "				a.in_time,\n"
					+ "				'%Y-%m-%d'\n" + "			) intime,\n"
					+ "			date_format(\n" + "				a.last_out_time,\n"
					+ "				'%Y-%m-%d'\n" + "			) lastouttime,\n"
					+ "			a.remark\n" + "		FROM\n" + "			zcgl.product a\n  where 1=1";
			
			//资产查询这里的数据权限是：超级管理员 查询所有；部门管理员查询相应的数据权限部门的数据（是指资产所属的部门）；普通用户 查询使用人是自己的资产	
			if (groupId.equals("3")) {//普通用户的数据权限
				sql += " and a.use_user = '" + userName + "' ";
			}else{
				sql += " and a.department_id in (" + dataAuthId + ") ";
			}
			if (!productCode.equals("")) {
				sql += " and a.product_code like '%" + productCode + "%' ";
			}
			if (!productName.equals("")) {
				sql += " and a.product_name like '%" + productName + "%' ";
			}
			if (!productVersion.equals("")) {
				sql += " and a.product_version like '%" + productVersion + "%' ";
			}
			if (!manufacturers.equals("")) {
				sql += " and a.manufacturers like '%" + manufacturers + "%' ";
			}
			if (!departmentId.equals("")) {
				sql += " and a.department_id like '%" + departmentId + "%' ";
			}
			if (!receiveUser.equals("")) {
				sql += " and a.receive_user like '%" + receiveUser + "%' ";
			}
			if (!inTimeBegin.equals("")) {
				sql += " and a.in_time >= '" + inTimeBegin + " 00:00:00'";
			}
			if (!inTimeEnd.equals("")) {
				sql += " and a.in_time <= '" + inTimeEnd + " 24:00:00'";
			}
			if (!productStatus.equals("")) {
				sql += " and a.product_status ='" + productStatus + "' ";
			}
			if (!manageUser.equals("")) {
				sql += " and a.manage_user like '%" + manageUser + "%' ";
			}
			if (!useUser.equals("")) {
				sql += " and a.use_user like '%" + useUser + "%' ";
			}
			if (!address.equals("")) {
				sql += " and a.address like '%" + address + "%' ";
			}
			sql += " ) a left join zcgl.sys_user b on a.useuser = b.user_name ) t  order by  " + sortName + "  " + sortOrder + " ";

			logger.info("资产列表：" + sql);
			Map resultMap = session.findSql(sql, Integer.parseInt(page),
					Integer.parseInt(pageSize));
			resultStr = new JsonUtil().map2json(resultMap);
			session.closeSession();

		} catch (Exception e) {
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return resultStr;

	}

}
