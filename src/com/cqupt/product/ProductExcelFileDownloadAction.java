package com.cqupt.product;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class ProductExcelFileDownloadAction  extends ActionSupport{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5489994619108462686L;


	HttpServletRequest request = null;

	public String execute() {
		request = ServletActionContext.getRequest();     
        String path = request.getRealPath("/")+"download/product.xls";
System.out.println(path);
  
   	    request.setAttribute("path", path);
   	    request.setAttribute("userFileName", "product.xls");
		return "success";
	
		
		
	}
}