<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加权限</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
$(function(){
	$('#authTree').tree({   
	    url:'${path}/sys/auth/tree.do',
	    checkbox:true,
	    animate:true
	});
	function submitForm(){
		if($('#addAuthForm').form('validate')){
			$('#addAuthForm').form('submit', {   
			    success: function(data){   
			       if(data=="succ"){
			    	   $.messager.alert('添加管理员','添加成功！','info',
							function(){
							 	$('#addAuthWin').window('close');
							 	$('#authListTab').datagrid('reload');
							 });
			       }
			    }   
			});  
		}
	}
	function clearForm(){
		$('#addAuthForm').form('clear');
		}
});
function getChecked(){
	var nodes = $('#authTree').tree('getChecked');
	var s = '';
	var ps = ''
	for(var i=0; i<nodes.length; i++){
		 var node = $('#authTree').tree('getParent', nodes[i].target);//获取父节点
		 var isleaf = $('#authTree').tree('isLeaf', nodes[i].target)
		 if(node!=null){
			 if(ps.indexOf(node.id) < 0 )
			 ps += node.id + ",";//父节点集合
		 }
		 if(isleaf){
			 s += nodes[i].id + ",";//子节点集合
		 }
	}
	alert("父节点"+ps);
	alert("子节点"+s);
}
</script>
<ul id="authTree"></ul>
<input type="button" value="获取选中" onclick="getChecked()"/>
		<%-- <div style="padding:10px 0 10px 60px">
	    <form id="addAuthForm" method="post" action="${path}/sys/auth/add.do">
	    	<input type="hidden" name="valid" value="1"/>
	    	<table>
	    		<tr>
	    			<td>权限编码：</td>
	    			<td><input class="easyui-textbox" type="text" name="authorityCode" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>操作路径：</td>
	    			<td> <input class="easyui-textbox" type="text" name="actUrl" />
	    			 </td>
	    		</tr>
	    		<tr>
	    			<td>备注：</td>
	    			<td><input class="easyui-textbox" type="text" name="remark" /></td>
	    		</tr>
	    		
	    		<tr>
	    			<td>父级权限:</td>
	    			<td>
	    				<select class="easyui-combobox" name="ppid">
	    					<option value="0">顶级</option>
	    					<c:forEach items="${auList}" var="au">
	    						<option value="${au.pid }">${au.remark }</option>
	    					</c:forEach>
	    				</select>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
	    </div> --%>
</body>
</html>