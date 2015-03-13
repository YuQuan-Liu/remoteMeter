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
$(function(){
	/* $('#loginName').validatebox({   
	    required: true,   
	    validType: "remote['${path}/admin/check.do','loginName']"  
	});   */
})
function submitForm(){
	if($('#updateForm').form('validate')){
		$('#updateForm').form('submit', {   
		    success: function(data){   
		       // var data = eval('(' + data + ')');  
		        $.messager.alert('修改管理员','修改成功！','info',
						function(){
						 	$('#updateWin').window('close');
						 	$('#adminListTab').datagrid('reload');
						 });
		       /*  if (data.success){ 
		            alert(data.message)   
		        }  */  
		    }   
		});  
	}
}
function clearForm(){
	$('#updateForm').form('clear');
	}
</script>
		<div style="padding:10px 0 10px 60px">
	    <form id="updateForm" method="post" action="${path}/admin/update.do">
	    	<input type="hidden" name="pid" value="${adInfo.pid }"/>
	    	<input type="hidden" name="valid" value="1"/>
	    	<table>
	    		<tr>
	    			<td>用户名：</td>
	    			<td><input class="easyui-validatebox" type="text" name="adminName" data-options="required:true" value="${adInfo.adminName}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>登录名：</td>
	    			<td> <input class="easyui-validatebox" id="loginName" name="loginName" value="${adInfo.loginName }" readonly="readonly"/></td>
	    		</tr>
	    		<tr>
	    			<td>手机：</td>
	    			<td><input class="easyui-validatebox" type="text" name="adminMobile" data-options="required:true" value="${adInfo.adminMobile }"></input></td>
	    		</tr>
	    		<tr>
	    			<td>固话：</td>
	    			<td><input class="easyui-validatebox" type="text" name="adminTel" data-options="required:true" value="${adInfo.adminTel }"></input></td>
	    		</tr>
	    		<tr>
	    			<td>邮箱：</td>
	    			<td><input class="easyui-validatebox" type="text" name="adminEmail" data-options="required:true,validType:'email'" value="${adInfo.adminEmail }"></input></td>
	    		</tr>
	    		<tr>
	    			<td>地址：</td>
	    			<td><input class="easyui-validatebox" type="text" name="adminAddr" data-options="required:true" value="${adInfo.adminAddr }"></input></td>
	    		</tr>
	    		<tr>
	    			<td>权限类型:</td>
	    			<td>
	    				<select class="easyui-combobox" name="language">
	    					<option value="1">Arabic</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>管辖片区:</td>
	    			<td>
	    				<select class="easyui-combobox" name="department.pid">
	    					<option value="1">测试</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>自来水公司:</td>
	    			<td>
	    				<select class="easyui-combobox" name="watercompany.pid">
	    					<option value="1">测试</option>
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