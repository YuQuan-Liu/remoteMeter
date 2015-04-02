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
$(function(){
	$('#authTree').tree({   
	    url:'${path}/sys/auth/tree.do?roleId=${role.pid}',
	    checkbox:true,
	    animate:true,
	    //loadMsg:'Loading...',
	    onLoadSuccess:function(node, data){
	    	getChecked();
	    }
	});
});
function getChecked(){
	var nodes = $('#authTree').tree('getChecked');
	var s = '';
	var ps = ''
	for(var i=0; i<nodes.length; i++){
		 var node = $('#authTree').tree('getParent', nodes[i].target);//获取父节点
		 var isleaf = $('#authTree').tree('isLeaf', nodes[i].target);//是否子节点
		 if(node!=null){
			 if(ps.indexOf(node.id) < 0 )
			 ps += node.id + ",";//父节点集合
		 }
		 if(isleaf){
			 s += nodes[i].id + ",";//子节点集合
		 }
	}
	$("#childauth").val(s);
	$("#parentauth").val(ps);
}
function submitForm(){
	if($('#updateRoleForm').form('validate')){
		getChecked();//获取树节点
		$('#updateRoleForm').form('submit', {   
		    success: function(data){   
		       if(data=="succ"){
		    	   $.messager.alert('添加角色','添加成功！','info',
						function(){
						 	$('#updateRoleWin').window('close');
						 	$('#roleListTab').datagrid('reload');
						 });
		       }
		    }   
		});  
	}
}
function clearForm(){
	$('#updateRoleForm').form('clear');
	$("#childauth").val('');
	$("#parentauth").val('');
}
</script>
<form action="${path}/sys/role/update.do" id="updateRoleForm" method="post">
<input type="hidden" name="childauth" id="childauth">
<input type="hidden" name="parentauth" id="parentauth">
<input type="hidden" name="pid" value="${role.pid}">
 <div class="easyui-layout" style="width:450px;height:250px;">
		<div data-options="region:'west',split:true" title="角色信息" style="width:300px;">
			<table>
	    		<tr>
	    			<td>角色名称：</td>
	    			<td><input class="easyui-textbox" type="text" name="roleName" data-options="required:true" value="${role.roleName}"/></td>
	    		</tr>
	    		<tr>
	    			<td>自来水公司：</td>
	    			<td>
	    			<select class="easyui-combobox" name="wcid" data-options="panelHeight:'auto'">
	    					<option value="1">测试</option>
	    			</select>
	    			 </td>
	    		</tr>
	    		<tr>
	    			<td>系统角色:</td>
	    			<td>
	    				<select class="easyui-combobox" name="systemRole" data-options="panelHeight:'auto'">
	    					<option value="1">是</option>
	    					<option value="0">否</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>备注：</td>
	    			<td><!-- <input class="easyui-textbox" type="text" name="remark" /> -->
	    			<textarea name="remark" style="height:60px;">${role.remark}</textarea>
	    			
	    			</td>
	    		</tr>
	    	</table>
		</div>
		<div data-options="region:'center',split:true" title="选择权限" style="width:200px;">
			<ul id="authTree"></ul>
		</div>
		<div data-options="region:'south',split:true" style="width:500px;height: 50px;padding: 5px;text-align:center">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清空</a>
		</div>
	</div>
	</form>
</body>
</html>