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

public class ProductUpdateAction extends ActionSupport implements
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

		
		String result = "success";
		try {
			session = DataStormSession.getInstance();
			String sql = "";
	//资产编号不能更改，要改资产编号，需要删除了重新添加 
			if (result.equals("success")) {
				sql = "update zcgl.product set product_name = '"
						+ product.getProductName() + "'," + "class_code = '"
						+ product.getClassCode() + "'," + "product_version = '"
						+ product.getProductVersion() + "'," + "specification = '"
						+ product.getSpecification() + "'," + "price = '"
						+ product.getPrice() + "'," + "manufacturers = '"
						+ product.getManufacturers() + "'," + "address = '"
						+ product.getAddress() + "'," + "receive_user = '"
						+ product.getReceiveUser() + "' " + "where id = " + id;

				logger.info("修改资产:" + sql);
				session.update(sql);
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
