package com.cqupt.sysManage.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.JsonUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

public class UserManagerQueryAction  extends ActionSupport{
	Logger logger = Logger.getLogger(getClass());
	private static final long serialVersionUID = -2465087541585226388L;
	/**
	 *   查询本部门用户信息
	 */
	HttpServletRequest request = null;
	public String execute(){
					request=ServletActionContext.getRequest();
					HttpSession session = request.getSession();
					String queryDeptId = request.getParameter("queryDeptId");
					if(queryDeptId == null){
						queryDeptId = "01";
					}
					String sessionDeptId = (String)session.getAttribute("deptId");
					String pageSize = request.getParameter("pagesize");
					String page = request.getParameter("page");
					 String sortName = request.getParameter("sortname");
			    		String sortOrder = request.getParameter("sortorder");
					HttpServletResponse response=ServletActionContext.getResponse();
			           //设置字符集    
		           response.setCharacterEncoding("UTF-8");    
		           PrintWriter out;
					try {
						out = response.getWriter();
						   //直接输入响应的内容    
				        out.println(getUserList(sessionDeptId,queryDeptId,pageSize, page,sortName,sortOrder));    
				        out.flush();    
				        out.close();    
					} catch (IOException e) {
						e.printStackTrace();
					}    

			       return null;//不需要跳转某个视图 因为上面已经有了直接输出的响应结果    

			}
			
	private char[] getUserList(String sessionDeptId,String queryDeptId ,String pageSize, String page,String sortName,String sortOrder){
				String resultStr = "";
				String sql = "";
				DataStormSession session = null;
				try{
					session = DataStormSession.getInstance();
					sql = "select b.* , @rownum:=@rownum+1 as rownum  FROM (select @rownum:=0) r,(select t.user_id userid, t.user_name username, " +
							"t.dept_id deptid,t.dept_name deptname, t.group_name groupname, t.user_state userstate, ifnull(t.user_email,' ') useremail, " +
							"ifnull(t.phone_num,' ') usermobphone,t.team_name teamname,t.data_auth_name dataauthname "+
				 	  " from  zcgl.sys_user t WHERE 1=1 and t.dept_id like '%" + queryDeptId + "%' ";
					
					sql += " ) b  order by  " + sortName + "  " + sortOrder + " ";
					logger.info("查询用户:"+sql);	
					session = DataStormSession.getInstance();
					Map resultMap = session.findSql(sql, Integer.parseInt(page), Integer.parseInt(pageSize));
					resultStr = JsonUtil.map2json(resultMap);
					
					session.closeSession();
				} catch (CquptException ce) {
					try {
						resultStr = "error";
						session.exceptionCloseSession();
					} catch (CquptException e1) {
						e1.printStackTrace();
					}
					ce.printStackTrace();
				} 
				return resultStr.toCharArray();
				
			}

		}
