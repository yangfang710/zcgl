<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title></title>
	
        <style type="text/css">
           body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:100px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
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
    <link href="../com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="../com_css/LigerUILib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <link href="../com_css/LigerUILib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />    
    <script src="../com_css/LigerUILib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/core/base.js" type="text/javascript"></script>    
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>  
 	 <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
 	  <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>    
 	 <script src="../com_css/LigerUILib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="../com_css/LigerUILib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/jquery-validation/messages_cn.js" type="text/javascript"></script>
    
     <link rel="stylesheet" href="../com_css/uploadify/uploadify.css" type="text/css"></link>	
	<script type="text/javascript" src="../com_css/uploadify/swfobject.js"></script>
	<script type="text/javascript" src="../com_css/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
    
     <script type="text/javascript">
       var grid = null;   
       var manager = null; 
       var total = 0;
       var inId = 0;
     	$(document).ready(function() {
     		
     		  $("#myFile").uploadify({
                    'uploader' : '../com_css/uploadify/uploadify.swf',
                    'script' : 'productAddExcelAction',//后台处理的请求
                    'cancelImg' : '../com_css/uploadify/cancel.png',
                    'fileDataName':'myFile',//服务器端根据这个接收文件
                    'queueID' : 'fileQueue',// 文件队列的ID，该ID与存放文件队列的div的ID一致
                    'queueSizeLimit' : 1,//当允许多文件生成时，设置选择文件的个数
                    'fileDesc' : 'excel文件',//用来设置选择文件对话框中的提示文本
                    'fileExt' : '*.xls', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
                    'auto' : false,//false需要点击上传按钮才上传
                    'multi' : true,//可以上传多个文件
                    'simUploadLimit' : 1,//允许同时上传的个数 
                    'buttonText' : '浏览文件',
                    'onCancel' : function(event,queueID,fileObj,data) {
                    	  alert(' 文件 ' + fileObj.name + ' 被取消!');                    	
                    },//取消上传文件
                    'onClearQueue' : function(event) {
					    //  alert('The queue has been cleared.');
					  },//清空上传队列
                    
                    'onError' : function(event, queueID, fileObj, errorObj) {
                        alert(' 上传失败' + errorObj.info + '错误类型' + errorObj.type);
                    },
                    'onComplete' : function(event, queueID, fileObj, response, data) {                    
                    	var strArray=new  Array(); 
	  					strArray=response.split("@");
	  					var error = null; 
	  					error = strArray[0];
	  					inId = strArray[1];
                      	total = strArray[2];	
                      	if(error != "success"){
                         	alert(error);
                      	}else{
	                      	if(total != "")	{
	                         	alert('共插入'+total+'条记录');    
	                         	 window.parent.$("#maingrid4").ligerGrid().loadData(true);
	                         	$.ligerDialog.success('提交成功，请关闭对话框！','提示',function(){		  		
	               	   				 var parentWindow_body =  $(window.parent.document).find("body");if(! parentWindow_body.find(".dealWithMask").length){parentWindow_body.append("<input type=text class=dealWithMask style=height:0;border:none/>");}parentWindow_body.find(".dealWithMask").focus(); window.parent.$.ligerDialog.close();
	                 		    });
	                        }	 
                   		 }
                  
                    }

                    	
       			   });          

            });
     	
        $(function() {  
         
   			var v = $("form").validate({
   				debug:true,
   				errorPlacement: function(lable, element) {
                    if (element.hasClass("l-textarea")) element.ligerTip({ content: lable.html(), appendIdTo: lable });
                    else if (element.hasClass("l-text-field")) element.parent().ligerTip({ content: lable.html(), appendIdTo: lable });
                    else lable.appendTo(element.parents("td:first").next("td"));
                },
                success: function(lable) {
                    lable.ligerHideTip();
                },
                submitHandler: function() {
                    $("form .l-text,.l-textarea").ligerHideTip();
                    alert("Submitted!");
                }
   			});
   			
   			$("form").ligerForm();
   			$(".l-button-test").click(function() {
                alert(v.element($("#txtName")));
            });
   			manager= $("#maingrid4").ligerGetGridManager();
       });
           
         function f_query()
         {        
		  if (!manager) return;  
        	var inId1 =  encodeURI(inId.valueOf());  
        	manager.setOptions({
	        		
	        		parms:[{name:'inId',value:inId1}]
	        	});
            

        	 manager.loadData(true);
         }
       
	    function f_download(){
	      		var url = "productExcelFileDownloadAction";
		  		window.open(url);
	    		window.close();
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
</head>

<body style="padding:6px; overflow:hidden;">



<table width="100%">
	<tr>
		<td colspan="2" height="5"></td>
	</tr>
	<tr>
		<td class="blackbold"><img src="../images/util/arrow6.gif" hspace="5">上传文件</td>
		
	</tr>
	<tr >
		<td colspan="2" height="1" bgcolor="#BBD2E9" ></td>
	</tr>

</table>

    
    <div style="display:none">
    <!--  数据统计代码 --></div>
<fieldset style="top:inherit;width:100%">
    <form name="form1" method="post" action="supplierQuery" id="form1">

        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
           <tr >   
     
                <td align="right" class="l-table-edit-td" >资产导入模板:</td>
                <td align="left" class="l-table-edit-td"> 
                <input type="button" value="下载EXCEL模板" id="downLoadFileButton" class="l-button"  style="width:150px" onclick="f_download()"/></td>
             </tr> 
                 
             <tr>    
     
                <td align="right" class="l-table-edit-td" >上传文件:</td>
                <td align="left" class="l-table-edit-td"> 
	                <div id="fileQueue"></div>
					<input type="file" name="myFile" id="myFile" />
					<a href="javascript:$('#myFile').uploadifyUpload();">上传</a>|
					<a href="javascript:$('#myFile').uploadifyClearQueue();"> 取消上传</a> 
					
                </td>
             </tr>         
			 
 </table>
 
    </form>
        		
</fieldset>


</body>
</html>