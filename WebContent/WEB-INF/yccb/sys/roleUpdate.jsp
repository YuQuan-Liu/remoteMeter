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
}
function submitForm(){
	$.messager.confirm('Info', '<fmt:message key='common.confirmupdate'/>？', function(r){
		if(r){
			getChecked();
			var childauth = $("#childauth").val();
			var role_pid = $("#role_pid").val();
			$.ajax({
				url:'${path}/sys/role/update.do',
				type:'post',
				data:{
					pid:role_pid,
					childauth:childauth
				},
				success:function(data){
					if(data=="true"){
						$.messager.show({
							title:"Info",
							msg:"<fmt:message key='common.updateok'/>",
							showType:'slide'
						});
						$('#authTree').tree('reload');
					}
				}
			});	
		}
	});
}
</script>
<form action="${path}/sys/role/update.do" id="updateRoleForm" method="post">
<input type="hidden" name="childauth" id="childauth">
<input type="hidden" name="pid" id="role_pid" value="${role.pid}">
<div style="padding: 10px">
	<table style="margin:0px auto;">
		<tr>
			<td><fmt:message key='role.name'/>：</td>
    		<td><input class="easyui-textbox" type="text" name="roleName" id="roleName" data-options="disabled:true" value="${role.roleName}" /></td>
			
			<td><fmt:message key='common.remark'/>：</td>
			<td><input class="easyui-textbox" name="remark" id="remark" data-options="disabled:true" value="${role.remark}" />
			</td>
		</tr>
	</table>
</div>
</form>
<c:if test="${role.pid != 1}">
<c:if test="${role.systemRole == '0'}">
	<div style="text-align: center; padding: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()"><fmt:message key='common.update'/></a>
	</div>
</c:if>
</c:if>
<div class="easyui-panel" title="<fmt:message key='role.choose'/>" style="padding:5px;height:250px;">
	<ul id="authTree" class="easyui-tree" data-options="url:'${path}/sys/role/tree_detail.do?pid=${role.pid }',method:'get',animate:true,checkbox:true"></ul>
</div>

</body>
</html>