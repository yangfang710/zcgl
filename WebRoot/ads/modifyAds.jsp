<%@ page language="java"   pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<%@ include file="/top/commons.jspf"%>
<title>修改公告页面</title>
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
		 $("Form1").ligerForm();
	      $(".l-button-test").click(function(){
	          alert(v.element($("#txtName")));
	      });   
	});
  
  function ajaxUpdate(){
	  var id = $("#id").val();
  	var information = $('#information').val(); //这里直接就序列化了表单里面的值；很方便   	
  	information = encodeURIComponent(encodeURIComponent(information));
	$.ajax( {
		url : 'adsUpdateAction?information='+information+'&id='+id, //后台处理程序   
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
		<tr>
			<td width="30px"></td>
			<td >
		     	<input id="id" name="id" type="hidden" value="${request.id }" />
		     	<textarea rows="10" cols="70" id="information" name="information">${request.information }</textarea>
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