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
import com.cqupt.pub.exception.CquptException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ProductUpdateBatch extends ActionSupport implements
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
	String productName,classCode,productVersion,specification,price,manufacturers;
	String productNameStr="",classCodeStr="",productVersionStr="",specificationStr="",priceStr="",manufacturersStr="";
	String[] idArray = id.split(";");
	sql = "select product_name,class_code,product_version,specification,price,manufacturers" +
			" from zcgl.product where id = '"+idArray[0]+"'";
	List resultList = session.findSql(sql);
	Map resultMap = (Map)resultList.get(0);
	productName = resultMap.get("productName").toString();
	classCode = resultMap.get("classCode").toString();
	productVersion = resultMap.get("productVersion").toString();
	specification = resultMap.get("specification").toString();
	price = resultMap.get("price").toString();
	manufacturers = resultMap.get("manufacturers").toString();
	
	if(!productName.equals(product.getProductName() )){
		productNameStr = ",product_name = '"+ product.getProductName() + "'";
	}
	if(!classCode.equals(product.getClassCode() )){
		classCodeStr = ",class_code = '"+ product.getClassCode() + "'";
	}
	if(!productVersion.equals(product.getProductVersion() )){
		productVersionStr = ",product_version = '"+ product.getProductVersion() + "'";
	}
	if(!specification.equals(product.getSpecification() )){
		specificationStr = ",specification = '"+ product.getSpecification() + "'";
	}
	if(!price.equals(product.getPrice() )){
		priceStr = ",price = '"+ product.getPrice() + "'";
	}
	if(!manufacturers.equals(product.getManufacturers() )){
		manufacturersStr = ",manufacturers = '"+ product.getManufacturers() + "'";
	}
	for(int i=0;i<idArray.length;i++){	
		//id='"+idArray[i]+"' 这句是为了每个变量前面能加逗号
		sql = "update zcgl.product set id='"+idArray[i]+"' "+productNameStr+
				 classCodeStr+ 
				 productVersionStr+
				 specificationStr+
				 priceStr+
				 manufacturersStr+
				"  where id = '"+idArray[i]+"'";
		logger.info("批量修改资产:" + sql);
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
