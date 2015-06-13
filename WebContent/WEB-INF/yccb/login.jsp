<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
<%@include file="/commonjsp/top.jsp" %>
<link href="${path}/resource/css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="gang">
  <div style="height:169px; overflow:hidden;"><img src="${path}/resource/images/login_01.jpg" width="1004" height="169" /></div>
  <div style="height:128px; overflow:hidden;"><img src="${path}/resource/images/login_02.jpg" width="1004" height="128" /></div>
  <div>
     <div style="width:277px; float:left; height:255px; overflow:hidden;"><img src="${path}/resource/images/login_mid1.jpg" width="277" height="255" /></div>
     <div class="login_midbg">
     	<div class="lang_postion">
     		<a href="${path}/resource/lang.do?langType=zh" style="color:rgb(199, 199, 224)">中文</a> 
     		<a href="${path}/resource/lang.do?langType=en" style="color:rgb(199, 199, 224)">EN</a>
     	</div>
     	<form onsubmit="return submitInfo()" id = "loginForm" action="${path}/resource/login.do" method="post">
	     	<table width="285" border="0" cellspacing="0" cellpadding="0" style="margin-left:180px; margin-top:48px; line-height:24px;">
			  <tr>
			    <td width="71" class="pa_bottom" style=" padding-bottom:20px; "><fmt:message key='login.uname'/>：</td>
			    <td style=" padding-bottom:20px; " colspan="2"><input id="loginname" name="loginname" type="text" class="login_input" tabindex="1" /></td>
			  </tr>
			  <tr>
			    <td class="pa_bottom" style=" padding-bottom:20px; "><fmt:message key='login.password'/>：</td>
			    <td style=" padding-bottom:20px;" colspan="2"><input id="loginkey" name="loginkey" type="password" class="login_input" /></td>
			  </tr>
			    <tr>
			    <td class="pa_bottom" style=" padding-bottom:20px; "><fmt:message key='login.checkcode'/>：</td>
			    <td width="105" style=" padding-bottom:20px;"><input id="checkcode" name="checkcode" type="text" class="login_input" maxlength="4" style="width:84px;" /></td>
			    <td width="109" style=" padding-bottom:20px;"><img id="checkimage" src="${path}/resource/codeImg.do" onclick="changeCheck()"></td>
			  </tr>
			  <tr>
				<td style=" padding-bottom:20px;" colspan="2"><span id = "error_info" style="color:red">${message_login}</span></td>
			    <td style=" padding-bottom:20px;" ><input name="" type="submit" value="<fmt:message key='login.submit'/>" class="login_botton" ></td>
			  </tr>
			</table>
		</form>
     </div>
     <div style="width:262px; float:left; height:255px; overflow:hidden;"><img src="${path}/resource/images/login_mid3.jpg" width="262" height="255" /></div>
  </div>
  <div><img src="${path}/resource/images/login_03.jpg" width="1004" height="94" /></div>
  <div class="login_copyrightbg">copyright 2015 淅川西岛光电仪表科技有限公司</div>
</div>
<script type="text/javascript">
$("#loginname").focus();
/* ===========================用户模块=================================*/
//获取验证码
function changeCheck(){
	$("#checkcode").val("");
	$("#checkcode").focus();
	$("#checkimage").attr("src","${path}/resource/codeImg.do?ch="+Math.random());
}
//获取登录信息
function submitInfo(){
	var loginname = $("#loginname").val();
	var loginkey = $("#loginkey").val();
	var checkcode = $("#checkcode").val();
	if(loginname == ""){
		$("#error_info").text("<fmt:message key='login.error.uname'/>");
		$("#loginname").focus();
		return false;
	}
	if(loginkey == ""){
		$("#error_info").text("<fmt:message key='login.error.password'/>");
		$("#loginkey").focus();
		return false;
	}
	if(checkcode.length != 4){
		$("#error_info").text("<fmt:message key='login.error.checkcode'/>");
		$("#checkcode").focus();
		changeCheck();
		return false;
	}
	return true;
}
</script>
</body>
</html>
