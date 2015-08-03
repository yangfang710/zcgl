<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/tld/pageADMOperAuth.tld"
	prefix="pageADMOperAuth.tld"%>
<html>
<head>
<%@ include file="/top/commons.jspf"%>
<title>资产列表页面</title>

<style type="text/css">
body {
	font-size: 13px;
}

.blackbold_b { /* 标题样式 */
	line-height: 22px;
	width: 150px;
	height: 22px;
	font-size: 12px;
	font-weight: bold;
	color: #FF5317
}

.l-button {
	width: 80px;
}
.l-table-edit-td {
	padding: 4px;
	font-size: 13px;
}
</style>

</head>

<body style="padding:6px; overflow:hidden;">

	<table width="100%">
		<tr>
			<td height="2"></td>
		</tr>
		<tr>
			<td class="blackbold_b"><img src="images/util/arrow6.gif"
				hspace="5">筛选条件</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#BBD2E9"></td>
		</tr>
	</table>

	<fieldset style="top:inherit;width:100%">
		<form name="form1" method="post" action="" id="form1">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td">仪器编号:</td>
					<td align="left" class="l-table-edit-td"><input
						name="productCode" id="productCode" type="text" /></td>
					<td align="right" class="l-table-edit-td">仪器名称:</td>
					<td align="left" class="l-table-edit-td"><input
						name="productName" id="productName" type="text" /></td>
					<td align="right" class="l-table-edit-td">型号:</td>
					<td align="left" class="l-table-edit-td"><input
						name="productVersion" id="productVersion" type="text" /></td>
					<td align="right" class="l-table-edit-td">厂家:</td>
					<td align="left" class="l-table-edit-td"><input
						name="manufacturers" id="manufacturers" type="text" /></td>
					<td></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">部门名称:</td>
					<td align="left" class="l-table-edit-td">
					
					<input type="hidden" name="departmentId" id="departmentId" />
							<input name="departmentName" type="text" id="departmentName"
								validate="{required:true}" ltype="text" value="点击选择部门"
								readonly="readonly" onclick="selectParentRole(this)" onfocus="this.blur()" /></td>
					<td align="right" class="l-table-edit-td">领用人:</td>
					<td align="left" class="l-table-edit-td"><input
						name="receiveUser" id="receiveUser" type="text"></input></td>
					<td align="right" class="l-table-edit-td">入库开始时间:</td>
					<td align="left" class="l-table-edit-td"><input
						name="inTimeBegin" id="inTimeBegin" type="text" ltype="date"></input>
					</td>
					<td align="right" class="l-table-edit-td">入库结束时间:</td>
					<td align="left" class="l-table-edit-td"><input
						name="inTimeEnd" id="inTimeEnd" type="text" ltype="date"></input>
					</td>
					<td><input type="button" value="查询" class="l-button"
						onClick="f_query()" /></td>
				</tr>

				<tr>
					<td align="right" class="l-table-edit-td">当前状态:</td>
					<td><select name="productStatus" id="productStatus"
						align="left" class="l-table-edit-td" style="width:200px;">
							<option value="全部">全部</option>
							<option value="库存">库存</option>
							<option value="在用">在用</option>
							<option value="故障">故障</option>
							<option value="报废">报废</option>
							<option value="校内转入">校内转入</option>
					</select>
					<td align="right" class="l-table-edit-td">管理责任人:</td>
					<td align="left" class="l-table-edit-td"><input
						name="manageUser" id="manageUser" type="text"></input></td>
					<td align="right" class="l-table-edit-td">使用人:</td>
					<td align="left" class="l-table-edit-td"><input name="useUser"
						id="useUser" type="text"></input></td>
					<td align="right" class="l-table-edit-td">存放地名称:</td>
					<td align="left" class="l-table-edit-td"><input name="address"
						id="address" type="text"></input></td>
					<td><input type="button" value="查询所有" class="l-button"
						onclick="f_query_all()" /></td>
				</tr>
			</table>

		</form>
	</fieldset>




	<table width="100%">
		<tr>
			<td colspan="2" height="1" bgcolor="#BBD2E9"></td>
		</tr>
		<tr>
			<td colspan="2" height="5"></td>
		</tr>
		<tr>
			<td class="blackbold_b"><img src="images/util/arrow6.gif"
				hspace="5">资产列表</td>
		</tr>

	</table>


	<div id="maingrid4" style="margin:0; padding:0"></div>
	<div style="display: none;"></div>

</body>

<script type="text/javascript">
	var grid = null;
	var manager = null;
	$(function() {
		<pageADMOperAuth.tld:pageADMOperAuth menuId="'010101','010102','010103','010104','010105','010106','010107','010108'"></pageADMOperAuth.tld:pageADMOperAuth>
//'010107',
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
		//get_curDay();
		//get_lDay();

	});
	/*function get_curDay() {
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
		document.getElementById('inTimeEnd').value = date;

	}

	function get_lDay() { //获取本月1号
		var today = new Date();
		var month = today.getMonth() + 1;
		var year = today.getFullYear();
		if (month * 1 < 10) {
			month = '0' + month;
		}
		var date = year + "-" + month + "-" + "01";
		document.getElementById('inTimeBegin').value = date;
	}
*/
	function getXMLHttpRequest() {
		var xhr;
		try {
			xhr = new XMLHttpRequest();
		} catch (err1) {
			try {
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
				alert(xhr);
			} catch (err2) {
				alert("您的浏览器版本不支持Ajax....");
			}
		}
		return xhr;
	}
	//添加资产
	function addProduct() {
		var vin = $.ligerDialog.open({
			title : '新增资产',
			height : 450,
			width : 800,
			url : 'productAddAction',
			showMax : true,
			isResize : true,
			slide : false,
			buttons : [ {
				text : '关闭窗口',
				onclick : f_closeAddWindow
			} ]
		});
		vin.max();
	}
	//修改资产
	function modifyProduct() {
		var row = manager.getCheckedRows();
        
       	if (row.length == 0) { 
       		$.ligerDialog.error('请选择需要修改的行！');
       		return ; 
       	}else if(row.length > 1){           	
       		$.ligerDialog.error('修改时只能选择一行！'); 
       		return ;  
   		}else{ 
			var m = $.ligerDialog.open({
				title : '修改资产',
				height : 500,
				width : 1000,
				url : 'productModifyAction?id=' + row[0].id+'&ttType=modify',
				showMax : true,
				isResize : true,
				slide : false,
				buttons : [ {
					text : '关闭窗口',
					onclick : f_closeAddWindow
				} ]
			});
			m.max();
   		}
	}
	//批量修改资产
	function f_batch_modify() {
		var row = manager.getCheckedRows();
        
       	if (row.length == 0) { 
       		$.ligerDialog.error('请选择需要修改的行！');
       		return ; 
       	}
       	var txtID = "";
		for ( var i = 0; i < row.length - 1; i++) {
			txtID += row[i].id + ";";
		}
		if (i == row.length - 1) {//最后一个不加分号	    		  
			txtID += row[i].id;
		}
		var m = $.ligerDialog.open({
			title : '批量修改资产',
			height : 500,
			width : 1000,
			url : 'productModifyAction?id=' + txtID+'&ttType=batchModify',
			showMax : true,
			isResize : true,
			slide : false,
			buttons : [ {
				text : '关闭窗口',
				onclick : f_closeAddWindow
			} ]
		});
		m.max();
	}
	
	//删除资产
	function deleteProduct() {
		var manager = $("#maingrid4").ligerGetGridManager();

		if (!manager) {
			$.ligerDialog.error('请查询列表后,选中需要删除的条目,再删除！')
			return;
		} else {
			var row = manager.getCheckedRows();

			if (row.length == 0) {
				$.ligerDialog.error('请选择需要修改的行！');
				return;
			} else {
				$.ligerDialog.confirm('确认要删除该资产信息吗?若删除,则不能恢复，请谨慎操作！', function(yes) {

					if (yes) {

						var txtID = "";
						for ( var i = 0; i < row.length - 1; i++) {
							txtID += row[i].id + ";";
						}
						if (i == row.length - 1) {//最后一个不加分号	    		  
							txtID += row[i].id;
						}
						$.ajax({
							url : 'productDelete?id=' + txtID, //后台处理程序   
							type : 'post', //数据发送方式   
							dataType : 'text', //接受数据格式   
							data : null, //要传递的数据；就是上面序列化的值   
							success : function(result) {

								if (result == 'success') {
									manager.deleteSelectedRow();
									$.ligerDialog.success('删除成功！');
								} else {
									$.ligerDialog
											.error('系统异常,删除失败,请重试或联系系统管理员！');
								}
							} //回传函数(这里是函数名)    
						});
						//根据主键用ajax访问action删除数据；删除后给出提示框，成功或失败
					}
				});

			}
			//根据主键用ajax访问action删除数据；删除后给出提示框，成功或失败
		}
	}
	//导入资产
	function excelInProduct() {
		var win_excel = $.ligerDialog.open({
			title : '资产导入',
			height : 300,
			width : 600,
			url : 'product/productAddExcel.jsp',
			showMax : true,
			isResize : true,
			slide : false,
			buttons : [ {
				text : '关闭窗口',
				onclick : f_closeAddWindow
			} ]
		});
	}
	//导出资产
	function excelOutProduct() {
		var productCode = encodeURIComponent(encodeURIComponent($(
				"#productCode").val()));
		var productName = encodeURIComponent(encodeURIComponent($(
				"#productName").val()));
		var productVersion = encodeURIComponent(encodeURIComponent($(
				"#productVersion").val()));
		var manufacturers = encodeURIComponent(encodeURIComponent($(
				"#manufacturers").val()));
		var departmentId = encodeURIComponent(encodeURIComponent($(
				"#departmentId").val()));

		var receiveUser = encodeURIComponent(encodeURIComponent($(
				"#receiveUser").val()));
		var inTimeBegin = encodeURIComponent(encodeURIComponent($(
				"#inTimeBegin").val()));
		var inTimeEnd = encodeURIComponent(encodeURIComponent($("#inTimeEnd")
				.val()));
		var productStatus = encodeURIComponent(encodeURIComponent($(
				"#productStatus").val()));
		var manageUser = encodeURIComponent(encodeURIComponent($("#manageUser")
				.val()));

		var useUser = encodeURIComponent(encodeURIComponent($("#useUser").val()));
		var address = encodeURIComponent(encodeURIComponent($("#address").val()));
		window.open('productDownExcel?productCode=' + productCode
				+ '&productName=' + productName + '&productVersion='
				+ productVersion + '&manufacturers=' + manufacturers
				+ '&departmentId=' + departmentId +

				'&receiveUser=' + receiveUser + '&inTimeBegin=' + inTimeBegin
				+ '&inTimeEnd=' + inTimeEnd + '&productStatus=' + productStatus
				+ '&manageUser=' + manageUser +

				'&useUser=' + useUser + '&address=' + address

		);
		window.close();
	}

	//资产状态变更
	function editProduct() {
		var row = manager.getCheckedRows();
        
       	if (row.length == 0) { 
       		$.ligerDialog.error('请选择需要修改的行！');
       		return ; 
       	}else if(row.length > 1){           	
       		$.ligerDialog.error('资产状态变更时只能选择一行！'); 
       		return ;  
   		}
		var m = $.ligerDialog.open({
			title : '变更资产',
			height : 500,
			width : 1000,
			url : 'productModifyAction?id=' + row[0].id + '&ttType=edit',
			showMax : true,
			isResize : true,
			slide : false,
			buttons : [ {
				text : '关闭窗口',
				onclick : f_closeAddWindow
			} ]
		});
		m.max();
	}
	//资产状态历史记录
	function detailProduct() {
		var row = manager.getSelectedRow();
		if (!row) {
			$.ligerDialog.error('请选择需要操作的行！');
			return;
		}
		var m = $.ligerDialog.open({
			title : '历史记录',
			height : 500,
			width : 1000,
			url : 'productModifyAction?id=' + row.id + '&ttType=detail',
			showMax : true,
			isResize : true,
			slide : false,
			buttons : [ {
				text : '关闭窗口',
				onclick : f_closeAddWindow
			} ]
		});
		m.max();
	}
	function f_closeAddWindow(item, dialog) {
		$.ligerDialog.confirm('确认要关闭窗口吗', function(yes) {
			if (yes)
				dialog.close();
		});
	}
	//每个字段取个别名，不用驼峰，为了实现排序功能
	function f_initGrid() {
		grid = manager = $("#maingrid4").ligerGrid({
			columns : [ {
				display : '序号',
				name : 'rownum',
				width : 40,
				minWidth : 30
			}, {
				display : 'id',
				name : 'id',
				width : 10,
				minWidth : 60,
				hide : 1
			},  {
				display : '部门编号',
				name : 'departmentid',
				width : 80,
				minWidth : 60,
				align: 'left',
				isSort : true
			}, {
				display : '资产归属部门名称',
				name : 'departmentname',
				width : 150,
				minWidth : 60,
				align: 'left',
				isSort : true
			}, {
				display : '仪器编号',
				name : 'productcode',
				width : 70,
				minWidth : 60, 
				align: 'left',
				render: function (row)
                {
                	return  '<a href="#" onclick="detailProduct()"><font color="blue">'+row.productcode+'</font></a>'; 
                },
				isSort : true
			}, {
				display : '仪器名称',
				name : 'productname',
				width : 80,
				minWidth : 60,
				align: 'left',
				isSort : true
			}, {
				display : '分类号',
				name : 'classcode',
				width : 80,
				minWidth : 60,
				align: 'left',
				isSort : true
			}, {
				display : '型号',
				name : 'productversion',
				width : 70,
				minWidth : 60,
				align: 'left',
				isSort : true
			}, {
				display : '规格',
				name : 'specification',
				width : 70,
				minWidth : 60,
				align: 'left',
				isSort : true
			}, {
				display : '单价',
				name : 'price',
				width : 70,
				minWidth : 60,
				align: 'left',
				isSort : true
			}, {
				display : '厂家',
				name : 'manufacturers',
				width : 120,
				minWidth : 60,
				align: 'left',
				isSort : true
			},

			{
				display : '使用方向',
				name : 'productuse',
				width : 70,
				minWidth : 60,
				align: 'left',
				isSort : true
			}, {
				display : '领用人',
				name : 'receiveuser',
				width : 80,
				minWidth : 60,
				align: 'left',
				isSort : true
			}, {
				display : '入库时间',
				name : 'intime',
				width : 100,
				minWidth : 60,
				align: 'left',
				isSort : true
			}, {
				display : '当前状态',
				name : 'productstatus',
				width : 80,
				minWidth : 60,
				align: 'left',
				isSort : true
			},

			{
				display : '管理责任人',
				name : 'manageuser',
				width : 80,
				minWidth : 60,
				align: 'left',
				isSort : true
			}, {
				display : '使用人',
				name : 'useuser',
				width : 80,
				minWidth : 60,
				align: 'left',
				isSort : true
			},{
				display : '使用人部门',
				name : 'useuserdept',
				width : 100,
				minWidth : 60,
				align: 'left',
				isSort : true
			},{
				display : '使用人团队',
				name : 'useuserteam',
				width : 100,
				minWidth : 60,
				align: 'left',
				isSort : true
			}, {
				display : '存放地名称',
				name : 'address',
				width : 80,
				minWidth : 60,
				align: 'left',
				isSort : true
			}, {
				display : '借用时间',
				name : 'lastouttime',
				width : 100,
				minWidth : 60,
				align: 'left',
				isSort : true
			},

			{
				display : '备注',
				name : 'remark',
				width : 150,
				minWidth : 60,
				align: 'left',
				isSort : true
			} ],
			dataAction : 'server',
			pageSize : 30,
			where : f_getWhere(),
			sortName : 'intime',
			sortOrder : 'desc',
			rownumbers : false,
			checkbox : true,
			url : 'productQueryAction',
			allowAdjustColWidth : true,
			width : '100%',
			height : '98%',
			toolbar : toolBar
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

	function f_query() {
		if (!manager)
			return;
		var productCode = $("#productCode").val();
		var productName = $("#productName").val();
		var productVersion = $("#productVersion").val();
		var manufacturers = $("#manufacturers").val();
		var departmentId = $("#departmentId").val();

		var receiveUser = $("#receiveUser").val();
		var inTimeBegin = $("#inTimeBegin").val();
		var inTimeEnd = $("#inTimeEnd").val();
		var productStatus = $("#productStatus").val();
		var manageUser = $("#manageUser").val();

		var useUser = $("#useUser").val();
		var address = $("#address").val();

		manager.setOptions({
			parms : [ {
				name : 'productCode',
				value : productCode
			}, {
				name : 'productName',
				value : productName
			}, {
				name : 'productVersion',
				value : productVersion
			}, {
				name : 'manufacturers',
				value : manufacturers
			}, {
				name : 'departmentId',
				value : departmentId
			},

			{
				name : 'receiveUser',
				value : receiveUser
			}, {
				name : 'inTimeBegin',
				value : inTimeBegin
			}, {
				name : 'inTimeEnd',
				value : inTimeEnd
			}, {
				name : 'productStatus',
				value : productStatus
			}, {
				name : 'manageUser',
				value : manageUser
			},

			{
				name : 'useUser',
				value : useUser
			}, {
				name : 'address',
				value : address
			} ]
		});
		manager.loadData(true);
	}
	function f_query_all() {
		if (!manager)
			return;
		$("#productCode").val("");
		$("#productName").val("");
		$("#productVersion").val("");
		$("#manufacturers").val("");
		$("#departmentId").val("");

		$("#receiveUser").val("");
		$("#inTimeBegin").val("全部");
		$("#inTimeEnd").val("全部");
		$("#productStatus").val("全部");
		$("#manageUser").val("");

		$("#useUser").val("");
		$("#address").val("");
		var inTimeBegin = $("#inTimeBegin").val();
		var inTimeEnd = $("#inTimeEnd").val();
		manager.setOptions({
			parms : [ {
				name : 'inTimeBegin',
				value : inTimeBegin
			}, {
				name : 'inTimeEnd',
				value : inTimeEnd
			} ]
		});
		manager.loadData(true);
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
