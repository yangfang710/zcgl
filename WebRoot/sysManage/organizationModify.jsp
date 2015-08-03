<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>

<head>

<title></title>
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

<body style="padding:10px">

   
		<form name="form1" method="post" action="" id="form1">

			<table cellpadding="0" cellspacing="0" class="l-table-edit"
				width="100%">
<!-- 上级部门，部门编号，部门名称不能修改 -->
				<tr>
					<td align="right" class="l-table-edit-td">
						<font color="#ff0000">*</font>上级部门:
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="hidden" name="hidParentDeptId" id="hidParentDeptId" />
						<input name="parentDeptName" type="text" id="parentDeptName"
							ltype="text" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">
						<font color="#ff0000">*</font>部门编号:
					</td>
					<td align="left" class="l-table-edit-td">
						
						<input name="deptId" type="text" id="deptId" ltype="text"
							validate="{required:true}" readonly="readonly"/>
						<input type="hidden" name="id" id="id" />
					</td>

				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">
						<font color="#ff0000">*</font>部门名称:
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="deptName" type="text" id="deptName" ltype="text"
							validate="{required:true}" readonly="readonly"/>
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

    <div style="display:none">
    <!--  数据统计代码 --></div>

    
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
	$("#hidParentDeptId").val("${request.parentDeptId}");
	$("#parentDeptName").val("${request.parentDeptName}");
	$("#id").val("${request.id}");
	$("#deptId").val("${request.deptId}");
	$("#deptName").val("${request.deptName}");
	
	$("#phoneNum").val("${request.phoneNum}");
	$("#address").val("${request.address}");
	$("#remark").val("${request.remark}");
	
	var deptState= "${request.deptState}";
    var radioObj = document.all("deptState");           
	for(var ii=0; ii<radioObj.length; ii++) {
		if(radioObj[ii].value== deptState) {
			radioObj[ii].checked = true; 
		}
	}

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
				 url :'organizationDeptUpdateAction?id='+$("#id").val(),  //后台处理程序  
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

	}else if (result == 'error') {
		$.ligerDialog.error('系统异常，联系管理员！');
		//window.parent.$("#maingrid4").ligerGrid().loadData(true);	   	
		//	var parentWindow_body =  $(window.parent.document).find("body");if(! parentWindow_body.find(".dealWithMask").length){parentWindow_body.append("<input type=text class=dealWithMask style=height:0;border:none/>");}parentWindow_body.find(".dealWithMask").focus(); window.parent.$.ligerDialog.close();
	}
}

</script>
</html>
