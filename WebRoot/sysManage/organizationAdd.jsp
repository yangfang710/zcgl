<%@ page contentType="text/html; charset=UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>增加部门</title>
<link href="../com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="../com_css/LigerUILib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <link href="../com_css/LigerUILib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />    
    <script src="../com_css/LigerUILib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/core/base.js" type="text/javascript"></script>    
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>  
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerButton.js" type="text/javascript"></script>
   
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>    
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script> 
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerTip.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="../com_css/LigerUILib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/jquery-validation/messages_cn.js" type="text/javascript"></script>
    <script src="../com_css/js/jquery.blockUI.js" type="text/javascript"></script>
    
    <style type="text/css">
           body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
    </style>

</head>

	<body style="padding: 10px">

		<form name="form1" method="post" action="" id="form1">

			<table cellpadding="0" cellspacing="0" class="l-table-edit"
				width="100%">

				<tr>
					<td align="right" class="l-table-edit-td">
						<font color="#ff0000">*</font>上级部门:
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="hidden" name="hidParentDeptId" id="hidParentDeptId" />
						<input name="parentDeptName" type="text" id="parentDeptName"
							ltype="text" value="点击选择部门" readonly="readonly"
							onclick="selectParentRole(this)" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">
						<font color="#ff0000">*</font>部门编号:
					</td>
					<td align="left" class="l-table-edit-td">
						<span id="parentValue" style="font-weight: bold; color: red;"></span>
						<input name="deptId" type="text" id="deptId" ltype="text"
							validate="{required:true}" onblur="checkDeptId()"/>
					</td>

				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">
						<font color="#ff0000">*</font>部门名称:
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="deptName" type="text" id="deptName" ltype="text"
							validate="{required:true}" />
					</td>

				</tr>

				<tr>
					<td align="right" class="l-table-edit-td">
						部门固定电话:
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="phoneNum" type="text" id="phoneNum" ltype="text" onblur="checklegal()"/>
						格式如:023-44052221
					</td>

				</tr>


				<tr>
					<td align="right" class="l-table-edit-td">
						地址:
					</td>
					<td align="left" class="l-table-edit-td">
						<textarea cols="100" rows="4" class="l-textarea" name="address"
							id="address" style="width: 300px"></textarea>
				</tr>

				<tr>
					<td align="right" class="l-table-edit-td" valign="top">
						<font color="#ff0000">*</font>是否可用:
					</td>
					<td align="left" class="l-table-edit-td">
						<input id="deptState" type="radio" name="deptState" value="可用"
							checked="checked" />
						可用
						<input id="deptState" type="radio" name="deptState" value="不可用" />
						不可用
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">
						备注:
					</td>
					<td align="left" class="l-table-edit-td">
						<textarea cols="100" rows="4" class="l-textarea" name="remark"
							id="remark" style="width: 300px"></textarea>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"></td>
					<td align="left" class="l-table-edit-td">
						<input type="submit" value="提交" class="l-button l-button-submit" />
					</td>
				</tr>

			</table>


		</form>
		<div style="display: none">
			<!--  数据统计代码 -->
		</div>


	</body>

<script type="text/javascript">
$(function() {
	$.metadata.setType("attr", "validate");
	var v = $("form").validate( {
		debug : true,
		errorPlacement : function(lable, element) {
			if (element.hasClass("l-textarea")) {
				element.ligerTip( {
					content : lable.html(),
					target : element[0]
				});
			} else if (element.hasClass("l-text-field")) {
				element.parent().ligerTip( {
					content : lable.html(),
					target : element[0]
				});
			} else {
				lable.appendTo(element.parents("td:first").next("td"));
			}
		},
		success : function(lable) {
			lable.ligerHideTip();
			lable.remove();
		},
		submitHandler : function() {
			$("form .l-text,.l-textarea").ligerHideTip();
			ajaxUpdate();
		}

	});


	$("form").ligerForm();

	$(".l-button-test").click(function() {
		alert(v.element($("#txtName")));
	});
	// $("#accountDate").ligerDateEditor({ showTime: false });      
});

function checklegal() {
	var ab2 = /(\d{3}-\d{8})|(\d{4}-\d{7})/;
	var phoneNum = document.getElementById("phoneNum").value;
	if (phoneNum != "") {
		if (ab2.test(phoneNum) == false) {
			alert("请正确填写固定电话号码,例如 023-87888822");
			return false;
		}
	}
}

function ajaxUpdate() {//提交表单内容

	var ab2 = /(\d{3}-\d{8})|(\d{4}-\d{7})/;
	var phoneNum = document.getElementById("phoneNum").value;
	if (phoneNum != "") {
		if (ab2.test(phoneNum) == false) {
			alert("请正确填写固定电话号码,例如 023-87888822");
			return false;
		}
	}

	var hidParentDeptId = $("#hidParentDeptId").val();
	if (hidParentDeptId == "" || hidParentDeptId == null) {
		alert("请选择父部门后再提交!");
		return;
	}
	$.blockUI( {
		message : '<h4><img src="../images/loading.gif" /> 系统正在提交中……</h4>'
	});
	var params = $('#form1').serialize().replace(/\+/g, ' '); //这里直接就序列化了表单里面的值；很方便  
	// params = decodeURIComponent(params,true);	      
	// params = encodeURIComponent(encodeURIComponent(params)).replace(/%253D/g,'=').replace(/%2526/g,'&');

	$.ajax( { //把表单内容插入数据库
				url : 'organizationDeptAddAction', //后台处理程序   
				type : 'post', //数据发送方式   
				dataType : 'text', //接受数据格式   
				data : params, //要传递的数据；就是上面序列化的值   
				success : submit_Result
			//回传函数(这里是函数名)    
			});

}

function submit_Result(result) { //回传函数实体，参数为XMLhttpRequest.responseText   
	$.unblockUI();

	if (result == 'success') {

		window.parent.$("#maingrid4").ligerGrid().loadData(true);
		$.ligerDialog
				.success(
						'提交成功，请关闭对话框！',
						'提示',
						function() {
							var parentWindow_body = $(window.parent.document)
									.find("body");
							if (!parentWindow_body.find(".dealWithMask").length) {
								parentWindow_body
										.append("<input type=text class=dealWithMask style=height:0;border:none/>");
							}
							parentWindow_body.find(".dealWithMask").focus();
							window.parent.$.ligerDialog.close();
						});

	} else if (result == 'deptError') {
		$.ligerDialog.error('上级部门填写不正确！');
	} else if (result == 'deptExist') {
		$.ligerDialog.error('部门ID已存在，请重新输入！');
	} else if (result == 'deptNameExist') {
		$.ligerDialog.error('部门名称已存在，请重新输入！');
	} else if (result == 'error') {
		$.ligerDialog.error('系统异常，联系管理员！');
		//window.parent.$("#maingrid4").ligerGrid().loadData(true);	   	
		//	var parentWindow_body =  $(window.parent.document).find("body");if(! parentWindow_body.find(".dealWithMask").length){parentWindow_body.append("<input type=text class=dealWithMask style=height:0;border:none/>");}parentWindow_body.find(".dealWithMask").focus(); window.parent.$.ligerDialog.close();
	}
}

function selectParentRole(obj) {
	var time = new Date();
	var url = "organizationAddDeptTree.jsp";
	var orgIdAndName = window.showModalDialog(url + "?time=" + time,window,"dialogHeight:400px;dialogWidth:300px;center:yes;status:no;scroll:yes;help:no;resizable:yes;edge:Raised");
	if (orgIdAndName) {
		var org = orgIdAndName.split(":");
		var orgId = org[0];
		var orgName = org[1];
		if (orgId.indexOf(";;") != -1) {
			orgId = orgId.substring(0, orgId.indexOf(";;"));
		}
		$("#hidParentDeptId").val(orgId);
		$("#parentDeptName").val(orgName);
		$('#parentValue').empty();//先清空上一次选择的值
		$('#parentValue').html(orgId);
		
	}
}
function checkDeptId(){
	//部门编号只能输入两位数字或字母，正则验证
	
	
}
</script>	
</html>
