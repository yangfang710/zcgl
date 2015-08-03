package com.cqupt.ads;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;

public class AdsModifyAction extends ActionSupport {

	private static final long serialVersionUID = 1794195924380050122L;
	HttpServletRequest request = null;

	public String execute() {
		this.request = ServletActionContext.getRequest();
		Logger logger = Logger.getLogger(this.getClass());
		DataStormSession session = null;
		String result = "";
		String id = request.getParameter("id");
		try {
			session = DataStormSession.getInstance();
			String sql = "SELECT * from zcgl.ads where id=" + id;

			logger.info("修改资产时的查询：" + sql);
			List resultList = session.findSql(sql);
			// request.setAttribute("resultList", resultList);
			for (int i = 0; i < resultList.size(); i++) {
				Map resultMap = (Map) resultList.get(i);
				request.setAttribute("id", resultMap.get("id"));
				request.setAttribute("information",
						resultMap.get("information"));
				request.setAttribute("operUser", resultMap.get("operUser"));
				request.setAttribute("operTime", resultMap.get("operTime"));
			}
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
