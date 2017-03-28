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
  <div>
 	<div style="font-family:'宋体';font-size:50px;color:#fff;text-align:center;margin:100px auto;">智能远传抄表系统</div>
     <div class="">
     	<%-- <div class="lang_postion">
     		<a href="${path}/resource/lang.do?langType=zh" style="color:rgb(199, 199, 224)">中文</a> 
     		<a href="${path}/resource/lang.do?langType=en" style="color:rgb(199, 199, 224)">EN</a>
     	</div> --%>
     	<form onsubmit="return submitInfo()" id = "loginForm" action="${path}/resource/login.do" method="post">
	     	<table width="285" border="0" cellspacing="0" cellpadding="0" style="margin:48px auto; line-height:24px;">
			  <tr>
			    <td width="71" class="pa_bottom" style=" padding-bottom:20px; "><fmt:message key='login.uname'/>：</td>
			    <td style=" padding-bottom:20px; " colspan="2"><input id="loginname" name="loginname" type="text" class="login_input" tabindex="1" /></td>
			  </tr>
			  <tr>
			    <td class="pa_bottom" style=" padding-bottom:20px; "><fmt:message key='login.password'/>：</td>
			    <td style=" padding-bottom:20px;" colspan="2"><input id="loginkey" name="loginkey" type="password" class="login_input" tabindex="2" /></td>
			  </tr>
			    <tr>
			    <td class="pa_bottom" style=" padding-bottom:20px; "><fmt:message key='login.checkcode'/>：</td>
			    <td width="105" style=" padding-bottom:20px;"><input id="checkcode" name="checkcode" type="text" class="login_input" maxlength="4" style="width:84px;" tabindex="3"/></td>
			    <td width="109" style=" padding-bottom:20px;"><img id="checkimage" src="${path}/resource/codeImg.do" onclick="changeCheck()"></td>
			  </tr>
			  <tr>
				<td style=" padding-bottom:20px;"><span id = "error_info" style="color:red">${message_login}</span></td>
				<td width="109" style=" padding-bottom:20px;">
					<select name="identity" style="width:100px;" hidden>
				        <option value="1" selected>管理员</option>
				        <option value="2">用户</option>
				    </select><!--  -->
				</td>
			    <td style=" padding-bottom:20px;"><input name="" type="submit" value="<fmt:message key='login.submit'/>" class="login_botton" tabindex="4"></td>
			  </tr>
			</table>
		</form>
     </div>
  </div>
  
  <!-- <div class="login_copyrightbg">copyright </div> -->
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
