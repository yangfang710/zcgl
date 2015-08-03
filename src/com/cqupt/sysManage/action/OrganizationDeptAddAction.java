package com.cqupt.sysManage.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class OrganizationDeptAddAction extends ActionSupport implements
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
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		// String deptState = DecodeUtils.decodeRequestString(
		// department.getDeptState(), "");
		
		String userName = (String) request.getSession().getAttribute(
				"userName");
		String result = "success";
		try {
			session = DataStormSession.getInstance();
			String sql = "";
			List list = null;
			Map map = null;
			//验证上级部门是否正确
			sql = "select dept_id from zcgl.sys_dept where dept_id='"+department.getHidParentDeptId()+"'";
			logger.info("sql:"+sql);
			list = session.findSql(sql);
			if(list.size() == 0){
				result = "deptError";
			}
			//验证部门ID是否存在
			sql = "select dept_id from zcgl.sys_dept where dept_id='"+department.getHidParentDeptId()+department.getDeptId()+"'";
			logger.info("sql:"+sql);
			list = session.findSql(sql);
			if(list.size()>0){
				result = "deptExist";
			}
			//验证部门名称是否存在
			sql = "select dept_id from zcgl.sys_dept where dept_name='"+department.getDeptName()+"'";
			logger.info("sql:"+sql);
			list = session.findSql(sql);
			if(list.size()>0){
				result = "deptNameExist";
			}
			if(result.equals("success")){
				sql = "INSERT INTO zcgl.sys_dept (dept_id,dept_name,parent_dept_id,dept_address,phone_num,dept_state,in_date,oper_user_name,remark) VALUES ('"
						+ department.getHidParentDeptId()+department.getDeptId()
						+ "','"
						+ department.getDeptName()
						+ "','"
						+ department.getHidParentDeptId()
						+ "','"
						+ department.getAddress()
						+ "','"
						+ department.getPhoneNum()
						+ "','"
						+ department.getDeptState()
						+ "',sysdate(),'"
						+ userName
						+ "','" + department.getRemark() + "')";
				logger.info("添加部门:" + sql);
				session.add(sql);
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
