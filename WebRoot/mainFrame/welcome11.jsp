<%@ page  import="java.util.*"   contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/top/commons.jspf"%>
<link href="mainFrame/css/ads.css" style="text/css" rel="stylesheet"/>
<script type="text/javascript">
function show(information){
var p=document.createElement("DIV");
if (!info) var info=information;
p.id="p";
p.style.position="absolute";
p.style.width=document.body.scrollWidth;
p.style.height=(document.body.offsetHeight>document.body.scrollHeight)?'100%':document.body.scrollHeight;
p.style.zIndex='998';
p.style.top='10px';
  p.style.left='10%';
p.style.backgroundColor="gray";
p.style.opacity='0.5';
p.style.filter="alpha(opacity=80)";
document.body.appendChild(p);
var p1=document.createElement("DIV");
var top=parseInt(parseInt(document.body.scrollHeight)*0.25)+document.body.scrollTop;
p1.style.position="absolute";
p1.style.width="300px";
p1.id="p1";
var left=Math.ceil(((document.body.scrollWidth)-parseInt(p1.style.width.replace('px','')))/2)+document.body.scrollLeft;
p1.style.height="200px";
p1.style.zIndex='999';
p1.style.top=top+'px';
 p1.style.left=left+'px';
p1.style.border="0px solid green";
var html="";
  html+="<center>"
  
  html+="<div class='p1' style='height:20px;overflow:hidden;background:green;width:300px;border-left:1px solid green;border-right:1px solid green;color:#fff;font-size:9pt;font-weight:bold;text-align:left;'> ⊙ 公告详情</div>"
html+="<div id='c' style='height:150px;width:300px;background-color:#FEEACB;overflow:hidden;border-left:1px solid green;border-right:1px solid green;padding-top:40px;font-size:9pt;'>"+info+"<br><br><br>[ <a href='javascript:this.cancle()'>关闭</a> ]</div>"
  html+="<div class='p1' style='height:1px;overflow:hidden;background:#FEEACB;width:298px;border-left:1px solid green;border-right:1px solid green;'></div>"

  html+="</center>"
document.body.appendChild(p1);
p1.innerHTML=html;
var arr=document.getElementsByTagName("select");
var i=0;
while(i<arr.length){
  arr[i].style.visibility='hidden';
  i++;
}
this.cancle=function(){
document.body.removeChild(document.getElementById('p'));
document.body.removeChild(document.getElementById('p1'));
var arr=document.getElementsByTagName("select");
  var i=0;
  while(i<arr.length){
  arr[i].style.visibility='visible';
  i++;
  }
}
}


function read(messageId){

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
}

function f_closeAddWindow(item, dialog)
{
    
   $.ligerDialog.confirm('确认要关闭窗口吗', function (yes)
             
             {
             if(yes)
                 dialog.close();
             })
    
}
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
	<li>发布内容：<a href="#" onclick=" read('  ${list.id }  ')" >${list.information }</a></li>
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
