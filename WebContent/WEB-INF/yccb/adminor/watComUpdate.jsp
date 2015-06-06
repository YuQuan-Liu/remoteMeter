<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新自来水公司</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
$(function(){
	
})
function submitForm(){
	$('#watUpdateForm').form('submit', {
		onSubmit:function(){
			$('#watUpdateForm').form('validate');
		},
	    success: function(data){   
	       if(data=="succ"){
	    	   $('#watComUpdateWin').window('close');
	    	   $.messager.show({
					title:'更新自来水公司',
					msg:'更新成功！',
					showType:'slide',
					timeout:3000
				});
			 	$('#watComListTab').datagrid('reload');
	       }
	    }   
	});  	
	
}
</script>
		<div style="padding:10px 0 10px 60px">
	    <form id="watUpdateForm" method="post" action="${path}/admin/watcom/update.do">
	    	<input type="hidden" name="valid" value="1"/>
	    	<input type="hidden" name="pid" value="${watcom.pid }"/>
	    	<table>
	    		<tr>
	    			<td>自来水公司名：</td>
	    			<td><input class="easyui-textbox" type="text" name="companyName" data-options="required:true" value="${watcom.companyName }"/></td>
	    		</tr>
	    		<tr>
	    			<td>自来水公司地址：</td>
	    			<td> <input class="easyui-textbox" type="text" name="companyAddr" data-options="required:true" value="${watcom.companyAddr }"/></td>
	    		</tr>
	    		<tr>
	    			<td>自来水公司标识：</td>
	    			<td><input class="easyui-textbox" type="text" name="mark" data-options="required:true" value="${watcom.mark }"/></td>
	    		</tr>
	    		<tr>
	    			<td>邮箱主机：</td>
	    			<td><input class="easyui-textbox" type="text" name="emailHost" data-options="required:true" value="${watcom.emailHost }"/></td>
	    		</tr>
	    		<tr>
	    			<td>邮箱用户名：</td>
	    			<td><input class="easyui-textbox" type="text" name="emailUser" data-options="required:true" value="${watcom.emailUser }"/></td>
	    		</tr>
	    		<tr>
	    			<td>交费地址：</td>
	    			<td><input class="easyui-textbox" type="text" name="payAddr" data-options="required:true" value="${watcom.payAddr }"/></td>
	    		</tr>
	    		<tr>
	    			<td>查询电话：</td>
	    			<td><input class="easyui-textbox" type="text" name="telephone" data-options="required:true" value="${watcom.telephone }"/></td>
	    		</tr>
	    		<tr>
	    			<td>备注：</td>
	    			<td>
	    			<input class="easyui-textbox" name="remark" data-options="multiline:true" style="height:60px" value="${watcom.remark }">
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
	    </div>
</body>
</html>