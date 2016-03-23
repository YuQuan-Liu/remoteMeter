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
	$('#updateadminbtn').linkbutton('disable');
	$('#updateForm').form('submit', { 
		onSubmit:function(){
			return $('#updateForm').form('validate');
		},
	    success: function(data){  
	    	if(data == "true"){
	    		$.messager.show({
					title:'Info',
					msg:'<fmt:message key='common.updateok'/>',
					showType:'slide',
					timeout:3000
				});
	    	}
	    }
	}); 
	$('#updateadminbtn').linkbutton('enable');
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


$.extend($.fn.validatebox.defaults.rules, {
    equals: {
        validator: function(value,param){
            return value == $(param[0]).textbox("getValue");
        },
        message: '<fmt:message key='admin.pwdnotequal'/>'
    }
});

function changepwd(){
	$('#changepwdbtn').linkbutton('disable');
	var old_ = $("#old").textbox("getValue");
	var new_ = $("#p1").textbox("getValue");
	var pid = $("#adminid").val();
	
	if($('#changePwd').form('validate')){
		$.ajax({
			type:"POST",
			url:"${path}/admin/admin/changepwd.do",
			data:{
				old_:old_,
				new_:new_,
				pid:pid
			},
			dataType:"json",
			success:function(data){
				if(data == 'true'){
					$.messager.show({
						title:'Info',
						msg:'<fmt:message key='common.updateok'/>',
						showType:'slide',
						timeout:3000
					});
				}else{
					$.messager.show({
						title:'Info',
						msg:'<fmt:message key='admin.oldpwderror'/>',
						showType:'slide',
						timeout:3000
					});
				}
			}
		});
	}
	$('#changepwdbtn').linkbutton('enable');
}
function resetpwd(){
	
	var pid = $("#adminid").val();
	
	$.messager.confirm('Info', '<fmt:message key='admin.confirmresetpwd'/>', function(r){
		if(r){
			$.ajax({
				url:'${path}/admin/admin/resetpwd.do',
				type:'post',
				data:{
					pid:pid
				},
				success:function(data){
					if(data=="true"){
						$.messager.show({
							title:"Info",
							msg:"<fmt:message key='common.resetok'/>",
							showType:'slide'
						});
					}
				}
			});	
		}
	});
}

function changerole(){
	var rid = $("#roleid").combobox("getValue");
	var pid = $("#adminid").val();
	if(rid == ""){
		$.messager.show({
			title:'Info',
			msg:'<fmt:message key='admin.chooserole'/>',
			showType:'slide',
			timeout:3000
		});
		return;
	}
	$.messager.confirm('Info', '<fmt:message key='common.confirmupdate'/><fmt:message key='role'/>', function(r){
		if(r){
			$.ajax({
				url:'${path}/admin/admin/changerole.do',
				type:'post',
				data:{
					pid:pid,
					rid:rid
				},
				success:function(data){
					if(data=="true"){
						$.messager.show({
							title:"Info",
							msg:"<fmt:message key='common.updateok'/>",
							showType:'slide'
						});
					}
				}
			});	
		}
	});
}

function changedep(){
	var did = $("#depid").combobox("getValue");
	var pid = $("#adminid").val();
	if(did == ""){
		$.messager.show({
			title:'Info',
			msg:'<fmt:message key='admin.choosedep'/>',
			showType:'slide',
			timeout:3000
		});
		return;
	}
	$.messager.confirm('Info', '<fmt:message key='common.confirmupdate'/><fmt:message key='areas'/>', function(r){
		if(r){
			$.ajax({
				url:'${path}/admin/admin/changedep.do',
				type:'post',
				data:{
					pid:pid,
					did:did
				},
				success:function(data){
					if(data=="true"){
						$.messager.show({
							title:"Info",
							msg:"<fmt:message key='common.updateok'/>",
							showType:'slide'
						});
					}
				}
			});	
		}
	});
}
</script>
	<div style="padding:10px;">
	    <form id="updateForm" method="post" action="${path}/admin/admin/update.do">
	    	<input type="hidden" name="valid" value="1"/>
	    	<input type="hidden" name="watercompany.pid" value="${wcid}"/>
	    	<input type="hidden" name="pid" id="adminid" value="${admin.pid }"/>
	    	<table style="margin:0px auto;">
	    		<tr>
	    			<td><fmt:message key='common.username'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="adminName" value="${admin.adminName}" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='admin.loginName'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="loginName" id="loginName" value="${admin.loginName }"
	    			data-options="required:true,novalidate:true,onChange:checkLoginName,invalidMessage:'<fmt:message key='admin.loginNameexit'/>'" validType="nonValidate[]"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='common.mobile'/>：</td>
	    			<td><input class="easyui-numberbox" type="text" name="adminMobile" value="${admin.adminMobile }"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='common.tel'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="adminTel" value="${admin.adminTel }"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='common.email'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="adminEmail" value="${admin.adminEmail }"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='common.addr'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="adminAddr" value="${admin.adminAddr }"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='admin.role'/>:</td>
	    			<td><input class="easyui-textbox" type="text" name="roleName" data-options="disabled:true" value="${admin.roleName }"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='areas'/>:</td>
	    			<td><input class="easyui-textbox" type="text" name="roleName" data-options="disabled:true" value="${admin.depName }"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='admin.admintype'/>:</td>
	    			<td><input class="easyui-textbox" type="text" name="nowc" data-options="disabled:true"  value="<c:if test="${admin.nowc ==0}"><fmt:message key='watcom'/></c:if><c:if test="${admin.nowc ==1}"><fmt:message key='common.property'/></c:if> "></input></td>
	    		</tr>
	    	</table>
	    </form>
	</div>

	<div style="text-align:center;padding:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" id="updateadminbtn"><fmt:message key='common.submit'/></a>
	</div>
	
	<div style="padding:10px;margin-top:10px;">
			<form id="changePwd" method="post" action="${path}/admin/admin/changepwd.do">
			<table style="margin:0px auto;">
				<tr>
	    			<td><fmt:message key='admin.oldpwd'/>：</td>
	    			<td><input class="easyui-textbox" type="password" name="old" id="old" data-options="required:true" validType="length[6,10]"/></td>
	    		</tr>
				<tr>
	    			<td><fmt:message key='admin.newpwd'/>：</td>
	    			<td><input class="easyui-textbox" type="password" name="new1" id="p1" data-options="required:true" validType="length[6,10]"/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='admin.againpwd'/>：</td>
	    			<td><input class="easyui-textbox" type="password" name="new2" id="p2" data-options="required:true" validType="equals['#p1']"/></td>
	    		</tr>
				<tr>
					<td colspan="2"style="text-align:center">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="changepwd()" id="changepwdbtn"><fmt:message key='admin.updatepwd'/></a>
					</td>
				</tr>
			</table>
			</form>
		</div>
		
		<div style="padding:10px;margin-top:10px;">
			<table style="margin:0px auto;">
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
					<td colspan="2"style="text-align:center">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="resetpwd()" id="resetpwdbtn"><fmt:message key='admin.resetpwd'/></a>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="changerole()" id="changerolebtn"><fmt:message key='admin.changerole'/></a>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="changedep()" id="changedepbtn"><fmt:message key='admin.changedep'/></a>
					</td>
				</tr>
			</table>
		</div>
</body>
</html>