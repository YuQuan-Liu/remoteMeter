<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>片区详情</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
<script type="text/javascript">
$(function(){
	$('#neibourListTab').datagrid({
	    url:'${path}/admin/dep/nbrTabContent.do?depId=${dep.pid}',
	    striped:true,
	    fit:false,
	    rownumbers:true,
	    border:false,
	    autoRowHeight:false,
	    rowStyler: function(index,row){
				return 'height:30px;';
			},
	    columns:[[
	        {field:'pid',title:'ID',width:50},   
	        {field:'neighborName',title:'小区名',width:100},   
	        {field:'neighborAddr',title:'地址',width:100},
			{field:'timer',title:'抄表时间',width:100,align:'center'},
			{field:'remark',title:'备注',width:100,halign:'center',align:'left'}
	    ]],
	});
})

function TFFormatter(val,row){
	if(val=="1")
	return "有";
	if(val=="0")
	return "无";
}
function closeWin(){
	$('#depDetailWin').window('close');
}
</script>
		<div style="padding:10px 0 10px 60px">
	    	<input type="hidden" name="valid" value="1"/>
	    	<table>
	    		<tr>
	    			<td>片区名：</td>
	    			<td><input class="easyui-textbox" type="text" name="departmentName" data-options="required:true" readonly="readonly" value="${dep.departmentName }"/></td>
	    			<td>备注：</td>
	    			<td>
	    			<input class="easyui-textbox" name="remark" type="text" readonly="readonly" value="${dep.remark }"/>
	    			</td>
	    		</tr>
	    	</table>
	    </div>
	     <div style="padding:10px 10px 0 10px">
		<table id="neibourListTab"></table>
	  </div>
	  
	   <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWin()"><fmt:message key='main.mm.close'/></a>
	    </div>
</body>
</html>