<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
	<body style="padding: 10px">
		<div style="padding: 10px;">
			<form id="ffAdd" method="post">
				<table>
					

					<tr>
						

						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>仪器名称：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="productName" name="productName"
								validate="{required:true}" value="${request.productName }" />
						</td>
						
						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>厂家：
						</td>
						<td class="l-table-edit-td">
							<textarea style="height: 50px; width: 135px" id="manufacturers"
								name="manufacturers" validate="{required:true}">${request.manufacturers }</textarea>
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>分类号：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="classCode" name="classCode"
								validate="{required:true}" value="${request.classCode }" />
						</td>

						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>型号：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="productVersion" name="productVersion"
								validate="{required:true}" value="${request.productVersion }" />
						</td>
					</tr>

					<tr>
						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>规格：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="specification" name="specification"
								validate="{required:true}" value="${request.specification }" />
						</td>

						<td align="right" class="l-table-edit-td">
								<font color="#ff0000">*</font>单价：
						</td>
						<td class="l-table-edit-td">
							<input type="text" id="price" name="price" ltype='spinner'
								ligerui="{type:'float'}"  validate="{required:true}"
								onblur="onlyNum()" value="${request.price }" />
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
			f_modify();
		}
	});
	

	$("#ffAdd").ligerForm();
});
function f_modify() {

	var params = $('#ffAdd').serialize(); //这里直接就序列化了表单里面的值；很方便   	
	$.ajax( {
		url : 'productUpdateBatch?id=${request.id}', //后台处理程序   
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
	} else {
		alert("操作失败...请重新再试或联系管理员");
	}
}
function f_close() {
	window.parent.$.ligerDialog.close();
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
