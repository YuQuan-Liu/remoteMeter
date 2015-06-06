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
function submitForm(){
	var n_id = $("#neighborid").val();
	
	$('#updateGprsForm').form('submit', {	
		onSubmit:function(){
			return $('#updateGprsForm').form('validate');
		},
		success: function(data){
			if(data=="succ"){
				$.messager.show({
					title:"修改集中器",
					msg:"修改成功！",
					showType:'slide'
				});
				$('#updateGprsWin').window('close');
				$('#ddv-'+n_id).datagrid('reload');
			}
		}	
	}); 
}

$.extend($.fn.validatebox.defaults.rules, {
	nonValidate: {
        validator: function(value, param){
            return false;
        }
    }
});

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
function clearForm(){
	$('#updateGprsForm').form('clear');
}
</script>
<form action="${path}/infoin/neighbor/updateGprs.do" id="updateGprsForm" method="post">
 <input type="hidden" id="neighborid" name="neighborid" value="${gprs.neighbor.pid }"/>
 <input type="hidden" id="pid" name="pid" value="${gprs.pid }"/>
 <input type="hidden" id="valid" name="valid" value="${gprs.valid }"/>
 <%-- <input type="text" id="installTime" name="installTime" value="${gprs.installTime }"/> --%>
 <div style="padding:10px;">
			<table style="margin:0px auto;">
	    		<tr>
	    			<td>SIM卡号：</td>
	    			<td><input class="easyui-textbox" type="text" name="gprstel" data-options="required:true" value="${gprs.gprstel }"/></td>
	    		</tr>
	    		<tr>
	    			<td>集中器地址：</td>
	    			<td><input class="easyui-textbox" type="text" name="gprsaddr" id="gprsaddr" value="${gprs.gprsaddr }"
						data-options="required:true,novalidate:true,onChange:checkGPRSAddr,invalidMessage:'集中器已存在！'" validType="nonValidate[]"/></td>
	    		</tr>
	    		<tr>
	    			<td>安装地址:</td>
	    			<td><input class="easyui-textbox" type="text" name="installAddr" data-options="required:true" value="${gprs.installAddr }"/></td>
	    		</tr>
	    		<tr>
	    			<td>使用协议：</td>
	    			<td>
	    				<select class="easyui-combobox" name="gprsprotocol" data-options="panelHeight:'auto'" style="width: 80px">
							<option value="1" <c:if test="${gprs.gprsprotocol==1 }">selected="selected"</c:if>>自主协议</option>
							<option value="2" <c:if test="${gprs.gprsprotocol==2 }">selected="selected"</c:if>>188协议</option>
						</select>
	    			</td>
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