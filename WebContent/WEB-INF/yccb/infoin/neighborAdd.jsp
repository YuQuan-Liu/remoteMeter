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
$(function(){
	
});

function submitForm(){
	if($('#addNeighborForm').form('validate')){
		$('#addNeighborForm').form('submit', {	
			success: function(data){	
				if(data=="succ"){
					$.messager.alert('添加小区','添加成功！','info',
						function(){
						 	$('#addNeighborWin').window('close');
						 	$('#neighborListTab').datagrid('reload');
						 });
				}
			}	
		});  
	}
}
//检查本自来水公司下此小区是否已经存在
function checkNbrName(){
alert(111);
}

function clearForm(){
	$('#addNeighborForm').form('clear');
}
</script>
	<form action="${path}/infoin/neighbor/add.do" id="addNeighborForm" method="post">
	 	<div style="padding:10px 0 10px 60px">
			<table>
				<tr>
					<td>小区名：</td>
					<td><input class="easyui-textbox" type="text" name="neighborName" data-options="required:true" onblur="checkNbrName()"/></td>
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
							<option value="1" >有</option>
							<option value="0" selected="selected">无</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>定时抄表时间：</td>
					<td><input class="easyui-textbox" type="text" name="timer" data-options="required:true" value="01 00 00"/></td>
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