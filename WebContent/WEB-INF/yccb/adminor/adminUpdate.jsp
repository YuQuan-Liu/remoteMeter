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
	$('#updateForm').form('submit', { 
		onSubmit:function(){
			return $('#updateForm').form('validate');
		},
	    success: function(data){  
	    	if(data == "true"){
	    		$.messager.show({
					title:'修改信息',
					msg:'修改成功！',
					showType:'slide',
					timeout:3000
				});
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


$.extend($.fn.validatebox.defaults.rules, {
    equals: {
        validator: function(value,param){
            return value == $(param[0]).textbox("getValue");
        },
        message: '两次密码不相同.'
    }
});

function changepwd(){
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
						title:'修改密码',
						msg:'修改成功！',
						showType:'slide',
						timeout:3000
					});
				}else{
					$.messager.show({
						title:'修改密码',
						msg:'旧密码输入错误！',
						showType:'slide',
						timeout:3000
					});
				}
			}
		});
	}
}
function resetpwd(){
	
	var pid = $("#adminid").val();
	
	$.messager.confirm('提示', '确定要重置吗？', function(r){
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
							title:"重置密码",
							msg:"重置成功",
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
			title:'修改权限',
			msg:'请选择权限！',
			showType:'slide',
			timeout:3000
		});
		return;
	}
	$.messager.confirm('提示', '确定要更改角色吗？', function(r){
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
							title:"更改角色",
							msg:"更改成功",
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
			title:'修改片区',
			msg:'请选择片区！',
			showType:'slide',
			timeout:3000
		});
		return;
	}
	$.messager.confirm('提示', '确定要更改片区吗？', function(r){
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
							title:"更改片区",
							msg:"更改成功",
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
	    			<td>用户名：</td>
	    			<td><input class="easyui-textbox" type="text" name="adminName" value="${admin.adminName}" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>登录名：</td>
	    			<td><input class="easyui-textbox" type="text" name="loginName" id="loginName" value="${admin.loginName }"
	    			data-options="required:true,novalidate:true,onChange:checkLoginName,invalidMessage:'登录名已存在！'" validType="nonValidate[]"></input></td>
	    		</tr>
	    		<tr>
	    			<td>手机：</td>
	    			<td><input class="easyui-numberbox" type="text" name="adminMobile" value="${admin.adminMobile }"></input></td>
	    		</tr>
	    		<tr>
	    			<td>固话：</td>
	    			<td><input class="easyui-textbox" type="text" name="adminTel" value="${admin.adminTel }"></input></td>
	    		</tr>
	    		<tr>
	    			<td>邮箱：</td>
	    			<td><input class="easyui-textbox" type="text" name="adminEmail" value="${admin.adminEmail }"></input></td>
	    		</tr>
	    		<tr>
	    			<td>地址：</td>
	    			<td><input class="easyui-textbox" type="text" name="adminAddr" value="${admin.adminAddr }"></input></td>
	    		</tr>
	    		<tr>
	    			<td>权限角色:</td>
	    			<td><input class="easyui-textbox" type="text" name="roleName" data-options="disabled:true" value="${admin.roleName }"></input></td>
	    		</tr>
	    		<tr>
	    			<td>管辖片区:</td>
	    			<td><input class="easyui-textbox" type="text" name="roleName" data-options="disabled:true" value="${admin.depName }"></input></td>
	    		</tr>
	    		<tr>
	    			<td>人员类型:</td>
	    			<td><input class="easyui-textbox" type="text" name="nowc" data-options="disabled:true"  value="<c:if test="${admin.nowc ==0}">自来水</c:if><c:if test="${admin.nowc ==1}">物业</c:if> "></input></td>
	    		</tr>
	    	</table>
	    </form>
	</div>

	<div style="text-align:center;padding:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
	</div>
	
	<div style="padding:10px;margin-top:10px;">
			<form id="changePwd" method="post" action="${path}/admin/admin/changepwd.do">
			<table style="margin:0px auto;">
				<tr>
	    			<td>旧密码：</td>
	    			<td><input class="easyui-textbox" type="password" name="old" id="old" data-options="required:true" validType="length[6,10]"/></td>
	    		</tr>
				<tr>
	    			<td>新密码：</td>
	    			<td><input class="easyui-textbox" type="password" name="new1" id="p1" data-options="required:true" validType="length[6,10]"/></td>
	    		</tr>
	    		<tr>
	    			<td>重复密码：</td>
	    			<td><input class="easyui-textbox" type="password" name="new2" id="p2" data-options="required:true" validType="equals['#p1']"/></td>
	    		</tr>
				<tr>
					<td colspan="2"style="text-align:center">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="changepwd()">修改密码</a>
					</td>
				</tr>
			</table>
			</form>
		</div>
		
		<div style="padding:10px;margin-top:10px;">
			<table style="margin:0px auto;">
				<tr>
	    			<td>权限角色:</td>
	    			<td>
	    				<select class="easyui-combobox" id="roleid" name="roleid" data-options="panelHeight:'auto',required:true" style="width:100px;">
							<option value="">请选择权限</option>
							<c:forEach var="r" items="${role_list }">
							<option value="${r.pid }">${r.roleName }</option>
							</c:forEach>
						</select>
	    			</td>
	    		</tr>
				<tr>
	    			<td>管辖片区:</td>
	    			<td>
	    				<select class="easyui-combobox" id="depid" name="department.pid" data-options="anelHeight:'auto',required:true" style="width:100px;">
							<option value="">请选择片区</option>
							<c:forEach var="d" items="${dep_list }">
							<option value="${d.pid }">${d.departmentName }</option>
							</c:forEach>
						</select>
	    			</td>
	    		</tr>
				<tr>
					<td colspan="2"style="text-align:center">
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="resetpwd()">重置密码</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="changerole()">修改权限</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="changedep()">修改片区</a>
					</td>
				</tr>
			</table>
		</div>
</body>
</html>