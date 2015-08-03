<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>资产列表页面</title>
<%@ include file="/top/commons.jspf"%>
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
<body style="padding:10px">
	<div style="padding:10px;">
		<form id="ffAdd" method="post">
			<table>


				<tr>
					<td align="right" class="l-table-edit-td"><label
						for="productCode">仪器编号：</label>
					</td>
					<td class="l-table-edit-td"><input type="text"
						id="productCode" name="productCode" readonly="readonly"
						value="${request.productCode }" onfocus="this.blur()"  /></td>

					<td align="right" class="l-table-edit-td"><label
						for="productName">仪器名称：</label>
					</td>
					<td class="l-table-edit-td"><input type="text"
						id="productName" name="productName" readonly="readonly"
						value="${request.productName }"  onfocus="this.blur()"  /></td>
				</tr>
				<tr id="adminTr">
					<td align="right" class="l-table-edit-td"><label
						for="departmentId">领用单位：</label></td>
					<td class="l-table-edit-td"><input type="hidden"
						name="departmentId" id="departmentId" value="${request.departmentId }"/> <input
						name="departmentName" type="text" id="departmentName"
						validate="{required:true}" ltype="text" value="${request.departmentName }"
						readonly="readonly" onclick="selectParentRole(this)" onfocus="this.blur()" /></td>

					<td align="right" class="l-table-edit-td"><label
						for="manageUser"><font color="#ff0000">*</font>管理责任人：</label>
					</td>
					<td class="l-table-edit-td"><input type="text"
						id="manageUser" name="manageUser"
						value="${request.manageUser }" />
					</td>
				</tr>

				<tr>
					<td align="right" class="l-table-edit-td"><label
						for="address"><font color="#ff0000">*</font>存放地名称：</label>
					</td>
					<td class="l-table-edit-td"><textarea
							style="height:50px;width:135px" id="address"
							name="address" validate="{required:true}">${request.address }</textarea></td>
					<td align="right" class="l-table-edit-td"><label
						for="productStatus"><font color="#ff0000">*</font>当前状态：</label></td>
					<td class="l-table-edit-td">
					<input type="text" id="productStatus" name="productStatus"
								validate="{required:true}" value="${request.productStatus }"/>
					</td>

				</tr>

				<tr>

					<td align="right" class="l-table-edit-td"><label
						for="productUse"><font color="#ff0000">*</font>使用方向：</label></td>
					<td class="l-table-edit-td">
					<input type="text" id="productUse" name="productUse"
								validate="{required:true}" value="${request.productUse }"/>
					
					</td>
					<td align="right" class="l-table-edit-td"><label for="useUser"><font
							color="#ff0000">*</font>使用人：</label>
					</td>
					<td class="l-table-edit-td"><input type="text" id="useUser"
						name="useUser" validate="{required:true}"  value="${request.useUser }"/></td>
				</tr>

				<tr>
					<td align="right" class="l-table-edit-td"><label
						for="lastOutTime">借用时间：</label></td>
					<td class="l-table-edit-td"><input type="text"
						id="lastOutTime" name="lastOutTime" ltype="date"/></td>
					<td align="right" class="l-table-edit-td"><label for="remark">备注：</label>
					</td>
					<td class="l-table-edit-td"><textarea
							style="height:50px;width:200px" id="remark" name="remark">${request.remark }</textarea>
					</td>

				</tr>

				<tr>
					<td align="center" colspan="4" style="padding-top:10px"><input
						type="submit" value="提交" class="l-button l-button-submit" /> <input
						type="button" value="取 消" class="l-button l-button-submit"
						onclick="f_close()" /></td>

				</tr>
			</table>
		</form>

	</div>
</body>
<script type="text/javascript">
	$(function() {
		var groupId = "${session.groupId}";
		//超级管理员才可以修改  领用单位和管理负责人
		if(groupId=="1"){
			$("#adminTr").show();
		}else{
			$("#adminTr").hide();
		}
		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			debug : true,
			errorPlacement : function(lable, element) {
				if (element.hasClass("l-textarea")) {
					element.ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else if (element.hasClass("l-text-field")) {
					element.parent().ligerTip({
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
				f_modify();
			}
		});
		
		$("#manageUser").ligerComboBox({
			url : 'manageUserQueryAction?ttType=deptManager',
			isMultiSelect : false,
			isShowCheckBox : false
		});
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
		/*下拉框的值回显 JQuery实现
		$("#productStatus option").each(function() { 
			if ($(this).val() == "${request.productStatus}") { 
				$(this).attr("selected", "selected"); 
			} 
		}); 
		$("#productUse option").each(function() { 
			if ($(this).val() == "${request.productUse}") { 
				$(this).attr("selected", "selected"); 
			} 
		}); */
		$("#ffAdd").ligerForm();
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

	function f_modify() {

		var params = $('#ffAdd').serialize(); //这里直接就序列化了表单里面的值；很方便   	
		$.ajax({
			url : 'productEdit?id=${request.id}', //后台处理程序   
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
								var parentWindow_body = $(
										window.parent.document).find("body");
								if (!parentWindow_body.find(".dealWithMask").length) {
									parentWindow_body
											.append("<input type=text class=dealWithMask style=height:0;border:none/>");
								}
								parentWindow_body.find(".dealWithMask").focus();
								window.parent.$.ligerDialog.close();
							});
		} else if (data == "usernotexist") {
			$.ligerDialog.success('使用人数据不存在，请联系管理员添加用户信息后再操作！', '提示', function() {
			});
		}else {
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
</script>
</html>
