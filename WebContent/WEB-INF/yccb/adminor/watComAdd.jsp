<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加自来水公司</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
$.extend($.fn.validatebox.defaults.rules, {
    equals: {
        validator: function(value,param){
            return value == $(param[0]).val();
        },
        message: '<fmt:message key='admin.pwdnotequal'/>'
    }
});

$.extend($.fn.validatebox.defaults.rules, {
	nonValidate: {
      validator: function(value, param){
          return false;
      }
  }
});
function submitForm(){
	$('#addwcbtn').linkbutton('disable');
	$('#watAddForm').form('submit', {
		onSubmit: function(){
			var isValid = $('#watAddForm').form('validate');
			return isValid;	// return false will stop the form submission
		},
	    success: function(data){   
	       if(data=="succ"){
	    	   $('#watComAddWin').window('close');
	    	   $.messager.show({
					title:'Info',
					msg:'<fmt:message key='common.addok'/>',
					showType:'slide',
					timeout:3000
				});
			 	$('#watComListTab').datagrid('reload');
	       }
	    }   
	});	  
	$('#addwcbtn').linkbutton('enable');
}

//检查登录名是否已经存在
function checkLoginName(){
	var name = $("#loginName").textbox("getValue");
	
	$.ajax({
		type:"POST",
		url:"${path}/admin/admin/checkloginname.do",
		data:{
			name:name
		},
		dataType:"json",
		success:function(data){
			if(data == 'true'){
				$("#loginName").textbox("enableValidation");
			}else{
				$("#loginName").textbox("disableValidation");
			}
		}
	});
}
</script>
		<div style="padding:10px 0 ;">
	    <form id="watAddForm" method="post" action="${path}/admin/watcom/add.do">
	    	<input type="hidden" name="valid" value="1"/>
	    	<table style="margin:0px auto;">
	    		<tr>
	    			<td><fmt:message key='watcom.name'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="companyName" data-options="required:true" missingMessage="请输入自来水公司" invalidMessage=""/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='watcom.addr'/>：</td>
	    			<td> <input class="easyui-textbox" type="text" name="companyAddr" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='watcom.mark'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="mark" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='watcom.emailHost'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="emailHost" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='watcom.emailUser'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="emailUser" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='watcom.pwd'/>：</td>
	    			<td><input class="easyui-textbox" type="password" name="emailPassword" id="p1" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='admin.againpwd'/>：</td>
	    			<td><input class="easyui-textbox" type="password" name="emailPassword2" id="p2" data-options="required:true" validType="equals['#p1']"/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='watcom.payaddr'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="payAddr" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='watcom.tel'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="telephone" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='common.remark'/>：</td>
	    			<td>
	    			<input class="easyui-textbox" name="remark" data-options="multiline:true" style="height:60px">
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><fmt:message key='watcom.adminname'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="adminName" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='watcom.loginName'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="loginName" id="loginName" 
	    			data-options="required:true,novalidate:true,onChange:checkLoginName,invalidMessage:'<fmt:message key='admin.loginNameexit'/>'" validType="nonValidate[]"/></td>
	    		</tr>
	    		
	    	</table>
	    </form>
	    </div>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" id="addwcbtn"><fmt:message key='common.submit'/></a>
	    </div>
</body>
</html>