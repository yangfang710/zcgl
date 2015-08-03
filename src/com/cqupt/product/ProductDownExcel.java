package com.cqupt.product;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.DecodeUtils;
import com.cqupt.pub.util.ExcelServiceImpl;
import com.cqupt.pub.util.IExcelService;
import com.cqupt.pub.util.Tools;
import com.opensymphony.xwork2.ActionSupport;

public class ProductDownExcel extends ActionSupport {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass());
	InputStream excelStream;
	HttpServletRequest request = null;

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String execute() {
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;
		request = ServletActionContext.getRequest();
		String dataAuthId = (String) request.getSession().getAttribute(
				"dataAuthId");
		String groupId = (String) request.getSession().getAttribute(
				"groupId");
	
		String userName = (String) request.getSession().getAttribute(
				"userName");
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

		try {// d.storage_in_amount,
			session = DataStormSession.getInstance();

			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			String fileName = "资产明细.xls";
			try {
				fileName = URLEncoder.encode(fileName, "UTF-8");
				response.setHeader("Content-Disposition",
						"attachment;fileName=" + fileName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			IExcelService es = new ExcelServiceImpl();
			String[] title = { "序号", "部门编号", "部门名称", "仪器编号", "仪器名称", "分类号",
					"型号", "规格", "单价", "厂家", "使用方向", "领用人", "入库时间", "当前状态",
					"管理责任人", "使用人","使用人部门","使用人团队", "存放地名称", "借用时间", "备注" };// excel工作表的标题
			String[] keys = { "rownum", "departmentid", "departmentname",
					"productcode", "productname", "classcode",
					"productversion", "specification", "price",
					"manufacturers", "productuse", "receiveuser", "intime",
					"productstatus", "manageuser", "useuser","useuserdept","useuserteam", "address",
					"lastouttime", "remark", };

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

			sql += " ) a left join zcgl.sys_user b on a.useuser = b.user_name ) t  order by  t.intime desc";


			logger.info("资产列表导出excel：" + sql);

			excelStream = es.getExcelInputStream(title, keys, sql);
			resultStr = "excel";
		} catch (CquptException ce) {
			ce.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.closeSession();
				} catch (CquptException e) {
					e.printStackTrace();
				}
			}
		}

		// System.out.print("resultStr===="+resultStr);
		return resultStr;
	}
}
