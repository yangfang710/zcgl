<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF8"); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>按照存放地统计结果</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv=Content-Type content="text/html;charset=utf-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
   
    <link href="com_css/LigerUILib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="com_css/highcharts/jquery-1.8.2.min.js"></script>
    <script src="com_css/highcharts/highcharts.js"></script>
    <script src="com_css/highcharts/exporting.js"></script>
	    
    
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
	$(function () {
		
		var flag =  '<%=request.getAttribute("record")%>'; 
		if(flag == 'no'){
			alert("该部门无资产记录！");
			return ;
		}
		
		var m= '<%=request.getAttribute("xLay")%>';
		var ch = new Array;
		ch= m.split(",");
		var n='<%=request.getAttribute("yLay")%>';
		var num = new Array;
		num = n.split(",");
		sum = 0;
		for( i =0;i<num.length;i++){num[i] = parseInt(num[i]);}
		for(i = 0;i<num.length;i++){ sum+=num[i];}
		var result = new Array;
		for(i = 0;i<num.length;i++){
			result[i] = [ch[i],num[i]];
		}
		
		
		$('#container').highcharts({
            chart: {
                type: 'pie'
            },
            credits:{
                enabled:false//不显示highCharts版权信息
             },
             lang:{
             	downloadJPEG: "下载JPEG 图片",
             	downloadPDF: "下载PDF文档",
             	downloadPNG: "下载PNG 图片",
             	downloadSVG: "下载SVG 矢量图",
             	printChart: "打印图片"
             },
            title: {
                text: '存放地-数量统计'
            },
            tooltip: {
            	pointFormat: '{series.name}: <b>{point.y}台</b>',
 
                shared: true,
                useHTML: true
            },
            plotOptions: { 
                pie: { 
                    allowPointSelect: true,  //选中某块区域是否允许分离
                    cursor: 'pointer', 
                    dataLabels: { 
                        enabled: true,  //是否直接呈现数据 也就是外围显示数据与否 
                        formatter: function() {  
                            return '<b>'+ this.point.name +'</b>: '+ this.y +' 台';  
                        }
                    }, 
                    showInLegend: true   //是否显示图例
                  
                } 
            }, 
            series: [{
            	type:'pie',
                name: '数量',
            	data:result
            }]
        });
	});
	 
    
	</script>
	<style type="text/css">
body{ font-size:12px;}
.l-table-edit {}
.l-table-edit-td{ padding:4px;}
.l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
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
  </head>
  
  <body>
    <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
    <table width="100%" id="result">
		<tr>
			<td  height="1" bgcolor="#BBD2E9"></td>
		</tr>
		<tr>
			<td height="5"></td>
		</tr>
		<tr>
			
		</tr>

	</table>
    
  </body>
</html>
