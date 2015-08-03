package com.cqupt.ads;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class AdsDeleteAction extends ActionSupport {

	private static final long serialVersionUID = 1794195924380050122L;
	HttpServletRequest request = null;
	HttpServletResponse response = null;

	public String execute() {
		this.request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		Logger logger = Logger.getLogger(this.getClass());
		DataStormSession session = null;
		String result = "";
		String id = request.getParameter("id");
		try {
			session = DataStormSession.getInstance();
			String sql = "delete from zcgl.ads where id=" + id;

			logger.info("删除公告时的查询：" + sql);
			session.delete(sql);

			session.closeSession();
			result = "success";

		} catch (Exception e) {
			result = "error";
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		PrintWriter out;
		try {
			out = response.getWriter();
			// 直接输入响应的内容
			out.print(result);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
