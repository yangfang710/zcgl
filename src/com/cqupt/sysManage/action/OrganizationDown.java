package com.cqupt.sysManage.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.ExcelServiceImpl;
import com.cqupt.pub.util.IExcelService;
import com.opensymphony.xwork2.ActionSupport;

public class OrganizationDown extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass());
		InputStream excelStream;   
		HttpServletRequest request = null;

	    public InputStream getExcelStream() {
			return excelStream;
		}

		public void setExcelStream(InputStream excelStream) {
			this.excelStream = excelStream;
		}


		public String execute(){ 
			String resultStr = "";
			String sql = "";
			DataStormSession session = null;
	
			try{//d.storage_in_amount,
				session = DataStormSession.getInstance();
				List resultList = null;
				Map resultMap = null;
				
				HttpServletResponse response = ServletActionContext.getResponse();
				 response.setCharacterEncoding("UTF-8");    
				String fileName =  "部门信息.xls";
				try {
					fileName = new String(fileName.getBytes(),"ISO-8859-1");
					response.setHeader("Content-Disposition", "attachment;fileName="+fileName);
				}catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
				IExcelService es = new ExcelServiceImpl();   
				   String[] title = {"部门编号","部门名称","上级部门","地址","电话","新增时间","状态","备注"};//excel工作表的标题
			        String[] keys = {"deptId","deptName","parentDeptName","deptAddress","phoneNum","inDate","deptState","remark"};
		   
			        sql = "SELECT @rownum:=@rownum+1 as rownum,a.* from (select t.id,t.dept_id,t.dept_name,t.dept_state,t.parent_dept_id,t.dept_address," +
							"t.phone_num,date_format(t.in_date, '%Y-%c-%d %H:%i:%s') in_date,t.oper_user_name,ifnull(t.remark,'') remark,s.dept_name parent_dept_name " +
							"FROM (select @rownum:=0) r,zcgl.sys_dept t,zcgl.sys_dept s where t.parent_dept_id=s.dept_id and t.dept_state = '可用' ) a";
					
				logger.info("部门信息Excel:"+sql);	
		        excelStream = es.getExcelInputStream(title,keys,sql);      
		        resultStr="excel";
				} catch (CquptException ce) {
					ce.printStackTrace();
				} finally {
					if (session != null) {
						try {
							session.closeSession();
						} catch (CquptException e) {
							e.printStackTrace();
						}
					}
				}
			
			 System.out.print("resultStr===="+resultStr);
			 return resultStr;   
			}
		}

