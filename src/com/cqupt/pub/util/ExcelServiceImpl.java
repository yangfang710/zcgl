package com.cqupt.pub.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cqupt.pub.dao.DataStormSession;
import com.cqupt.pub.exception.CquptException;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelServiceImpl implements IExcelService {   

	   public InputStream getExcelInputStream(String[] title,String[] keys, String sql) {   
	   //将OutputStream转化为InputStream   
	        ByteArrayOutputStream out = new ByteArrayOutputStream();   
	        putDataOnOutputStream(out,title,sql,keys);   
	        return new ByteArrayInputStream(out.toByteArray());   
	    }   


		private void putDataOnOutputStream(ByteArrayOutputStream os,String[] title, String sql,String[] keys) {   
	        jxl.write.Label label;   
	        WritableWorkbook workbook;  
			StringBuffer strList = new StringBuffer();
			String[] rowArray = null;
			String[] colArray = null;
	        try {   
	            workbook = Workbook.createWorkbook(os);   
	            WritableSheet sheet = workbook.createSheet("Sheet1", 0);   
	            for(int i=0; i<title.length; i++){
	            	//Label(列号,行号 ,内容 )   
					label = new jxl.write.Label(i, 0, title[i]); //定义单元格对象，指明位置和内容  
					sheet.addCell(label);    //将定义好的单元格添加到工作表中
	            }
	            List splitList = getResult(sql);
	          //把splitList放入StringBuffer strList，一行用‘；’号隔开
				for (int j = 0; j < splitList.size(); j++) {
					Map resultMap = (Map) splitList.get(j);
					
					for(int k=0; k<keys.length; k++){
						
						if(resultMap.get(keys[k]) == null || resultMap.get(keys[k]).equals("") ){
							strList.append(" @@");
						}else
						strList.append(resultMap.get(keys[k]).toString()+"@@");
						
					}
					if(j < splitList.size()-1)
						strList.append("##");
				}
	//System.out.println(strList.toString());
				rowArray = strList.toString().split("##");
				for (int j = 0; j < title.length; j++) {//列
					
					for(int i=1; i <= rowArray.length; i++){	// 行,数据从第一行开始存	
						//System.out.println(rowArray[i-1]);
						colArray = rowArray[i-1].split("@@");
						//System.out.println(j+","+ i+","+ colArray[j].toString());
						label = new jxl.write.Label(j, i, colArray[j].toString());	
						sheet.addCell(label);
					}
				}
	            workbook.write();   
	            workbook.close();   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }   

	    }


		private List getResult(String sql) {
			DataStormSession session  = null;
			List list = null;
			try {
				session = DataStormSession.getInstance();
				list = session.findSql(sql);
			} catch (CquptException e) {
				e.printStackTrace();
			}finally{
				if(session != null){
					try {
						session.closeSession();
					} catch (CquptException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return list;
		}   

	}  
