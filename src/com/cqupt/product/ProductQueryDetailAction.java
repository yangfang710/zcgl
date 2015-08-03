package com.cqupt.product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.cqupt.pub.util.JsonUtil;
import com.opensymphony.xwork2.ActionSupport;

public class ProductQueryDetailAction extends ActionSupport {
	Logger logger = Logger.getLogger(getClass());
	private static final long serialVersionUID = 1794195924380050122L;
	HttpServletRequest request = null;

	public String execute() {
		this.request = ServletActionContext.getRequest();

		String pageSize = request.getParameter("pagesize");
		String page = request.getParameter("page");
		HttpServletResponse response = ServletActionContext.getResponse();
		// 设置字符集
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(getProductList(pageSize, page));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 直接输入响应的内容
		return null;// 不需要跳转某个视图 因为上面已经有了直接输出的响应结果

	}

	private String getProductList(String pageSize, String page) {
		DataStormSession session = null;
		String resultStr = "";

		String id = request.getParameter("id");

		try {
			session = DataStormSession.getInstance();
			String sql = "";

			sql = "SELECT\n"
					+ "	@rownum :=@rownum + 1 AS rownum,\n"
					+ "	t.*\n"
					+ "FROM\n"
					+ "	(SELECT @rownum := 0) r,\n"
					+ "	(\n"
					+ "		SELECT\n"
					+ "			a.id,\n"
					+ "			b.address,\n"
					+ "			a.class_code,\n"
					+ "			b.department_id,\n"
					+ "			b.department_name,\n"
					+ "			b.manage_user,\n"
					+ "			a.manufacturers,\n"
					+ "			a.price,\n"
					+ "			a.specification,\n"
					+ "			a.receive_user,\n"
					+ "			b.use_user,\n"
					+ "			a.product_code,\n"
					+ "			a.product_name,\n"
					+ "			b.product_status,\n"
					+ "			b.product_use,\n"
					+ "			a.product_version,\n"
					+ "			date_format(\n"
					+ "				b.last_out_time,\n"
					+ "				'%Y-%m-%d'\n"
					+ "			) last_out_time,\n"
					+ "			b.remark,b.oper_user\n"
					+ "		FROM\n"
					+ "			zcgl.product a  left join zcgl.product_edit b on a.id=b.product_id \n"
					+ " where a.id = " + id + " ";

			sql += " order by b.last_out_time desc ) t";

			logger.info("资产历史记录查询：" + sql);
			Map resultMap = session.findSql(sql, Integer.parseInt(page),
					Integer.parseInt(pageSize));
			resultStr = new JsonUtil().map2json(resultMap);
			session.closeSession();

		} catch (Exception e) {
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return resultStr;

	}

}
