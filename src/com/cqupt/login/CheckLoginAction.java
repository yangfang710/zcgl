package com.cqupt.login;

import java.io.IOException;
import java.io.PrintWriter;



import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.Md;
import com.cqupt.pub.util.ObjectChanger;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

public class CheckLoginAction extends ActionSupport{
Logger logger = Logger.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;
	private ObjectChanger objChanger = ObjectChanger.getInstance();

	private String userId;
	private String password;
	private String usercaptcha;
	private String userName;
	private String userDept;
	private String userDeptId;
	private String groupId;
	private String dataAuthId;
	private String parentDeptId;
	private String area;
	private String contactNumber;

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getDataAuthId() {
		return dataAuthId;
	}

	public void setDataAuthId(String dataAuthId) {
		this.dataAuthId = dataAuthId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}
	private Md md5fun = new Md();
	
	public String getUsercaptcha() {
		return usercaptcha;
	}

	public void setUsercaptcha(String usercaptcha) {
		this.usercaptcha = usercaptcha;
	}
	
	public String getUserId() 
	{
		return userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	public void setUserDeptId(String userDeptId) {
		this.userDeptId = userDeptId;
	}

	public String getUserDeptId() {
		return userDeptId;
	}
	
	public void setParentDeptId(String parentDeptId) {
		this.parentDeptId = parentDeptId;
	}

	public String getParentDeptId() {
		return parentDeptId;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArea() {
		return area;
	}
	public String execute()
	{
		logger.info("CheckLoginAction：");
		this.request = ServletActionContext.getRequest();
		ServletContext sc = ServletActionContext.getServletContext();
		String result="success";
		String userId=this.getUserId();
		String userPwd=this.getPassword();
		this.setUserId(request.getAttribute("userId").toString());
		this.setPassword(request.getAttribute("password").toString());
		this.setUsercaptcha(request.getAttribute("usercaptcha").toString());
	
		HttpServletResponse response=ServletActionContext.getResponse();
		//检测该客户端是否有用户已登录
		/*	HttpSession session1 = request.getSession();         //把用户放进session	
		String old_user_name = (String)session1.getAttribute("userId");
		logger.info("************"+session1.getId()+session1.getAttribute("userId")+"************");
//	System.out.println("old_user_name:"+old_user_name);
//	System.out.println(old_user_name!=null);

		
		
		
		if( old_user_name!=null ){
			//强制用户下线
			//OnlineUser.online.get(old_user_name).invalidate();		
			
			result = result+"@isExist";
		}else{
			result = checkPwd(userId, userPwd); //验证登录名		
			if(result.equals("success"))         //登录名成功验证,验证验证码
				result = checkCaptcha();			
			if(result.equals("success"))        //验证码成功验证是否登陆
				result = checkLogin(userId,request.getRemoteAddr(),sc);
			result = result+"@noExist";	
		}
		
*/
		result = checkPwd(userId, userPwd); //验证登录名		
		if(result.equals("success"))         //登录名成功验证,验证验证码
			result = checkCaptcha();			
		if(result.equals("success"))        //验证码成功验证是否登陆
			result = checkLogin(userId,request.getRemoteAddr(),sc);
		 response.setCharacterEncoding("UTF-8");    
         PrintWriter out;
		try {
			out = response.getWriter();
			out.print(result);    
	        out.flush();    
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}    

         //直接输入响应的内容    
             
		return null;
	}
	
	 /**
     * 用户密码验证
     * @param prop
     * @return useIdError-登录名错误 pwdError-密码错误  success-验证成功 ---sysError-系统异常
     * @throws 
     */
	String checkPwd(String userId, String userPwd) {
		String result;
		DataStormSession session = null;
		try 
		{
			session = DataStormSession.getInstance();
			String sql="select t.user_pwd,t.dept_name,t.user_name,t.dept_id,t.group_id,m.parent_dept_id,m.phone_num " +
					"from zcgl.sys_user t,zcgl.sys_dept m where t.dept_id=m.dept_id and t.user_id='"+userId+"'" +
							" and t.user_state = '可用' ";//在用户表中根据userId来获取密码的MD5值
			logger.info("检测登录用户信息sql："+sql);
			List<Map<String, Object>> resultList = session.findSql(sql); 
			if((resultList.size())==0)
			{
				result = "登录名"+userId+"不存在或已禁用";
			} else{
				Map resultMap = (Map)resultList.get(0);		
				String passwd = resultMap.get("userPwd").toString();
				setUserName(resultMap.get("userName").toString());
				setUserDept(resultMap.get("deptName").toString());
				setUserDeptId(resultMap.get("deptId").toString());
				setGroupId(resultMap.get("groupId").toString());
				setContactNumber(resultMap.get("phoneNum") == null ? "" : resultMap.get("phoneNum").toString());
				setParentDeptId(resultMap.get("parentDeptId").toString());
				/*
				sql = "SELECT DISTINCT b.data_auth_id FROM  sys_user a,sys_user_group_data_auth b WHERE a.user_id ='"+userId+"' AND b.group_id =a.group_id";
System.out.println("sql"+sql);
				List resultList1 = session.findSql(sql);
				StringBuffer sb = new StringBuffer();
				
				
				for(int i = 0; i < resultList1.size(); i++) {
					Map resultMap1 = (Map)resultList1.get(i);
					sb.append("'"+resultMap1.get("dataAuthId")+"'");
					if(i != resultList1.size()-1)
						sb.append(",");
				}

*/				
				//根据部门、角色组、用户　来确定该用户的数据权限(userId取传进来的值）
				StringBuffer sb = new StringBuffer();
				
				String deptId = resultMap.get("deptId").toString();
				String groupId = resultMap.get("groupId").toString();
				List dataAuthList = null;
				Map dataAuthMap = null;
				Map resultMap1 = null;
				String dataType = "";
				//根据角色分数据权限
				if(groupId.equals("1")){
					//省公司超级管理员
					logger.info("!!!!!!本用户为超级管理员用户!!!!!!");
					//读取所有部门ＩＤ号
					sql = "select t.dept_id from zcgl.sys_dept t where t.dept_state = '可用'";
					logger.info("读取所有部门ＩＤ号sql："+sql);
					dataAuthList = session.findSql(sql);
					for(int i = 0; i < dataAuthList.size(); i++) {
						resultMap1 = (Map)dataAuthList.get(i);
						sb.append("'"+resultMap1.get("deptId")+"'");
						if(i != dataAuthList.size()-1)
							sb.append(",");
					}
					
				}else if(groupId.equals("2")){
					logger.info("!!!!!!本用户为部门管理员用户!!!!!!");
					//读取自己所属数据权限里的部门ＩＤ号	
					sql = "select data_auth_id from zcgl.sys_user where user_id='"+userId+"'";
					List list = session.findSql(sql);
					Map map = (Map)list.get(0);
					String dataAuthId = map.get("dataAuthId").toString();
					String [] dataAuthIdArray = dataAuthId.split(";");
					for(int j = 0; j< dataAuthIdArray.length; j++){
					
						sql = "select t.dept_id from zcgl.sys_dept t where t.dept_state = '可用' and t.dept_id = '"+dataAuthIdArray[j]+"'";
						logger.info("数据权限里的部门ＩＤ号sql："+sql);
						dataAuthList = session.findSql(sql);
						resultMap1 = (Map)dataAuthList.get(0);
						sb.append("'"+resultMap1.get("deptId")+"'");
						if(j != dataAuthIdArray.length-1 ){
							sb.append(",");
						}
						/*for(int i = 0; i < dataAuthList.size(); i++) {
							resultMap1 = (Map)dataAuthList.get(i);
							sb.append("'"+resultMap1.get("deptId")+"'");
							
							if(j== dataAuthIdArray.length-1 && i == dataAuthList.size()-1){
								
							}else{
								sb.append(",");
							}
						}*/
					}
				}else{
					logger.info("!!!!!!本用户为普通用户!!!!!!");
					//读取自己的资产，在查询的地方要加product表的use_user = session中的用户名
					sb.append("'"+deptId+"'");
					
					
				}
				logger.info("最后的数据权限dataAuthId:"+sb.toString());
	            setDataAuthId(sb.toString());
	           
				if(passwd.equals(md5fun.getMD5ofStr(userPwd))) {//md5加密
					result = "success";
				}else {
					result = "登录名"+userId+"密码输入错误";
				}		System.out.println(passwd + ","+md5fun.getMD5ofStr(userPwd));
			} 
			
			session.closeSession();
		}		
		catch (Exception e) 
		{
			result = "系统异常，请联系系统管理员";
			e.printStackTrace();
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				e1.printStackTrace();
			}
			
		}
		return result;
		
	}
	/*
	 * 验证码是否正确
	 */
	String checkCaptcha(){
		HttpSession session = request.getSession();
		String result = "success";

		String captcha = session.getAttribute("randomStr").toString();
		if(!captcha.equals(this.getUsercaptcha())) {
			result = "验证码输入错误，请重新输入";
		}
			
		return result;
	}
	/*
	 * 验证账号是否已经登陆
	 */
	String checkLogin (String userId,String addr,ServletContext sc){
		String result="success";
		String loginTime = "";
		try {
			loginTime = objChanger.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
		} catch (CquptException e) {
			e.printStackTrace();
			//系统错误
			result = "系统异常，请联系系统管理员";
		}
try {
	
	//取消单个用户登录机制
	HttpSession session = request.getSession();         //把用户放进session	
	
	

	
	session.setAttribute("userId", userId);//
	session.setAttribute("userIp", addr);//
	session.setAttribute("loginTime", loginTime);//
	session.setAttribute("userName", getUserName());
	session.setAttribute("deptName", getUserDept());
	session.setAttribute("deptId", getUserDeptId());
	session.setAttribute("groupId", getGroupId());
	session.setAttribute("parentDeptId", getParentDeptId());
	session.setAttribute("dataAuthId", getDataAuthId());
	
	//用户每次登陆都刷新一次application scope里部门电话的值，避免每次更改部门电话就要重启服务器。
	//ServletActionContext.getServletContext().setAttribute(this.getUserDeptId() + "TelNumber", this.getContactNumber());
	
	OnlineUser.online.put(userId,session);
	
	/*	限制用户ID同时不能多个地方登录
	 * if(OnlineUser.online.containsKey(userId) == false){	//此员工未曾登陆			
			HttpSession session = request.getSession();         //把用户放进session	
			session.setAttribute("userId", userId);//
			session.setAttribute("userIp", addr);//
			session.setAttribute("loginTime", loginTime);//
			session.setAttribute("userName", getUserName());
			session.setAttribute("userDept", getUserDept());
			session.setAttribute("deptId", getUserDeptId());
			session.setAttribute("rlId", getRlId());
			session.setAttribute("dataAuthId", getDataAuthId());
			System.out.println("this.getRlId()"+this.getDataAuthId());
			
			OnlineUser.online.put(userId,session);
		
		} else if( ((HttpSession)OnlineUser.online.get(userId)).getAttribute("userIp").equals(addr) )
		{
			((HttpSession)OnlineUser.online.get(userId)).invalidate();
			//清空上次登陆的session
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);//
			session.setAttribute("userIp", addr);//
			session.setAttribute("loginTime", loginTime);//
			session.setAttribute("userName", getUserName());
			session.setAttribute("userDept", getUserDept());
			session.setAttribute("deptId", getUserDeptId());
			session.setAttribute("rlId", getRlId());
			OnlineUser.online.put(userId,session);
			result = "success";
		} else {
			result = "登录名"+userId+"已经登陆，登陆ip地址为 " + ((HttpSession)OnlineUser.online.get(userId)).getAttribute("userIp");
		}*/
		}
		catch (Exception e) {
			e.printStackTrace();
			//系统错误
			result = "系统异常，请联系系统管理员";
		}
		return result;
	}

	

	


}
