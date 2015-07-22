<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加管理员</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">

function submitForm(){
	var did = $("#depid").combobox("getValue");
	var rid = $("#roleid").combobox("getValue");
	if(did == ""){
		$.messager.show({
			title:'Info',
			msg:'<fmt:message key='admin.choosedep'/>',
			showType:'slide',
			timeout:3000
		});
		return;
	}
	if(rid == ""){
		$.messager.show({
			title:'Info',
			msg:'<fmt:message key='admin.chooserole'/>',
			showType:'slide',
			timeout:3000
		});
		return;
	}
	$('#addForm').form('submit', { 
		onSubmit:function(){
			return $('#addForm').form('validate');
		},
	    success: function(data){   
	       if(data=="succ"){
	    		$('#addWin').window('close');
			 	$('#adminListTab').datagrid('reload');
	       }
	    }   
	}); 
}

$.extend($.fn.validatebox.defaults.rules, {
	nonValidate: {
      validator: function(value, param){
          return false;
      }
  }
});

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
		<div style="padding:10px;">
	    <form id="addForm" method="post" action="${path}/admin/admin/add.do">
	    	<input type="hidden" name="valid" value="1"/>
	    	<input type="hidden" name="watercompany.pid" value="${wcid}"/>
	    	<table style="margin:0px auto;">
	    		<tr>
	    			<td><fmt:message key='common.username'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="adminName" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='admin.loginName'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="loginName" id="loginName" 
	    			data-options="required:true,novalidate:true,onChange:checkLoginName,invalidMessage:'<fmt:message key='admin.loginNameexit'/>'" validType="nonValidate[]"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='common.mobile'/>：</td>
	    			<td><input class="easyui-numberbox" type="text" name="adminMobile"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='common.tel'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="adminTel"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='common.email'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="adminEmail"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='common.addr'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="adminAddr"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='admin.role'/>:</td>
	    			<td>
	    				<select class="easyui-combobox" id="roleid" name="roleid" data-options="panelHeight:'200',required:true" style="width:100px;">
							<option value=""><fmt:message key='admin.chooserole'/></option>
							<c:forEach var="r" items="${role_list }">
							<option value="${r.pid }">${r.roleName }</option>
							</c:forEach>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='areas'/>:</td>
	    			<td>
	    				<select class="easyui-combobox" id="depid" name="department.pid" data-options="panelHeight:'200',required:true" style="width:100px;">
							<option value=""><fmt:message key='admin.choosedep'/></option>
							<c:forEach var="d" items="${dep_list }">
							<option value="${d.pid }">${d.departmentName }</option>
							</c:forEach>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='admin.admintype'/>:</td>
	    			<td>
	    				<select class="easyui-combobox" id="nowc" name="nowc" data-options="panelHeight:'auto',required:true" style="">
							<option value="0"><fmt:message key='watcom'/></option>
							<option value="1"><fmt:message key='common.property'/></option>
						</select>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()"><fmt:message key='common.submit'/></a>
	    </div>
</body>
</html>