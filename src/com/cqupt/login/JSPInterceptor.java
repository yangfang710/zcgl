package com.cqupt.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 拦截所有非登录用户对JSP的访问~因为struts2不能对JSP进行拦截(不拦截登录必须的JSP页面)
 * @author Kevin Su
 * Modify Date:20130818
 */
public class JSPInterceptor implements Filter {
	ServletContext context;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest r = (HttpServletRequest) request;
		HttpSession session = r.getSession();
		String path = r.getRequestURI();
		System.out.println("~~~~~~~~~~~~~JSP interceptor~~~~~~~~~~~~~:" + path);
		if(sessionIsUseable(session) || path.equals("/zcgl/login.jsp") || path.equals("/zcgl/identify_Code.jsp")) {
			chain.doFilter(request, response);
		} else if (path.equals("/zcgl/")) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			System.out.println("access denied !");
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		context = filterConfig.getServletContext();
	}
	
	private boolean sessionIsUseable(HttpSession session) {
		if( session.getAttribute("userName") == null)
			return false;
		if( session.getAttribute("dataAuthId") == null)
			return false;	
		if( session.getAttribute("groupId") == null)
			return false;
		if( session.getAttribute("userId") == null)
			return false;
		if( session.getAttribute("userIp") == null)
			return false;
		if( session.getAttribute("loginTime") == null)
			return false;
		if( session.getAttribute("deptName") == null)
			return false;
		if( session.getAttribute("deptId") == null)
			return false;

		return true;
	}

}
