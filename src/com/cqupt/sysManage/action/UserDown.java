package com.cqupt.sysManage.action;




import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.ExcelServiceImpl;
import com.cqupt.pub.util.IExcelService;
import com.opensymphony.xwork2.ActionSupport;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;



public class UserDown extends ActionSupport {
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
			request = ServletActionContext.getRequest();
		
			
			
				
				String queryDeptId = request.getParameter("queryDeptId");
				if(queryDeptId == null){
					queryDeptId = "01";
				}
			
		
			try{//d.storage_in_amount,
				session = DataStormSession.getInstance();
				List resultList = null;
				Map resultMap = null;
				String brandId = "";
				

				
				HttpServletResponse response = ServletActionContext.getResponse();
				 response.setCharacterEncoding("UTF-8");    
				String fileName =  "用户信息.xls";
				try {
					fileName = new String(fileName.getBytes(),"ISO-8859-1");
					response.setHeader("Content-Disposition", "attachment;fileName="+fileName);
				}catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
				IExcelService es = new ExcelServiceImpl();   
				   String[] title = {"登录名","用户姓名","用户所属部门编号","用户所属部门","用户所在团队","用户所属组","数据权限","状态","用户联系方式","邮箱"};//excel工作表的标题
			        String[] keys = {"userId","userName","deptId","deptName","teamName","groupName","dataAuthName","userState","phoneNum","userEmail"};
		   
		        sql = "select m.user_id , m.user_name ,m.dept_id, m.dept_name ,m.team_name, m.group_name ,m.data_auth_name, m.user_state , m.phone_num , m.user_email " +
				" from zcgl.sys_user m " +
				"where  m.dept_id like '%" + queryDeptId + "%' ";
				
				
				
				logger.info("用户信息Excel:"+sql);	
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

