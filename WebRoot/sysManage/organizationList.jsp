<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="/WEB-INF/tld/pageADMOperAuth.tld" prefix="pageADMOperAuth.tld" %>
<html>
<head>
<link
	href="../com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />

<link href="../com_css/LigerUILib/ligerUI/skins/ligerui-icons.css"
	rel="stylesheet" type="text/css" />
<script src="../com_css/LigerUILib/jquery/jquery-1.3.2.min.js"
	type="text/javascript"></script>
<script src="../com_css/LigerUILib/ligerUI/js/core/base.js"
	type="text/javascript"></script>
<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerGrid.js"
	type="text/javascript"></script>
<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerDialog.js"
	type="text/javascript"></script>

<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerToolBar.js"
	type="text/javascript"></script>
<script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerResizable.js"
	type="text/javascript"></script>

<style type="text/css">
body {
	font-size: 12px;
}

.l-table-edit {
	
}

.l-table-edit-td {
	padding: 4px;
}

.l-button-submit,.l-button-test {
	width: 80px;
	float: left;
	margin-left: 10px;
	padding-bottom: 2px;
}

.l-verify-tip {
	left: 230px;
	top: 120px;
}

.blackbold { /* 标题样式 */
	line-height: 22px;
	width: 100px;
	height: 22px;
	font-size: 12px;
	font-weight: bold;
	color: #FF5317
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

<title>部门管理</title>
</head>
<body style="padding:6px; overflow:hidden;">
	<div id="toptoolbar"></div>
	<table width="100%" id="result">
		<tr>
			<td colspan="2" height="5"></td>
		</tr>
		<tr>
			<td class="blackbold_b"><img src="../images/util/arrow6.gif"
				hspace="5" />部门列表</td>
		</tr>

	</table>


	<div id="maingrid4" style="margin:0; padding:0"></div>

</body>

<script type="text/javascript">

    var grid = null; 
   
      $(function ()
        {
         	<pageADMOperAuth.tld:pageADMOperAuth menuId="'040201','040202','040203','040204'"></pageADMOperAuth.tld:pageADMOperAuth>            
            f_initGrid();  			

             
        });
       
      
        function f_add()
        {
           
        	var m =$.ligerDialog.open({ 
	        	  title: '新增部门信息',
	        	  height: 400, 
	        	  width: 700, 
	        	  url: 'organizationAdd.jsp',	        	
	        	   showMax: true,	        	   
	        	  isResize: true,   
	        	  slide: false,  	        
	        	   buttons: [{ text: '关闭窗口', onclick: f_closeAddWindow }]
	          });
        	m.max();
        }
       
         function f_modify()
        {
         var  manager = $("#maingrid4").ligerGetGridManager();
            if(!manager) {
            		$.ligerDialog.error('请查询部门清单后,选中需要修改的条目,再修改！')
            		return;
            } else {
            	var row = manager.getSelectedRow();
            	if (!row) { 
            		$.ligerDialog.error('请选择需要修改的行！'); 
            		return;
            	}
            	
            	
            	 var m = $.ligerDialog.open({ 
   	        	  title: '修改部门信息',
   	        	  height: 400, 
   	        	  width: 700, 
   	        	  url: 'organizationModifyAction?id='+row.id,	        	
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
            		$.ligerDialog.error('请查询部门清单后,选中需要删除的条目,再删除！')
            		return;
            } else {
            	var row = manager.getSelectedRow();
            	if (!row) { $.ligerDialog.error('请选择需要删除的行！'); return; }
            	$.ligerDialog.confirm('确认要删除该部门吗?若删除,会删除该部门和部门下的用户,请谨慎操作！', function (yes)
                     {
              
                     if(yes)
                     {
                     
                     
                             $.ajax({   
				              url :'organizationListDeleteAction?deptId='+row.deptId,  //后台处理程序   
				              type:'post',    //数据发送方式   
				              dataType:'text',   //接受数据格式   
				              data:null,   //要传递的数据；就是上面序列化的值   
				              success:function(result){
				            	 
                    		  	if(result=='success'){
                    		  		manager.deleteSelectedRow();
                    		  		$.ligerDialog.success('删除成功！');
                    		  	}else{
                    		  		$.ligerDialog.error('系统异常,删除失败,请重试或联系系统管理员！');
                    		  	}
				              } //回传函数(这里是函数名)    
					     }); 
                     
                     
         
                     }
                      
                     });
            	//根据主键用ajax访问action删除数据；删除后给出提示框，成功或失败
            } 
        }
        
        function f_down(){
        	var actionUrl = "organizationDown"; 
  	       
 	        window.open(actionUrl);
 	    	window.close();
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
   

        
        
        
        
        
        function f_closeAddWindow(item, dialog)
        {
            
           $.ligerDialog.confirm('确认要关闭窗口吗', function (yes)
                     {
                     if(yes)
                         dialog.close();
                     })
            
        }
        
        
        
        function getArgs(strParame) {
         var args = new Object();
         var query = location.search.substring(1); // Get query string
         var pairs = query.split("&"); // Break at ampersand
         for (var i = 0; i < pairs.length; i++) {
             var pos = pairs[i].indexOf('='); // Look for "name=value"
             if (pos == -1) continue; // If not found, skip
             var argname = pairs[i].substring(0, pos); // Extract the name
             var value = pairs[i].substring(pos + 1); // Extract the value
             value = decodeURIComponent(value); // Decode it, if needed
             args[argname] = value; // Store as a property
         }
         return args[strParame]; // Return the object
     }

        
           
         function f_initGrid()
        {
        var actionAdr = encodeURI(encodeURI("organizationListQueryAction?deptId="+getArgs('deptId')));
        grid = $("#maingrid4").ligerGrid({
                columns: [
                { display: '序号', name: 'rownum',width: 40, minWidth: 30 },
                { display: 'id', name: 'id',width: 10, minWidth: 30,hide:1 },
                { display: '部门编号', name: 'deptid',  width: 60, align: 'left',minWidth: 40,isSort : true },
                { display: '部门名称', name: 'deptname', width: 150, align: 'left',minWidth: 40,isSort : true},
                { display: '上级部门', name: 'parentdeptname', width: 150, align: 'left',minWidth: 40,isSort : true},
                { display: '电话', name: 'phonenum', width: 80, align: 'left',minWidth: 40 ,isSort : true},
                { display: '地址', name: 'deptaddress',width: 100, align: 'left',minWidth: 40,isSort : true}, 
                { display: '新增时间', name: 'indate', width: 80, align: 'left',minWidth: 40,isSort : true },
                { display: '状态', name: 'deptstate', width: 80, align: 'left',minWidth: 40,isSort : true },
                { display: '操作人', name: 'operusername', width: 60, align: 'left',minWidth: 40,isSort : true},
                { display: '备注', name: 'remark', width: 60, align: 'left',minWidth: 40,isSort : true}
                ], dataAction: 'server',pageSize:30,where : f_getWhere(),
                url: actionAdr, sortName: 'orderId',allowAdjustColWidth:true,
                width: '100%', height: '100%',frozen:false,sortName : 'deptid',
    			sortOrder : 'asc',
                toolbar: toolBar
            });
        
        }
        
           function f_search()
        { 
            grid.loadData(f_getWhere());
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
        
 		
    </script>
</html>
