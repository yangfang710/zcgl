<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>资产管理系统 </title>
	
	<link href="com_css/css/login.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="com_css/formValidator/style/validator.css" type="text/css"></link>
	<script type="text/javascript" src="com_css/js/jquery-1.6.2.min.js"></script>
	<script type="text/javascript" src="com_css/js/jquery.blockUI.js"></script>
	<script type="text/javascript" src="com_css/formValidator/formValidator-4.0.1.min.js"></script>
	<script type="text/javascript" src="com_css/formValidator/formValidatorRegex.js"></script>
	<script type="text/javascript" src="com_css/js/loginjs.js"></script>
<script type="text/javascript">
$(function(){
	document.getElementById("loginAction_userId").focus();
});
function key_down(){
	if (event.keyCode == 13) {
		checkLogin();
	}
}
</script>	
<style type="text/css">
</style>
</head>

<body>
<div id="container">
	<div id="header">
		<img alt="" src="mainFrame/images/zcdx.png" >
	</div>
	<div id="content">
		<div class="left_main">
			<ul class="news" style="color:#FFFFFF;"><br /><br />
				<li><font style="font-size:20px;" color="black" size="5">&nbsp;&nbsp;&nbsp;&nbsp;通信学院  资产管理系统  </font></li>
			</ul>
		</div>
		<form id="form1" method="post" action = "loginAction">
			<fieldset class="right_main">
				<dl class="setting">
					<dt>
						<label ><font color="black">登录名</font></label>
					</dt>
					<dd>
						<span class="text_input">
							<input type="text" name="userId" id="loginAction_userId"/>
						</span>
						<div id="usernameTip" style="width:200px;"></div>
					</dd>
					<dt>
						<label><font color="black">密　码</font></label>
					</dt>
					<dd>
						<span class="text_input">
							<input type="password" name="password" id="loginAction_password" />
						</span>
						<div id="userpassTip" style="width:200px;"></div>
					</dd>
					<dt>
						<label><font color="black">验证码</font></label>
					</dt>
					<dd>
						<span class="short_input">
							<input id="loginAction_usercaptcha" type="text" style="text-transform: uppercase;" name="usercaptcha" onkeydown="key_down()"/>
						</span>
						<span class="yzm">
							<img src="identify_Code.jsp" id="identifycode"/>
							<a href="javascript:changeCode()">换一张</a>
						</span>
						<div id="vdcodeTip" style="width:280px;"></div>
					</dd>
					<dd>
						<input type="button" id="loginBtn" value="登录" name="sm1"  class="login_btn" onClick="checkLogin()" />
						<div id="loginbottom" class="login">
							<font color="#ff0000"><div id="errorInfo"><div class="warn" id="resultpsw"></div></div></font>
						</div>
					</dd>
				</dl>
				
			</fieldset>
		</form>
	</div>	
	<div id="footer"></div>
</div>
</body>
</html>