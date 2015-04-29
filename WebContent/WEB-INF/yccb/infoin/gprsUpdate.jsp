<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改集中器</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
$(function(){
});
function submitForm(){
	if($('#updateGprsForm').form('validate')){
		//一个小区下的集中器的IP和端口必须相同
		if($("#ip").val() == $("#port").val()){
			$('#updateGprsForm').form('submit', {   
			    success: function(data){   
			       if(data=="succ"){
			    	   $.messager.alert('修改集中器','修改成功！','info',
							function(){
			    		   		$('#updateGprsWin').window('close');
							 	$('#neighborListTab').datagrid('reload');
							 });
			       }
			    }   
			});  
		}else{
			$.messager.alert('提示','集中器的IP和端口必须相同');   
		}
		
	}
}
function clearForm(){
	$('#updateGprsForm').form('clear');
}
</script>
<form action="${path}/infoin/neighbor/updateGprs.do" id="updateGprsForm" method="post">
 <input type="hidden" id="neighborid" name="neighborid" value="${gprs.neighbor.pid }"/>
 <input type="hidden" id="pid" name="pid" value="${gprs.pid }"/>
 <input type="hidden" id="valid" name="valid" value="${gprs.valid }"/>
 <%-- <input type="text" id="installTime" name="installTime" value="${gprs.installTime }"/> --%>
 <div style="padding:10px 0 10px 60px">
			<table>
	    		<tr>
	    			<td>SIM卡号：</td>
	    			<td><input class="easyui-textbox" type="text" name="gprstel" data-options="required:true" value="${gprs.gprstel }"/></td>
	    		</tr>
	    		<tr>
	    			<td>集中器地址：</td>
	    			<td><input class="easyui-textbox" type="text" name="gprsaddr" data-options="required:true" value="${gprs.gprsaddr }"/></td>
	    		</tr>
	    		<tr>
	    			<td>安装地址:</td>
	    			<td><input class="easyui-textbox" type="text" name="installAddr" data-options="required:true" value="${gprs.installAddr }"/></td>
	    		</tr>
	    		<tr>
	    			<td>使用协议：</td>
	    			<td><input class="easyui-textbox" type="text" name="gprsprotocol" data-options="required:true" value="${gprs.gprsprotocol }"/></td>
	    		</tr>
	    		<tr>
	    			<td>抄表IP：</td>
	    			<td><input class="easyui-textbox" type="text" id="ip" name="ip" data-options="required:true" value="${gprs.ip }"/></td>
	    		</tr>
	    		<tr>
	    			<td>抄表端口：</td>
	    			<td><input class="easyui-textbox" type="text" id="port" name="port" data-options="required:true" value="${gprs.port }"/></td>
	    		</tr>
	    		<tr>
	    			<td>备注：</td>
	    			<td><input class="easyui-textbox" type="text" name="remark" value="${gprs.remark }"/></td>
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