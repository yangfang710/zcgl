<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/tld/pageADMOperAuth.tld" prefix="pageADMOperAuth.tld"%>
<html>
<head>
<%@ include file="/top/commons.jspf"%>
<title>公告列表页面</title>

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
					<td align="right" class="l-table-edit-td">创建人:</td>
					<td align="left" class="l-table-edit-td">
						<input name="operUser" id="operUser" type="text"></input>
					</td>
					<td align="right" class="l-table-edit-td">创建时间:</td>
					<td align="left" class="l-table-edit-td">
						<input name="operTime" id="operTime" type="text" ltype="date"></input>
					</td>
					<td>
						<input type="button" value="查询" class="l-button" onClick="f_query()" />
					</td>
					<td>
						<input type="button" value="查询所有" class="l-button" onclick="f_query_all()" />
					</td>
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
				hspace="5">公告列表</td>
		</tr>

	</table>


	<div id="maingrid4" style="margin:0; padding:0"></div>
	<div style="display: none;"></div>

</body>

<script type="text/javascript">
	var grid = null;
	var manager = null;
	$(function() {
		<pageADMOperAuth.tld:pageADMOperAuth menuId="'040401','040402','040403'"></pageADMOperAuth.tld:pageADMOperAuth>

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
	function f_add() {
		var m =$.ligerDialog.open({
			title : '新增公告',
			height : 400,
			width : 600,
			url : 'ads/addAds.jsp',
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
	//修改公告
	function f_modify() {
		var row = manager.getSelectedRow();
		if (!row) {
			$.ligerDialog.error('请选择需要操作的行！');
			return;
		}
		$.ligerDialog.open({
			title : '修改公告',
			height : 400,
			width : 600,
			url : 'adsModifyAction?id=' + row.id,
			showMax : true,
			isResize : true,
			slide : false,
			buttons : [ {
				text : '关闭窗口',
				onclick : f_closeAddWindow
			} ]
		});
	}

	//删除公告
	function f_delete() {
		var manager = $("#maingrid4").ligerGetGridManager();
		if (!manager) {
			$.ligerDialog.error('请查询列表后,选中需要删除的条目,再删除！')
			return;
		} else {
			var row = manager.getSelectedRow();
			if (!row) {
				$.ligerDialog.error('请选择需要删除的行！');
				return;
			}
			$.ligerDialog.confirm('确认要删除该公告吗?若删除,则不能恢复，请谨慎操作！', function(yes) {

				if (yes) {
					var xhr = getXMLHttpRequest();
					xhr.open("post", "adsDeleteAction?id="+ row.id, true);
					xhr.send();
					xhr.onreadystatechange = function() {
						if (xhr.readyState == 4 && xhr.status == 200) {
							var responseContext = xhr.responseText;
							if (responseContext == 'success') {
								manager.deleteSelectedRow();
								$.ligerDialog.success('删除成功！');
							} else {
								$.ligerDialog.error('系统异常,请联系系统管理员！');
							}

						}

					}

				}

			})
			//根据主键用ajax访问action删除数据；删除后给出提示框，成功或失败
		}
	}

	 function f_initGrid(){
     	grid = manager = $("#maingrid4").ligerGrid({
             columns: [
                   	{ display: '序号', name: 'id', width: 50, minWidth: 60 },
 					{ display: '公告内容', name: 'information', width: 300,align: 'left', minWidth: 60,
 						render: function (row)
 		                {
 		                	return  '<a href="#" onclick="read()"><font color="blue">'+row.information+'</font></a>'; 
 		                }
                   	},
 					{ display: '附件内容', name: 'appendixName', width: 300,align: 'left', minWidth: 60 },
 					{ display: '创建人', name: 'operUser', width: 100,align: 'left', minWidth: 60 },
 					{ display: '创建时间', name: 'operTime', width: 150,align: 'left', minWidth: 60 }
 					], 
 					dataAction: 'server',
 					pageSize:20,
 					where : f_getWhere(),
 					rownumbers:true, 
 					checkbox:false,
 		            url: 'adsQueryAction',
 		            allowAdjustColWidth:true,
 		            width: '100%', 
 		            height: '100%',
 		            toolbar:toolBar                               
         });
     }
	 function read(){
		 var row = manager.getSelectedRow();
			if (!row) {
				$.ligerDialog.error('请选择需要操作的行！');
				return;
			}
			var m = $.ligerDialog.open({ 
		    	  title: '查看公告信息',
		    	  height: 400, 
		    	  width: 700, 
		    	  url: 'messageReadAction?messageId='+row.id,	        	
		    	   showMax: true,	        	   
		    	  isResize: true,   
		    	  slide: false,  	        
		    	   buttons: [{ text: '关闭窗口', onclick: f_closeAddWindow }]
		      }); 
		      m.max();
		}


	function f_closeAddWindow(item, dialog) {
		$.ligerDialog.confirm('确认要关闭窗口吗', function(yes) {
			if (yes)
				dialog.close();
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
		var operUser = $("#operUser").val();
		var operTime = $("#operTime").val();
		manager.setOptions({
			parms : [ {
				name : 'operUser',
				value : operUser
			}, {
				name : 'operTime',
				value : operTime
			} ]
		});
		manager.loadData(true);
	}
	function f_query_all() {
		if (!manager)
			return;
		$("#operUser").val("");
		$("#operTime").val("全部");
		var operUser = $("#operUser").val();
		var operTime = $("#operTime").val();
		manager.setOptions({
			parms : [ {
				name : 'operTime',
				value : operTime
			} ]
		});
		manager.loadData(true);
	}
</script>
</html>
