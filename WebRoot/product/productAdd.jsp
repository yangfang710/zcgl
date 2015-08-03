<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/top/commons.jspf"%>
		<title>资产列表页面</title>

<style type="text/css">
body {
	font-size: 13px
}

.l-table-edit-td {
	padding: 4px;
	font-size: 13px;
}

.l-button-submit {
	width: 80px;
	float: left;
	margin-left: 50px;
	padding-bottom: 2px;
}
</style>
	</head>
	<body style="padding: 10px">
		<div style="padding: 10px;">
			<form id="ffAdd" method="post">
				<table>
					<tr>
						

						<td align="right" class="l-table-edit-td">
							
								<font color="#ff0000">*</font>部门名称：
							
						</td>
						<td class="l-table-edit-td">
							<input type="hidden" name="departmentId" id="departmentId" />
							<input name="departmentName" type="text" id="departmentName"
								validate="{required:true}" ltype="text" value="点击选择部门"
								readonly="readonly" onclick="selectParentRole(this)" onfocus="this.blur()" />
						</td>
						<td align="right" class="l-table-edit-td">				
						</td>
						<td class="l-table-edit-td">
						</td>
					</tr>

					<tr>
						<td align="right" class="l-table-edit-td">
							
								<font color="#ff0000">*</font>仪器编号：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="productCode" name="productCode"
								validate="{required:true}" />
						</td>

						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>仪器名称：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="productName" name="productName"
								validate="{required:true}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>分类号：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="classCode" name="classCode"
								validate="{required:true}" />
						</td>

						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>型号：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="productVersion" name="productVersion"
								validate="{required:true}" />
						</td>
					</tr>

					<tr>
						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>规格：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="specification" name="specification"
								validate="{required:true}" />
						</td>

						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>单价：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="price" name="price" 
								ligerui="{type:'float'}" value="0.00" validate="{required:true}"
								onblur="onlyNum()" />
						</td>
					</tr>

					<tr>
						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>厂家：
						</td>
						<td class="l-table-edit-td">
							<textarea style="height: 50px; width: 135px" id="manufacturers"
								name="manufacturers" validate="{required:true}"></textarea>
						</td>

						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>存放地名：
						</td>
						<td class="l-table-edit-td">
							<textarea style="height: 50px; width: 135px" id="address"
								name="address"></textarea>
						</td>
					</tr>

					<tr>
						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>领用人：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="receiveUser" name="receiveUser"
								validate="{required:true}" />
							
						</td>


						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>入库时间：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="inTime" name="inTime"
								validate="{required:true}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>管理责任人：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="manageUser" name="manageUser"
								validate="{required:true}" />
						</td>
						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>当前状态：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="productStatus" name="productStatus"
								validate="{required:true}" value="库存"/>
							
						</td>



					</tr>

					<tr>
						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>使用方向：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="productUse" name="productUse"
								validate="{required:true}" value="科研"/>
							
						</td>
						<td align="right" class="l-table-edit-td">
								使用人：
						</td>
						<td class="l-table-edit-td">
							<input type="text" ltype="text" id="useUser" name="useUser" />
						</td>

					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
								借用时间：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="lastOutTime" name="lastOutTime" />
						</td>
						<td align="right" class="l-table-edit-td">
								备注：
						</td>
						<td colspan="3">
							<textarea style="height: 60px; width: 200px" id="remark"
								name="remark"></textarea>
						</td>
					</tr>

					<tr>
						<td align="center" colspan="4" style="padding-top: 10px">
							<input type="submit" value="提交" class="l-button l-button-submit" />
							<input type="button" value="取 消" class="l-button l-button-submit"
								onclick="f_close()" />
						</td>

					</tr>
				</table>
			</form>

		</div>
	</body>
	
 <script type="text/javascript">
$(function() {

	 $.metadata.setType("attr", "validate");
             var v = $("form").validate({
                 debug: true,
                 errorPlacement: function (lable, element)
                 {
                     if (element.hasClass("l-textarea"))
                     {
                         element.ligerTip({ content: lable.html(), target: element[0] }); 
                     }
                     else if (element.hasClass("l-text-field"))
                     {
                         element.parent().ligerTip({ content: lable.html(), target: element[0] });
                     }
                     else
                     {
                         lable.appendTo(element.parents("td:first").next("td"));
                     }
                 },
                 success: function (lable)
                 {
                     lable.ligerHideTip();
                     lable.remove();
                 },
                 submitHandler: function ()
                 {
                     $("form .l-text,.l-textarea").ligerHideTip();
                     ajaxUpdate();
                     
                 }
             });
	$("#manageUser").ligerComboBox( {
		url : 'manageUserQueryAction?ttType=deptManager',
		isMultiSelect : false,
		isShowCheckBox : false
	});
	$("#inTime").ligerDateEditor();
	$("#lastOutTime").ligerDateEditor();
	  $("#productUse").ligerComboBox({
			url : 'selectQueryAction?ttType=productUse',
			isMultiSelect : false,
			isShowCheckBox : false
		}); 
	   $("#productStatus").ligerComboBox({
			url : 'selectQueryAction?ttType=productStatus',
			isMultiSelect : false,
			isShowCheckBox : false
		}); 

	 $("form").ligerForm();
      $(".l-button-test").click(function(){
          alert(v.element($("#txtName")));
      });   

	get_curDay();
});

function get_curDay() {
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth() + 1;
	var year = today.getFullYear();
	if (month * 1 < 10) {
		month = '0' + month;
	}
	if (day * 1 < 10) {
		day = '0' + day;
	}
	var date = year + "-" + month + "-" + day;
	document.getElementById('lastOutTime').value = date;

}
function ajaxUpdate() {
	var departmentId = $("#departmentId").val();
	if (departmentId == null || departmentId == "") {
		alert("请选择部门后再提交");
		return false;
	}
	var params = $('#ffAdd').serialize(); //这里直接就序列化了表单里面的值；很方便   	
	$.ajax( {
		url : 'productAdd', //后台处理程序   
		type : 'post', //数据发送方式   
		dataType : 'text', //接受数据格式   
		data : params, //要传递的数据；就是上面序列化的值   
		success : submit_Result
	//回传函数(这里是函数名)    
			});
}

function submit_Result(data) {
	if (data == "success") {
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
	} else if (data == "exist") {
		$.ligerDialog.success('添加的资产已经存在！', '提示', function() {
		});
	}else if (data == "usernotexist") {
		$.ligerDialog.success('使用人数据不存在，请联系管理员添加用户信息后再操作！', '提示', function() {
		});
	}  else {
		alert("添加失败...请重新再试或联系管理员");
	}
}
function f_close() {
	window.parent.$.ligerDialog.close();
}

function selectParentRole(obj) {
	var time = new Date();
	var url = "sysManage/organizationAddDeptTree.jsp";
	var orgIdAndName = window
			.showModalDialog(
					url + "?time=" + time,
					window,
					'dialogHeight:400px;dialogWidth:300px;center:yes;status:no;scroll:yes;help:no;resizable:yes;edge:Raised');
	if (orgIdAndName) {
		var org = orgIdAndName.split(":");
		var orgId = org[0];
		var orgName = org[1];
		if (orgId.indexOf(";;") != -1) {
			orgId = orgId.substring(0, orgId.indexOf(";;"));
		}
		document.getElementById("departmentId").value = orgId;
		document.getElementById("departmentName").value = orgName;
	}
}
function onlyNum() {
	var re = /^[1-9]+[0-9]*(\.\d+)?$/;
	if (!re.test($("#price").val())) {
		alert("请输入大于1的数!");
		$("#price").val("0.00");
		return;
	}
}
</script>
</html>
