//手机扫码模块~by Kevin~
var isFinish = false; 
var manager2;
var counter = 60;
var s;
function getSerialNumber(){
	counter = 60;
	isFinish = false;//true:成功，false:失败
	var serialNumber = 'deprecated';
	manager2 = $.ligerDialog.waitting('<font size="2pt"><br />&nbsp;请用手机客户端扫描串号并上传....</font><br /><br /><font size="2"><span style="margin-left:70px;" id="counter" ></span><input type="button" style="margin-left:150px;width:60px;" class="l-button"  onclick="cancelScan()" value="取消扫描" /></font>'); 
	s = setTimeout(function () {
		manager2.close(); 
		isFinish = true;
		$.ligerDialog.confirm('扫描串号失败！是否重新扫描？', function (flag) {
			if(flag == true) {
				getSerialNumber();
			}
			});
	}, counter * 1000);
	if(arguments.length == 0) {
		queryServer();
	} else if (arguments.length == 1) {
		queryServer(1);
	}
	
}
function queryServer(){
	var paramLength = arguments.length;
	counter --;
	if(counter >= 0){
		$('#counter').html(counter);
	}
	setTimeout(function(){
		$.ajax({
			url:'serialNumberQueryAction',
			type:'post',
			content:'text',
			success:function(data){
				if((data == 'deprecated' || data == '') && isFinish == false) {
					//queryServer();
					if(paramLength == 0) {
						queryServer();
					} else if (paramLength == 1) {
						queryServer(1);
					}
				}
			//	alert(data);
				
				if(data != 'deprecated' && data != '') {
					//$('#serialNumber').val(data);
					//alert(arguments.length + ',' + arguments[0] + ',' + arguments[1]);
					if(paramLength == 0) {
						$('#serialNumber').val(data);
					} else if (paramLength == 1) {
						$('#serialNumberBackup').val(data);
					}
					manager2.close();
					var manager3 = $.ligerDialog.waitting('<font size="3"><span style="margin-left:80px;">扫码成功</span></font>');
					setTimeout(function () { manager3.close(); }, 2000);
					clearTimeout(s);
				}
			}
		});
	}, 1000);
}
function cancelScan(){
	manager2.close();
	clearTimeout(s);
	isFinish = true;
}