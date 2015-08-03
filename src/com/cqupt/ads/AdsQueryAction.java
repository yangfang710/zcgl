package com.cqupt.ads;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.DecodeUtils;
import com.cqupt.pub.util.JsonUtil;
import com.opensymphony.xwork2.ActionSupport;

public class AdsQueryAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	Logger logger = Logger.getLogger(getClass());

	public String execute() {
		logger.info("AdsQueryAction:)");
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			// 直接输入响应的内容
			out.println(getDate());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;// 不需要跳转某个视图 因为上面已经有了直接输出的响应结果

	}

	private String getDate() {
		String resultStr = "";
		String sql = "";
		DataStormSession session = null;

		String operUser = "";
		String operTime = "";
		try {
			String pageSize = request.getParameter("pagesize");
			String page = request.getParameter("page");

			operUser = DecodeUtils.decodeRequestString(request
					.getParameter("operUser"));
			operTime = DecodeUtils.decodeRequestString(
					request.getParameter("operTime"), "全部", "");

			session = DataStormSession.getInstance();
			sql = "SELECT\n"
					+ "	@rownum :=@rownum + 1 AS rownum,\n"
					+ "	t.*\n"
					+ "FROM\n"
					+ "	(SELECT @rownum := 0) r,\n"
					+ "	(SELECT a.id,a.information,a.oper_user,date_format(a.oper_time,'%Y-%m-%d %H:%i:%s') oper_time,appendix_name FROM zcgl.ads a where 1=1 ";

			if (!operUser.equals("")) {
				sql += " and a.oper_user like '" + operUser + "%' ";
			}
			if (!operTime.equals("")) {
				sql += "  and a.oper_time<'" + operTime + " 23:59:59' ";
			}

			sql += " ORDER BY oper_time DESC ) t";

			logger.info("查询公告信息：" + sql);
			resultStr = JsonUtil.map2json(session.findSql(sql,
					Integer.parseInt(page), Integer.parseInt(pageSize)));
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
