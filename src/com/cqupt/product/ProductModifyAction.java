package com.cqupt.product;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class ProductModifyAction extends ActionSupport{

	private static final long serialVersionUID = 1794195924380050122L;
	HttpServletRequest request=null;


	public String execute()
	{
		this.request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		String ttType = request.getParameter("ttType");
		//ttType=modify 跳转到修改页面
		//ttType=edit 跳转到变更页面
		if(ttType.equals("batchModify")){
			//批量修改,取第一条的值在页面返回
			String[] idArray = id.split(";");
			id = idArray[0];
		}
		DataStormSession session = null;
		try 
		{
			session = DataStormSession.getInstance();
			String sql = "SELECT * from zcgl.product where 1=1";

			if(!id.equals("")){
				sql += " and id = '"+id+"'";
			}

			System.out.println("修改资产时的查询："+sql);
			List resultList = session.findSql(sql);
			Map resultMap = (Map)resultList.get(0);
			//dept_id,dept_name,parent_dept_id,area,dept_address,phone_num,
			//email,dept_state,company_name,post_num,remark
			//id保持为JSP传来的值
			id = request.getParameter("id");
			request.setAttribute("id", id);
			request.setAttribute("productDetail", resultMap.get("productDetail"));
			request.setAttribute("departmentId", resultMap.get("departmentId"));
			request.setAttribute("departmentName", resultMap.get("departmentName"));	
	
			request.setAttribute("productCode", resultMap.get("productCode"));
			request.setAttribute("productName", resultMap.get("productName"));
			request.setAttribute("classCode", resultMap.get("classCode"));
			request.setAttribute("productVersion", resultMap.get("productVersion"));				
			request.setAttribute("specification", resultMap.get("specification"));		
			
			request.setAttribute("price", resultMap.get("price"));
			request.setAttribute("manufacturers", resultMap.get("manufacturers"));
			request.setAttribute("productUse", resultMap.get("productUse"));
			request.setAttribute("receiveUser", resultMap.get("receiveUser"));				
			request.setAttribute("productStatus", resultMap.get("productStatus"));	
			
			request.setAttribute("manageUser", resultMap.get("manageUser"));
			request.setAttribute("useUser", resultMap.get("useUser"));
			request.setAttribute("address", resultMap.get("address"));
			request.setAttribute("lastOutTime", resultMap.get("lastOutTime"));				
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
		            
   
        
       return ttType;


	}
	
	
}
