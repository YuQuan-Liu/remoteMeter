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
	
})
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
</script>
		<div style="padding:10px 0 10px 60px">
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
	    </div>
</body>
</html>