<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加角色</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
function getChecked(){
	var nodes = $('#authTree').tree('getChecked');
	var s = '';
	if(nodes.length == 0){
		return false;
	}
	for(var i=0; i<nodes.length; i++){
		 var isleaf = $('#authTree').tree('isLeaf', nodes[i].target);//是否子节点
		 if(isleaf){
			 s += nodes[i].id;//子节点集合
			 if(i != nodes.length-1){
				 s += ",";
			 }
		 }
	}
	$("#childauth").val(s);
	return true;
}
function submitForm(){
	$('#addrolebtn').linkbutton('disable');
	if(!getChecked()){
		$.messager.show({
			title:'Info',
			msg:'<fmt:message key='common.addok'/>',
			showType:'slide',
			timeout:3000
		});
		return;
	}
	//获取树节点
	if($('#roleAddForm').form('validate')){
		var roleName = $("#roleName").textbox("getValue");
		var remark = $("#remark").textbox("getValue");
		var childauth = $("#childauth").val();
		$.ajax({
			type:"POST",
			url:"${path}/sys/role/add.do",
			dataType:"json",
			data:{
				roleName:roleName,
				remark:remark,
				childauth:childauth
			},
			success:function(data){
				if(data == "succ"){
					$.messager.show({
						title:'Info',
						msg:'添加成功！',
						showType:'slide',
						timeout:3000
					});
		    	   	$('#addRoleWin').window('close');
				 	$('#roleListTab').datagrid('reload');
				}
				$('#addrolebtn').linkbutton('enable');
			}
		});
	}
	
}
function checkRoleName(){
	var name = $("#roleName").textbox("getValue");
	
	$.ajax({
		type:"POST",
		url:"${path}/sys/role/checkname.do",
		data:{
			name:name
		},
		dataType:"json",
		success:function(data){
			if(data == 'true'){
				$("#roleName").textbox("enableValidation");
			}else{
				$("#roleName").textbox("disableValidation");
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
</script>
<form id="roleAddForm" method="post" action="${path}/sys/role/add.do">
	<input type="hidden" name="childauth" id="childauth">
	<div style="padding: 10px">
		<table style="margin:0px auto;">
			<tr>
				<td><fmt:message key='role.name'/>：</td>
	    		<td><input class="easyui-textbox" type="text" name="roleName" id="roleName" 
	    		data-options="required:true,novalidate:true,onChange:checkRoleName,invalidMessage:'<fmt:message key='role.exist'/>'" validType="nonValidate[]" /></td>
				
				<td><fmt:message key='common.remark'/>：</td>
				<td><input class="easyui-textbox" name="remark" id="remark" type="text" />
				</td>
			</tr>
		</table>
	</div>
</form>
<div style="text-align: center; padding: 5px">
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" id="addrolebtn"><fmt:message key='common.submit'/></a>
</div>
<div class="easyui-panel" title="<fmt:message key='role.choose'/>" style="padding:5px;height:250px;">
	<ul id="authTree" class="easyui-tree" data-options="url:'${path}/sys/role/tree.do',method:'get',animate:true,checkbox:true"></ul>
</div>
</body>
</html>