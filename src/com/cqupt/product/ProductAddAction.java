package com.cqupt.product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ProductAddAction extends ActionSupport implements
		ModelDriven<Product> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(this.getClass());
	private Product product = new Product();
	HttpServletRequest request = null;
	
	public Product getModel() {
		return product;
	}

	@Override
	public String execute() throws Exception {
		this.request = ServletActionContext.getRequest();
		String operUserName = (String) request.getSession().getAttribute(
				"userName");

		String resultStr = "success";
		DataStormSession session = DataStormSession.getInstance();
		HttpServletResponse response = ServletActionContext.getResponse();
		String sql = "select product_code from zcgl.product where product_code='"
				+ product.getProductCode() + "'";
		List list = session.findSql(sql);
		if (list.size() > 0) {
			resultStr = "exist";
		}
		sql = "select user_id from zcgl.sys_user where user_name='"+product.getUseUser()+"'";
		list = session.findSql(sql);
		if (list.size() == 0) {
			resultStr = "usernotexist";
		}
		if(resultStr.equals("success")){
			sql = "insert into zcgl.product (department_id,department_name,product_code,product_name,class_code,product_version,specification,price,manufacturers,product_use,receive_user,in_time,product_status,manage_user,use_user,address,last_out_time,remark,oper_user,oper_time) values ('"
					+ product.getDepartmentId()
					+ "','"
					+ product.getDepartmentName()
					+ "','"
					+ product.getProductCode()
					+ "','"
					+ product.getProductName()
					+ "','"
					+ product.getClassCode()
					+ "','"
					+ product.getProductVersion()
					+ "','"
					+ product.getSpecification()
					+ "','"
					+ product.getPrice()
					+ "','"
					+ product.getManufacturers()
					+ "','"
					+ product.getProductUse()
					+ "','"
					+ product.getReceiveUser()
					+ "','"
					+ product.getInTime()
					+"','"
					+ product.getProductStatus()
					+ "','"
					+ product.getManageUser()
					+ "','"
					+ product.getUseUser()
					+ "','"
					+ product.getAddress()
					+ "','"
					+ product.getLastOutTime() + "','" + product.getRemark() + "','" + operUserName + "',sysdate())";
			logger.info("增加资产："+sql);
			session.add(sql);
		}
		session.closeSession();
		//资产表提交
		//插入资产历史记录
		if(resultStr.equals("success")){
			session = DataStormSession.getInstance();
			sql = "select * from zcgl.product where product_code='"
					+ product.getProductCode() + "'";
			list = session.findSql(sql);
			if (list.size() > 0) {
				Map map = (Map)list.get(0);
				String id = map.get("id").toString();
				sql = "insert into zcgl.product_edit (product_id,department_id,department_name,manage_user,address,product_status,product_use,use_user,last_out_time,remark,oper_user,oper_time)" +
						" values ('"+id+"','"+product.getDepartmentId()+"','"+product.getDepartmentName()+"'," +
								"'"+product.getManageUser()+"','"+product.getAddress()+"','"+ product.getProductStatus()+"'," +
										"'"+product.getProductUse() +"','"+product.getUseUser() +"','"+product.getLastOutTime()+"','"+ product.getRemark() +"','"+operUserName+"',sysdate())";
				logger.info("资产状态变更,记入历史表:" + sql);
				session.add(sql);
			}
			
			session.closeSession();
		}
		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(resultStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
