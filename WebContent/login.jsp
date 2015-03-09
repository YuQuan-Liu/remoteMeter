<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
<%@include file="/commonjsp/top.jsp" %>
<link href="resource/css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="gang">
  <div style="height:169px; overflow:hidden;"><img src="resource/images/login_01.jpg" width="1004" height="169" /></div>
  <div style="height:128px; overflow:hidden;"><img src="resource/images/login_02.jpg" width="1004" height="128" /></div>
  <div>
     <div style="width:277px; float:left; height:255px; overflow:hidden;"><img src="resource/images/login_mid1.jpg" width="277" height="255" /></div>
     <div class="login_midbg">
     	
     	<form autocomplete="off" onsubmit="return submitInfo()" id = "loginForm">
	     	<table width="285" border="0" cellspacing="0" cellpadding="0" style="margin-left:180px; margin-top:48px; line-height:24px;">
			  <tr>
			    <td width="71" class="pa_bottom" style=" padding-bottom:20px; ">用户名：</td>
			    <td style=" padding-bottom:20px; " colspan="2"><input id="loginname" name="loginname" type="text" class="login_input" tabindex="1" /></td>
			  </tr>
			  <tr>
			    <td class="pa_bottom" style=" padding-bottom:20px; ">密   码：</td>
			    <td style=" padding-bottom:20px;" colspan="2"><input id="loginkey" name="loginkey" type="password" class="login_input" /></td>
			  </tr>
			    <tr>
			    <td class="pa_bottom" style=" padding-bottom:20px; ">验证码：</td>
			    <td width="105" style=" padding-bottom:20px;"><input id="checkcode" name="checkcode" type="text" class="login_input" maxlength="4" style="width:84px;" /></td>
			    <td width="109" style=" padding-bottom:20px;"><img id="checkimage" src="${path}/codeImg.do" onclick="changeCheck()"></td>
			  </tr>
			  <tr>
				<td style=" padding-bottom:20px;" colspan="2"><span id = "error_info" style="color:red"></span></td>
			    <td style=" padding-bottom:20px;" ><input name="" type="submit" value="登录" class="login_botton" ></td>
			  </tr>
			</table>
		</form>
     </div>
     <div style="width:262px; float:left; height:255px; overflow:hidden;"><img src="resource/images/login_mid3.jpg" width="262" height="255" /></div>
  </div>
  <div><img src="resource/images/login_03.jpg" width="1004" height="94" /></div>
  <div class="login_copyrightbg">copryright 2013 淅川西岛光电仪表科技有限公司</div>
</div>
<script>
	$("#loginname").focus();
	/* ===========================用户模块=================================*/
	//获取验证码
	function changeCheck(){
		$("#checkcode").val("");
		$("#checkcode").focus();
		$("#checkimage").attr("src","${path}/codeImg.do?ch="+Math.random());
	}
	//获取登录信息
	function submitInfo(){
		var loginname = $("#loginname").val();
		var loginkey = $("#loginkey").val();
		var checkcode = $("#checkcode").val();
		if(loginname == ""){
			$("#error_info").text("请输入用户名！");
			$("#loginname").focus();
			return false;
		}
		if(loginkey == ""){
			$("#error_info").text("请输入密码！");
			$("#loginkey").focus();
			return false;
		}
		if(checkcode.length != 4){
			$("#error_info").text("请输入验证码！");
			$("#checkcode").focus();
			changeCheck();
			return false;
		}
		
		$.ajax({
			type:"post",
			url:"${path}/login.do",
			//dataType:"xml",
			data:$("#loginForm").serialize(),
			success:function(xml){
				var result = $(xml).find("result").attr("msg");
				var error = $(xml).find("result").attr("error");
				if(result == "1"){
					window.location.href="remote/index.jsp";
				}else{
					changeCheck();
					if(error == 0){
						$("#error_info").text("请核对用户名密码!");
						$("#loginname").focus();
					}else{
						if(error == 1){
							$("#error_info").text("验证码错误");
							$("#checkcode").focus();
						}else{
							if(error == 2){
								window.location.href="login.jsp";
							}else{
								window.location.href="login.jsp";
							}
						}
					}
				}
			}
		});
		return false;
	}
	// 获取输入的更改信息
	function saveInfo(){
		var adname = $("#adname").val();
		var lgname = $("#lgname").val();
		var admobile = $("#admobile").val();
		var ademail = $("#ademail").val();
		var adaddress = $("#adaddress").val();
		
		if(adname == ""){
			$("#msg1").text("*");
			$("#adname").focus();
			return false;
		}
		if(lgname == ""){
			$("#msg2").text("*");
			$("#lgname").focus();
			return false;
		}
		
	/*	if(admobile == ""){
			$("#msg3").text("*");
			$("#admobile").focus();
		}else{
			var reg=/^[0-9\-\+\(\)]*$/;
			if(!reg.test(admobile)){
				$("#msg3").text("*");
				$("#admobile").val("");
				$("#admobile").focus();
				return false;
			}
		}
		
		if(ademail == ""){
			$("#msg4").text("*");
			$("#ademail").focus();
			return false;
		}else{
			var reg=/^[a-zA-Z]([a-zA-Z0-9]*[-_.]?[a-zA-Z0-9]+)+@([\w-]+\.)+[a-zA-Z]{2,}$/;
			if(!reg.test(ademail)){
				$("#msg4").text("*");
				$("#ademail").val("");
				$("#ademail").focus();
				return false;
			}
		}
		if(adaddress == ""){
			$("#msg5").text("*");
			$("#adaddress").focus();
			return false;
		}		*/
		$.ajax({
			type:"post",
			url:"../../remote/UpdataAdmin",
			dataType:"xml",
			data:{
				adname:adname,
				lgname:lgname,
				admobile:admobile,
				ademail:ademail,
				adaddress:adaddress	
			},
			success:function(xml){
				var result = $(xml).find("result").attr("msg");
				var error =$(xml).find("result").attr("error");
				if (error=="0"){
//					alert("登录名已注册！重新输入！");
					$("#error_info").text("登录名已注册！重新输入！");
		    		$("#error_dialog").dialog("open");
					$("#msg2").text("*");
					$("#lgname").val("");
					$("#lgname").focus();
					
				}else{
				       if(result == "1"){
//					       alert("修改成功！");
				    		$("#error_info").text("修改成功！");
				    		$("#error_dialog").dialog("open");
//				    		window.parent.location.reload();
				    		setTimeout("window.parent.location.href='../../remote/index.jsp'",1000);
//				    		return;
					       //window.location.reload();
					       //opener.location.reload();
					       //top.location.reload();
				       }
				}
			}
		}); 
	}
	// 确认返回
	/*function sure(){
		return parent.location.reload();
	}*/
	//获取输入密码
	function modpsd(){
		var oldpsd=$("#oldpsd").val();
		var newpsd=$("#newpsd").val();
		var rptpsd=$("#rptpsd").val();
		
		if (oldpsd==""){
			$("#msg1").text("*");
			$("#oldpsd").focus();
			return false;
		}
		if (newpsd==""){
			$("#msg2").text("*");
			$("#newpsd").focus();
			return false;
		}
		if(newpsd.length < 6){
			$("#error_info").text("密码必须大于6个字符！！！");
			$("#error_dialog").dialog("open");
			$("#newpsd").val("");
			$("#rptpsd").val("");
			$("#newpsd").focus();
			return false;
		}
		if (rptpsd==""){
			$("#msg3").text("*");
			$("#rptpsd").focus();
			return false;
		}
	/*	if($("#newpsd").val()!= $("#rptpsd").val()){			    	
//			alert("新密码输入不一致！请重新输入");
			$("#error_info").text("新密码输入不一致！请重新输入!");
			$("#error_dialog").dialog("open");
			
			$("#newpsd").val("");
			$("#rptpsd").val("");
			$("#newpsd").focus();
		}*/
		$.ajax({
			type:"post",
			url:"../../remote/UpdataKey",
			dataType:"xml",
			data:{
				oldpsd:oldpsd,
				newpsd:newpsd,
				rptpsd:rptpsd
			},
			success:function(xml){
				var result = $(xml).find("result").attr("msg");
				if(result=="0"){
					$("#error_info").text("原密码错误！重新输入！");
					$("#error_dialog").dialog("open");
//					alert("旧密码不正确！重新密码！");
					$("#oldpsd").val("");
					$("#newpsd").val("");
					$("#rptpsd").val("");
					$("#oldpsd").focus();
					
				}else{
					if(result=="1"){
//						alert("新旧密码不能一致！重新输入新密码！");
						$("#error_info").text("新旧密码不能一致！重新输入新密码！");
						$("#error_dialog").dialog("open");
						$("#newpsd").val("");
						$("#rptpsd").val("");
						$("#newpsd").focus();	
					}else{
						if(result=="2"){
//							alert("新密码不一致！重新输入！");
							$("#error_info").text("新密码输入不一致！请重新输入!");
							$("#error_dialog").dialog("open");
							$("#newpsd").val("");
							$("#rptpsd").val("");
							$("#newpsd").focus();	
						}else{
							if(result=="3"){
//								alert("修改成功！请重新登录！");		
								$("#error_info").text("修改成功！请重新登录！");
								$("#error_dialog").dialog("open");
								setTimeout("window.parent.location.href='../../login.jsp'",2000);
//								window.parent.location.href="http://localhost:8080/RemoteV2/login.jsp";
//								clearTimeout(t);
							}
						}
					}
				}
			}
		});
	}
	//获取增加信息
	function addInfo(){
		var adname = $("#adname").val();
		var lgname = $("#lgname").val();
		var admobile = $("#admobile").val();
		var ademail = $("#ademail").val();
		var adaddress = $("#adaddress").val();
		var authority = $("#authority option:selected").val();
		var dpid = $("#department option:selected").val();
		
		if(adname == ""){
			$("#msg1").text("*");
			$("#adname").focus();
			return false;
		}
		if(lgname == ""){
			$("#msg2").text("*");
			$("#lgname").focus();
			return false;
		}
		if(admobile == ""){
//			$("#msg3").text("*");
//			$("#admobile").focus();
		}else{
			var reg=/^[0-9\-\+\(\)]*$/;
			if(!reg.test(admobile)){
				$("#msg3").text("*");
				$("#admobile").val("");
				$("#admobile").focus();
				return false;
			}
		}
		
		if(ademail == ""){
//			$("#msg4").text("*");
//			$("#ademail").focus();
			/*return false;*/
		}else{
			var reg=/^[a-zA-Z0-9]([a-zA-Z0-9]*[-_.]?[a-zA-Z0-9]+)+@([\w-]+\.)+[a-zA-Z]{2,}$/;
			if(!reg.test(ademail)){
				$("#msg4").text("*");
				$("#ademail").val("");
				$("#ademail").focus();
				return false;
			}
		}
		if(adaddress == ""){
//			$("#msg5").text("*");
//			$("#adaddress").focus();
//			return false;
		}
		if(authority > 10){
			if(dpid == 0){
				$("#error_info").text("请选择片区！");
	    		$("#error_dialog").dialog("open");
	    		$("#department").focus();
	    		return false;
			}
		}
		$("#msg1").text("");
		$("#msg2").text("");
		$("#msg3").text("");
		$("#msg4").text("");
		$("#msg5").text("");
		$.ajax({
			type:"post",
			url:"../../remote/Add",
			dataType:"xml",
			data:{
				adname:adname,
				lgname:lgname,
				admobile:admobile,
				ademail:ademail,
				adaddress:adaddress,
				authority:authority,
				dpid:dpid
			},
			success:function(xml){
				var result = $(xml).find("result").attr("msg");
//				var error  =  $(xml).find("error").attr("error");
				if(result=="0"){
//					alert("登录名已注册！重新输入！");
					$("#error_info").text("登录名已注册！重新输入！");
		    		$("#error_dialog").dialog("open");
					$("#msg2").text("*");
					$("#lgname").val("");
					$("#lgname").focus();
					
				}else{
					if(result=="1"){
//						  alert("添加成功！");
							$("#error_info").text("添加成功！");
				    		$("#error_dialog").dialog("open");
							    $("#adname").val("");
							    $("#lgname").val("");
							    $("#admobile").val("");
							    $("#ademail").val("");
							    $("#adaddress").val("");
					
					}	
				}
			}
		}); 
	}
	/* ===========================end=================================*/

</script>
</body>
</html>
