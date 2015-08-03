package com.cqupt.product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ProductEditAction extends ActionSupport implements
		ModelDriven<Product> {

	private static final long serialVersionUID = 1L;

	private Product product = new Product();

	public Product getModel() {
		return product;
	}

	Logger logger = Logger.getLogger(getClass());
	HttpServletRequest request = null;

	public String execute() {
		DataStormSession session = null;
		this.request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		// String deptState = DecodeUtils.decodeRequestString(
		// department.getDeptState(), "");
		String userName = (String) request.getSession().getAttribute(
				"userName");
		
		String result = "success";
		try {
			
			session = DataStormSession.getInstance();
			String sql = "";
			sql = "select user_id from zcgl.sys_user where user_name='"+product.getUseUser()+"'";
			List list = session.findSql(sql);
			if (list.size() == 0) {
				result = "usernotexist";
			}
			if (result.equals("success")) {
				//资产状态变更
				sql = "update zcgl.product set department_id = '"
						+ product.getDepartmentId() + "',"
						+ "department_name = '" + product.getDepartmentName() + "',"
						+ "manage_user = '" + product.getManageUser() + "',"
						+ "address = '" + product.getAddress()
						+ "'," + "product_status = '"
						+ product.getProductStatus() + "'," + "product_use = '"
						+ product.getProductUse() + "'," + "use_user = '"
						+ product.getUseUser() + "'," + "last_out_time = '"
						+ product.getLastOutTime() + "'," + "remark = '"
						+ product.getRemark() + "' " + "where id = " + id;

				logger.info("资产状态变更:" + sql);
				session.update(sql);
				//资产状态变更 ,记入历史表
				sql = "insert into zcgl.product_edit (product_id,department_id,department_name,manage_user,address,product_status,product_use,use_user,last_out_time,remark,oper_user,oper_time)" +
						" values ('"+id+"','"+product.getDepartmentId()+"','"+product.getDepartmentName()+"'," +
								"'"+product.getManageUser()+"','"+product.getAddress()+"','"+ product.getProductStatus()+"'," +
										"'"+product.getProductUse() +"','"+product.getUseUser() +"','"+product.getLastOutTime()+"','"+ product.getRemark() +"','"+userName+"',sysdate())";
				logger.info("资产状态变更,记入历史表:" + sql);
				session.add(sql);
			}
			session.closeSession();
		} catch (CquptException e) {
			result = "error";
			try {
				session.exceptionCloseSession();
			} catch (CquptException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
