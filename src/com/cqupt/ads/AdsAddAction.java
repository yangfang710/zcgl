package com.cqupt.ads;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.util.DecodeUtils;
import com.opensymphony.xwork2.ActionSupport;

public class AdsAddAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(this.getClass());
	HttpServletRequest request = null;

	@Override
	public String execute() throws Exception {
		this.request = ServletActionContext.getRequest();
		String operUserName = (String) request.getSession().getAttribute(
				"userName");
		String information = DecodeUtils.decodeRequestString(request
				.getParameter("information"));
		String appendixName = request.getParameter("appendixName");	
		String resultStr = "success";
		DataStormSession session = DataStormSession.getInstance();
		HttpServletResponse response = ServletActionContext.getResponse();

		session = DataStormSession.getInstance();
		String sql = "insert into zcgl.ads (information,oper_user,oper_time,appendix_name) values('"
				+ information + "','" + operUserName + "',sysdate(),'"+appendixName+"')";
		logger.info("插入公告信息:" + sql);
		session.add(sql);
		session.closeSession();
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(resultStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
