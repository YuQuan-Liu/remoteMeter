<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加片区</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
$(function(){
	$('#neighborCombo').combo({   
	    required:true,   
	    url:'/admin/dep/nbrlistContent.do',
		method:'post',
		valueField:'id',
		textField:'text',
		multiple:true,
		panelHeight:'auto'
	});  
})
function submitForm(){
	if($('#depAddForm').form('validate')){
		$('#depAddForm').form('submit', {   
		    success: function(data){   
		       if(data=="succ"){
		    	   $.messager.alert('添加管理员','添加成功！','info',
						function(){
						 	$('#watComAddWin').window('close');
						 	$('#watComListTab').datagrid('reload');
						 });
		       }
		    }   
		});  
	}
}
function clearForm(){
	$('#depAddForm').form('clear');
	}
</script>
<form id="depAddForm" method="post" action="${path}/admin/dep/add.do">
		<div style="padding:10px 0 10px 60px">
	    	<input type="hidden" name="valid" value="1"/>
	    	<table>
	    		<tr>
	    			<td>片区名：</td>
	    			<td><input class="easyui-textbox" type="text" name="priceKindName" data-options="required:true"/></td>
	    			<td>备注：</td>
	    			<td>
	    			<input class="easyui-textbox" name="remark" type="text"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>自来水小的小区：</td>
	    			<td><input id="neighborCombo"></td>
	    		</tr>
	    	</table>
	    </div>
 </form>	    
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
	    </div>
</body>
</html>