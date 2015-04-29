<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加集中器</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
$(function(){
});
function submitForm(){
	if($('#addGprsForm').form('validate')){
		$('#addGprsForm').form('submit', {   
		    success: function(data){   
		       if(data=="succ"){
		    	   $.messager.alert('添加集中器','添加成功！','info',
						function(){
		    		   		$('#addGprsWin').window('close');
						 	$('#neighborListTab').datagrid('reload');
						 });
		       }
		    }   
		});  
	}
}
function clearForm(){
	$('#addGprsForm').form('clear');
}
</script>
<form action="${path}/infoin/neighbor/addGprs.do" id="addGprsForm" method="post">
 <input type="hidden" id="neighborid" name="neighborid" value="${neighborid }"/>
 <div style="padding:10px 0 10px 60px">
			<table>
	    		<tr>
	    			<td>SIM卡号：</td>
	    			<td><input class="easyui-textbox" type="text" name="gprstel" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>集中器地址：</td>
	    			<td><input class="easyui-textbox" type="text" name="gprsaddr" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>安装地址:</td>
	    			<td><input class="easyui-textbox" type="text" name="installAddr" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>使用协议：</td>
	    			<td><input class="easyui-textbox" type="text" name="gprsprotocol" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>抄表IP：</td>
	    			<td><input class="easyui-textbox" type="text" name="ip" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>抄表端口：</td>
	    			<td><input class="easyui-textbox" type="text" name="port" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>备注：</td>
	    			<td><input class="easyui-textbox" type="text" name="remark" data-options="required:true"/></td>
	    		</tr>
	    	</table>
		</div>
		 <div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
		</div>
	</form>
</body>
</html>