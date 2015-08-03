package com.cqupt.sysManage.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class UserManagerModifyAction extends ActionSupport{

	private static final long serialVersionUID = -1015300446840329702L;
	HttpServletRequest request = null;

	public String execute() throws Exception {
		request = ServletActionContext.getRequest();
		String userId =  request.getParameter("userId");
		DataStormSession session = null;
		try 
		{
			session = DataStormSession.getInstance();
		
			String sql = "select t.user_id,t.user_pwd,t.user_name,t.group_name,t.group_id,t.dept_id ,t.dept_name ," +
					"ifnull(t.user_email,'') user_email ,ifnull(t.phone_num,'') phone_num ,t.user_state,t.team_name,t.data_auth_id,t.data_auth_name "+
		 	  " from zcgl.sys_user t WHERE t.user_id = '"+userId+"'";
			System.out.println(sql);
			List resultList = session.findSql(sql);
			Map resultMap = (Map)resultList.get(0);
			request.setAttribute("txtName", resultMap.get("userName"));
			request.setAttribute("groupId", resultMap.get("groupId"));
			request.setAttribute("txtUserGroup", resultMap.get("groupName"));
			request.setAttribute("userEmail", resultMap.get("userEmail"));
			request.setAttribute("deptId", resultMap.get("deptId"));
			request.setAttribute("deptName", resultMap.get("deptName"));
			request.setAttribute("txtPhone", resultMap.get("userPhoneNum"));
			request.setAttribute("userState",resultMap.get("userState"));
			request.setAttribute("txtUserId", userId);
			request.setAttribute("txtPsw", resultMap.get("userPwd"));
			request.setAttribute("teamName", resultMap.get("teamName"));
			request.setAttribute("dataAuthId", resultMap.get("dataAuthId"));
			request.setAttribute("dataAuthName", resultMap.get("dataAuthName"));
			session.closeSession();
		}		
		catch (Exception e) 
		{
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		            
		
			return "modify";
		}



}
