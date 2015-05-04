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
	if($('#watUpdateForm').form('validate')){
		$('#watUpdateForm').form('submit', {   
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
		    	  /*  $.messager.alert('添加管理员','添加成功！','info',
						function(){
						 	$('#watComAddWin').window('close');
						 	$('#watComListTab').datagrid('reload');
						 }); */
		       }
		    }   
		});  
	}
}
function clearForm(){
	$('#watUpdateForm').form('clear');
	}
</script>
		<div style="padding:10px 0 10px 60px">
	    <form id="watUpdateForm" method="post" action="${path}/admin/watcom/add.do">
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
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
	    </div>
</body>
</html>