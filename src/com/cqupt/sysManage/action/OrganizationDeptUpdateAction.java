package com.cqupt.sysManage.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import org.apache.log4j.Logger;

public class OrganizationDeptUpdateAction extends ActionSupport implements
		ModelDriven<Organization> {

	private static final long serialVersionUID = 1L;
	
	
	private Organization department = new Organization();

	public Organization getModel() {
		return department;
	}

	Logger logger = Logger.getLogger(getClass());
	HttpServletRequest request = null;

	public String execute() {
		DataStormSession session = null;
		this.request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		// String deptState = DecodeUtils.decodeRequestString(
		// department.getDeptState(), "");
		
		String operUserName = (String) request.getSession().getAttribute(
				"operUserName");
		String result = "success";
		try {
			session = DataStormSession.getInstance();
			String sql = "";
			
			if(result.equals("success")){
				sql = "update zcgl.sys_dept set dept_address = '"+department.getAddress()+"'," +
						"phone_num = '"+department.getPhoneNum()+"'," +
						"dept_state = '"+department.getDeptState()+"'," +
						"remark = '"+ department.getRemark() +"' " +
						"where id = " +id;
						
				logger.info("修改部门:" + sql);
				session.update(sql);
			}
			session.closeSession();
		} catch (CquptException e) {
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
			out.print(result);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	

}
