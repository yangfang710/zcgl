package com.cqupt.sysManage.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.JsonUtil;
import com.opensymphony.xwork2.ActionSupport;

public class OrganizationModifyAction extends ActionSupport{

		private static final long serialVersionUID = 1794195924380050122L;
		HttpServletRequest request=null;

	
		public String execute()
		{
			this.request = ServletActionContext.getRequest();
			String id = request.getParameter("id");
			

			
			DataStormSession session = null;
			try 
			{
				session = DataStormSession.getInstance();
				String sql = "SELECT @rownum:=@rownum+1 as rownum,a.* from (select t.dept_id,t.dept_name,t.parent_dept_id,ifnull(t.dept_address,'') dept_address,t.dept_state," +
				"ifnull(t.phone_num,'') phone_num,date_format(t.in_date, '%Y-%c-%d %H:%i:%s') in_date,ifnull(t.remark,'') remark,s.dept_name parent_dept_name " +
				"FROM (select @rownum:=0) r,zcgl.sys_dept t,zcgl.sys_dept s where t.parent_dept_id=s.dept_id and t.dept_state = '可用' ";

		if(!id.equals("")){
			sql += " and t.id = '"+id+"'";
		}
		
		sql += " ) a where a.remark != '结点'";
System.out.println("修改部门时的查询："+sql);
				List resultList = session.findSql(sql);
				Map resultMap = (Map)resultList.get(0);
				//dept_id,dept_name,parent_dept_id,area,dept_address,phone_num,
				//email,dept_state,company_name,post_num,remark
				request.setAttribute("id", id);
				request.setAttribute("deptName", resultMap.get("deptName"));
				request.setAttribute("deptId", resultMap.get("deptId"));
				request.setAttribute("parentDeptId", resultMap.get("parentDeptId"));
				request.setAttribute("parentDeptName", resultMap.get("parentDeptName"));
				request.setAttribute("area", resultMap.get("area"));
				request.setAttribute("address", resultMap.get("deptAddress"));
				request.setAttribute("phoneNum", resultMap.get("phoneNum"));				
				request.setAttribute("deptState", resultMap.get("deptState"));			
				request.setAttribute("remark", resultMap.get("remark"));
				
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
