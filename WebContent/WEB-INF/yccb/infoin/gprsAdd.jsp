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
					title:"添加集中器",
					msg:"添加成功",
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
	    			<td>SIM卡号：</td>
	    			<td><input class="easyui-textbox" type="text" name="gprstel" id="gprstel" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>集中器地址：</td>
	    			<td><input class="easyui-textbox" type="text" name="gprsaddr" id="gprsaddr" 
						data-options="required:true,novalidate:true,onChange:checkGPRSAddr,invalidMessage:'集中器已存在！'" validType="nonValidate[]"/></td>
	    		</tr>
	    		<tr>
	    			<td>安装地址:</td>
	    			<td><input class="easyui-textbox" type="text" name="installAddr" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>使用协议：</td>
	    			<td>
	    				<select class="easyui-combobox" name="gprsprotocol" data-options="panelHeight:'auto'" style="width: 80px">
							<option value="1">自主协议</option>
							<option value="2" selected="selected">188协议</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>监听IP：</td>
	    			<td><input class="easyui-textbox" type="text" id="ip" name="ip" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>监听端口：</td>
	    			<td><input class="easyui-textbox" type="text" id="port" name="port" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>备注：</td>
	    			<td><input class="easyui-textbox" type="text" name="remark"/></td>
	    		</tr>
	    	</table>
		</div>
		 <div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
		</div>
	</form>
</body>
</html>