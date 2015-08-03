<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<%@ include file="/top/commons.jspf"%>
     <link rel="stylesheet" href="${pageContext.request.contextPath}/com_css/uploadify/uploadify.css" type="text/css"></link>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/com_css/uploadify/swfobject.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/com_css/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
<title>添加公告页面</title>
  <script >
  $(function() {
		 $.metadata.setType("attr", "validate");
	             var v = $("form").validate({
	                 debug: true,
	                 errorPlacement: function (lable, element){
	                     if (element.hasClass("l-textarea")){
	                         element.ligerTip({ content: lable.html(), target: element[0] }); 
	                     }
	                     else if (element.hasClass("l-text-field")){
	                         element.parent().ligerTip({ content: lable.html(), target: element[0] });
	                     }
	                     else{
	                         lable.appendTo(element.parents("td:first").next("td"));
	                     }
	                 },
	                 success: function (lable){
	                     lable.ligerHideTip();
	                     lable.remove();
	                 },
	                 submitHandler: function (){
	                     $("form .l-text,.l-textarea").ligerHideTip();
	                     ajaxUpdate();
	                     
	                 }
	             });
	             var myUrlHead = 'sysScanUploadAction';
	        	 $("#myFileHead").uploadify({
	                 'uploader' : '../com_css/uploadify/uploadify.swf',
	                 'script' :myUrlHead ,//后台处理的请求
	                 'cancelImg' : '../com_css/uploadify/cancel.png',
	                 'fileDataName':'myFileHead',//服务器端根据这个接收文件
	                 'queueID' : 'fileQueueHead',// 文件队列的ID，该ID与存放文件队列的div的ID一致
	                 'queueSizeLimit' :5,//当允许多文件生成时，设置选择文件的个数
	                 'fileDesc' : '请选择 gif,bmp,png,jpeg,jpg,doc,xls,ppt,txt,docx,xlsx,pptx,pdf文件类型',//用来设置选择文件对话框中的提示文本
	                 'fileExt' : '*.gif;*.bmp;*.png;*.jpeg;*.jpg;*.doc;*.xls;*.txt;*.ppt;*.docx;*.xlsx;*.pptx;*.pdf', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
	                 'auto' : false,//false需要点击上传按钮才上传
	                 'multi' : true,//可以上传多个文件
	                 'simUploadLimit' :1,//允许同时上传的个数 
	                 'buttonText' : '浏览文件',
	                 'onCancel':function(event,queueId,fileObj,data){
	                 	
	                 },
	                 'onError' : function(event, queueID, fileObj, errorObj) {
	                     alert(" 上传失败" + errorObj.info + "错误类型" + errorObj.type);
	                 },
	                 'onComplete' : function(event, queueID, fileObj, response, data) {
	                 	var resultArray=response.split("@"); 
	                 	if(resultArray[0] == 'success'){
	                 		alert("上传成功");
	                 		//alert(resultArray[2]);
	                 		$("#appendixName").val(resultArray[2]+';'+$("#appendixName").val());
	                 		//alert($("#appendixName").val());
	                 	}else{
	                 		alert("上传失败,请重试或联系管理员!");
	                 	}
	                 }
	             });
		 $("Form1").ligerForm();
	      $(".l-button-test").click(function(){
	          alert(v.element($("#txtName")));
	      });   
	});
  
  function ajaxUpdate(){
	  $.blockUI({
			message : '<h5><img src="../images/loading.gif" /> 系统正在提交中……</h5>'
		});
		var information = $("#information").val();
		var appendixName = $("#appendixName").val();

		information = encodeURIComponent(encodeURIComponent(information));
	$.ajax( {
		url : 'adsAddAction?information='+information+'&appendixName='+appendixName, //后台处理程序   
		type : 'post', //数据发送方式   
		dataType : 'text', //接受数据格式   
		success : submit_Result
	});
}

function submit_Result(data) {
	if (data == "success") {
		window.parent.$("#maingrid4").ligerGrid().loadData(true);
		$.ligerDialog.success('提交成功，请关闭对话框！','提示',function() {
				var parentWindow_body = $(window.parent.document).find("body");
				if (!parentWindow_body.find(".dealWithMask").length) {
					parentWindow_body.append("<input type=text class=dealWithMask style=height:0;border:none/>");
				}
				parentWindow_body.find(".dealWithMask").focus();
				window.parent.$.ligerDialog.close();
		});
	} else {
		alert("添加失败...请重新再试或联系管理员");
	}
}

  </script>
<style type="text/css">
		.blackbold_b { /* 标题样式 */
			line-height: 22px;
			width: 150px;
			height: 22px;
			font-size: 12px;
			font-weight: bold;
			color: #FF5317
		}
    </style>

</head>

<body>
<form name="Form1" id="Form1" method="post">
	<table >
		<tr height="15px"></tr>
		
		   <tr >
             <td align="right" class="l-table-edit-td">上传附件:</td>
                <td  id="more" align="left" class="l-table-edit-td">
                <div id="fileQueueHead"></div>
                   <input name="myFileHead" type="file"  id ="myFileHead" /> 
                   <a href="javascript:$('#myFileHead').uploadifyUpload()">上传</a>|
				   <a href="javascript:$('#myFileHead').uploadifyClearQueue()"> 取消上传</a>
				   	<input name="appendixName"  type="hidden"  id="appendixName" value = ""/>
				   	(支持office文档、txt文件、图片文件、pdf文档，可以上传多个)
                </td>  
          
            </tr>
		<tr>
			 <td width="9%" align="right" class="l-table-edit-td"><font color="#ff0000">*</font>公告内容:</td>
			<td >
		     	<textarea rows="10" cols="70" id="information" name="information"></textarea>
			</td>
			<td width="10px"></td>
		</tr>
		<tr height="15px"></tr>
        <tr>
        	<td width="30px"></td>
			<td  align="center">
				<input type="submit" value="提交" class="l-button l-button-submit" />
			</td>
			<td width="10px"></td>
		</tr>
		<tr height="5px"></tr>
	</table>
</form>
</body>
</html>