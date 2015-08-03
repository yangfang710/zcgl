package com.cqupt.ads;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class ShouyeAction extends ActionSupport {

	private static final long serialVersionUID = 1794195924380050122L;
	HttpServletRequest request = null;

	public String execute() {
		this.request = ServletActionContext.getRequest();
		Logger logger = Logger.getLogger(this.getClass());
		DataStormSession session = null;
		String result = "";
		try {
			session = DataStormSession.getInstance();
			String sql = "SELECT * from zcgl.ads ORDER BY oper_time DESC ";

			logger.info("首页显示公告时的查询：" + sql);
			List resultList = session.findSql(sql);
			request.setAttribute("resultList", resultList);
			// for (int i = 0; i < resultList.size(); i++) {
			// Map resultMap = (Map) resultList.get(i);
			// request.setAttribute("information",
			// resultMap.get("information"));
			// request.setAttribute("operUser", resultMap.get("operUser"));
			// request.setAttribute("operTime", resultMap.get("operTime"));
			// }
			session.closeSession();
			result = "success";

		} catch (Exception e) {
			result = "error";
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return result;
	}
}
