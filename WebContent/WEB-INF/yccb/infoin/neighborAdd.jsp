<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加小区</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">

function submitForm(){
	$('#addNeighborForm').form('submit', {	
		onSubmit:function(){
			
			var switch_ = $("#timerSwitch").combobox("getValue");
			var timer = $("#timer").textbox("getValue");
			if(switch_ != 0){
				//判断timer 
				if(timer == ""){
					$.messager.show({
						title:"Info",
						msg:"<fmt:message key='n.timernull'/>",
						showType:'slide'
					});
					return false;
				}else{
					var d_h = timer.split(/[ ]/);
					if(d_h.length != 2){
						$.messager.show({
							title:"Info",
							msg:"<fmt:message key='n.timerstyle'/>",
							showType:'slide'
						});
						return false;
					}else{
						var d = d_h[0];
						var h = d_h[1];
						if((d >= 1) && (d <= 31) && (h >=0) && (h<=23)){
							//is good
						}else{
							$.messager.show({
								title:"Info",
								msg:"<fmt:message key='n.timerstyle'/>",
								showType:'slide'
							});
							return false;
						}
					}
				}
			}
			
			return $('#addNeighborForm').form('validate');
		},
		success: function(data){
			if(data=="succ"){
				$.messager.show({
					title:"Info",
					msg:"<fmt:message key='common.addok'/>",
					showType:'slide'
				});
				$('#addNeighborWin').window('close');
				$('#neighborListTab').datagrid('reload');
			}
		}	
	}); 
}
//检查本自来水公司下此小区是否已经存在
function checkNbrName(){
	var name = $("#neighborName").textbox("getValue");
	
	$.ajax({
		type:"POST",
		url:"${path}/infoin/neighbor/searchn_name.do",
		data:{
			n_name:name
		},
		dataType:"json",
		success:function(data){
			if(data == 'true'){
				$("#neighborName").textbox("enableValidation");
			}else{
				$("#neighborName").textbox("disableValidation");
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
function timeswitchChange(){
	var switch_ = $("#timerSwitch").combobox("getValue");
	if(switch_ == 0){
		$("#timer").textbox("disable");
	}else{
		$("#timer").textbox("enable");
	}
}
</script>
	<form action="${path}/infoin/neighbor/add.do" id="addNeighborForm" method="post">
	 	<div style="padding:10px;">
			<table style="margin:0px auto;">
				<tr>
					<td><fmt:message key='n.name'/>：</td>
<!-- 					<td><input class="easyui-textbox" type="text" name="neighborName" id="neighborName" data-options="required:true,delay:2500" validType="checkNbrName['#neighborName']"/></td> -->
						<td><input class="easyui-textbox" type="text" name="neighborName" id="neighborName" 
						data-options="required:true,novalidate:true,onChange:checkNbrName,invalidMessage:'<fmt:message key='n.exist'/>'" validType="nonValidate[]"/></td>
				</tr>
				<tr>
					<td><fmt:message key='common.addr'/>：</td>
					<td><input class="easyui-textbox" type="text" name="neighborAddr" data-options="required:true"/></td>
				</tr>
				<tr>
					<td><fmt:message key='n.main'/>：</td>
					<td>
					<select class="easyui-combobox" name="mainMeter" data-options="panelHeight:'auto'" style="width: 80px">
							<option value="1" selected="selected"><fmt:message key='common.have'/></option>
							<option value="0"><fmt:message key='common.nothave'/></option>
					</select>
					 </td>
				</tr>
				<tr>
					<td><fmt:message key='n.switch'/>：</td>
					<td>
						<select class="easyui-combobox" name="timerSwitch" id="timerSwitch" data-options="panelHeight:'auto',onSelect:timeswitchChange" style="width: 80px">
							<option value="1" ><fmt:message key='common.open'/></option>
							<option value="0" selected="selected"><fmt:message key='common.close'/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td><fmt:message key='n.timer'/>：</td>
					<td><input class="easyui-textbox" type="text" name="timer" id="timer" data-options="required:true,disabled:true" value="01 00"/></td>
				</tr>
				<tr>
					<td><fmt:message key='n.ip'/>：</td>
					<td><input class="easyui-textbox" type="text" name="ip" data-options="required:true" value=""/></td>
				</tr>
				<tr>
					<td><fmt:message key='common.remark'/>：</td>
					<td>
					<input class="easyui-textbox" name="remark" data-options="multiline:true" style="height:60px">
					</td>
				</tr>
			</table>
		</div>
		 <div style="text-align:center;padding:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()"><fmt:message key='common.submit'/></a>
		</div>
	</form>
</body>
</html>