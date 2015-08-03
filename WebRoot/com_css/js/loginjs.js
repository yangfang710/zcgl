

function callback(data){
	$(document).ready(function(){

		
		var dataArray=new  Array(); 
		dataArray = data.split("@");
		//alert(dataArray[0]);
		if(dataArray[0] == "success")
		{
			//alert('登陆成功!');
			
			if(dataArray[1] == "isExist"){
			//	alert(dataArray[1]);
				$.unblockUI();
				alert("不能同时登录多个用户，请正常退出后重新登录！");
				window.location.reload(true);
			}else{
				window.location.href="loginAction";
			}
		}else{
			$.unblockUI();
			changeCode();
			$("#errorInfo").html("<span>"+data+"</span>");
		}

	})
}

function changeCode(){
	var code=document.getElementById('identifycode');
	code.setAttribute('src','identify_Code.jsp?'+Math.random());
}

function checkLogin(){

	 var loginAction_userId = $("#loginAction_userId").val();
	 var loginAction_password = $("#loginAction_password").val();
	 var loginAction_usercaptcha = $("#loginAction_usercaptcha").val();
	 $.blockUI({        message: "正在登录，请稍候……"});
	 $.get("checkLoginAction?userId=" + loginAction_userId + "&password="+loginAction_password+"&usercaptcha="+loginAction_usercaptcha,null,callback);
	 setTimeout(function () { $.unblockUI();alert('系统异常，登陆超时，请稍后再试'); }, 60000);
	//var loginAction_userId = document.getElementById('loginAction_userId');
	//var loginAction_password = document.getElementById('loginAction_password');
	//var loginAction_usercaptcha = document.getElementById('loginAction_usercaptcha');
	//code.setAttribute('src','identify_Code.jsp?'+Math.random());
}

window.onload = function(){
   changeCode();
   document.oncontextmenu = function() { return false;}
  
  
}
$(document).ready(function(){
	$("#userId").focus(function(){
		$("#boxId").css("background","#F2FAFC");
	}).blur(function(){
		$("#boxId").css("background","#FFF");
	});
	$("#userpsw").focus(function(){
		$("#boxpsw").css("background","#F2FAFC");
	}).blur(function(){
		$("#boxpsw").css("background","#FFF");
	});
	$("#userverg").focus(function(){
		$("#boxverg").css("background","#F2FAFC");
	}).blur(function(){
		$("#boxverg").css("background","#FFF");
	});
	
})
