<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF8"); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>按照领用单位设备金额统计结果</title>
    
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
		
		var xLay = "<%=request.getAttribute("xLay")%>";  //  '1','2'
		var data1=new Array();
		data1 = xLay.split(",");
		var yLay='<%=request.getAttribute("yLay")%>';   //  1,1
		var data2 = new Array;
		data2 = yLay.split(",");
		for( i =0;i<data2.length;i++){
			data2[i] = parseInt(data2[i]);
		}
		$('#container').highcharts({
            chart: {
                type: 'column'
            },
            credits:{
                enabled:false//不显示highCharts版权信息
             },
             lang:{
            	 downloadPNG: "下载PNG 图片",
            	 downloadJPEG: "下载JPEG文档",
            	 downloadPDF: "下载PDF 图片",
           		downloadSVG: "下载SVG 矢量图",
             	printChart: "打印图片"
             },
            title: {
                text: '领用单位-设备金额统计'
            },
            xAxis: {
            	labels: {
                    rotation: -35,
                    align: 'right',
                    style: {
                        fontSize: '13px',
                        fontFamily: '黑体'
                    }
                },
                categories: data1
            },
            yAxis: {
                min: 0,
                allowDecimals:false,
                title: {
                    text: '金额(元)'
                }
            },
           /* tooltip: {
                headerFormat: '<span style="font-size:8px">{point.key}</span><table>',
                pointFormat: '<tr><td style="font-size:8px;color:{series.color};padding:0">{series.name}: <b>{point.y:.1f} 元</b></td></tr>',
                footerFormat: '</table>',
 
                shared: true,
                useHTML: true
            },*/
            plotOptions: {
                column: {
                    pointPadding: 0.3,
                    borderWidth: 0,
                    dataLabels:{
                    	enabled:true //是否显示数据标签
                    }
                }
            },
            series: [{
                name: '金额',
                //data: [49.9, 71.5, 106.4, 129.2, 144.0,50,50,50]
            	data:data2
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
