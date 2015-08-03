package com.cqupt.pub.util;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;

public class LoadDeptNum extends HttpServlet{

	private static final long serialVersionUID = 729482071336819415L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		Logger logger = Logger.getLogger(LoadDeptNum.class);
		DataStormSession session = null;
		List<Map<String, Object>> list = null;
		Map<String, Object> map = null;
		String sql = "";
		try {
			session = DataStormSession.getInstance();
			sql = "select dept_id,phone_num from zcgl.sys_dept";
			logger.info("server start ... initialize department telephone number ..." + sql);
			list = session.findSql(sql);
			ServletContext context = config.getServletContext();
			for(int i = 0; i < list.size(); i ++) {
				map = list.get(i);
				//System.out.println(map.get("deptId") + "TelNumber:" + map.get("contactNumber"));
				//System.out.println("ServletContext:" + config.getServletContext());
				if(map.get("phoneNum").toString().equals("")){
					logger.info("部门编号为" + map.get("deptId") + "的部门联系电话为空！");
				} else {
					context.setAttribute(map.get("deptId") + "TelNumber", map.get("phoneNum"));
				}
				
			}
			logger.info("initialize department cellphone number successfully...");
			session.closeSession();
		} catch (CquptException e) {
			e.printStackTrace();
			if(session != null) { 
				try {
					session.exceptionCloseSession();
				} catch (CquptException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		
		
	}

}
