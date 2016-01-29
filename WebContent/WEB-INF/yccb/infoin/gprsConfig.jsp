<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配置集中器</title>
<%@include file="/commonjsp/top.jsp" %>
</head>
<body>
 <input type="hidden" id="gprsconfig_nid" name="neighborid" value="${gprs.neighbor.pid }"/>
 <input type="hidden" id="gprsconfig_pid" name="pid" value="${gprs.pid }"/>
 <c:if test="${gprs.gprsprotocol==2 }">
 	<div style="margin:10px;">
 		<label><fmt:message key='g.addr'/>：</label><span>${gprs.gprsaddr }</span>
 		<select class="easyui-combobox" id="gprsslave" name="gprsslave" style="width:100px" data-options="panelHeight:'200'">
 			<option value="0">请选择底层类型</option>
 			<option value="1">MBUS表</option>
 			<option value="2">485表</option>
 			<option value="3">采集器</option>
 		</select>
 		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="configgprsslave()" >设置底层类型</a>
 		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="querygprsslave()" >查询底层类型</a>
 	</div>
 	
 	<table id="gprsmetersTbl" style="width:100%;height:500px;"></table>
 </c:if>
 <c:if test="${gprs.gprsprotocol!=2 }">
 	<div style="margin:10px;">
 		<label>暂不支持远程配置</label>
 	</div>
 </c:if>
<script type="text/javascript">
$(function(){
	$("#gprsmetersTbl").datagrid({
		striped:true,
		fitColumns:true,
		method:'post',
// 		url:"${path}/infoin/neighbor/listgprsmeters",
// 		queryParams:{
// 			pid:"${gprs.pid}"
// 		},
		loadMsg:'<fmt:message key="main.loading"/>',
		rownumbers:true,
		columns:[[
		          {field:'pid',title:'ID',width:60,checkbox:true},
		          {field:'collectorAddr',title:'<fmt:message key='m.caddr'/>',width:80},
		          {field:'meterAddr',title:'<fmt:message key='m.maddr'/>',width:80},
		          {field:'remark',title:'<fmt:message key='common.remark'/>',width:80}
		      ]],
		toolbar: [{ 
			text: '<fmt:message key="g.deleteall"/>', 
			iconCls: 'icon-add', 
			handler: deleteAllmeters
		}, '-', {
			text: '<fmt:message key="g.addcjq"/>', 
			iconCls: 'icon-edit', 
			handler: addcjq
		}, '-', {
			text: '<fmt:message key="g.addmeter"/>', 
			iconCls: 'icon-edit', 
			handler: addmeter
		}, '-', { 
			text: '<fmt:message key="g.deletemeter"/>', 
			iconCls: 'icon-remove',
			handler:  deletemeter
		}, '-', { 
			text: '<fmt:message key="g.readdata"/>', 
			iconCls: 'icon-remove',
			handler:  readJZQdata
		}]
	});
});
function deleteAllmeters(){
	
}
function addcjq(){
	
}
function addmeter(){
	
}
function deletemeter(){
	
}
function readJZQdata(){
	
}
</script>
</body>
</html>