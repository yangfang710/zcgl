package com.cqupt.product;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class SelectQueryAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	Logger logger = Logger.getLogger(this.getClass());
	
	public String execute() {
		request = ServletActionContext.getRequest();

		response = ServletActionContext.getResponse();
		// 设置字符集
		response.setCharacterEncoding("UTF-8");
		String ttType = request.getParameter("ttType");
		String selectName="";
		if(ttType.equals("productUse")){
			selectName = "使用方向";
		}else if(ttType.equals("productStatus")){
			selectName = "当前状态";
		}
		PrintWriter out;
		try {
			out = response.getWriter();
			String resultStr = "";
			String sql = "";
			DataStormSession session = null;
			try {
				session = DataStormSession.getInstance();
				sql = "select t.id ,t.select_val text from zcgl.select_item t where t.select_name ='"+selectName+"'";
				JSONArray jsonObject = JSONArray.fromObject(session.findSql(sql));
				resultStr = jsonObject.toString();
				session.closeSession();
			} catch (Exception e) {
				try {
					session.exceptionCloseSession();
				} catch (CquptException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} 
			logger.info(resultStr);
			// 直接输入响应的内容
			out.println(resultStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;// 不需要跳转某个视图 因为上面已经有了直接输出的响应结果

	}

	
}
