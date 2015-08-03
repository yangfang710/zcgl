package com.cqupt.product;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.util.ExcelIn;

public class ProductAddExcelAction {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass());

		HttpServletRequest request = null;
		private static final int BUFFER_SIZE = 20 * 1024; // 20K

		private File myFile; // 与页面 <input type="file"> 控件的 name 保持一致
	
		private String myFileFileName; //
		private String contentType;
		private Connection conn = null;
		Statement stmt = null;
		public File getMyFile() {
			return myFile;
		}

		public void setMyFile(File myFile) {
			this.myFile = myFile;
		}

		// 是setMyFileFileName而不是setFileName 。 struts2的规则。MyFile 是上面private File
		// myFile

		public String getContentType() {
			return contentType;
		}

		public String getMyFileFileName() {
			return myFileFileName;
		}

		public void setMyFileFileName(String myFileFileName) {
			this.myFileFileName = myFileFileName;
		}

		// 同上
		public void setMyFileContentType(String contentType) {
			this.contentType = contentType;
		}

		HttpSession session = null;
		
		public String execute() {
			request = ServletActionContext.getRequest();
			
			session = request.getSession();
			String deptId = session.getAttribute("deptId").toString();
			String deptName = session.getAttribute("deptName").toString();		
			String userId = session.getAttribute("userId").toString();
			String userName = session.getAttribute("userName").toString();
			String operUserName = (String) session.getAttribute(
					"userName");
			String extName = ""; // 保存文件拓展名
			String newFileName = ""; // 保存新的文件名
			String nowTimeStr = ""; // 保存当前时间
			SimpleDateFormat sDateFormat;
			Random r = new Random(); // 一个随机数对象

			String savePath = ServletActionContext.getServletContext().getRealPath(
					""); // 获取项目根路径
			savePath = savePath + "/upload/"; // 拼串组成要上传保存文件的路径，即：D:\Program
			// Files\apache-tomcat-6.0.20\webapps\(项目名)\pic\secondhand
			// 这样的路径
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码

			// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
			int rannum = (int) (r.nextDouble() * (99 - 10 + 1)) + 10; // 获取随机数
			sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
			nowTimeStr = sDateFormat.format(new Date()); // 当前时间
			
			newFileName = nowTimeStr + rannum + "资产信息.xls"; // 文件重命名后的名字
			String inId = nowTimeStr + rannum;//inId跟保存的文件名一样
			myFile.renameTo(new File(savePath + newFileName)); // 保存文件
			String result = null;
			PrintWriter out;
			try {
				out = response.getWriter();
				String storageInList = ExcelIn.getListByJxl(savePath + newFileName,
						1, 0, 18);// 从第一行，第0列开始解析			
				if (storageInList.equals(" ")){				
					result = "Excel格式不对，请参照模板@@";
				}else{
					result = insertStorageInList(storageInList,deptId,deptName,userId,userName,inId,operUserName);
					
				}
				out.print(result);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null; // 这里不需要页面转向，所以返回空就可以了
		}

		private String insertStorageInList( String storageInList, String deptId, String deptName,String userId,String userName,String inId,String operUserName) {
			DataStormSession session = null;		
					
			String resultStr = "";
			String sql= "";
			String[] resultArray = null;
			String[] cellArray = null;
			int inCount = 0;//插入的行数
		
			//String productDetail;
			String departmentId;
			String departmentName;
			String productCode;
			String productName;
			
			String classCode;
			String productVersion;
			String specification;
			String price;
			String manufacturers;
			
			String productUse;
			String receiveUser;
			String inTime;
			String productStatus;
			String manageUser;
			
			String useUser;
			String address;
			String lastOutTime;
			String remark;
			try{
			resultArray = storageInList.split(";");
			inCount = resultArray.length;//插入的行数
			
			session = DataStormSession.getInstance();	
			List list = null;
			Map map = null;
			for (int i = 0; i < resultArray.length; i++) {
				System.out.println(resultArray[i]);//一行的所有数据
				cellArray = resultArray[i].split("@");
				
					
				departmentId = cellArray[0].trim();	
				departmentName = cellArray[1].trim();	
				productCode = cellArray[2].trim();	
				productName = cellArray[3].trim();	
				
				classCode = cellArray[4].trim();	
				productVersion = cellArray[5].trim();	
				specification = cellArray[6].trim();	
				price = cellArray[7].trim();	
				manufacturers = cellArray[8].trim();	
				
				productUse = cellArray[9].trim();	
				receiveUser = cellArray[10].trim();	
				inTime = cellArray[11].trim();	
				productStatus = cellArray[12].trim();	
				manageUser = cellArray[13].trim();	
				
				useUser = cellArray[14].trim();	
				address = cellArray[15].trim();	
				lastOutTime = cellArray[16].trim();	
				remark = cellArray[17].trim();	
				logger.info("productCode:"+productCode);
				sql = "select * from zcgl.product where product_code='"
						+ productCode + "'";
				list = session.findSql(sql);
				if (list.size() > 0) {					
					resultStr = "资产编号"+productCode+"已存在@@";
					break;//跳出for循环			
							
				}else{
					sql = "insert into zcgl.product (department_id,department_name,product_code,product_name,class_code,product_version,specification,price,manufacturers,product_use,receive_user,in_time,product_status,manage_user,use_user,address,last_out_time,remark,oper_user,oper_time) values ('"
							+ departmentId
							+ "','"
							+ departmentName
							+ "','"
							+ productCode
							+ "','"
							+ productName
							+ "','"
							+ classCode
							+ "','"
							+ productVersion
							+ "','"
							+ specification
							+ "','"
							+ price
							+ "','"
							+ manufacturers
							+ "','"
							+ productUse
							+ "','"
							+ receiveUser
							+ "','"
							+ inTime
							+"','"
							+ productStatus
							+ "','"
							+ manageUser
							+ "','"
							+ useUser
							+ "','"
							+ address
							+ "','"
							+ lastOutTime + "','" + remark + "','" + operUserName + "',sysdate())";
					logger.info("增加资产excel导入："+sql);
					session.add(sql);					
					resultStr = "success@@";
				}
			}//for循环结束
			String[] result = resultStr.split("@");
			if(result[0].equals("success") ) {
				session.closeSession();//正常时提交
			} else{			
				session.exceptionCloseSession();	//为正常时回滚		
			}
			//资产表提交
			//插入资产历史记录
			session = DataStormSession.getInstance();
			
			if(result[0].equals("success") ) {
				for (int i = 0; i < resultArray.length; i++) {
					System.out.println(resultArray[i]);//一行的所有数据
					cellArray = resultArray[i].split("@");
					
					departmentId = cellArray[0].trim();	
					departmentName = cellArray[1].trim();	
					productCode = cellArray[2].trim();	
					productName = cellArray[3].trim();	
					
					classCode = cellArray[4].trim();	
					productVersion = cellArray[5].trim();	
					specification = cellArray[6].trim();	
					price = cellArray[7].trim();	
					manufacturers = cellArray[8].trim();	
					
					productUse = cellArray[9].trim();	
					receiveUser = cellArray[10].trim();	
					inTime = cellArray[11].trim();	
					productStatus = cellArray[12].trim();	
					manageUser = cellArray[13].trim();	
					
					useUser = cellArray[14].trim();	
					address = cellArray[15].trim();	
					lastOutTime = cellArray[16].trim();	
					remark = cellArray[17].trim();	
					//插入资产历史记录					
					sql = "select * from zcgl.product where product_code='"
							+ productCode + "'";
					list = session.findSql(sql);
					if (list.size() > 0) {
						map = (Map)list.get(0);
						String id = map.get("id").toString();
						sql = "insert into zcgl.product_edit (product_id,department_id,department_name,manage_user,address,product_status,product_use,use_user,last_out_time,remark,oper_user,oper_time)" +
								" values ('"+id+"','"+departmentId+"','"+departmentName+"'," +
										"'"+manageUser+"','"+address+"','"+ productStatus+"'," +
												"'"+productUse +"','"+useUser +"','"+lastOutTime+"','"+ remark +"','"+operUserName+"',sysdate())";
						logger.info("资产状态变更,记入历史表:" + sql);
						session.add(sql);
					}
				}
				
				
				session.closeSession();//正常时提交
				resultStr = "success@"+inId+"@"+inCount;	
				
			} else{			
				session.exceptionCloseSession();	//为正常时回滚		
			}
		}catch (Exception e) {
			resultStr = "系统异常,请重试或者联系系统管理员@@";
			try {
				session.exceptionCloseSession();			
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		}
	System.out.println(resultStr);
		return resultStr;

	}


	}
