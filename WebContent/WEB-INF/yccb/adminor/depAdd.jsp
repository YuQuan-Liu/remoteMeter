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
	$('#allNbrTab').datagrid({
	    url:'${path}/admin/dep/listallNbr.do',
	    //fit:true,
	    queryParams:{},
	    rownumbers:true,
	    autoRowHeight:false,
	    rowStyler: function(index,row){
			return 'height:30px;';
		},
	    columns:[[
	        {field:'nid',title:'ID',width:100,checkbox:true},   
	        {field:'name',title:'<fmt:message key='common.neighborName'/>',width:100},
	        {field:'addr',title:'<fmt:message key='common.addr'/>',width:100}
	    ]]
	});
});
function submitForm(){
	$('#adddepbtn').linkbutton('disable');
	var nbr_ids = [];
	var rows = $('#allNbrTab').datagrid('getSelections');
	
	if($('#depAddForm').form('validate')){
		var name = $("#departmentName").textbox("getValue");
		var remark = $("#remark").textbox("getValue");
		for(var i=0; i<rows.length; i++){
			var row = rows[i];
			nbr_ids.push(row.nid);
		}
		if(nbr_ids.length != 0){
			$.ajax({
				type:"POST",
				url:"${path}/admin/dep/add.do",
				dataType:"json",  
		        traditional :true,
				data:{
					name:name,
					remark:remark,
					'nbr_ids':nbr_ids
				},
				success:function(data){
					if(data == "succ"){
						$('#depListTab').datagrid('reload');
						$('#depAddWin').window('close');
					}
				}
			});
		}else{
			$.messager.alert('Info','<fmt:message key='common.chooserecord'/>');
		}
	}else{
		$.messager.alert('Info','<fmt:message key='common.enterinfo'/>');
	}
	$('#adddepbtn').linkbutton('enable');
}

//检查本自来水公司下此片区名是否已经存在
function checkDepName(){
	var name = $("#departmentName").textbox("getValue");
	
	$.ajax({
		type:"POST",
		url:"${path}/admin/dep/checkdepname.do",
		data:{
			name:name
		},
		dataType:"json",
		success:function(data){
			if(data == 'true'){
				$("#departmentName").textbox("enableValidation");
			}else{
				$("#departmentName").textbox("disableValidation");
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
</script>
	<form id="depAddForm" method="post" action="${path}/admin/dep/add.do">
		<div style="padding: 10px">
			<input type="hidden" name="valid" value="1" />
			<table style="margin:0px auto;">
				<tr>
					<td><fmt:message key='areas'/>：</td>
					<td><input class="easyui-textbox" type="text" name="departmentName" id="departmentName" 
					data-options="required:true,novalidate:true,onChange:checkDepName,invalidMessage:'<fmt:message key='dep.exist'/>'" validType="nonValidate[]" /></td>
					<td><fmt:message key='common.remark'/>：</td>
					<td><input class="easyui-textbox" name="remark" id="remark" type="text" />
					</td>
				</tr>
			</table>
		</div>
	</form>

	<div style="text-align: center; padding: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" id="adddepbtn"><fmt:message key='common.submit'/></a>
	</div>
	<table id="allNbrTab" style="width:500px;height:380px;"></table>
</body>
</html>