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

$.extend($.fn.validatebox.defaults.rules, {
	nonValidate: {
        validator: function(value, param){
            return false;
        }
    }
});

function submitForm(){
	
	$('#addGprsForm').form('submit', {
		onSubmit:function(){
			return $('#addGprsForm').form('validate');
		},
		success: function(data){
			if(data=="succ"){
				$.messager.show({
					title:"Info",
					msg:"<fmt:message key='common.addok'/>",
					showType:'slide'
				});
				$('#addGprsWin').window('close');
				$('#ddv-${neighborid }').datagrid('reload');
			}
		}	
	});
}

//检查集中器的地址  是否已经存在
function checkGPRSAddr(){
	var addr = $("#gprsaddr").textbox("getValue");
	
	$.ajax({
		type:"POST",
		url:"${path}/infoin/neighbor/searchgprs_addr.do",
		data:{
			gprsaddr:addr
		},
		dataType:"json",
		success:function(data){
			if(data == 'true'){
				$("#gprsaddr").textbox("enableValidation");
			}else{
				$("#gprsaddr").textbox("disableValidation");
			}
		}
	});
}
</script>
<form action="${path}/infoin/neighbor/addGprs.do" id="addGprsForm" method="post">
 <input type="hidden" id="neighborid" name="neighborid" value="${neighborid }"/>
 <div style="padding:10px;">
			<table style="margin:0px auto">
	    		<tr>
	    			<td><fmt:message key='g.sim'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="gprstel" id="gprstel" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='g.addr'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="gprsaddr" id="gprsaddr" 
						data-options="required:true,novalidate:true,onChange:checkGPRSAddr,invalidMessage:'集中器已存在！'" validType="nonValidate[]"/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='g.installaddr'/>:</td>
	    			<td><input class="easyui-textbox" type="text" name="installAddr" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='g.protocol'/>：</td>
	    			<td>
	    				<select class="easyui-combobox" name="gprsprotocol" data-options="panelHeight:'auto'" style="width: 80px">
							<option value="1"><fmt:message key='g.hdeg'/></option>
							<option value="2" selected="selected"><fmt:message key='g.188'/></option>
							<option value="3"><fmt:message key='g.hdeg'/>2</option>
							<option value="4">D10</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='g.ip'/>：</td>
	    			<td><input class="easyui-textbox" type="text" id="ip" name="ip" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='g.port'/>：</td>
	    			<td><input class="easyui-textbox" type="text" id="port" name="port" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key='common.remark'/>：</td>
	    			<td><input class="easyui-textbox" type="text" name="remark"/></td>
	    		</tr>
	    	</table>
		</div>
		 <div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()"><fmt:message key='common.submit'/></a>
		</div>
	</form>
</body>
</html>