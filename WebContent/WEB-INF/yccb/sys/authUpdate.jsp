<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改权限</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
$(function(){
	
})
function submitForm(){
	if($('#updateAuthForm').form('validate')){
		$('#updateAuthForm').form('submit', {   
		    success: function(data){   
		       if(data=="succ"){
		    	   $('#updateAuthWin').window('close');
		    	   $.messager.show({
						title:'修改权限',
						msg:'修改成功！',
						showType:'slide',
						timeout:3000
					});
		    	   $('#authListTab').datagrid('reload');
		       }
		    }   
		});  
	}
}
function clearForm(){
	$('#updateAuthForm').form('clear');
	}
</script>
		<div style="padding:10px 0 10px 60px">
	    <form id="updateAuthForm" method="post" action="${path}/sys/auth/update.do">
	    	<input type="hidden" name="valid" value="${auth.valid}"/>
	    	<input type="hidden" name="pid" value="${auth.pid}"/>
	    	<table>
	    		<tr>
	    			<td>权限编码：</td>
	    			<td><input class="easyui-textbox" type="text" name="authorityCode" data-options="required:true" value="${auth.authorityCode}"/></td>
	    		</tr>
	    		<tr>
	    			<td>操作路径：</td>
	    			<td> <input class="easyui-textbox" type="text" name="actUrl" value="${auth.actUrl}"/>
	    			 </td>
	    		</tr>
	    		<tr>
	    			<td>备注：</td>
	    			<td><input class="easyui-textbox" type="text" name="remark" value="${auth.remark}"/></td>
	    		</tr>
	    		
	    		<tr>
	    			<td>父级权限:</td>
	    			<td>
	    				<select class="easyui-combobox" name="ppid" data-options="panelHeight:'auto'">
	    					<option value="0">顶级</option>
	    					<c:forEach items="${auList}" var="au">
	    						<option value="${au.pid}" 
	    						<c:if test="${au.pid==auth.ppid}">selected="selected"</c:if> >${au.remark }</option>
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