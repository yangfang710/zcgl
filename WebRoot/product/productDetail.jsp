<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
}

.l-button-submit {
	width: 80px;
	float: left;
	margin-left: 50px;
	padding-bottom: 2px;
}

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
<body style="padding:10px">
	<table width="100%">
		
		<tr>
			<td colspan="2" height="5"></td>
		</tr>
		<tr>
			<td class="blackbold_b"><img src="images/util/arrow6.gif"
				hspace="5">资产历史记录</td>
		</tr>

	</table>


	<div id="maingrid4" style="margin:0; padding:0"></div>
	<div style="display: none;"></div>
</body>
<script type="text/javascript">
var grid = null;
var manager = null;
$(function() {

	var v = $("form").validate({
		//调试状态，不会提交数据的
		debug : true,
		errorLabelContainer : "#errorLabelContainer",
		wrapper : "li",
		invalidHandler : function(form, validator) {
			$("#errorLabelContainer").show();
			setTimeout(function() {
				$.ligerDialog.tip({
					content : $("#errorLabelContainer").html()
				});
			}, 10);
		},
		submitHandler : function() {
			$("#errorLabelContainer").hide();
			alert("Submitted!");
		}
	});
	$(".l-button-test").click(function() {
		alert(v.element($("#txtName")));
	});

	f_initGrid();
	$("#form1").ligerForm();
	

});

function f_initGrid() {
	grid = manager = $("#maingrid4").ligerGrid({
		columns : [ {
			display : '序号',
			name : 'rownum',
			width : 40,
			minWidth : 30
		},   {
			display : '仪器编号',
			name : 'productCode',
			width : 100,
			minWidth : 60
		}, {
			display : '仪器名称',
			name : 'productName',
			width : 100,
			minWidth : 60
		}, {
			display : '分类号',
			name : 'classCode',
			width : 80,
			minWidth : 60
		}, {
			display : '型号',
			name : 'productVersion',
			width : 80,
			minWidth : 60
		},{
			display : '部门编号',
			name : 'departmentId',
			width : 80,
			minWidth : 60
		}, {
			display : '部门名称',
			name : 'departmentName',
			width : 100,
			minWidth : 60
		}, {
			display : '使用方向',
			name : 'productUse',
			width : 70,
			minWidth : 60
		}, {
			display : '当前状态',
			name : 'productStatus',
			width : 120,
			minWidth : 60
		}, {
			display : '管理责任人',
			name : 'manageUser',
			width : 80,
			minWidth : 60
		}, {
			display : '使用人',
			name : 'useUser',
			width : 80,
			minWidth : 60
		}, {
			display : '存放地名称',
			name : 'address',
			width : 100,
			minWidth : 60
		}, {
			display : '借用时间',
			name : 'lastOutTime',
			width : 120,
			minWidth : 60
		},

		{
			display : '备注',
			name : 'remark',
			width : 150,
			minWidth : 60
		} ],
		dataAction : 'server',
		pageSize : 30,
		where : f_getWhere(),
		rownumbers : false,
		checkbox : false,
		url : 'productQueryDetailAction?id=${request.id}',
		allowAdjustColWidth : true,
		width : '100%',
		height : '98%'
	});
}

function f_getWhere() {
	if (!grid)
		return null;
	var clause = function(rowdata, rowindex) {
		var key = $("#txtKey").val();
		return rowdata.orderId.indexOf(key) > -1;
	};
	return clause;
}
</script>
</html>
