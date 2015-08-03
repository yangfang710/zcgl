<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统计列表</title>
     <link href="com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="com_css/LigerUILib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />    
    <script src="com_css/LigerUILib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/core/base.js" type="text/javascript"></script>    
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>  
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>         
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>  
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerButton.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>  
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
      
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script> 
    <script src="com_css/LigerUILib/ligerUI/js/plugins/ligerTip.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="com_css/LigerUILib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="com_css/LigerUILib/jquery-validation/messages_cn.js" type="text/javascript"></script>

    <script type="text/javascript">
    var grid = null;   
    var manager=null;  
    $(function() {     
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
        var sel = document.getElementById ('sel');
        var today = new Date();
        var year = today.getFullYear(); 
	   for ( var i = 1980; i < 2050; i++){
		   var option = document.createElement ('option');
		   option.value = i;
		   $("#sel option").each(function() { 
				if ($(this).val() == year) { 
					$(this).attr("selected", "selected"); 
				} 
			}); 
		   var txt = document.createTextNode (i);
		   option.appendChild (txt);
		   sel.appendChild (option);
	   }

        $("form").ligerForm();
        $(".time").hide();
    }); 
    
  //统计柱状图
       function f_query(){    
       		var condition = $("#condition").val();
       		var departmentId = $("#departmentId").val();
       		var url="";
       		if(condition==5){
       			var txBeginDate = $("#sel").val();
       			url = "findAction?condition="+condition+"&txBeginDate="+txBeginDate+"&departmentId="+departmentId+"&type=column";
       		}else{
       			url = "findAction?condition="+condition+"&departmentId="+departmentId+"&type=column";
       		}
       		
        	$('#lineChart').attr("src", url); 
        }
       //选择最后一个显示年份
       function check(){
    	   var condition = $("#condition").val();
    	   if(condition==5){
    		   $(".time").show();
    	   }else{
    		   $(".time").hide();
    	   }
       }
       //饼状图统计
       function f_pie(){
    	   var condition = $("#condition").val();
    	   var departmentId = $("#departmentId").val();
      		var url="";
      		if(condition == 3){
      			url = "findAction?condition="+condition+"&departmentId="+departmentId+"&type=pie3";
      		}else if(condition == 4){
      			url = "findAction?condition="+condition+"&departmentId="+departmentId+"&type=pie4";
      		}else if(condition == 1){
      			url = "findAction?condition="+condition+"&departmentId="+departmentId+"&type=pie1";
      		}else if(condition == 2){
      			url = "findAction?condition="+condition+"&departmentId="+departmentId+"&type=pie2";
      		}else if(condition == 5){
      			 var txBeginDate = $("#sel").val();
      			url = "findAction?condition="+condition+"&txBeginDate="+txBeginDate+"&departmentId="+departmentId+"&type=pie5";
      		}
      		
       	$('#lineChart').attr("src", url); 
       }
       //导出统计
       function f_excelOut() {
    	   var condition = $("#condition").val();
    	   if(condition == -1){
    		   alert("请选择统计维度！");
    		   return;
    	   }
   		 	window.open('statisticsDownExcel?condition='+condition);
   			window.close();
   	}
       //部门树
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
    <style type="text/css">
		body{ 
			font-size:12px;
			background:#f9fbfc
		}
		.blackbold_b { /* 标题样式 */
			line-height: 22px;
			width: 150px;
			height: 22px;
			font-size: 12px;
			font-weight: bold;
			color: #FF5317
		}
		td{
			margin-left:20px
		}
    </style>

</head>

<body style="padding:6px; overflow:hidden;">
<div id="toptoolbar"></div> 
    <div style="display:none">
    <!--  数据统计代码 --></div>
 <table width="100%">
		<tr>
			<td height="2"></td>
		</tr>
		<tr>
			<td class="blackbold_b"><img src="images/util/arrow6.gif" hspace="5">选择维度</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#BBD2E9"></td>
		</tr>

	</table>
	
	<fieldset style="top:inherit;width:100%">
		<form name="form1" method="post" action="" id="form1">
			<table cellpadding="0" cellspacing="0" >
				<tr height="40px">
					<td width="10px"></td>
					<td align="right" >&nbsp;&nbsp;&nbsp;&nbsp;维度统计:&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td> <select name="condition" id="condition" style="width:300px;" onchange="check()"> 
					               <option value="1" >使用方向</option>    
					               <option value="2">当前状态</option>    
					               <option value="3">存放地</option>
					               <option value="4">设备总金额</option>
					               <option value="5">增减变动</option>
					               </select>
					</td>
					<td width="10px"></td>
					<td class="time" align="right">年份：&nbsp;&nbsp;</td>
					<td class="time"><select id="sel" name="txBeginDate">
					</select> </td>
					<td width="10px"></td>
					<td  align="right">部门：&nbsp;&nbsp;</td>
					<td >
						<input type="hidden" name="departmentId" id="departmentId" /> 
						<input name="departmentName" type="text" id="departmentName" validate="{required:true}" ltype="text" value="通信与信息工程学院" readonly="readonly" onclick="selectParentRole(this)" />
					</td>
					<td width="10px"></td>
					<td align="center"  >
						<input type="button"	 value="统计" id="Button1" class="l-button" style="width:80px;height:23px" onclick="f_query()" />
					</td>
					<td width="10px"></td>
					<td align="center"  >
						<input type="button"	 value="饼图统计" id="Button3" class="l-button" style="width:80px;height:23px" onclick="f_pie()" />
					</td>
					<td width="10px"></td>
					<td align="center" >
						<input type="button"	 value="导出" id="Button2" class="l-button" style="width:80px;height:23px" onclick="f_excelOut()" />
					</td>
				</tr>
	</table>
	</form>
	</fieldset>
	
	
<table width="100%">
 	<tr >
		<td colspan="2" height="1" bgcolor="#BBD2E9" ></td>
	</tr>
	<tr>
		<td colspan="2" height="5"></td>
	</tr>
	<tr>
		<td class="blackbold_b"><img src="images/util/arrow6.gif" hspace="5">统计结果</td>
	</tr>
	    
</table>
<iframe id="lineChart" src="findAction?condition=1&departmentId=01&type=column" style="height: 500px;width:100%"  frameborder="0"></iframe>

</body>
</html>