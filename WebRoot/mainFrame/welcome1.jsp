<%@ page  import="java.util.*"   contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%@ include file="/top/commons.jspf"%>
<link rel="stylesheet" type="text/css" href="mainFrame/pop/manhuaDialog.1.0.css"/>
<link href="mainFrame/css/ads.css" style="text/css" rel="stylesheet"/>
<script src="mainFrame/pop/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="mainFrame/pop/manhuaDialog.1.0.js"></script>
<script type="text/javascript">
function show(messageId){
	/*$("#test").manhuaDialog({					       
		Event : "click",								//触发响应事件
		title : "公告详情",					//弹出层的标题
		type : "id",									//弹出层类型(text、容器ID、URL、Iframe)
		content : "test",								//弹出层的内容获取(text文本、容器ID名称、URL地址、Iframe的地址)
		width : 500,									//弹出层的宽度
		height : 300,									//弹出层的高度	
		scrollTop : 200,	 							//层滑动的高度也就是弹出层时离顶部滑动的距离
		isAuto : true,									//是否自动弹出
		time : 2000,									//设置弹出层时间，前提是isAuto=true
		isClose : false,  								//是否自动关闭		
		timeOut : 5000									//设置自动关闭时间，前提是isClose=true	
	});*/
	
	var m = $.ligerDialog.open({ 
  	  title: '查看公告信息',
  	  height: 400, 
  	  width: 700, 
  	  url: 'messageReadAction?messageId='+messageId,	        	
  	   showMax: true,	        	   
  	  isResize: true,   
  	  slide: false,  	        
  	   buttons: [{ text: '关闭窗口', onclick: f_closeAddWindow }]
    }); 
    m.max();
});
</script>
</head>
<body>
 <%
   //循环显示数据
  	 List list=(List)request.getAttribute("resultList"); // 取request里面的对象队列
     if(list.size()!=0){
      	for(int i=0;i<list.size();i++){        
         	pageContext.setAttribute("list",list.get(i)); //保存到页面pageContext里面方便下面进行EL表达式调用
 %>

<ul class="column s1" id="info">
	<li class="title"><div>信息公示</div></li>
	<li>创建人：<a href="#">${list.operUser }</a></li>
	<li>创建时间：<a href="#">${list.operTime }</a></li>
	<li>发布内容：<a href="#"  onclick="show( ${list.id })"   id="test">${list.information }</a></li>
	<li class="title"><div></div></li>
</ul>
</br>
 <%
      } //for循环结束
    }else{
%>
<ul class="column s1" id="info">
	<li class="title"><div>信息公示</div></li>
	<li>创建人：<a href="#">无</a></li>
	<li>创建时间：<a href="#">无</a></li>
	<li>发布内容：<a href="#">无</a></li>
	<li class="title"><div></div></li>
</ul>
<%
    }
%>

</body>
</html>
