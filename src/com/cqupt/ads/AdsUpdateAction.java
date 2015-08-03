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
import com.opensymphony.xwork2.ActionSupport;

public class AdsUpdateAction extends ActionSupport {

	private static final long serialVersionUID = 1794195924380050122L;
	HttpServletRequest request = null;
	HttpServletResponse response = null;

	public String execute() {
		this.request = ServletActionContext.getRequest();
		Logger logger = Logger.getLogger(this.getClass());
		DataStormSession session = null;
		String result = "";
		String information = DecodeUtils.decodeRequestString(request
				.getParameter("information"));
		String id = request.getParameter("id");
		try {
			session = DataStormSession.getInstance();
			String sql = "update zcgl.ads set information = '" + information
					+ "' where id=" + id;

			logger.info("修改资产时的查询：" + sql);
			session.update(sql);
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
		response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
