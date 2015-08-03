package com.cqupt.ads;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;
import common.Logger;

public class MessageReadAction extends ActionSupport{

	private static final long serialVersionUID = -1015300446840329702L;
	Logger logger =  Logger.getLogger(this.getClass());
	HttpServletRequest request = null;

	public String execute() throws Exception {
logger.info("MessageReadAction:");
		request = ServletActionContext.getRequest();
		String deptId = request.getSession().getAttribute("deptId").toString();
		String userName = request.getSession().getAttribute("userName").toString();
		String messageId =  request.getParameter("messageId");
		DataStormSession session = null;
		try 
		{
			session = DataStormSession.getInstance();
		
			String sql = "SELECT id,information,oper_user,date_format(oper_time,'%Y-%m-%d %H:%i:%s') oper_time,appendix_name from zcgl.ads where id = '"+messageId+"'";;
logger.info("查询公告详细信息"+sql);			
			List resultList = session.findSql(sql);
			Map resultMap = (Map)resultList.get(0);
			request.setAttribute("id", resultMap.get("id").toString());
			request.setAttribute("information", resultMap.get("information").toString());
			request.setAttribute("operUser", resultMap.get("operUser").toString());
			request.setAttribute("operTime", resultMap.get("operTime").toString());
			request.setAttribute("appendixName", resultMap.get("appendixName").toString());
			
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
		            
		
			return "success";
		}



}
