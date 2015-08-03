<%@ page contentType="text/html;charset=utf-8" import="java.io.*,java.net.*" %>
<html>
<head>
    <title>下载页面
    </title>
 
    
</head>
  <body>
<%

	response.reset();//可以加也可以不加
	response.setContentType("application/x-download");
	response.setContentType("application/force-download");
	String filedownload =request.getAttribute("path").toString();
	String filedisplay ="资产信息EXCEL模板.xls";
	filedisplay = URLEncoder.encode(filedisplay,"UTF-8");
	response.addHeader("Content-Disposition","attachment;filename=" + filedisplay);

	OutputStream outp = null;
	FileInputStream in = null;
	try
	{
		outp = response.getOutputStream();
		in = new FileInputStream(filedownload);

		byte[] b = new byte[1024];
		int i = 0;
		while((i = in.read(b)) > 0){
			outp.write(b, 0, i);
		}
		outp.flush();
		out.clear();
		out = pageContext.pushBody();
	}catch(Exception e){
		System.out.println("Error!");
		e.printStackTrace();
	}finally{
		if(in != null){
			in.close();
			in = null;
		}
		if(outp != null){
			outp.close();
			outp = null;
		}
	}
%>



  </body>
</html>

