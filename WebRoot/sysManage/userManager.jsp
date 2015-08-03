<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/WEB-INF/tld/pageADMOperAuth.tld" prefix="pageADMOperAuth.tld" %>
<html>
	<head>
		<title>用户管理</title>
		<link href="com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css"
			rel="stylesheet" type="text/css" />

		<link href="com_css/LigerUILib/ligerUI/skins/ligerui-icons.css"
			rel="stylesheet" type="text/css" />
		<script src="com_css/LigerUILib/jquery/jquery-1.3.2.min.js"
			type="text/javascript">
</script>
		<script src="com_css/LigerUILib/ligerUI/js/core/base.js"
			type="text/javascript">
</script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerGrid.js"
			type="text/javascript">
</script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerResizable.js"
			type="text/javascript">
</script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerToolBar.js"
			type="text/javascript">
</script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerForm.js"
			type="text/javascript">
</script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerDateEditor.js"
			type="text/javascript">
</script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerComboBox.js"
			type="text/javascript">
</script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerCheckBox.js"
			type="text/javascript">
</script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerButton.js"
			type="text/javascript">
</script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerDialog.js"
			type="text/javascript">
</script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerDrag.js"
			type="text/javascript">
</script>

		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerTextBox.js"
			type="text/javascript">
</script>
		<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerTip.js"
			type="text/javascript">
</script>
		<script
			src="com_css/LigerUILib/jquery-validation/jquery.validate.min.js"
			type="text/javascript">
</script>
		<script src="com_css/LigerUILib/jquery-validation/jquery.metadata.js"
			type="text/javascript">
</script>
		<script src="com_css/LigerUILib/jquery-validation/messages_cn.js"
			type="text/javascript">
</script>
		<script type="text/javascript">
var grid = null;
var manager = null;
$(function() {
            
            <pageADMOperAuth.tld:pageADMOperAuth menuId="'040301','040302','040303','040304'"></pageADMOperAuth.tld:pageADMOperAuth>
             f_initGrid();
            var v = $("form").validate({
	                //调试状态，不会提交数据的
	                debug: true,
	                errorLabelContainer: "#errorLabelContainer", wrapper: "li",
	                invalidHandler: function (form, validator) {
	                    $("#errorLabelContainer").show();
	                    setTimeout(function () {
	                        $.ligerDialog.tip({ content: $("#errorLabelContainer").html() });
	                    }, 10);
	                },
	                submitHandler: function () {
	                    $("#errorLabelContainer").hide();
	                    alert("Submitted!");
	                }
	            });            
	            $(".l-button-test").click(function () {
	                alert(v.element($("#txtName")));
	            });
            $("form").ligerForm();
          
            manager= $("#maingrid4").ligerGetGridManager();
           // $("#txtUserGroup").ligerComboBox({ data: null, isMultiSelect: true, isShowCheckBox: true });
             
        }); 
        
        
        function f_add()
        {
            /*$.ligerDialog.open({ title: '新增用户信息', width: 650, height: 300,modal:false,url: 'sysManger/userManagerAdd.jsp', buttons: [
                { text: '关闭窗口', onclick: f_closeAddWindow }
            ]
            });*/
            var m = $.ligerDialog.open({ 
	        	  title: '新增用户信息',
	        	  height: 400, 
	        	  width: 700, 
	        	  url:'sysManage/userManagerAdd.jsp',	        	
	        	  showMax: true,
	 				isResize: true,
	 				slide: false, 
	        
	        	   buttons: [{ text: '关闭窗口', onclick: f_closeAddWindow }]
	          }); 
            m.max();
        }
        
        
       
         
      
       
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
   

         function f_modify()
        {
            
            if(!manager) {
            		$.ligerDialog.error('请查询用户清单后,选中需要修改的条目,再修改！')
            		return;
            } else {
            	var row = manager.getSelectedRow();
            	if (!row) { $.ligerDialog.error('请选择需要修改的行！'); return; }
            	/*var modifyUrl=
            	  $.ligerDialog.open({ title: '修改用户信息', width: 650,modal:false, height: 300, url: 'userManagerModifyAction?userId='+row.userId, buttons: [
                { text: '关闭窗口', onclick: f_closeAddWindow }
            	]
            	});*/
            	var m = $.ligerDialog.open({ 
  	        	  title: '修改用户信息',
  	        	  height: 400, 
  	        	  width: 700, 
  	        	  url: 'userManagerModifyAction?userId='+row.userid,	        	
  	        	   showMax: true,	        	   
  	        	  isResize: true,   
  	        	  slide: false,  	        
  	        	   buttons: [{ text: '关闭窗口', onclick: f_closeAddWindow }]
  	          }); 
            	m.max();
            }
        }
        
         function f_delete()
        {
           
            var manager = $("#maingrid4").ligerGetGridManager();
            if(!manager) {
            		$.ligerDialog.error('请查询用户清单后,选中需要删除的条目,再删除！')
            		return;
            } else {
            	var row = manager.getSelectedRow();
            	if (!row) { $.ligerDialog.error('请选择需要删除的行！'); return; }
            	$.ligerDialog.confirm('确认要删除该用户信息吗?若删除,用户信息将丢失', function (yes)
                     {
              
                     if(yes)
                     {
                     	var xhr = getXMLHttpRequest();
    					xhr.open("POST", "userManagerDeleteUserAction?userId="+row.userid, true);   
    					xhr.send(); 
    					xhr.onreadystatechange = function() { 
       						if (xhr.readyState == 4 && xhr.status == 200) {  
           					var responseContext = xhr.responseText;
                            if(responseContext=='success') 
                            {
                            	manager.deleteSelectedRow();
                            	$.ligerDialog.success('删除成功！');
                            }else
                            {
                            	$.ligerDialog.error('系统异常,请联系系统管理员！');
                            }
        
       	   
       }   
  
    }   
                     
                     }
                      
                     }) 
            	//根据主键用ajax访问action删除数据；删除后给出提示框，成功或失败
            }
            
    
        }
      
        function f_closeAddWindow(item, dialog)
        {
           $.ligerDialog.confirm('确认要关闭窗口吗', function (yes)
                     
                     {
                     if(yes)
                         dialog.close();
                     })
            
        }
        
        
         function f_initGrid()
        {
      
        grid = manager = $("#maingrid4").ligerGrid({
        	
                columns: [
                { display: '序号', name: 'rownum',width: 50, minWidth: 60 },
                { display: '登录名', name: 'userid',  width: 150, align: 'left',minWidth: 60 ,isSort : true},
                { display: '用户姓名', name: 'username', width: 150,align: 'left',minWidth: 60,isSort : true},
                { display: '用户部门编号', name: 'deptid', width: 120, align: 'left',minWidth: 60,isSort : true },
                { display: '用户部门', name: 'deptname', width: 150, align: 'left',minWidth: 60,isSort : true },
                { display: '团队名称', name: 'teamname', width: 150, align: 'left',minWidth: 60,isSort : true },
                { display: '用户所属组', name: 'groupname', width: 150, align: 'left',minWidth: 60,isSort : true},
                { display: '数据权限', name: 'dataauthname', width: 200, align: 'left',minWidth: 60,isSort : true},
                { display: '状态', name: 'userstate', width: 50, align: 'left',minWidth: 60 ,isSort : true},
                { display: '用户联系方式', name: 'usermobphone', width: 150, align: 'left',minWidth: 60 ,isSort : true},
                { display: '邮箱', name: 'useremail', width: 200, align: 'left',minWidth: 60,isSort : true }
                
                ], 
                dataAction: 'server',pageSize:30,where : f_getWhere(),rownumbers:false, checkbox:false,
	                url: 'userManagerQueryAction',allowAdjustColWidth:true,sortName : 'userid',
	    			sortOrder : 'asc',
	                width: '100%', height: '98%', 
	                toolbar: toolBar
            });
                   
        }
        
  
        function f_getWhere()
        {
            if (!grid) return null;
            var clause = function (rowdata, rowindex)
            {
                var key = $("#txtKey").val();
                return rowdata.orderId.indexOf(key) > -1;
            };
            return clause; 
        }
        
  			function f_query()
	         {
	        	if (!manager) return;	        	
	        	var queryDeptId = encodeURIComponent($("#queryDeptId").val());
	        	  
	            manager.setOptions(
	                { parms: [{name:'queryDeptId',value:queryDeptId}] }
	            );
	            
	            manager.loadData(true);
	         }
	         function f_query_all()
	         {
	        	 if (!manager) return;	        	
	        	var queryDeptId = "";
	            manager.setOptions(
	                { parms: [{name:'queryDeptId',value:queryDeptId}] }
	            );
		            
		            manager.loadData(true);
	         }
	       
function selectParentRole(obj) {
			var time=new Date();
			var url = "sysManage/organizationAddDeptTree.jsp";
			var orgIdAndName = window.showModalDialog(url+"?time="+time , window,
	    		'dialogHeight:400px;dialogWidth:300px;center:yes;status:no;help:no;resizable:yes;edge:Raised');
   			if(orgIdAndName){
				var org = orgIdAndName.split(":");
				var orgId = org[0];
				var orgName = org[1];
				if(orgId.indexOf(";;") != -1){
				orgId = orgId.substring(0,orgId.indexOf(";;"));
			}
		document.getElementById("queryDeptId").value = orgId;
		document.getElementById("queryDept").value = orgName;
   		}
}


	    /*    
	         function f_down(){
    	 		 $("#downloadbtn").click();
	        }
		
		          function f_action(){
    	  		 $("#formdownload").submit();
      		 }
      		 
      		*/ 
      		function f_down(){	    	
        	

        	var queryDeptId = encodeURIComponent($("#queryDeptId").val());
        	                                        
	        var actionUrl = "UserDown?queryDeptId="+queryDeptId; 
	       
	        window.open(actionUrl);
	    	window.close();
	     }
      		 
		
    </script>
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

.l-table-edit-td {
	padding: 4px;
	font-size: 13px;
}
</style>

	</head>

	<body style="padding: 6px; overflow: hidden;">



		<table width="100%">
			<tr>
				<td height="2"></td>
			</tr>
			<tr>
				<td class="blackbold_b">
					<img src="images/util/arrow6.gif" hspace="5">
					筛选条件
				</td>

			</tr>
			<tr>
				<td height="1" bgcolor="#BBD2E9"></td>
			</tr>

		</table>

		<fieldset style="top: inherit; width: 100%">
			<form name="form1" method="post" action="" id="form1">

				<table cellpadding="0" cellspacing="0" class="l-table-edit">
					<tr>
						<td align="right" class="l-table-edit-td">
							网点:
						</td>
						<td align="left" class="l-table-edit-td">
							<input type="hidden" name="queryDeptId" id="queryDeptId" />
							<input name="queryDept" type="text" id="queryDept" ltype="text"
								value="点击选择部门" readonly="readonly"
								onClick="selectParentRole(this)" />
						</td>

						<td align="right" class="l-table-edit-td">
							<input type="button" value="查询" id="Button1" class="l-button"
								style="width: 60px" onClick="f_query()" />
						</td>
						<td align="left" class="l-table-edit-td">
							<input type="button" value="查询所有" id="Button2" class="l-button"
								style="width: 80px" onclick="f_query_all()" />
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
				<td class="blackbold_b">
					<img src="images/util/arrow6.gif" hspace="5">
					用户信息
				</td>
			</tr>

		</table>


		<div id="maingrid4" style="margin: 0; padding: 0"></div>
		<div style="display: none;">
		</div>

	</body>
</html>
