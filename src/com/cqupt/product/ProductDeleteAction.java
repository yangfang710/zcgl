package com.cqupt.product;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class ProductDeleteAction extends ActionSupport {

	private static final long serialVersionUID = -3592801814695320357L;

	HttpServletRequest request = null;

	public String execute() {
		request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(deleteSupplierRecord(id));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String deleteSupplierRecord(String id) {

		DataStormSession session = null;
		String resultStr = "";
		String sql = "";
		try {
			session = DataStormSession.getInstance();
			String[] idArray = id.split(";");
			for (int i = 0; i < idArray.length; i++) {
				sql = "delete from zcgl.product  WHERE id = '" + idArray[i]
						+ "'";
				session.delete(sql);
				
				sql = "delete from zcgl.product_edit where product_id = '" + idArray[i]
						+ "'";
				session.delete(sql);
				resultStr = "success";
			}
			session.closeSession();
		} catch (Exception e) {
			resultStr = "error";
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
