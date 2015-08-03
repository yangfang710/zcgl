package com.cqupt.login;


import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionSupport;



/**
 * 用户登录验证  
 * @author sulei
 * 
 *
 */
public class LoginAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpServletRequest request=null;
	
	public String execute()
	{	
		return "success";
	}
	
}
