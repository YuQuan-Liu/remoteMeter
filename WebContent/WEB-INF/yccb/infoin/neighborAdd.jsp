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
			return $('#addNeighborForm').form('validate');
		},
		success: function(data){
			if(data=="succ"){
				$.messager.show({
					title:"添加小区",
					msg:"添加成功",
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

function clearForm(){
	$('#addNeighborForm').form('clear');
}
</script>
	<form action="${path}/infoin/neighbor/add.do" id="addNeighborForm" method="post">
	 	<div style="padding:10px;">
			<table style="margin:0px auto;">
				<tr>
					<td>小区名：</td>
<!-- 					<td><input class="easyui-textbox" type="text" name="neighborName" id="neighborName" data-options="required:true,delay:2500" validType="checkNbrName['#neighborName']"/></td> -->
						<td><input class="easyui-textbox" type="text" name="neighborName" id="neighborName" 
						data-options="required:true,novalidate:true,onChange:checkNbrName,invalidMessage:'小区已存在！'" validType="nonValidate[]"/></td>
				</tr>
				<tr>
					<td>地址：</td>
					<td><input class="easyui-textbox" type="text" name="neighborAddr" data-options="required:true"/></td>
				</tr>
				<tr>
					<td>有无管理表：</td>
					<td>
					<select class="easyui-combobox" name="mainMeter" data-options="panelHeight:'auto'" style="width: 80px">
							<option value="1" selected="selected">有</option>
							<option value="0">无</option>
					</select>
					 </td>
				</tr>
				<tr>
					<td>定时抄表开关：</td>
					<td>
						<select class="easyui-combobox" name="timerSwitch" data-options="panelHeight:'auto'" style="width: 80px">
							<option value="1" >开</option>
							<option value="0" selected="selected">关</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>定时抄表时间：</td>
					<td><input class="easyui-textbox" type="text" name="timer" data-options="required:true" value="01 00 00"/></td>
				</tr>
				<tr>
					<td>抄表IP：</td>
					<td><input class="easyui-textbox" type="text" name="ip" data-options="required:true" value=""/></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td>
					<input class="easyui-textbox" name="remark" data-options="multiline:true" style="height:60px">
					</td>
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